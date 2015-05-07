package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import model.resManager;
import view.EnterScreen;

public class SoundScreenEvents {
	public static boolean sB = true;

	public static ImageView buttonSound(EnterScreen ES) {
		ImageView img = new ImageView(resManager.getSBI());

		img.setOnMouseEntered(new OnMouseEntered());
		img.setOnMouseExited(new OnMouseExit());
		img.setOnMouseClicked(new OnMouseClicked(ES));

		return img;
	}

	public static class OnMouseEntered implements EventHandler {

		@Override
		public void handle(Event event) {
			ImageView iv = (ImageView) event.getSource();
			iv.setImage(resManager.getSPBI());
		}

	}

	public static class OnMouseExit implements EventHandler {

		@Override
		public void handle(Event event) {
			if (sB == true) {
				ImageView iv = (ImageView) event.getSource();
				iv.setImage(resManager.getSBI());
			} else {
				ImageView iv = (ImageView) event.getSource();
				iv.setImage(resManager.getSNPBI());
			}
		}

	}

	public static class OnMouseClicked implements EventHandler {
		private EnterScreen ES = null;

		public OnMouseClicked(EnterScreen ES) {
			this.ES = ES;
		}

		@Override
		public void handle(Event event) {
			if (sB == true) {
				sB = false;
				ES.mediaPlayer.pause();
			} else {
				sB = true;
				ES.mediaPlayer.play();
			}
		}

	}
}
