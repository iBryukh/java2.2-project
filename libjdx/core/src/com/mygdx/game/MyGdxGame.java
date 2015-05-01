package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class MyGdxGame extends ApplicationAdapter {
	
	public static final int PIXS_IN_METER = 10;
	private static World world = new World(new Vector2(), true);;
	private SpriteBatch batch;
	private static Player player;
	private Box2DDebugRenderer debugRenderer;
	private Matrix4 debugMatrix;
	private OrthographicCamera camera;
	private static ArrayList<Cell> cells;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		player = new Player();
		
		createWorldBounds();
		createLabyrinth();
        
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        debugRenderer = new Box2DDebugRenderer();
        camera.translate(new Vector3(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f,0));
	}

	@Override
	public void render() {
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		player.getBody().setLinearVelocity(0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.setRotation(90);
			player.getBody().setLinearVelocity(new Vector2(-30,0));
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.setRotation(-90);
			player.getBody().setLinearVelocity(new Vector2(30,0));
		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.setRotation(0);
			player.getBody().setLinearVelocity(new Vector2(0,30));
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.setRotation(180);
			player.getBody().setLinearVelocity(new Vector2(0,-30));
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
		
		batch.setProjectionMatrix(camera.combined);
		debugMatrix = batch.getProjectionMatrix().cpy().scale(10,10, 0);
		debugRenderer.render(world, debugMatrix);
		batch.begin();
		player.setPosition(player.getBody().getPosition().x*10-64, player.getBody().getPosition().y*10-64);
		player.draw(batch);
		for (Cell c:cells) c.draw(batch);
		for (int i = 0; i < player.getBullets().size(); ++i) player.getBullets().get(i).collide();
		batch.end();
	}
	
	private void createLabyrinth() {
		/*
		int[][] xyarr = 
			{
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			};
			*/
		int[][] xyarr = 
			{
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			};
		cells = new ArrayList<Cell>();
		for (int i = 0; i < xyarr.length; ++i) {
			for (int j = 0; j < xyarr[i].length; ++j) {
				if (xyarr[i][j]!=0) cells.add (new Cell (j*50, Gdx.graphics.getHeight()-(i+1)*50, xyarr[i][j]));
			}
		}
	}
	
	private void createWorldBounds () {
        createEdge(0,0,Gdx.graphics.getWidth()/PIXS_IN_METER,0);
        createEdge(0,0,0,Gdx.graphics.getHeight()/PIXS_IN_METER);
        createEdge(Gdx.graphics.getWidth()/PIXS_IN_METER,0,Gdx.graphics.getWidth()/PIXS_IN_METER,Gdx.graphics.getHeight()/PIXS_IN_METER);
        createEdge(0,Gdx.graphics.getHeight()/PIXS_IN_METER,Gdx.graphics.getWidth()/PIXS_IN_METER,Gdx.graphics.getHeight()/PIXS_IN_METER);
	}
	
	private void createEdge (float f1, float f2, float f3, float f4) {
		BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        bodyDef2.position.set(0,0);
        FixtureDef fixtureDef2 = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(f1,f2,f3,f4);
        fixtureDef2.shape = edgeShape;
        world.createBody(bodyDef2).createFixture(fixtureDef2);
        edgeShape.dispose();
        
	}
	
	public static World getWorld() {
		return world;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public static ArrayList<Cell> getCells() {
		return cells;
	}
}
