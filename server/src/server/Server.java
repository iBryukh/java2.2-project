package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.room.GameRoom;

public class Server {

	public static final int MAIN_SERVER_PORT = 7999;

	public static void main(String args[]) {
		try {
			ServerSocket serverSocket = new ServerSocket(MAIN_SERVER_PORT);
			GameRoom gameRoom = new GameRoom();
			gameRoom.start();
			Socket socket = serverSocket.accept();
			gameRoom.add(socket);
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
