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

	public Player(Socket s) {
		try {
			socket = s;
			objectOS = new ObjectOutputStream(socket.getOutputStream());
			objectOS.flush();
			objectIS = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(ArrayList<Player> players) {
		try {
			ArrayList<TransferData> list = new ArrayList<>();
			for (int i = 0; i < players.size(); ++i)
				list.add(players.get(i).td);
			objectOS.writeObject(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void get() {
		try {
			td = (TransferData) (objectIS.readObject());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		td.update();
	}
}