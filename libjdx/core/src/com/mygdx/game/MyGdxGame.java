package com.mygdx.game;

import java.util.ArrayList;

import client.Client;

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
	private static World world = new World(new Vector2(), true);
	private SpriteBatch batch;
	private static Player player;
	private Box2DDebugRenderer debugRenderer;
	private Matrix4 debugMatrix;
	private OrthographicCamera camera;
	private static ServerConnector connector; 
	private String nick;
	
	public MyGdxGame(Client client, String nick) {
		connector = new ServerConnector(client);
		this.nick = nick;
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		Explosion.clear();
		
		createWorldBounds();
		createLabyrinth();
		player = new Player(nick);
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        debugRenderer = new Box2DDebugRenderer();
        camera.translate(new Vector3(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f,0));
        
        GUI.init();
        GUI.showPrepareForBattle();
	}

	@Override
	public void render() {
		connector.send(player.getData());
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (player.isAlive())
			playerControl();
		
		batch.setProjectionMatrix(camera.combined);
		//debugMatrix = batch.getProjectionMatrix().cpy().scale(10,10, 0);
		//debugRenderer.render(world, debugMatrix);
		batch.begin();
		for (Cell c:Cell.getCells()) if (c.isAlive() && c.getType()!=3) c.draw(batch);
		if (player.isAlive()) {
			player.setPosition(player.getBody().getPosition().x*10-20, player.getBody().getPosition().y*10-20);
			player.draw(batch);
		}
		
		ArrayList<Player> arr = connector.getEnemies();
		for (Player p: arr) {
			p.setPosition(p.getBody().getPosition().x*10-20, p.getBody().getPosition().y*10-20);
			p.draw(batch);
			for (int i = 0; i < p.getBullets().size(); ++i) {
				if (!p.getBullets().get(i).collide())
					p.getBullets().get(i).playerCollide();
			}
		}
		for (Cell c:Cell.getCells()) if (c.isAlive() && c.getType()==3) c.draw(batch);
		for (int i = 0; i < player.getBullets().size(); ++i) player.getBullets().get(i).collide();
		Explosion.drawAll(batch);
		GUI.draw(batch);
		batch.end();
	}
	
	private void createLabyrinth() {
		int[][] xyarr = 
			{
				{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
				{2, 0, 0, 1, 3, 3, 0, 0, 0, 2, 3, 3, 3, 0, 0, 2},
				{2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 2},
				{2, 1, 0, 1, 2, 0, 0, 1, 1, 0, 0, 2, 1, 0, 1, 2},
				{2, 1, 1, 2, 1, 0, 1, 2, 2, 1, 0, 1, 2, 1, 1, 2},
				{2, 3, 0, 3, 3, 0, 1, 3, 3, 0, 0, 3, 3, 0, 3, 2},
				{2, 3, 0, 3, 3, 0, 0, 3, 3, 1, 0, 3, 3, 0, 3, 2},
				{2, 1, 1, 2, 1, 0, 1, 2, 2, 1, 0, 1, 2, 1, 1, 2},
				{2, 1, 0, 1, 2, 0, 0, 1, 1, 0, 0, 2, 1, 0, 1, 2},
				{2, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
				{2, 0, 0, 3, 3, 3, 2, 0, 0, 0, 3, 3, 1, 0, 0, 2},
				{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
			};
		
		for (int i = 0; i < xyarr.length; ++i) {
			for (int j = 0; j < xyarr[i].length; ++j) {
				if (xyarr[i][j]!=0) Cell.getCells().add (new Cell (j*50, Gdx.graphics.getHeight()-(i+1)*50, xyarr[i][j]));
			}
		}
	}
	
	private void playerControl() {
		player.getBody().setLinearVelocity(0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.setRotation(90);
			player.getBody().setLinearVelocity(new Vector2(-20,0));
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.setRotation(-90);
			player.getBody().setLinearVelocity(new Vector2(20,0));
		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.setRotation(0);
			player.getBody().setLinearVelocity(new Vector2(0,20));
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.setRotation(180);
			player.getBody().setLinearVelocity(new Vector2(0,-20));
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			player.shoot();
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
	
	public static void newGame () {
		GUI.showPrepareForBattle();
		Cell.refreshCells();
		player.setHealth(3);
		player.setRandomPosition();
	}
	
	public static World getWorld() {
		return world;
	}
	
	public static Player getPlayer() {
		return player;
	}

	public static ServerConnector getConnector() {
		return connector;
	}
	
	public static void gameOver() {
		player.setRandomPosition();
	}

}
