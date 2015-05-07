package com.mygdx.game.desktop;

import javafx.application.Application;
import javafx.stage.Stage;
import view.EnterScreen;
import client.Client;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void launch (Client client, String nick) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		config.resizable = false;
		config.title = "Global Tank War";
		config.allowSoftwareMode = true;
		new LwjglApplication(new MyGdxGame(client, nick), config);
	}

}
