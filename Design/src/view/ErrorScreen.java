package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ErrorScreen extends Application {
	Stage mainStage = null;
	public ImageView screen = null;
	public AnchorPane anPane = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = new Stage(StageStyle.TRANSPARENT);
		StackPane root = new StackPane();

		screen = new ImageView("pict/TestErrorScreen.png");
		root.getChildren().add(screen);
		anPane = new AnchorPane();
		root.getChildren().add(anPane);

		Text text = new Text();
		text.setFont(new Font(50));
		text.setWrappingWidth(200);
		text.setTextAlignment(TextAlignment.JUSTIFY);
		text.setText("EROR");
		text.setFill(Color.RED);

		Button ok = new Button();
		ok.setText("OK");
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				close();
			}
		});

		AnchorPane.setLeftAnchor(text, 130.0);
		AnchorPane.setTopAnchor(text, 10.0);
		AnchorPane.setLeftAnchor(ok, 175.0);
		AnchorPane.setTopAnchor(ok, 200.0);

		anPane.getChildren().add(ok);
		anPane.getChildren().add(text);

		mainStage.setScene(new Scene(root, 400, 300, Color.TRANSPARENT));
		mainStage.setTitle("Global Tank War - Eror window");
		mainStage.show();

	}

	public void close() {
		Platform.runLater(new closing());
	}

	private class closing implements Runnable {

		@Override
		public void run() {
			mainStage.close();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
