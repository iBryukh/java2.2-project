package com.mygdx.game;

import java.io.Serializable;

public class CellData implements Serializable{

	private static final long serialVersionUID = 3487629833856715123L;
	private int id;
	private int state;
	
	public CellData(int id, int state) {
		this.id = id;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public int getState() {
		return state;
	}

}
