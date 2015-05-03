package com.mygdx.game;

import java.util.ArrayList;

import static com.mygdx.game.MyGdxGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Player extends Sprite{

	private ArrayList<Bullet> bullets;
	private Body body;

	public Player(int type, int x, int y, int angle) {
		super(doTexture(type));
		this.setScale(40f / this.getWidth());
		this.setPosition(//Gdx.graphics.getWidth() / 2 - this.getWidth() / 2 + 65,
		//		Gdx.graphics.getHeight() / 2 - this.getHeight() / 2 + 65);
		x, y);
		this.setRotation(angle);
		
		BodyDef bodyDef = new BodyDef();
        bodyDef.type = (type == 0) ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX()/PIXS_IN_METER + 2, getY()/PIXS_IN_METER + 2);
        body = getWorld().createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20f/PIXS_IN_METER, 20f/PIXS_IN_METER);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);

		bullets = new ArrayList<Bullet>();
	}
	
	public Player () {
		this (0, 25, 25, 0);
	}

	public void addBullet (Bullet b) {
		bullets.add(b);
	}
	
	private static Texture doTexture (int type) {
		if (type == 0) 
			return new Texture("tank.png");
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
	
	public Body getBody() {
		return body;
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public PlayerData getData () {
		ArrayList<BulletData> arr = new ArrayList<BulletData>();
		for (Bullet b:bullets) arr.add(b.getData());
		return new PlayerData((int)getX(), (int)getY(), (int)getRotation(), arr);
	}
	
}
