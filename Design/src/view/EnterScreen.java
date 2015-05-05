package view;


import java.io.File;

import controller.ExitScreenEvents;
import controller.PlayScreenEvents;
import controller.SoundScreenEvents;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.resManager;

@SuppressWarnings("deprecation")
public class EnterScreen extends Application {
    Stage mainStage = null;
    public TextField nik= null;
    public TextField ip = null;
    public ImageView screen = null;
	public AnchorPane anPane=null;
	public StackPane root=null;
	public MediaPlayer mediaPlayer=null;
    
    @Override
    public void start(Stage primaryStage) throws Exception {   
    	//model.Sound.sound();
        mainStage  = new  Stage(StageStyle.TRANSPARENT);  
       // mainStage  = new  Stage(); 
            
        root = new StackPane();        
        screen = new ImageView(resManager.getEnterScreenBackground());        
        root.getChildren().add(screen);        
        anPane = new AnchorPane();        
        root.getChildren().add(anPane);   
        
        
        String musicFile = "testSound.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        
        nik =textField();
        ip = textField();
        
        nik.setPrefSize(179, 24);
        ip.setPrefSize(179, 24);
       
        AnchorPane.setLeftAnchor(nik,180.0);
        AnchorPane.setLeftAnchor(ip, 180.0);
       
        AnchorPane.setTopAnchor(nik, 292.0);
        AnchorPane.setTopAnchor(ip, 340.0);   
        
        anPane.getChildren().add(nik);
        anPane.getChildren().add(ip);  
    
        ImageView exitButton =ExitScreenEvents.buttonExit(this);
        ImageView playButton = PlayScreenEvents.buttonPlay(this);
        ImageView soundButton = SoundScreenEvents.buttonSound(this);
        
        AnchorPane.setLeftAnchor(exitButton,650.0);
        AnchorPane.setTopAnchor(exitButton, 7.0); 
        
        AnchorPane.setLeftAnchor(playButton,169.0);
        AnchorPane.setTopAnchor(playButton, 400.0); 
        
        AnchorPane.setLeftAnchor(soundButton,665.0);
        AnchorPane.setTopAnchor(soundButton, 525.0); 
        
        anPane.getChildren().add(exitButton);
        anPane.getChildren().add(playButton);
        anPane.getChildren().add(soundButton);
  
   /*     ImageView gif = new ImageView("pict/Global-tank-war.gif");        
        AnchorPane.setLeftAnchor(gif,0.0);
        AnchorPane.setTopAnchor(gif, 0.0); 
        anPane.getChildren().add(gif); */
       
        
       // new Scene(root, 800, 600,Color.TRANSPARENT);
        mainStage.setScene(new Scene(root, 800, 600,Color.TRANSPARENT));
        //  mainStage.initStyle(StageStyle.TRANSPARENT);
        //mainStage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        mainStage.setTitle("Global Tank War");
        // mainStage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        // Scene scene = new Scene(root, 1024, 768, null);
        // mainStage.setScene(scene);
        
        mainStage.show();

    }
    
    private TextField textField(){
    	TextField field =TextFieldBuilder.create()
        		.style("-fx-background-color:#000000;"
        				+ "-fx-border-color: #4682b4;"   
        				+ "-fx-text-fill:#4682b4;"
        				+ "-fx-border-radius: 5px ;"
        				+ "")        	    
        	      .build();
		return field; 	
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
