package com.mygdx.game;

import java.io.Serializable;

public class BulletData implements Serializable{

	private static final long serialVersionUID = -4105411395536190427L;
	private int x;
	private int y;
	
	public BulletData(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
