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
				players.get(i).get();

			for (int i = 0; i < players.size(); ++i)
				if (!players.get(i).isConnected())
					players.remove(i);
			
			Data toSend = dataToSend();
			for (int i = 0; i < players.size(); ++i) {
				if(players.get(i).isFirstTimeConnected()){
					players.get(i).send(toSend, cells);
					players.get(i).changeFirstTimeConnected();
				} else {
					players.get(i).send(toSend, cells);
				}
			}

			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) { }

		}
	}

	private Data dataToSend() {
		HashMap<Integer, CellData> changedCell = updateCells();
		ArrayList<PlayerData> playersData = new ArrayList<>();
		int alivePlayers = 0;
		for (int i = 0; i < players.size(); ++i) {
			playersData.add(players.get(i).getData().getPlayers().get(0));
			if (players.get(i).getData().getPlayers().get(0).isAlive())
				++alivePlayers;
		}
		Data toReturn = new Data(playersData, changedCell);
		toReturn.setNewGame((alivePlayers < 2) ? true : false);
		return toReturn;
	}

	private HashMap<Integer, CellData> updateCells() {
		HashMap<Integer, CellData> changedCellPerTick = new HashMap<>();
		for (int i = 0; i < players.size(); ++i) {
			HashMap<Integer, CellData> cells = players.get(i).getData().getCells();
			Set<Integer> keys = cells.keySet();
			for (Integer id : keys) {
				CellData cell = changedCellPerTick.get(id);
				if (cell == null) {
					changedCellPerTick.put(id, cells.get(id));
				} else {
					CellData cd = cells.get(id);
					changedCellPerTick.put(id, (cell.getState() > cd.getState()) ? cd : cell);
				}
			}
		}
		Set<Integer> keys = changedCellPerTick.keySet();
		for(Integer id : keys){
			cells.put(id, changedCellPerTick.get(id));
		}
		return changedCellPerTick;
	}

}
