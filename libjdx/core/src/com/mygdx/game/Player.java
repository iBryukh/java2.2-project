package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.mygdx.game.transfer.BulletData;
import com.mygdx.game.transfer.CellData;
import com.mygdx.game.transfer.Data;
import com.mygdx.game.transfer.PlayerData;

public class Player extends Sprite{

	public static final Vector2[] places = {new Vector2(20,20),new Vector2(20,580),new Vector2(780,20),new Vector2(780, 580)};
	private ArrayList<Bullet> newBullets;
	private ArrayList<Bullet> bullets;
	private Body body;
	private int health;

	public Player(int type, float x, float y, int angle) {
		super(doTexture(type));
		this.setScale(40f / this.getWidth());
		this.setPosition(x, y);
		this.setRotation(angle);
		this.health = 3;
		
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
		newBullets = new ArrayList<Bullet>();
	}
	
	public Player () {
		//this (0, 25, 25, 0);
		this (0);
	}
	
	public Player (int type) {
		//this (0, 25, 25, 0);
		this (type, -100, -100, 0);
		setRandomPosition();
	}
	
	public void setRandomPosition () {
		ArrayList<Player> arr = (ArrayList<Player>) MyGdxGame.getConnector().getEnemiesStatic().clone();
		if (arr.size()==0) {
			body.setTransform(new Vector2 (places[0].x/PIXS_IN_METER, places[0].y/PIXS_IN_METER), 0);
			return;
		}
		arr.add(MyGdxGame.getPlayer());
		Boolean b = true;
		for (int i = 0; i < places.length; ++i) {
			for (Player p: arr) {
				if (Vector2.dst(places[i].x, places[i].y, p.getX(), p.getY())<50) {
					b = false;
				}
			}
			if (b) {
				body.setTransform(new Vector2 (places[3].x/PIXS_IN_METER, places[3].y/PIXS_IN_METER), 0);
				break;
			}
			b = true;
		}
	}
	
	public void addBullet (Bullet b) {
		bullets.add(b);
		newBullets.add(b);
	}
	
	public void destroy () {
		if (body!=null) {
			try {
				getWorld().destroyBody(body);
				body = null;
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
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
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void hit() {
		if (health > 0) {
			health--;
		}
		if (health==0) {
			getBody().setTransform(new Vector2(-10, -10), 0);
			getBody().setLinearVelocity(0, 0);
		}
	}
	
	public Boolean isAlive () {
		return health>0;
	}

	public Data getData () {
		ArrayList<BulletData> arr = new ArrayList<BulletData>();
		if (newBullets.size() > 0) {
			arr.add(newBullets.get(0).getData());
			newBullets.remove(0);
		}
		PlayerData pdata = new PlayerData(body.getPosition().x, body.getPosition().y, (int)getRotation(), health>0, arr);
		return new Data (pdata, Cell.getUpdatedCells());
	}

	public void update(float x, float y, int angle, BulletData bullet) {
		body.setTransform(new Vector2(x, y), 0);
		this.setRotation(angle);
		if (bullet != null) addBullet(new Bullet(this, bullet.getX(), bullet.getY(), bullet.getSpeedX(), bullet.getSpeedY()));
	}
	
}
