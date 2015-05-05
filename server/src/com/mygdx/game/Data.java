package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

import multiplayer.transfer.TransferData;

public class Data implements TransferData{

	private static final long serialVersionUID = -2160011134826569848L;
	private HashMap<Integer, CellData> cells;
	private ArrayList <PlayerData> players;
	
	public Data(ArrayList<PlayerData> players, HashMap<Integer, CellData> cells) {
		this.cells = cells;
		this.players = players;
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

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
