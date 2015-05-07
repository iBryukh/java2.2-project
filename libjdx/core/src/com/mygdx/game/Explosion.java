package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {

	private static ArrayList<Explosion> explosions = new ArrayList<>();
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;
	private float x, y;
	
	public Explosion(float x, float y) {
        textureAtlas = new TextureAtlas(Gdx.files.internal("explosion.txt"));
        animation = new Animation(1/20f, textureAtlas.getRegions());
        this.x = x;
        this.y = y;
	}
	
	public void draw (Batch batch) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		TextureRegion t = animation.getKeyFrame(elapsedTime, true);
		batch.setColor(1, 1, 1, 0.7f);
		batch.draw(t, x-t.getRegionWidth()/2, y-t.getRegionHeight()/2);
		batch.setColor(0, 0, 0, 1f);
	}
	
	public void dispose() {
        textureAtlas.dispose();
    }

	public static ArrayList<Explosion> getExplosions() {
		return explosions;
	}
	
	public static void clear () {
		explosions.clear();
	}
	
	public static void addExplosion (float x, float y) {
		explosions.add(new Explosion(x, y));
	}
	
	public static void drawAll (Batch batch) {
		for (int i = 0; i < explosions.size(); ++i) {
			if (explosions.get(i).animation.isAnimationFinished(explosions.get(i).elapsedTime)) {
				explosions.get(i).dispose();
				explosions.remove(i);
			} else	{
				explosions.get(i).draw(batch);
			}
		}
	}

}
