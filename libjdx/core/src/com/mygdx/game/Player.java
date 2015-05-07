package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mygdx.game.MyGdxGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.mygdx.game.transfer.BulletData;
import com.mygdx.game.transfer.CellData;
import com.mygdx.game.transfer.Data;
import com.mygdx.game.transfer.PlayerData;

public class Player extends Sprite{

	private ArrayList<Bullet> newBullets;
	private ArrayList<Bullet> bullets;
	private Body body;
	private int health;
	private int frags;
	private String nick;
	private TextArea tnick;
	private float elapsedTime;

	public Player(int type, float x, float y, int angle) {
		super(doTexture(type));
		this.setScale(40f / this.getWidth());
		this.setPosition(x, y);
		this.setRotation(angle);
		this.health = 3;
		this.frags = 0;
		this.elapsedTime = 0;
		this.nick = "ivan".toLowerCase();
		Skin skin = new Skin (Gdx.files.internal("uiskin.json"));
		tnick = new TextArea("", skin);
		tnick.setScale (1f, 0.01f);
		tnick.getStyle().background = null;
		
		
		BodyDef bodyDef = new BodyDef();
        bodyDef.type = (type == 0) ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX()/PIXS_IN_METER + 2, getY()/PIXS_IN_METER + 2);
        body = getWorld().createBody(bodyDef);
        Shape shape;
        if (type == 0) {
        	shape = new CircleShape();
        	shape.setRadius(20f/PIXS_IN_METER);
        } else {
        	shape = new PolygonShape();
        	((PolygonShape) shape).setAsBox(20f/PIXS_IN_METER, 20f/PIXS_IN_METER);
        }
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);

		bullets = new ArrayList<Bullet>();
		newBullets = new ArrayList<Bullet>();
	}
	
	public Player () {
		this (0);
	}
	
	public Player (int type) {
		this (type, -100, -100, 0);
		setRandomPosition();
	}
	
	public void shoot() {
		if (elapsedTime < 0.5) return;
		elapsedTime = 0;
		int speedX = 0;
		int speedY = 0;
		switch ((int)getRotation()) {
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
		Bullet b = new Bullet(getBoundingRectangle().x+18, getBoundingRectangle().y+18, speedX, speedY);
		addBullet(b);
	}
	
	public void setRandomPosition () {
		do {
			float x = (float) (Math.random()*Gdx.graphics.getWidth());
			float y = (float) (Math.random()*Gdx.graphics.getHeight());
			body.setTransform(new Vector2(x/PIXS_IN_METER, y/PIXS_IN_METER), 0);
			setPosition(x, y);
		} while (overlap());
		setRotation(Math.round (Math.random()*4)*90);
	}
	
	public Boolean overlap (){
		for (Cell c:MyGdxGame.getCells()) {
			if (c.getBoundingRectangle().overlaps(getBoundingRectangle()))
				return true;
		}
		return false;
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
		elapsedTime += Gdx.graphics.getDeltaTime();
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(batch);
			bullets.get(i).move();
			if (bullets.get(i).worldCollide())
				bullets.remove(i);
		}
		
		tnick.setText((nick==null)?"":nick);
		tnick.setWidth(tnick.getText().length()*8);
		tnick.setPosition(getX()-tnick.getWidth()/2+getWidth()/2, getY()+30);
		tnick.draw(batch, 1);
		
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
	
	public int getFrags() {
		return frags;
	}

	public void addFrag() {
		++this.frags;
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
		PlayerData pdata = new PlayerData(nick, body.getPosition().x, body.getPosition().y, (int)getRotation(), health>0, arr);
		return new Data (pdata, Cell.getUpdatedCells());
	}

	public void update(float x, float y, int angle, BulletData bullet) {
		body.setTransform(new Vector2(x, y), 0);
		this.setRotation(angle);
		if (bullet != null) addBullet(new Bullet(this, bullet.getX(), bullet.getY(), bullet.getSpeedX(), bullet.getSpeedY()));
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
}
