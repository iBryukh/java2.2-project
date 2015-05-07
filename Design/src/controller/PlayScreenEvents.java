package controller;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.resManager;
import view.EnterScreen;
import view.ErrorScreen;
import view.ErrorScreen.closing;

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
		public OnMouseClicked(EnterScreen ES) {
			this.ES = ES;
		}

		@Override
		public void handle(Event event) {
			//metod(ES.nik.getText(),ES.ip.getText());        передача nik і ip
			try {
				play();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private void play() throws Exception{
			ImageView gif = new ImageView("pict/Global-tank-war.gif");
			AnchorPane.setLeftAnchor(gif, 0.0);
			AnchorPane.setTopAnchor(gif, 0.0);
			ES.anPane.getChildren().add(gif);
			//time();
			Timer timer = new Timer();
			Timer timer2 = new Timer();
			
			TimerTask task = new TimerTask() {
				public void run() {
					Platform.runLater(new Runnable() {
						public void run() {
							ImageView setImg = new ImageView("pict/setImg.png");
							ImageView tankAnim = new ImageView(
									"pict/Tank-animation.gif");
							AnchorPane.setLeftAnchor(setImg, 0.0);
							AnchorPane.setTopAnchor(setImg, 0.0);
							AnchorPane.setLeftAnchor(tankAnim, 0.0);
							AnchorPane.setTopAnchor(tankAnim, 320.0);
							ES.anPane.getChildren().add(setImg);
							ES.anPane.getChildren().add(tankAnim);
						}
					});
				}
			};
			TimerTask task2 = new TimerTask() {
				public void run() {
					Platform.runLater(new Runnable() {
						public void run() {
							//
						}
						
					});
				}
			};
			timer.schedule(task, 5000);
			timer2.schedule(task2, 8000);
		}

	}

}
