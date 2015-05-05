package server.room;

import java.io.*;
import java.net.*;
import java.util.*;

import com.mygdx.game.transfer.CellData;
import com.mygdx.game.transfer.Data;
import com.mygdx.game.transfer.PlayerData;

import server.room.player.*;

public class GameRoom extends Thread {

	public static final int DEFAULT_PLAYERS_NUMBER = 5;
	private final int MAX_PLAYER_IS_ROOM;

	private ArrayList<Player> players;
	private HashMap<Integer, CellData> cells;
	private boolean running;

	public GameRoom(int maxPlayerNumber) {
		this.players = new ArrayList<Player>();
		this.running = true;
		this.MAX_PLAYER_IS_ROOM = maxPlayerNumber;
		cells = new HashMap<>();
	}

	public GameRoom() {
		this(DEFAULT_PLAYERS_NUMBER);
	}

	public void destroyGame() {
		synchronized (players) {
			for (int i = 0; i < players.size(); ++i) {
				players.get(i).disconnect();
				players.remove(i);
			}
			running = false;
			Thread.currentThread().interrupt();
		}
	}

	public void add(Socket socket) throws IOException {
		synchronized (players) {
			if (players.size() < MAX_PLAYER_IS_ROOM)
				players.add(new Player(socket));
		}
	}

	@Override
	public void run() {
		while (running) {
			for (int i = 0; i < players.size(); ++i)
				if (!players.get(i).isConnected())
					players.remove(i);
			
			for(int i = 0; i < players.size(); ++i)
				players.get(i).get();
				
			Data toSend = dataToSend();
			for (int i = 0; i < players.size(); ++i) {
				players.get(i).send(toSend);
			}

			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private Data dataToSend() {
		updateCells();
		ArrayList<PlayerData> playersData = new ArrayList<>();
		for (int i = 0; i < players.size(); ++i) {
			if(players.get(i).getData() != null)
				playersData.add(players.get(i).getData().getPlayers().get(0));
		}
		Data toReturn = new Data(playersData, cells);
		return toReturn;
	}

	private void updateCells() {
		for (int i = 0; i < players.size(); ++i) {
			if (players.get(i).getData() != null) {
				HashMap<Integer, CellData> cells = players.get(i).getData().getCells();
				Set<Integer> keys = cells.keySet();
				for (Integer id : keys) {
					CellData thisCell = this.cells.get(id);
					CellData otherCell = cells.get(id);
					if (thisCell != null) {
						this.cells.put(id, (thisCell.getState() > otherCell.getState()) ? otherCell : thisCell);
					} else {
						this.cells.put(id, otherCell);
					}
				}
			}
		}
	}

}
