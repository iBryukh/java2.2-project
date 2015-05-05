package client;

import java.io.*;
import java.net.*;
import java.util.*;

import multiplayer.transfer.*;

public class Client {
	private Socket socket;
	private ObjectInputStream objectIS;
	private ObjectOutputStream objectOS;
	private boolean connected;

	public Client(InetAddress addr, int port) {
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

	public boolean isConnected(){
		return connected;
	}
	
	public void disconnect() {
		try {
			if (socket != null)
				socket.close();
			if (objectIS != null)
				objectIS.close();
			if (objectOS != null)
				objectOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public static void main(String[] args) throws UnknownHostException{
		Client c = new Client(InetAddress.getByName(null), 7000);
		Coordinates q = new Coordinates(1, 1);
		while(c.isConnected()){
			c.send(q);
			q = (Coordinates) c.get().get(0);
			System.out.println(q.x);
		}
	}
}
