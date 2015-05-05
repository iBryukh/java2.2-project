package com.mygdx.game.transfer;


public class CellData implements TransferData{

	private static final long serialVersionUID = 3487629833856715123L;
	private int id;
	private int state;
	private int type;
	
	public CellData(int id, int state, int type) {
		this.id = id;
		this.state = state;
		this.type = type;
	}
	
	public void setState(int state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}
	
	public int getType() {
		return type;
	}

	public int getState() {
		return state;
	}

}
