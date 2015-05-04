package com.mygdx.game;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import multiplayer.transfer.Coordinates;
import multiplayer.transfer.TransferData;
import client.Client;

public class ServerConnector {

	//temporary
	ArrayList<Player> players = new ArrayList<Player>();
	private Client client;

	//dummy
	public ServerConnector() {
		try {
			client = new Client(InetAddress.getByName(null), 7000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		players.add(new Player(1, 100, 0, -90));
		//players.add(new Player(1, 0, 100, -90));
	}
	
	
	public void send(TransferData td) {
		client.send(td);
	}

	//dummy
	public ArrayList<Player> getEnemies() {
		
		ArrayList <PlayerData>  c = client.get();
		for (int i = 0; i < c.size(); ++i) {
			if (Math.abs(c.get(i).getX() - MyGdxGame.getPlayer().getBody().getPosition().x) >= 4 || Math.abs(c.get(i).getY() - MyGdxGame.getPlayer().getBody().getPosition().y) >= 4)
				players.get(0).update(c.get(i).getX(), c.get(i).getY(), c.get(i).getAngle());
		}
		return players;
	}

}
