package com.mygdx.game;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mygdx.game.transfer.Data;
import com.mygdx.game.transfer.PlayerData;

import client.Client;

public class ServerConnector {

	ArrayList<Player> players = new ArrayList<Player>();
	private Client client;

	public ServerConnector(Client client) {
		this.client = client;
	}
	
	public ServerConnector() {
		try {
			client = new Client();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	
	public void send(Data td) {
		client.send(td);
	}

	public ArrayList<Player> getEnemies() {
		Data data = client.get();
		ArrayList <PlayerData>  c = data.getPlayers();
		if (c!=null && c.size() > 0) {
			for (int i = 0; i < c.size(); ++i) {
				if (players.size()<c.size()) players.add(new Player("", 1));
				if (players.size()>c.size()) players.remove(players.size()-1).destroy();
				try {
					players.get(i).update(c.get(i).getX(), c.get(i).getY(), c.get(i).getAngle(), (c.get(i).getBullets().size() > 0) ? c.get(i).getBullets().get(0) : null);
					players.get(i).setNick(c.get(i).getNick());
				}
				catch (NullPointerException n) {
					//TODO
					System.out.println(c.get(i));
				}
			}
			Cell.updateCells(data.getCells());
			if (data.isNewGame()) {
				MyGdxGame.newGame();
			}
		}
		return players;
	}

	public ArrayList<Player> getEnemiesStatic() {
		return players;
	}
}
