package com.mygdx.game;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerData implements Serializable{

	private static final long serialVersionUID = -3256063162115004795L;
	private ArrayList<BulletData> bullets;
	private int angle;
	private int x;
	private int y;
	
	public PlayerData(int x, int y, int angle, ArrayList<BulletData> bullets) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.bullets = bullets;
	}

	public ArrayList<BulletData> getBullets() {
		return bullets;
	}

	public int getAngle() {
		return angle;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
