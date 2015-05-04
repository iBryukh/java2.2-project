package client;

import java.io.*;
import java.net.*;

import logger.Logger;
import server.Server;
import transfer.Coordinates;
import transfer.TransferData;

public class Client {
	private Socket socket;
	private ObjectInputStream objectIS;
	private ObjectOutputStream objectOS;

	public Client(InetAddress addr) {
		try {
			socket = new Socket(addr, Server.MAIN_SERVER_PORT);
			objectOS = new ObjectOutputStream(socket.getOutputStream());
			objectOS.flush();
			objectIS = new ObjectInputStream(socket.getInputStream());
			play();
		} catch (IOException e) {
			Logger.error("client>>> failed to connect to server");
		}
	}

	public void getTable() {
		try {
			objectOS.writeObject(new Coordinates(10, 10));
			Coordinates c = (Coordinates) (objectIS.readObject());
			System.out.println(c.x + "x" + c.y);
		} catch (IOException e) {
			// Logger.error("IO exception");
		} catch (ClassNotFoundException e) {
			Logger.error("clien>>> class not fould");
		}
	}

	public void send(TransferData td) {
		try {
			objectOS.writeObject(td);
		} catch (IOException e) {
		}
	}

	public <Type extends TransferData> Type get() {
		try {
			return (Type) (objectIS.readObject());
		} catch (IOException e) {
			// Logger.error("IO exception");
		} catch (ClassNotFoundException e) {
			Logger.error("clien>>> class not fould");
		}
		return null;
	}

	public void play() {
		Coordinates c = new Coordinates(1, 1);
		while (true) {
			send(c);
			c = get();
			System.out.println(c.x);
		}
	}

	public static void main(String[] args) throws UnknownHostException {
		new Client(InetAddress.getByName(null));
	}
}