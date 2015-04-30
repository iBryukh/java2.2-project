package controller;

import controller.ExitScreenEvents.OnMouseClicked;
import controller.ExitScreenEvents.OnMouseEntered;
import controller.ExitScreenEvents.OnMouseExit;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.resManager;
import view.EnterScreen;

	public class PlayScreenEvents {
	
		public static ImageView buttonPlay(EnterScreen ES){
			ImageView img = new ImageView(resManager.getPBI());
    	
			img.setOnMouseEntered(new OnMouseEntered());
			img.setOnMouseExited(new OnMouseExit());
			img.setOnMouseClicked(new OnMouseClicked(ES));
    	
			return img;
		}
		
    public static class OnMouseEntered implements EventHandler{

        @Override
        public void handle(Event event) {
            ImageView iv = (ImageView) event.getSource();
            iv.setImage(resManager.getPPBI());
        }
        
    }
    public static class OnMouseExit implements EventHandler{

        @Override
        public void handle(Event event) {
            ImageView iv = (ImageView) event.getSource();
            iv.setImage(resManager.getPBI());
        }
        
    }
   
    public static class OnMouseClicked implements EventHandler{
        private EnterScreen ES = null;
        
        //ES присвоїти вікно з грою або загрузкою

        public OnMouseClicked(EnterScreen ES) {
            this.ES = ES;
        }
        
        @Override
        public void handle(Event event) {
 
        	 ImageView gif = new ImageView("pict/Global-tank-war.gif");        
             AnchorPane.setLeftAnchor(gif,0.0);
             AnchorPane.setTopAnchor(gif, 0.0); 
             ES.anPane.getChildren().add(gif);
          
        }
        
    }
    
    
    public class EnterScreenPlay extends Application {

		@Override
		public void start(Stage arg0) throws Exception {
			// TODO Auto-generated method stub
			Stage mainStage  = new  Stage(StageStyle.TRANSPARENT);  
			StackPane root = new StackPane();  
	  
			AnchorPane anPane = new AnchorPane();
			ImageView gif = new ImageView("pict/Global-tank-war.gif"); 
			AnchorPane.setLeftAnchor(gif,0.0);
			AnchorPane.setTopAnchor(gif, 0.0); 
			anPane.getChildren().add(gif); 
			mainStage.setScene(new Scene(root, 800, 600,Color.TRANSPARENT));    
			mainStage.setTitle("Global Tank War");
			mainStage.show();
		}
    	
    }
}
