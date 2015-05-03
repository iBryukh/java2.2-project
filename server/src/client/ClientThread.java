package client;

import java.net.*;
import java.io.*;

import logger.Logger;
import server.Server;
import transfer.Coordinates;

class ClientThread extends Thread {

	private Socket socket;
	private ObjectInputStream oin = null;
	private ObjectOutputStream out = null;
	private static int counter = 0;
	private int id = counter++;

	public ClientThread(InetAddress addr) {
		try {
			Logger.info("client>>> clien with id: " + id + " is connecting..");
			socket = new Socket(addr, Server.MAIN_SERVER_PORT);
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			oin = new ObjectInputStream(socket.getInputStream());
			start();
		} catch (IOException e) {
			Logger.error("client>>> failed to connect to server");
		}
	}

	public void run() {
		try {
			out.writeObject(new Coordinates(10, 10));
			// out.flush();
			Coordinates c = (Coordinates) (oin.readObject());
			System.out.println(c.x + "x" + c.y);
			oin.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			// Logger.error("IO exception");
		} catch (ClassNotFoundException e) {
			Logger.error("clien>>> class not fould");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				Logger.error("client>>> failed to close clien socket");
			}
		}
	}
}