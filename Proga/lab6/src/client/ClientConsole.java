package client;

import common.CommandInfo;
import common.Request;
import common.Response;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Scanner;


public class ClientConsole {
	private static final String SERVER_HOST = "server";
	private static final int SERVER_PORT = 12345;
	private Map<String, CommandInfo> serverCommands;



	private void syncCommands(SocketChannel client) throws Exception {
		Request request = new Request("get_commands", null, null);
		byte[] requestBytes = serializeRequest(request);
		ByteBuffer buffer = ByteBuffer.allocate(4 + requestBytes.length);
		buffer.putInt(requestBytes.length);
		buffer.put(requestBytes);
		buffer.flip();
		while (buffer.hasRemaining()) {
			client.write(buffer);
		}


		Response response = readResponse(client);
		this.serverCommands = (Map<String, CommandInfo>) response.getData();
		System.out.println("Commands synchronized with server");

	}

	public static void main(String[] args) {
		ClientConsole client = new ClientConsole();
		client.run();
	}

	public void run(){
		try (SocketChannel client = SocketChannel.open(new InetSocketAddress(SERVER_HOST, SERVER_PORT))) {
			syncCommands(client);
			clientProcess(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clientProcess(SocketChannel client) throws IOException, ClassNotFoundException {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			process(client, scanner,false);
		}

	}

	private Response deserializeResponse(byte[] bytes) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
		return (Response) ois.readObject();
	}

	private byte[] serializeRequest(Request request) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(request);
		oos.flush();
		return baos.toByteArray();
	}
	private Response readResponse(SocketChannel client) throws IOException, ClassNotFoundException {

		ByteBuffer lengthBuf = ByteBuffer.allocate(4);
		while (lengthBuf.hasRemaining()) {
			if (client.read(lengthBuf) == -1) throw new IOException("Server closed connection");
		}
		lengthBuf.flip();
		int length = lengthBuf.getInt();

		ByteBuffer bodyBuf = ByteBuffer.allocate(length);
		while (bodyBuf.hasRemaining()) {
			if (client.read(bodyBuf) == -1) throw new IOException("Connection lost during reading");
		}

		bodyBuf.flip();
		byte[] resBytes = new byte[bodyBuf.remaining()];
		bodyBuf.get(resBytes);
		return deserializeResponse(resBytes);
	}

	public void process(SocketChannel client, Scanner scanner, boolean isScript) throws IOException, ClassNotFoundException {
		String[] commandAndKey = scanner.nextLine().trim().split("\\s+");
		String cmdName = commandAndKey[0];

		String key = (commandAndKey.length > 1) ? (commandAndKey[1]) : null;

		if (cmdName.equals("execute_script")) {
			if (key == null) {
				System.out.println("U must send path to file");
				return;
			}
			File scriptFile = new File(key);
			try (Scanner fileScanner = new Scanner(scriptFile)) {
				System.out.println("Executing script: " + key);
				while (fileScanner.hasNextLine()) {
					process(client, fileScanner,true);
				}
				System.out.println("Script " + key + " finished");
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
			}
			return;
		}

		Object ticketArg = null;
		CommandInfo info = serverCommands.get(cmdName);

		if (info != null) {
			if (info.needObject()) {
				System.out.println("Create Ticket: ");
				CreateTicket tc = new CreateTicket(scanner,isScript);
				ticketArg = tc.getTicket();
			}
		} else if (!cmdName.equals("get_commands")) {
			System.out.println("Haven't this command");
			return;
		}

		Request request = new Request(cmdName, key, ticketArg);

		byte[] requestBytes = serializeRequest(request);
		ByteBuffer buffer = ByteBuffer.allocate(4 + requestBytes.length);
		buffer.putInt(requestBytes.length);
		buffer.put(requestBytes);
		buffer.flip();
		while (buffer.hasRemaining()) {
			client.write(buffer);
		}
		Response response = readResponse(client);
		System.out.println(response.getMessage());
		if(cmdName.equals("exit")){
			client.close();
			System.exit(0);
		}
	}





}
