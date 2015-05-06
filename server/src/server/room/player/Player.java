package server.room.player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.mygdx.game.transfer.CellData;
import com.mygdx.game.transfer.Data;
import com.mygdx.game.transfer.PlayerData;

public class Player {
	private Socket socket;
	private ObjectInputStream objectIS;
	private ObjectOutputStream objectOS;
	private Data data;
	private boolean connected;
	private boolean firstTimeConnected;

	public Player(Socket s) {
		try {
			socket = s;
			objectOS = new ObjectOutputStream(socket.getOutputStream());
			objectOS.flush();
			objectIS = new ObjectInputStream(socket.getInputStream());
			connected = true;
			firstTimeConnected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isFirstTimeConnected(){
		return firstTimeConnected;
	}
	
	public void changeFirstTimeConnected(){
		firstTimeConnected = false;
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
	
	public void send(final Data data) {
		try {
			ArrayList <PlayerData> players = (ArrayList<PlayerData>) data.getPlayers().clone();
			for(int i = 0 ; i < data.getPlayers().size(); ++i){
				if(data.getPlayers().get(i) == this.data.getPlayers().get(0))
					players.remove(i);
			}
			Data toSend = new Data(players, data.getCells());
			toSend.setNewGame(data.isNewGame());
			objectOS.writeObject(toSend);
		} catch (IOException e) {
			disconnect();
		}
	}
	
	public void send(final Data data, final HashMap<Integer, CellData> allCells) {
		try {
			ArrayList <PlayerData> players = (ArrayList<PlayerData>) data.getPlayers().clone();
			for(int i = 0 ; i < data.getPlayers().size(); ++i){
				if(this.data != null && data.getPlayers().get(i) == this.data.getPlayers().get(0))
					players.remove(i);
			}
			Data toSend = new Data(players, allCells);
			toSend.setNewGame(data.isNewGame());
			objectOS.writeObject(toSend);
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