package com.mygdx.game;

import static com.mygdx.game.MyGdxGame.PIXS_IN_METER;
import static com.mygdx.game.MyGdxGame.getWorld;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Cell extends Sprite {

	private int type;
	private Body body;
	private int health;
	
	public Cell(float x, float y, int type) {
		super(doTexture(type));
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
	        shape.setAsBox(24f/PIXS_IN_METER, 24f/PIXS_IN_METER);
	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = shape;
	        fixtureDef.density = 1f;
	        body.createFixture(fixtureDef);
	        body.setFixedRotation(true);
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
		if (health <= 0) {
			MyGdxGame.getWorld().destroyBody(body);
			MyGdxGame.getCells().remove(this);
		}
	}

	public int getType() {
		return type;
	}
}
