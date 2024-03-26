package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private Clip clip;

    public Music(String songFilePath) {
        try {
            File audioFile = new File(songFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void playMusic() {
        if (clip != null && !clip.isRunning()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}