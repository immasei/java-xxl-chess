package XXLChess;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

 /**
  * Sound class
  */
public class Sound {
    /**
     * clip sound
     */
    private Clip chess;

    /**
     * state of running sound
     */
    private boolean ok = true;

    /**
     * Sound constructor, requires a file path
     * @param loc, file path
     */
    public Sound(String loc) {
        try {
            File file = new File(loc);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            chess = AudioSystem.getClip();
            chess.open(sound);
        } catch (Exception e) {
            this.ok = false;
        }
    }

    /**
     * run sound file
     * in case the file does not work, game runs as normal
     */
    public void play(){
        if (this.ok) {
            chess.setFramePosition(0);  //rewind
            chess.start();              //play
        } 
    }
}