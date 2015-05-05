package com.mygdx.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import multiplayer.transfer.TransferData;

public class PlayerData implements TransferData{

	private static final long serialVersionUID = -3256063162115004795L;
	private ArrayList<BulletData> bullets;
	private HashMap<Integer, CellData> cells;
	private int angle;
	private float x;
	private float y;
	
	public PlayerData(float x, float y, int angle, ArrayList<BulletData> bullets, HashMap<Integer, CellData> cells) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.bullets = bullets;
		this.cells = cells;
	}

	public void setCells(HashMap<Integer, CellData> cells) {
		this.cells = cells;
	}

	public ArrayList<BulletData> getBullets() {
		return bullets;
	}

	public HashMap<Integer, CellData> getCells() {
		return cells;
	}

	public int getAngle() {
		return angle;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
