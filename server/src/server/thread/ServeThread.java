package server.thread;

import java.io.*;
import java.net.*;

import transfer.Coordinates;
import logger.Logger;

public class ServeThread extends Thread {

	private static final String PASSWORD = "pass";
	private Socket socket;
	private ObjectInputStream in = null;
	private ObjectOutputStream oos = null;

	public ServeThread(Socket s) throws IOException {
		Logger.info("server>>> new client connected to server");
		socket = s;
		oos = new ObjectOutputStream(socket.getOutputStream());
		oos.flush();
		in = new ObjectInputStream(socket.getInputStream());
		start();
	}

	public void update(){
		try {
			Coordinates c = (Coordinates) (in.readObject());
			System.out.println(c.x + "x" + c.y);
			c.x++;
			c.y++;
			oos.writeObject(c);
			//oos.flush();
		} catch (IOException e) {
			Logger.error("server>>> IO Exception");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true){}
	}
}
