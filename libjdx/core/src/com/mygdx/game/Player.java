package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite{

	private ArrayList<Bullet> bullets;
	
	public Player(int type) {
		super(doTexture(type));
		this.setScale(50f / this.getWidth());
		this.setPosition(Gdx.graphics.getWidth() / 2 - this.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - this.getHeight() / 2);
		bullets = new ArrayList<Bullet>();
	}
	
	public Player () {
		this (0);
	}

	public void addBullet (Bullet b) {
		bullets.add(b);
	}
	
	private static Texture doTexture (int type) {
		if (type == 0) new Texture("tank.png");
		return new Texture("tank2.png");
	}
	
	@Override
	public void draw (Batch batch) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(batch);
			bullets.get(i).move();
			if (bullets.get(i).worldCollide())
				bullets.remove(i);
		}
		
		super.draw(batch);
	}
	
}
