package controller;

import java.util.Timer;
import java.util.TimerTask;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.resManager;
import view.EnterScreen;

public class PlayScreenEvents {

	public static ImageView buttonPlay(EnterScreen ES) {
		ImageView img = new ImageView(resManager.getPBI());

		img.setOnMouseEntered(new OnMouseEntered());
		img.setOnMouseExited(new OnMouseExit());
		img.setOnMouseClicked(new OnMouseClicked(ES));

		return img;
	}

	public static class OnMouseEntered implements EventHandler {

		@Override
		public void handle(Event event) {
			ImageView iv = (ImageView) event.getSource();
			iv.setImage(resManager.getPPBI());
		}

	}

	public static class OnMouseExit implements EventHandler {

		@Override
		public void handle(Event event) {
			ImageView iv = (ImageView) event.getSource();
			iv.setImage(resManager.getPBI());
		}

	}

	public static class OnMouseClicked implements EventHandler {
		private EnterScreen ES = null;

		// ES присвоїти вікно з грою або загрузкою

		public OnMouseClicked(EnterScreen ES) {
			this.ES = ES;
		}

		@Override
		public void handle(Event event) {
			
			try {
				ImageView gif = new ImageView("pict/Global-tank-war.gif");
				AnchorPane.setLeftAnchor(gif, 0.0);
				AnchorPane.setTopAnchor(gif, 0.0);
				ES.anPane.getChildren().add(gif);
				time();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		public  void time() throws InterruptedException{
			 Timer timer = new Timer();
			 TimerTask task = new TimerTask() {
				    public void run()
				    {ImageView setImg = new ImageView("pict/setImg.png");
					AnchorPane.setLeftAnchor(setImg, 0.0);
					AnchorPane.setTopAnchor(setImg, 0.0);
					ES.anPane.getChildren().add(setImg);
				    }};
			    timer.schedule( task, 5000);
		}
	}
}
