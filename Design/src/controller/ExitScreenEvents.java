package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import model.resManager;
import view.EnterScreen;

public class ExitScreenEvents {
    public static class OnMouseEntered implements EventHandler{

        @Override
        public void handle(Event event) {
            ImageView iv = (ImageView) event.getSource();
            iv.setImage(resManager.getExitPressedButtonImage());
        }
        
    }
    public static class OnMouseExit implements EventHandler{

        @Override
        public void handle(Event event) {
            ImageView iv = (ImageView) event.getSource();
            iv.setImage(resManager.getExitButtonImage());
        }
        
    }
    public static class OnMouseClicked implements EventHandler{
        private EnterScreen ES = null;

        public OnMouseClicked(EnterScreen ES) {
            this.ES = ES;
        }
        
        @Override
        public void handle(Event event) {
            ES.close();
        }
        
    }
}
