package itmo.lab7.server.Service;


import itmo.lab7.model.Ticket;

import java.nio.channels.SocketChannel;

public class ArgsForCommands {
	public Ticket ticket;
	public int key;
	public String login;

	public String fileName;
	public SocketChannel socketChannel;

	public ArgsForCommands(int key, Ticket ticket, String login) {
		this.key = key;
		this.ticket = ticket;
		this.login = login;
	}
	public ArgsForCommands(int key,String login) {
		this.key = key;
		this.login = login;
	}
	public ArgsForCommands(String login) {
		this.login = login;
	}

	public ArgsForCommands(String fileName,String login) {
		this.fileName = fileName;
		this.login = login;
	}

	public ArgsForCommands(SocketChannel socketChannel,String login) {
		this.socketChannel = socketChannel;
		this.login = login;
	}



}
