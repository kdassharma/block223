package ca.mcgill.ecse223.block.view;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class MusicClass extends JFrame {
	private Clip musicClip;
	//switches
	
	public MusicClass(String musicChosen)
	{
		run(musicChosen);
	}
	
	private void run(String musicChosen) {     
      try {
        // Open an audio input stream.           
        File soundFile = new File(musicChosen); 
        
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
        
        // Get a sound clip resource.)
        musicClip = AudioSystem.getClip();
        
        // Open audio clip and load samples from the audio input stream.
        musicClip.open(audioIn);
        
      } catch (UnsupportedAudioFileException e) {
    	  System.out.println("check1");
      } catch (IOException e) {
    	  System.out.println("check2");
      } catch (LineUnavailableException e) {
    	  System.out.println("check3");
      }
   }

   public void playMusic() {
	   musicClip.start();
   }
   
   public void stopMusic() {
	   musicClip.stop();
		   
   }
   public void endMusic()
   {
	   musicClip.flush();
   }
}