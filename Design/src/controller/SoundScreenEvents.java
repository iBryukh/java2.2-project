package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import model.resManager;
import view.EnterScreen;

public class SoundScreenEvents {
    public static class OnMouseEntered implements EventHandler{

        @Override
        public void handle(Event event) {
            ImageView iv = (ImageView) event.getSource();
            iv.setImage(resManager.getSoundPressedButtonImage());
        }
        
    }
    public static class OnMouseExit implements EventHandler{

        @Override
        public void handle(Event event) {
            ImageView iv = (ImageView) event.getSource();
            iv.setImage(resManager.getSoundButtonImage());
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
        	
        	if(ES.equals(resManager.getSoundButtonImage()))
        		ES.setImage(resManager.getSoundNotButtonImage());
           else ES.setImage(resManager.getSoundButtonImage());
        	   
        	
        }
        
    }
}
