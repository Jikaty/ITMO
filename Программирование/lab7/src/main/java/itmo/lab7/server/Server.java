package itmo.lab7.server;


import itmo.lab7.common.CommandInfo;
import itmo.lab7.common.Request;
import itmo.lab7.common.Response;
import itmo.lab7.model.Ticket;
import itmo.lab7.server.Service.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import itmo.lab7.server.Commands.Command;
import itmo.lab7.server.Commands.TypeOfArgument;
import itmo.lab7.server.Commands.TypeOfSecondArgument;
import itmo.lab7.server.Service.ArgsForCommands;
import itmo.lab7.server.Service.CommandManager;
import itmo.lab7.server.Service.TicketManager;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


public class Server {
	private final Logger logger = LogManager.getLogger(Server.class);
	private final CommandManager commandManager = CommandManager.getInstance();
	private final TicketManager ticketManager = TicketManager.getInstance();
	private final DBManager dbManager = DBManager.getInstance();
	private final InetAddress address;
	private final int PORT;
	private final ExecutorService ioPool = Executors.newCachedThreadPool();
	private final ForkJoinPool processingPool = new ForkJoinPool();



	public Server(InetAddress address, int PORT){
		logger.debug("address:{} PORT:{}", address, PORT);
		this.PORT = PORT;
		this.address = address;
	}

	public static void main(String[] args){
		try {
			InetAddress address = InetAddress.getByName("0.0.0.0");
			Server server = new Server(address,12345);
			server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void startServer(){
		dbManager.createTables();
		dbManager.loadTickets(ticketManager);
		startConsoleThread();
		try{
			try (var serverSocketChannel = ServerSocketChannel.open()) {
				serverSocketChannel.configureBlocking(false);
				var serverSocket = serverSocketChannel.socket();
				serverSocket.bind(new InetSocketAddress(address, PORT));
				logger.info("Сервер запущен и ожидает подключений на порту {}", PORT);
				try (var selector = Selector.open()) {
					serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
					while (true) {
						selector.select();
						Set<SelectionKey> selectedKeys = selector.selectedKeys();
						Iterator<SelectionKey> iter = selectedKeys.iterator();
						while (iter.hasNext()) {
							SelectionKey key = iter.next();
							iter.remove();
							if (!key.isValid()) continue;
							if (key.isAcceptable()) {
								ServerSocketChannel server = (ServerSocketChannel) key.channel();
								connectionAccept(server,selector);
							}
							if (key.isReadable()) {
								SocketChannel client = (SocketChannel) key.channel();
								key.interestOps(0);
								readFromClient(client, key);
							}
						}
					}
				}

			}

		} catch (Exception e){
			logger.error(e);
		}
	}
	private void connectionAccept(ServerSocketChannel server, Selector selector){
		try {
			SocketChannel client = server.accept();
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ);
			logger.info("Установлено новое соединение с клиентом: {}", client.getRemoteAddress());
		} catch (IOException e) {
			logger.error("Ошибка при принятии соединения: ", e);
		}
	}

	private void readFromClient(SocketChannel client, SelectionKey key) {
		ioPool.submit(() -> {
			try {
				ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
				if (client.read(lengthBuffer) <= 0) return;
				lengthBuffer.flip();
				int length = lengthBuffer.getInt();
				ByteBuffer bodyBuffer = ByteBuffer.allocate(length);
				while (bodyBuffer.hasRemaining()) {
					client.read(bodyBuffer);
				}
				bodyBuffer.flip();
				byte[] data = new byte[bodyBuffer.remaining()];
				bodyBuffer.get(data);
				Request request = deserializeRequest(data);
				CompletableFuture
						.supplyAsync(() -> handleRequest(request), processingPool)
						.thenAcceptAsync(response -> {
							try {
								sendResponse(client, response);
							} catch (IOException e) {
								logger.error("Ошибка отправки " + e.getMessage());
							} finally {
								if (key.isValid()) {
									key.interestOps(SelectionKey.OP_READ);
									key.selector().wakeup();
								}
							}
						}, ioPool);

			} catch (Exception e) {
				logger.error("Ошибка при чтении из клиента: " + e.getMessage());
				key.cancel();
				try { client.close(); } catch (IOException ignored) {}
			}
		});
	}

	private Request deserializeRequest(byte[] bytes) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
		return (Request) ois.readObject();
	}

