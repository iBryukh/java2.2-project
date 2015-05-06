package com.mygdx.game.transfer;

import java.util.ArrayList;
import java.util.HashMap;

public class Data implements TransferData{

	private static final long serialVersionUID = -2160011134826569848L;
	private HashMap<Integer, CellData> cells;
	private ArrayList <PlayerData> players;
	private Boolean newGame;
	
	public Data(ArrayList<PlayerData> players, HashMap<Integer, CellData> cells) {
		this.cells = cells;
		this.players = players;
		this.newGame = false;
	}
	
	public Data(PlayerData player, HashMap<Integer, CellData> cells) {
		this.cells = cells;
		this.players = new ArrayList<>();
		players.add(player);
	}

	public HashMap<Integer, CellData> getCells() {
		return cells;
	}

	public void setCells(HashMap<Integer, CellData> cells) {
		this.cells = cells;
	}

	public ArrayList<PlayerData> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<PlayerData> players) {
		this.players = players;
	}

	public Boolean isNewGame() {
		return newGame;
	}

	public void setNewGame(Boolean newGame) {
		this.newGame = newGame;
	}

}
