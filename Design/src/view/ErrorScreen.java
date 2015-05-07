package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
	private static String errorText;
	public Text textError=null;

	@Override
	public void start(Stage primaryStage) {
		
		mainStage = new Stage(StageStyle.TRANSPARENT);
		StackPane root = new StackPane();

		screen = new ImageView("pict/ErrorScreen.png");
		root.getChildren().add(screen);
		anPane = new AnchorPane();
		root.getChildren().add(anPane);

		Text t=new Text();
		t.setText(errorText);
		t.setFill(Color.WHITE);
		/*t.setPrefRowCount(5);
        t.setPrefColumnCount(50);
        t.setWrapText(true);
        t.setPrefWidth(175);*/
		
		Button ok = new Button();
		ok.setText("OK");
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				close();
			}
		});
		AnchorPane.setLeftAnchor(t,130.0);
		AnchorPane.setTopAnchor(t, 100.0);
		AnchorPane.setLeftAnchor(ok, 175.0);
		AnchorPane.setTopAnchor(ok, 250.0);

		anPane.getChildren().add(ok);
		anPane.getChildren().add(t);
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
	
	public static void open (String args) {
		errorText=args;
		new ErrorScreen().start (new Stage());
	}
	
	}
