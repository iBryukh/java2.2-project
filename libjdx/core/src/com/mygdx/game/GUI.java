package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class GUI {

	private static Sprite prepareFB;
	private static int prepareFBtime;
	private static TextField textHealth;
	private static TextField textFrags;
	private static Sprite heart;
	private static Sprite skull;
	
	public static void init() {
        heart = new Sprite(new Texture("heart.png"));
        heart.setPosition (0, Gdx.graphics.getHeight()-heart.getHeight());
        skull = new Sprite(new Texture("skull.png"));
        skull.setPosition (Gdx.graphics.getWidth()-72, Gdx.graphics.getHeight()-skull.getHeight());
        Skin skin = new Skin (Gdx.files.internal("uiskin.json"));
        textHealth = new TextField("", skin);
        textHealth.setPosition(20, Gdx.graphics.getHeight()-30);
        textHealth.setSize(55, 25);
        textFrags = new TextField("", skin);
        textFrags.setPosition(skull.getX()+15, Gdx.graphics.getHeight()-30);
        textFrags.setSize(55, 25);
	}
	
	public static void showPrepareForBattle() {
		if (prepareFB==null) {
			Texture t = new Texture("prepare for battle.png");
			prepareFB = new Sprite (t);
			prepareFB.setPosition((Gdx.graphics.getWidth() - prepareFB.getWidth())/2, (Gdx.graphics.getHeight() - prepareFB.getHeight())/2);
		}
		prepareFBtime = 130;
	}
	
	public static void draw (Batch batch) {
		if (prepareFBtime > 0) {
			--prepareFBtime;
			prepareFB.draw(batch);
		}
		
		String frgs = ("00" + MyGdxGame.getPlayer().getFrags());
		textFrags.setText("     "  + frgs.substring(frgs.length()-3, frgs.length()));
		textFrags.draw(batch, 1);
		textHealth.setText("     "+MyGdxGame.getPlayer().getHealth()+"/3");
		textHealth.draw(batch, 1);
		heart.draw(batch);
		skull.draw(batch);
	}
	
	private GUI() {
		
	}

}
