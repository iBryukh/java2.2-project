package com.mygdx.game;

import java.io.Serializable;

public class BulletData implements Serializable{

	private static final long serialVersionUID = -4105411395536190427L;
	private int x;
	private int y;
	private int speedX;
	private int speedY;
	
	public BulletData(int x, int y, int speedX, int speedY) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}
	
}
