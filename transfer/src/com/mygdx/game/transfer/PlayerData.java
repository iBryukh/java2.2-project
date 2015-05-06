package com.mygdx.game.transfer;

import java.util.ArrayList;

public class PlayerData implements TransferData{

	private static final long serialVersionUID = -3256063162115004795L;
	private ArrayList<BulletData> bullets;
	private Boolean alive;
	private int angle;
	private float x;
	private float y;
	
	public PlayerData(float x, float y, int angle, Boolean alive, ArrayList<BulletData> bullets) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.bullets = bullets;
		this.alive = alive;
	}

	public ArrayList<BulletData> getBullets() {
		return bullets;
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

	public Boolean isAlive() {
		return alive;
	}
}
