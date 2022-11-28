import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicManager {
    AudioInputStream inputStream;
    AudioInputStream inputStream2;
    long clipTimePosition;
    Clip bgmusic;
    Clip pointclip;
    Clip startclip;
    Clip deathclip;
    Clip eatingclip;
   MusicManager(){// public void

        {
            try {
                inputStream = AudioSystem.getAudioInputStream(new File("./images/wavFile.wav"));
                bgmusic = AudioSystem.getClip();
                bgmusic.open(inputStream);
//                FloatControl gainControl = (FloatControl) bgmusic.getControl(FloatControl.Type.MASTER_GAIN);
//                gainControl.setValue(-30.0f); // Reduce volume by 30 decibels.
                bgmusic.start();
                bgmusic.loop(Clip.LOOP_CONTINUOUSLY);//0





            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }
    void manageMusic(boolean pause,boolean gameOver){
       System.out.println("clicked" + gameOver + pause);
        if (gameOver){
            clipTimePosition = bgmusic.getMicrosecondPosition();
            bgmusic.stop(); // why stop don work here
            bgmusic.close();
            //bgmusic.flush();
        }
        else if(pause == false){
            bgmusic.setMicrosecondPosition(clipTimePosition);
            bgmusic.start();
        }
        else {
            clipTimePosition = bgmusic.getMicrosecondPosition();
            bgmusic.stop();
        }
    }
    void foodClip(){

        {
            try {
                inputStream2 = AudioSystem.getAudioInputStream(new File("./images/pointclip.wav"));
                eatingclip = AudioSystem.getClip();
                eatingclip.open(inputStream2);
                eatingclip.start();
              //  bgmusic.loop(Clip.LOOP_CONTINUOUSLY);//0





            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }
    void gameOverclip(){

        {
            try {
                inputStream2 = AudioSystem.getAudioInputStream(new File("./images/death.wav"));
                deathclip = AudioSystem.getClip();
                deathclip.open(inputStream2);
                deathclip.start();
                bgmusic.loop(0);//Clip.LOOP_CONTINUOUSLY





            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
