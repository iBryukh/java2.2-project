package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {

	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;
	
	public Explosion() {
        textureAtlas = new TextureAtlas(Gdx.files.internal("explosion.txt"));
        animation = new Animation(1/10f, textureAtlas.getRegions());
	}
	
	public void draw (Batch batch) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		TextureRegion t = animation.getKeyFrame(elapsedTime, true);
		batch.draw(t, 100-t.getRegionWidth()/2, 100-t.getRegionHeight()/2);
	}

}
