package model;

import javafx.scene.image.Image;

public class resManager {
	
    public static Image getEnterScreenBackground(){
       return new Image("pict/EnterScreen.png"); 
    }
    //Exit
    public static Image getExitButtonImage(){
        return new Image("pict/ExitButtonImage.png");
    }
    
    public static Image getExitPressedButtonImage(){
        return new Image("pict/ExitPressedButtonImage.png");
    }
    //Play
     public static Image getPlayButtonImage(){
         return new Image("pict/PlayButtonImage.png");
     }
     
     public static Image getPlayPressedButtonImage(){
         return new Image("pict/PlayPressedButtonImage.png");
     }
     //Sound 
     public static Image getSoundButtonImage(){
         return new Image("pict/SoundButtonImage.png");
     }
     
      public static Image getSoundNotButtonImage(){
          return new Image("pict/SoundNotButtonImage.png");
      }
      
      public static Image getSoundPressedButtonImage(){
          return new Image("pict/SoundPressedButtonImage.png");
      }
}
