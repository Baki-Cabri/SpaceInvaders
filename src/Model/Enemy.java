package Model;

import Utils.SoundManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * The Enemy class represents an enemy entity in the game.
 * It handles loading the enemy sprite, updating its position,
 * and rendering it on the screen.
 */
public class Enemy extends EntityBase {
    // Attributes
    private int speed;            // Speed of the enemy's horizontal movement
    private BufferedImage sprite; // Sprite image for the enemy
    private boolean isAlive;      // Whether the enemy is alive
    private int health;           // Health of the enemy (optional for future damage handling)
    private int scaledWidth;
    private int scaledHeight;
    private SoundManager soundManager;

    // Constructor initializes the enemy's position, speed, and health
    public Enemy(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;       // Set movement speed
        this.isAlive = true;      // Set enemy as alive by default
        this.health = 1;          // Default health (can be customized)
        loadSprite();             // Load the enemy sprite
        soundManager = SoundManager.getInstance();
    }

    /**
     * Loads the sprite for the enemy from the resources directory.
     */
    private void loadSprite() {
        try {
            // Update the path to correctly locate the alien image
            String alienImagePath = System.getProperty("user.dir") + "/resources/img/alien.png";
            sprite = ImageIO.read(new File(alienImagePath));
            // System.out.println("Loaded sprite for enemy: " + alienImagePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load enemy sprite.");
        }
    }

    /**
     * Updates the enemy's position and movement behavior.
     */
    public void update() {
        if (!isAlive) return; // Skip updating if the enemy is dead

        // Horizontal movement
        x += speed;

        // Change direction and move down when hitting screen edges
        if (x <= 0 || x >= 760) { // Assuming a screen width of 800px
            speed = -speed;
            y += 20; // Move down by 20px when changing direction
        }

        // Debugging: Log position updates
        // System.out.println("Enemy updated: (" + x + ", " + y + "), Speed: " + speed);
    }



    /**
     * Draws the enemy on the screen.
     *
     * @param g2 The Graphics2D object used for rendering.
     */
    public void draw(Graphics2D g2) {
        if (isAlive) {
            if (sprite != null) {
                // Draw the sprite if available
                scaledWidth = sprite.getWidth() * 2;
                scaledHeight = sprite.getHeight() * 2;
                g2.drawImage(sprite, x, y, null);
                g2.drawImage(sprite, x, y, scaledWidth, scaledHeight, null);

            } else {
                // Fallback: Draw a red rectangle if sprite is missing
                g2.setColor(Color.RED);
                g2.fillRect(x, y, 40, 30); // Default size
            }
        }
    }

    /**
     * Handles damage to the enemy.
     * If health reaches 0, the enemy is marked as dead.
     */
    public void takeDamage() {
        health--; // Reduce health by 1
        if (health <= 0) {
            isAlive = false; // Mark the enemy as dead
            System.out.println("Enemy destroyed at position (" + x + ", " + y + ")");
            soundManager.enemyHitSound();
        } else {
            System.out.println("Enemy took damage. Remaining health: " + health);
        }
    }

    // Getters and Setters
    public boolean isAlive() {
        return isAlive;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}