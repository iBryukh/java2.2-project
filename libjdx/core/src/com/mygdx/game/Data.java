package com.mygdx.game;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable{

	private static final long serialVersionUID = -6616462056457819137L;
	private PlayerData player;
	private ArrayList<CellData> cells;
	
	public Data(PlayerData player, ArrayList<CellData> cells) {
		this.player = player;
		this.cells = cells;
	}

	public PlayerData getPlayer() {
		return player;
	}

	public ArrayList<CellData> getCells() {
		return cells;
	}

}
