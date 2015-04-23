package view;


import java.net.URL;

import controller.ExitScreenEvents;
import controller.PlayScreenEvents;
import controller.SoundScreenEvents;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.PasswordField;
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

public class EnterScreen extends Application {
    Stage mainStage = null;
    public TextField nik= null;
    public TextField ip = null;
    public ImageView screen = null;
    
    @Override
    public void start(Stage primaryStage) throws Exception {   
    	//model.Sound.sound();
        mainStage  = new  Stage(StageStyle.TRANSPARENT);  
       // mainStage  = new  Stage(); 
       
               
        StackPane root = new StackPane();        
        screen = new ImageView(resManager.getEnterScreenBackground());        
        root.getChildren().add(screen);        
        AnchorPane anPane = new AnchorPane();        
        root.getChildren().add(anPane);     
        
        nik = new TextField();
        ip = TextFieldBuilder.create()
        		.style("-fx-background-color:red;"      				
        				+ "-fx-border: px;")        	    
        	      .build();
 
        nik.setPrefSize(179, 24);
       ip.setPrefSize(179, 24);
       
        AnchorPane.setLeftAnchor(nik,180.0);
        AnchorPane.setLeftAnchor(ip, 180.0);
       
        AnchorPane.setTopAnchor(nik, 292.0);
        AnchorPane.setTopAnchor(ip, 340.0);   
        
        anPane.getChildren().add(nik);
        anPane.getChildren().add(ip); 
        
     //   
        ImageView exitButton = new ImageView(resManager.getExitButtonImage());
        
        
        exitButton.setOnMouseEntered(new ExitScreenEvents.OnMouseEntered());
        exitButton.setOnMouseExited(new ExitScreenEvents.OnMouseExit());
        exitButton.setOnMouseClicked(new ExitScreenEvents.OnMouseClicked(this));
        
        AnchorPane.setLeftAnchor(exitButton,650.0);
        AnchorPane.setTopAnchor(exitButton, 7.0); 

        
        anPane.getChildren().add(exitButton);
        
    //    
        ImageView playButton = new ImageView(resManager.getPlayButtonImage());
        
        playButton.setOnMouseEntered(new PlayScreenEvents.OnMouseEntered());
        playButton.setOnMouseExited(new PlayScreenEvents.OnMouseExit());
        playButton.setOnMouseClicked(new PlayScreenEvents.OnMouseClicked(this)); 
        
        AnchorPane.setLeftAnchor(playButton,169.0);
        AnchorPane.setTopAnchor(playButton, 400.0); 

        anPane.getChildren().add(playButton);
     //   
       ImageView soundButton = new ImageView(resManager.getSoundButtonImage());
        
        soundButton.setOnMouseEntered(new SoundScreenEvents.OnMouseEntered());
        soundButton.setOnMouseExited(new SoundScreenEvents.OnMouseExit());
        soundButton.setOnMouseClicked(new SoundScreenEvents.OnMouseClicked(soundButton)); 
        
        AnchorPane.setLeftAnchor(soundButton,665.0);
        AnchorPane.setTopAnchor(soundButton, 525.0); 

        anPane.getChildren().add(soundButton);
    

        
     
        
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
