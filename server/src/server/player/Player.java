package server.player;

import java.io.*;
import java.net.*;
import java.util.*;

import multiplayer.transfer.*;

public class Player {
	private Socket socket;
	private ObjectInputStream objectIS;
	private ObjectOutputStream objectOS;
	private TransferData td;
	private boolean connected;

	public Player(Socket s) {
		try {
			socket = s;
			objectOS = new ObjectOutputStream(socket.getOutputStream());
			objectOS.flush();
			objectIS = new ObjectInputStream(socket.getInputStream());
			connected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected(){
		return connected;
	}
	
	public void disconnect(){
		try {
			if (socket != null && !socket.isClosed())
				socket.close();
			if (objectOS != null)
				objectOS.close();
			if (objectIS != null)
				objectIS.close();
			connected = false;
		} catch (IOException e) { }
	}
	
	public void send(ArrayList<Player> players) {
		try {
			
			ArrayList<TransferData> list = new ArrayList<>();
			for (int i = 0; i < players.size(); ++i)
				if(this != players.get(i))
					list.add(players.get(i).td);
			//System.out.println("size: "+list.size());
			objectOS.writeObject(list);
		} catch (IOException e) {
			disconnect();
		}
	}

	public void get() {
		try {
			td = (TransferData) (objectIS.readObject());
		} catch (IOException | ClassNotFoundException e) {
			disconnect();
		}
	}

	public void update() {
		td.update();
	}
}