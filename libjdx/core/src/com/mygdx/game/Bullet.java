package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet extends Sprite {

	private Player player;
	private int speedX;
	private int speedY;
	private static Texture texture;
	
	public Bullet(Player player, float x, float y, int speedX, int speedY) {
		super (doTexture());
		this.speedX = speedX;
		this.speedY = speedY;
		this.setPosition(x, y);
		this.player = player;
	}
	
	public Bullet (float x, float y, int speedX, int speedY) {
		this (MyGdxGame.getPlayer(), x, y, speedX, speedY);
	}
	
	private static Texture doTexture() {
		if (texture==null) {
			Pixmap pixmap = new Pixmap(4,4,Pixmap.Format.RGBA8888);
			pixmap.setColor(Color.WHITE);
			pixmap.fill();
			texture = new Texture (pixmap);
			pixmap.dispose();
		}
		return texture;
	}
	
	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}
	
	public void move() {
		this.translate(speedX, speedY);
	}
	
	public Boolean worldCollide() {
		if(this.getX() < 0 || this.getX() > Gdx.graphics.getWidth() ||
				this.getY() < 0 || this.getY() > Gdx.graphics.getHeight())
			return true;
		return false;
	}
	
	public boolean collide() {
		ArrayList<Cell> arr = MyGdxGame.getCells();
		for (int i = 0; i < arr.size(); ++i) {
			if (arr.get(i).getBoundingRectangle().overlaps(getBoundingRectangle())) {
				if (arr.get(i).getType()!=3) {
					arr.get(i).hit();
					player.getBullets().remove(this);
				}
				return true;
			}
		}/*
		ArrayList<Player> arr2 = MyGdxGame.getConnector().getEnemies();
		for (int i = 0; i < arr2.size(); ++i) {
			if (arr2.get(i).getBoundingRectangle().overlaps(getBoundingRectangle())) {
				if (player == MyGdxGame.getPlayer()) {
					player.getBullets().remove(this);
					return true;
				}
			}
		}*/
		return false;
	}
	
	public boolean playerCollide () {
		if (this.getBoundingRectangle().overlaps(MyGdxGame.getPlayer().getBoundingRectangle())) {
			player.getBullets().remove(this);
			MyGdxGame.gameOver();
			return true;
		}
		return false;
	}
	
	public BulletData getData () {
		return new BulletData((int)getX(), (int)getY(), (int) speedX, (int) speedY);
	}
}
