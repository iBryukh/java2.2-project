package client;

import java.io.*;
import java.net.*;
import java.util.*;

import multiplayer.transfer.*;

public class Client extends Thread{
	private Socket socket;
	private ObjectInputStream objectIS;
	private ObjectOutputStream objectOS;

	public Client(InetAddress addr, int port) {
		try {
			socket = new Socket(addr, port);
			objectOS = new ObjectOutputStream(socket.getOutputStream());
			objectOS.flush();
			objectIS = new ObjectInputStream(socket.getInputStream());
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		play();
	}
	
	public void send(TransferData td) {
		try {
			objectOS.writeObject(td);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public <Type extends TransferData> ArrayList<Type> get() {
		try {
			return (ArrayList<Type>) (objectIS.readObject());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void play() {
		Coordinates c = new Coordinates(1, 1);
		while (true) {
			send(c);
			c = (Coordinates) get().get(0);
			System.out.println(c.x);
		}
	}

	public static void main(String[] args) throws UnknownHostException {
		new Client(InetAddress.getByName(null), 7000);
		new Client(InetAddress.getByName(null), 7000);
	}
}
