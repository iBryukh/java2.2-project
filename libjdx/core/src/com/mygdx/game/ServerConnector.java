package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class ServerConnector {

	//temporary
	ArrayList<Player> players = new ArrayList<Player>();
	
	//dummy
	public ServerConnector() {
		players.add(new Player(1, 100, 0, -90));
		players.add(new Player(1, 0, 100, -90));
	}
	
	//dummy
	public ArrayList<Player> getEnemies() {
		if (Math.random() < 0.02) players.get(1).addBullet(new Bullet(players.get(1), players.get(1).getBoundingRectangle().x+18, players.get(1).getBoundingRectangle().y+18, 10, 0));
		return players;
	}

}
