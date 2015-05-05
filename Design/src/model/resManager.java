package model;

import javafx.scene.image.Image;

public class resManager {

	public static Image getEnterScreenBackground() {
		return new Image("pict/EnterScreen.png");
	}

	// Exit
	public static Image getEBI() {
		return new Image("pict/ExitButtonImage.png");
	}

	public static Image getEPBI() {
		return new Image("pict/ExitPressedButtonImage.png");
	}

	// Play
	public static Image getPBI() {
		return new Image("pict/PlayButtonImage.png");
	}

	public static Image getPPBI() {
		return new Image("pict/PlayPressedButtonImage.png");
	}

	// Sound
	public static Image getSBI() {
		return new Image("pict/SoundButtonImage.png");
	}

	public static Image getSNPBI() {
		return new Image("pict/SoundNotPressedButtonImage.png");
	}

	public static Image getSPBI() {
		return new Image("pict/SoundPressedButtonImage.png");
	}
}
