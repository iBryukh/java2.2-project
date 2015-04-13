package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;

	@Override
	public void create() {
		batch = new SpriteBatch();
		player = new Player();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (player.getBoundingRectangle().x >= 0)
				player.translateX(-5.0f);
			player.setRotation(90);
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (player.getBoundingRectangle().x <= Gdx.graphics.getWidth() - 50)
				player.translateX(5.0f);
			player.setRotation(-90);
		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (player.getBoundingRectangle().y <= Gdx.graphics.getHeight() - 50)
				player.translateY(5.0f);
			player.setRotation(0);
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (player.getBoundingRectangle().y >= 0)
				player.translateY(-5.0f);
			player.setRotation(180);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			int speedX = 0;
			int speedY = 0;
			switch ((int)player.getRotation()) {
			case 0:
				speedY = 10;
				break;
			case -90:
				speedX = 10;
				break;
			case 180:
				speedY = -10;
				break;
			case 90:
				speedX = -10;
				break;
			}
			Bullet b = new Bullet(player.getBoundingRectangle().x+23, player.getBoundingRectangle().y+23, speedX, speedY);
			player.addBullet(b);
		}
		
		//Texture texture = new Texture ("ground.png");
		//texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		batch.begin();
		//batch.draw (texture, 0, 0, 0, 0, 800, 600);
		//batch.draw (texture, 0, 0, 800, 600, 4, 4, 32, 32);
		player.draw(batch);
		batch.end();
	}
}
