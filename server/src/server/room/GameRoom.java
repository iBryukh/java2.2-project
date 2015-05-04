package server.room;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server.player.Player;

public class GameRoom extends Thread {
	private List<Player> players;
	public GameRoom(){
		players = new ArrayList<Player>();
	}
	
	public void add(Socket socket) throws IOException{
		synchronized (players) {
			players.add(new Player(socket));	
		}
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
