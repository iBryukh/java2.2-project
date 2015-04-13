package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Sprite player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture img = new Texture("tank.png");
		player = new Sprite(img);
		player.setScale(50f / player.getWidth());
        player.setPosition(Gdx.graphics.getWidth()/2 -player.getWidth()/2, Gdx.graphics.getHeight()/2 - player.getHeight()/2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
        	if (player.getBoundingRectangle().x >= 0)
                player.translateX(-5.0f);
                player.setRotation(90);
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
        	if (player.getBoundingRectangle().x <= Gdx.graphics.getWidth()-50)
                player.translateX(5.0f);
                player.setRotation(-90);  
        } else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
        	if (player.getBoundingRectangle().y <= Gdx.graphics.getHeight()-50)
                player.translateY(5.0f);
                player.setRotation(0);
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
        	if (player.getBoundingRectangle().y >= 0 )
                player.translateY(-5.0f);
                player.setRotation(180);
        }
        batch.begin();
        player.draw(batch);
        batch.end();
	}
}
