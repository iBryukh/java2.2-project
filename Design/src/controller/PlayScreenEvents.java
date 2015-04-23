package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import model.resManager;
import view.EnterScreen;

public class PlayScreenEvents {
    public static class OnMouseEntered implements EventHandler{

        @Override
        public void handle(Event event) {
            ImageView iv = (ImageView) event.getSource();
            iv.setImage(resManager.getPlayPressedButtonImage());
        }
        
    }
    public static class OnMouseExit implements EventHandler{

        @Override
        public void handle(Event event) {
            ImageView iv = (ImageView) event.getSource();
            iv.setImage(resManager.getPlayButtonImage());
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
           // ES.close();
        }
        
    }
}
