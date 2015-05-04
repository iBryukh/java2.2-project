package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.room.GameRoom;

public class Server {

	private ServerSocket serverSocket;

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			GameRoom gameRoom = new GameRoom();
			gameRoom.start();
			while (true) {
				Socket socket = serverSocket.accept();
				gameRoom.add(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Server server = new Server(7000);
		server.run();
	}

}