	private byte[] serializeResponse(Response response) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(response);
		oos.flush();
		return baos.toByteArray();
	}

	public String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-384");
			byte[] messageDigest = md.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (byte b : messageDigest) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Ошибка хэширования", e);
		}
	}

	public Response handleRequest(Request request) {
		try {
			if (request == null) return new Response("Пустой запрос", null);

			String commandName = request.getCommandName();
			String login = request.getLogin();
			String password = hashPassword(request.getPassword());
			boolean isRegistration = request.isRegistration();
			if (commandName.equalsIgnoreCase("check_auth")) {
				if (isRegistration) {
					boolean success = dbManager.registerUser(login, password);
					return new Response(success ? "Регистрация успешна" : "Этот логин уже занят.", null, success);
				} else {
					boolean success = dbManager.checkUser(login, password);
					return new Response(success ? "Вход выполнен" : "Неверный логин или пароль.", null, success);
				}
			}

			if (!commandName.equalsIgnoreCase("get_commands") && !dbManager.checkUser(login, password)) {
				return new Response("Ошибка авторизации", null, false);
			}

			if (commandName.equalsIgnoreCase("get_commands")) {
				return new Response("Command synchronized", commandManager.getCommandsData());
			}

			Command cmd = commandManager.getCommand(commandName);
			if (cmd == null) return new Response("Команда не найдена", null);

			if (cmd.needsArgument()) {
				TypeOfArgument tp = (TypeOfArgument) cmd;
				if (tp.typeOfArgument().equals("Integer")) {
					Integer key = Integer.valueOf(request.getStringArg());
					if (cmd.needMoreThenOneArgument()) {
						TypeOfSecondArgument tp2 = (TypeOfSecondArgument) cmd;
						if (tp2.typeOfSecondArgument().equals("Ticket")) {
							Ticket ticket = (Ticket) request.getObjectArg();
							cmd.setArgs(new ArgsForCommands(key, ticket, login));
						}
					} else {
						cmd.setArgs(new ArgsForCommands(key, login));
					}
				} else if (tp.typeOfArgument().equals("fileName")) {
					String filename = request.getStringArg();
					cmd.setArgs(new ArgsForCommands(filename));
				}
			} else{
				cmd.setArgs(new ArgsForCommands(login));
			}

			String result = cmd.executeCommand();
			return new Response(result, null);

		} catch (Exception e) {
			logger.error("Ошибка", e);
			return new Response("Ошибка сервера " + e.getMessage(), null);
		}
	}

	private void sendResponse(SocketChannel client, Response response) throws IOException {
		byte[] responseBytes = serializeResponse(response);
		logger.debug("Отправка ответа клиенту. Размер: {} байт", responseBytes.length);
		int length = responseBytes.length;
		ByteBuffer responseBuffer = ByteBuffer.allocate(4+length);
		responseBuffer.putInt(length);
		responseBuffer.put(responseBytes);
		responseBuffer.flip();
		while(responseBuffer.hasRemaining()) {
			client.write(responseBuffer);
		}
	}

	private void startConsoleThread() {
		Thread consoleThread = new Thread(() -> {
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.equalsIgnoreCase("save")) {
				} else if (line.equalsIgnoreCase("exit")) {
					logger.info("Завершение работы сервера");
					System.exit(0);
				}
			}
		});
		consoleThread.setDaemon(true);
		consoleThread.start();
	}

}
