package server.thread;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameRoom extends Thread {
	private List<ServeThread> players;
	public GameRoom(){
		players = new ArrayList<ServeThread>();
	}
	
	public void add(Socket socket) throws IOException{
		players.add(new ServeThread(socket));
	}
	
	@Override
	public void run() {
		while(true){}
	}

}
