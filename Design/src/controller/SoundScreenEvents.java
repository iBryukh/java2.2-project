package controller;

import controller.PlayScreenEvents.OnMouseClicked;
import controller.PlayScreenEvents.OnMouseEntered;
import controller.PlayScreenEvents.OnMouseExit;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import model.resManager;
import view.EnterScreen;

public class SoundScreenEvents {
	
	public static ImageView buttonSound(){
    	ImageView img = new ImageView(resManager.getSBI());
    	
    	img.setOnMouseEntered(new OnMouseEntered());
    	img.setOnMouseExited(new OnMouseExit());
    	img.setOnMouseClicked(new OnMouseClicked(img));
    	
        return img;
    	}

	
    public static class OnMouseEntered implements EventHandler{

        @Override
        public void handle(Event event) {
            ImageView iv = (ImageView) event.getSource();
            iv.setImage(resManager.getSPBI());
        }
        
    }
    public static class OnMouseExit implements EventHandler{

        @Override
        public void handle(Event event) {
            ImageView iv = (ImageView) event.getSource();
            iv.setImage(resManager.getSBI());
        }
        
    }
    public static class OnMouseClicked implements EventHandler{
        private ImageView ES = null;
        
        public OnMouseClicked(ImageView ES) {
            this.ES = ES;
            //ES.setImage(resManager.getSoundNotButtonImage());
        }
        
        @Override
        public void handle(Event event) {
        	
        	if(ES.equals(resManager.getSBI()))
        		ES.setImage(resManager.getSNBI());
           else ES.setImage(resManager.getSBI());
        	   
        	
        }
        
    }
}
