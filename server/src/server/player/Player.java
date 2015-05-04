package server.player;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import transfer.Coordinates;
import transfer.TransferData;
import logger.Logger;

public class Player {
	private Socket socket;
	private ObjectInputStream objectIS;
	private ObjectOutputStream objectOS;

	public Player(Socket s) {
		try {
			socket = s;
			objectOS = new ObjectOutputStream(socket.getOutputStream());
			objectOS.flush();
			objectIS = new ObjectInputStream(socket.getInputStream());
			run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void run() {
		while (true) {
			try {
				TransferData td = (TransferData) (objectIS.readObject());
				/*System.out.println(c.x + "x" + c.y);
				c.x++;
				c.y++;*/
				td.update();
				objectOS.writeObject(td);
				// oos.flush();
			} catch (IOException e) {
				Logger.error("server>>> IO Exception");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}