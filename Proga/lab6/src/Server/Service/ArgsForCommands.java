package server.Service;


import model.Ticket;import java.net.Socket;import java.nio.channels.SocketChannel;

public class ArgsForCommands {
	public Ticket ticket;
	public int key;

	public String fileName;
	public SocketChannel socketChannel;

	public ArgsForCommands(int key, Ticket ticket) {
		this.key = key;
		this.ticket = ticket;
	}
	public ArgsForCommands(int key) {
		this.key = key;
	}

	public ArgsForCommands(String fileName) {
		this.fileName = fileName;
	}

	public ArgsForCommands(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}



}
