package server.room.player;

import java.io.*;
import java.net.*;

import com.mygdx.game.transfer.*;

public class Player {
	private Socket socket;
	private ObjectInputStream objectIS;
	private ObjectOutputStream objectOS;
	private Data data;
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
	
	public void send(Data data) {
		try {
			for(int i = 0; i < data.getPlayers().size(); ++i){
				if(this.data != null && data.getPlayers().get(i) == this.data.getPlayers().get(0)){
					//data.getPlayers().remove(i);
					break;
				}
			}
			objectOS.writeObject(data);
		} catch (IOException e) {
			disconnect();
		}
	}
	
	public void get() {
		try {
			data = (Data) (objectIS.readObject());
		} catch (IOException | ClassNotFoundException e) {
			disconnect();
		}
	}
	
	public Data getData(){
		return data;
	}
}