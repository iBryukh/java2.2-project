package com.mygdx.game;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import multiplayer.transfer.Coordinates;
import multiplayer.transfer.TransferData;
import client.Client;

public class ServerConnector {

	ArrayList<Player> players = new ArrayList<Player>();
	private Client client;

	public ServerConnector() {
		try {
			client = new Client(InetAddress.getByName(null), 7000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	
	public void send(TransferData td) {
		client.send(td);
	}

	public ArrayList<Player> getEnemies() {
		ArrayList <PlayerData>  c = client.get();
		if (c!=null && c.size() > 0) {
			for (int i = 0; i < c.size(); ++i) {
				if (players.size()<c.size()) players.add(new Player(-100, -100, 0, 0));
				if (players.size()>c.size()) players.remove(players.size()-1).destroy();
				try {
					players.get(i).update(c.get(i).getX(), c.get(i).getY(), c.get(i).getAngle(), (c.get(i).getBullets().size() > 0) ? c.get(i).getBullets().get(0) : null);
				}
				catch (NullPointerException n) {
					//TODO
					System.out.println(c.get(i));
				}
			}
			//Cell.updateCells(c.get(0).getCells());
		}
		return players;
	}

	public ArrayList<Player> getEnemiesStatic() {
		return players;
	}
}
