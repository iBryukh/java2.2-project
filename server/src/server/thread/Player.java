package server.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import transfer.Coordinates;

public class Player extends Thread{
	
	private ObjectOutputStream objectOS;
	private ObjectInputStream objectIS;
	private Socket socket;
	private final int ID;
	private static int freeID = 0;
	
	
	public Player(Socket socket){
		this.socket = socket;
		this.ID = ++freeID;
	}

	public void update(){
		try {
			Coordinates coord = (Coordinates) objectIS.readObject();
			coord.y++;
			coord.x++;
			objectOS.writeObject(coord);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			this.objectIS = new ObjectInputStream(socket.getInputStream());
			this.objectOS = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("run Player");
		update();
		while(true){}
	}
}
