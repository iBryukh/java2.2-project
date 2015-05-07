package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
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
	public String errorText = "";
	public Text textError=null;

	public ErrorScreen(String errorT) throws Exception {
		errorText=errorT;
		Stage stage= new Stage();
		start(stage);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mainStage = new Stage(StageStyle.TRANSPARENT);
		StackPane root = new StackPane();

		screen = new ImageView("pict/ErrorScreen.png");
		root.getChildren().add(screen);
		anPane = new AnchorPane();
		root.getChildren().add(anPane);

		Text text = new Text();
		text.setFont(new Font(50));
		text.setWrappingWidth(200);
		text.setTextAlignment(TextAlignment.JUSTIFY);
		text.setText("ERROR");
		text.setFill(Color.RED);
		
		textError = new Text();
		textError.setFont(new Font(25));
		textError.setWrappingWidth(200);
		textError.setTextAlignment(TextAlignment.JUSTIFY);
		textError.setText(errorText);
		textError.setFill(Color.RED);

		
		Button ok = new Button();
		ok.setText("OK");
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				close();
			}
		});
		AnchorPane.setLeftAnchor(textError,130.0);
		AnchorPane.setTopAnchor(textError, 100.0);
		AnchorPane.setLeftAnchor(text, 130.0);
		AnchorPane.setTopAnchor(text, 10.0);
		AnchorPane.setLeftAnchor(ok, 175.0);
		AnchorPane.setTopAnchor(ok, 200.0);

		anPane.getChildren().add(ok);
		anPane.getChildren().add(text);
		anPane.getChildren().add(textError);
		Scene scene=new Scene(root, 400, 300, Color.TRANSPARENT);
		scene.getStylesheets().add((getClass().getResource("application.css")).toExternalForm());
		mainStage.setScene(scene);
		mainStage.setTitle("Global Tank War - Eror window");
		mainStage.show();

	}

	public void close() {
		Platform.runLater(new closing());
	}
	public class closing implements Runnable {

		@Override
		public void run() {
			mainStage.close();
		}
	}
}
