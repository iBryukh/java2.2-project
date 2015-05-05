package client;

import java.io.*;
import java.net.*;
import java.util.*;

import multiplayer.transfer.*;

public class Client {
	public static final int DEFAULT_PORT = 7000;
	
	private Socket socket;
	private ObjectInputStream objectIS;
	private ObjectOutputStream objectOS;
	private boolean connected;

	public Client(InetAddress addr, int port) {
		connect(addr, port);
	}

	public Client() throws UnknownHostException{
		this(InetAddress.getByName(null), DEFAULT_PORT);
	}
	
	public void connect(InetAddress addr, int port) {
		if (!isConnected()) {
			try {
				socket = new Socket(addr, port);
				objectOS = new ObjectOutputStream(socket.getOutputStream());
				objectOS.flush();
				objectIS = new ObjectInputStream(socket.getInputStream());
				connected = true;
			} catch (IOException e) {
				connected = false;
			}
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public void disconnect() {
		try {
			if (socket != null)
				socket.close();
			if (objectOS != null)
				objectOS.close();
			if (objectIS != null)
				objectIS.close();
		} catch (IOException e) {
		}
		connected = false;
	}

	public void send(TransferData td) {
		try {
			objectOS.writeObject(td);
		} catch (IOException e) {
			disconnect();
		}
	}

	public <Type extends TransferData> ArrayList<Type> get() {
		try {
			return (ArrayList<Type>) (objectIS.readObject());
		} catch (ClassNotFoundException | IOException e) {
			disconnect();
		}
		return null;
	}

	public static void main(String[] args) throws UnknownHostException {
		Client c = new Client();
		Coordinates q = new Coordinates(1, 1);
		while (c.isConnected()) {
			c.send(q);
			// q = (Coordinates) c.get().get(0);
			// System.out.println(q.x);
			
			System.out.println(1);
		}
	}
}
