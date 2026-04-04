package server;


import common.CommandInfo;
import common.Request;
import common.Response;
import model.Ticket;

import org.apache.logging.log4j.LogManager;import org.apache.logging.log4j.Logger;import server.Commands.Command;
import server.Commands.TypeOfArgument;
import server.Commands.TypeOfSecondArgument;
import server.Service.ArgsForCommands;
import server.Service.CommandManager;import server.Service.TicketManager;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;



public class Server {
	private final Logger logger = LogManager.getLogger(Server.class);
	private final CommandManager commandManager = CommandManager.getInstance();
	private final TicketManager ticketManager = TicketManager.getInstance();
	private final InetAddress address;
	private final int PORT;
	private static final String fileName = "Data.xml";


	public Server(InetAddress address,int PORT){
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
		ticketManager.loadFromFile(fileName);
		startConsoleThread();
		try{
			try (var serverSocketChannel = ServerSocketChannel.open()) {
				serverSocketChannel.configureBlocking(false);
				var serverSocket = serverSocketChannel.socket();
				serverSocket.bind(new InetSocketAddress(address, PORT));
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
								readFromClient(client);
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
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private void readFromClient(SocketChannel client){
		ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
		try{
			int readByte = client.read(lengthBuffer);
			if(readByte == -1){
				logger.info("Client disconnected:{}",client.getRemoteAddress());
				client.close();
				return;
			}
			if (readByte < 4){
				while(lengthBuffer.position() < 4) {
					client.read(lengthBuffer);
				}
			}
			lengthBuffer.flip();
			int dataLength = lengthBuffer.getInt();
			ByteBuffer bodyBuf = ByteBuffer.allocate(dataLength);
			while (bodyBuf.hasRemaining()) {
				client.read(bodyBuf);
			}

			bodyBuf.flip();
			byte[] resBytes = new byte[bodyBuf.remaining()];
			bodyBuf.get(resBytes);
			Request request = deserializeRequest(resBytes);
			processing(request,client);



		} catch (Exception e) {
			logger.error(e);
		}
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

	public void processing(Request request, SocketChannel client) {
		try {
			if (request == null) return;

			String commandName = request.getCommandName();
			logger.info("Command accepted: {}", commandName);
			Response response;

			if (commandName.equalsIgnoreCase("get_commands")) {
				Map<String, CommandInfo> commandData = commandManager.getCommandsData();
				response = new Response("Command synchronized", commandData);
			} else {
				Command cmd = commandManager.getCommand(commandName);
				if (cmd == null) {
					response = new  Response("We haven't this command", null);
					sendResponse(client, response);
					return;
				}
				if(cmd.needsArgument()){
					TypeOfArgument tp =(TypeOfArgument) cmd;
					if (tp.typeOfArgument().equals("Integer") ) {
						try {
							Integer key = Integer.valueOf(request.getStringArg());
							if (cmd.needMoreThenOneArgument()) {
								TypeOfSecondArgument tp2 = (TypeOfSecondArgument) cmd;
								if(tp2.typeOfSecondArgument().equals("Ticket")){
									Ticket ticket = (Ticket) request.getObjectArg();
									ArgsForCommands args = new ArgsForCommands(key,ticket);
									cmd.setArgs(args);
								}
							} else {
								ArgsForCommands args = new ArgsForCommands(key);
								cmd.setArgs(args);
							}
						} catch (Exception e){
							response = new Response("Mistake in argue " + e.getMessage(), null);
							sendResponse(client,response);
							return;
						}
					} else if (tp.typeOfArgument().equals("fileName")) {
						String filenameFromPerson = request.getStringArg();
						if(commandManager.fileNames.contains(filenameFromPerson)) {
							System.out.println("Cyclic dependency in the file");
							return;
						}
						commandManager.fileNames.add(filenameFromPerson);
						ArgsForCommands args = new ArgsForCommands(filenameFromPerson);
						cmd.setArgs(args);
					}
				}
				String result = cmd.executeCommand();
				response = new Response(result, null);
			}
			sendResponse(client, response);
		} catch (Exception e) {
			System.out.println("Session was finished");
		}
	}

	private void sendResponse(SocketChannel client, Response response) throws IOException {
		byte[] responseBytes = serializeResponse(response);
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
					ticketManager.saveToFile(fileName);
				} else if (line.equalsIgnoreCase("exit")) {
					logger.info("Завершение работы сервера");
					ticketManager.saveToFile(fileName);
					System.exit(0);
				}
			}
		});
		consoleThread.setDaemon(true);
		consoleThread.start();
	}




}
