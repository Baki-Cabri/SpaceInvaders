package Utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SoundManager {
    private static SoundManager instance; // Singleton instance
    private Clip soundClip; // Clip for audio playback
    private FloatControl volumeControl; // For adjusting volume
    private final URL[] soundURLs = new URL[30]; // Array to store sound file paths
    private Clip backgroundMusicClip; // Clip for background music

    // Private constructor to prevent direct instantiation
    private SoundManager() {
        // Background music
        soundURLs[0] = getClass().getResource("/sound/Unorganic Asteroid Beat.wav");
        soundURLs[1] = getClass().getResource("resources/sound/SpaceWeed319.wav");

        // Sound effects
        soundURLs[2] = getClass().getResource("/sound/PlayerShoot.wav");
        soundURLs[3] = getClass().getResource("/sound/EnemyShoot.wav");
        soundURLs[4] = getClass().getResource("/sound/BlasterCollisionSound.wav");
    }

    // Public method to get the singleton instance
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void play() {
        if (soundClip != null) {
            soundClip.start();
        }
    }

    public void loop() {
        if (soundClip != null) {
            soundClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (soundClip != null && soundClip.isRunning()) {
            soundClip.stop();
        }
    }

    public void increaseVolume() {
        if (volumeControl != null) {
            float currentVolume = volumeControl.getValue();
            volumeControl.setValue(Math.min(currentVolume + 2.0f, volumeControl.getMaximum()));
            System.out.println("Volume increased to: " + volumeControl.getValue());
        } else {
            System.err.println("Volume control not available.");
        }
    }

    public void decreaseVolume() {
        if (volumeControl != null) {
            float currentVolume = volumeControl.getValue();
            volumeControl.setValue(Math.max(currentVolume - 2.0f, volumeControl.getMinimum()));
            System.out.println("Volume decreased to: " + volumeControl.getValue());
        } else {
            System.err.println("Volume control not available.");
        }
    }



    public void playBackgroundMusic(String filePath) {
        stopBackgroundMusic();
        try {
            System.out.println("Loading music file: " + filePath);
            File musicFile = new File(filePath);
            if (!musicFile.exists()) {
                System.err.println("Music file not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);

            // Retrieve the volume control after opening the clip
            if (backgroundMusicClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(-20); // Set default volume level
            }

            System.out.println("Starting background music...");
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop continuously
            backgroundMusicClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();  // Stop playback
            backgroundMusicClip.close(); // Release resources
            backgroundMusicClip = null; // Reset the Clip reference
            System.out.println("Background music stopped and resources released.");
        }
    }

    public void playShootSound() {
        try {
            // Load the sound file
            File soundFile = new File("resources/sound/PlayerShoot.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Play the sound
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void enemyHitSound() {
        try {
            // Load the sound file
            File soundFile = new File("resources/sound/BlasterCollisionSound.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Play the sound
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
