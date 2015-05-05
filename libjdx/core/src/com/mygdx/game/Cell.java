package com.mygdx.game;

import static com.mygdx.game.MyGdxGame.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.transfer.CellData;

public class Cell extends Sprite {

	private static HashMap<Integer, CellData> updatedCells = new HashMap<>();
	private static int staticId = 0;
	private int id;
	private int type;
	private Body body;
	private int health;
	
	public Cell(float x, float y, int type) {
		super(doTexture(type));
		this.id = staticId++;
		this.type = type;
		this.health = 3;
		
		this.setScale(50f / this.getWidth());
		this.setPosition(x, y);
		
		if (type!=3) {
			BodyDef bodyDef = new BodyDef();
	        bodyDef.type = BodyDef.BodyType.StaticBody;
	        bodyDef.position.set(x/PIXS_IN_METER + 2.5f,y/PIXS_IN_METER + 2.5f);
	        body = getWorld().createBody(bodyDef);
	        PolygonShape shape = new PolygonShape();
	        shape.setAsBox(25f/PIXS_IN_METER, 25f/PIXS_IN_METER);
	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = shape;
	        fixtureDef.density = 1f;
	        body.createFixture(fixtureDef);
	        body.setFixedRotation(true);
		}
	}

	public void update (int health) {
		if (this.health > health) this.health = health;
		if (health <= 0 && body!=null) {
			getWorld().destroyBody(body);
			body = null;
		}
	}
	
	private static Texture doTexture (int type) {
		if (type==1) return new Texture("wall.png");
		if (type==2) return new Texture("wall2.png");
		return new Texture("greens.png");
	}

	public Body getBody() {
		return body;
	}

	public void hit() {
		if (type==2) return;
		--health;
		if (health <= 0 && body!=null) {
			getWorld().destroyBody(body);
			body = null;
		}
		updatedCells.put(id, getData());
	}
	
	public int getHealth() {
		return health;
	}

	public boolean isAlive() {
		return health>0;
	}

	public int getType() {
		return type;
	}
	
	public int getId() {
		return id;
	}
	
	public CellData getData () {
		return new CellData(id, health, type);
	}
	
	public static HashMap<Integer, CellData> getUpdatedCells () {
		HashMap<Integer, CellData> temp = (HashMap<Integer, CellData>) updatedCells.clone();
		updatedCells.clear();
		return temp;
	}
	
	public static void updateCells (HashMap<Integer, CellData> map) {
		ArrayList<Cell> arr = MyGdxGame.getCells();
		for (Cell c:arr) {
			CellData d = map.get(c.getId());
			if (d!=null)
				c.update(d.getState());
		}
	}
}
