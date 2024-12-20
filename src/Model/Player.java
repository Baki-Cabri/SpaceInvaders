package Model;

import Controller.InputHandler;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Player implements KeyListener {
    // Attributes
    private String name;
    private int health;
    private int score;
    private int positionX; // Horizontal position
    private int positionY; // Vertical position
    private int speed;     // Movement speed

    public BufferedImage default1, default2, default3, default4, default5;
    public BufferedImage left1, left2, left3, left4, left5;
    public BufferedImage right1, right2, right3, right4, right5;
    public String direction;

    InputHandler inputH;

    int spriteCounter = 0;
    int spriteNum = 1;


    /**
     * Sets the default values for the player attributes like health, score, position, and speed.
     */
    public void setDefaultValues() {
        this.health = 100;   // Default health
        this.score = 0;      // Initial score
        this.positionX = 400; // Default horizontal position
        this.positionY = 550; // Default vertical position
        this.speed = 5;      // Default movement speed
    }


    public void update() {
        // Check for input to move the player
        if (inputH.leftPressed == true) {
            direction = "left";
            positionX -= speed; // Move left
        } else if (inputH.rightPressed == true) {
            direction = "right";
            positionX += speed; // Move right
        } else {
            direction = "default"; // No movement
        }

        // Handle sprite animation
        spriteCounter++;
        if (spriteCounter > 5) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            } else if (spriteNum == 3) {
                spriteNum = 4;
            } else if (spriteNum == 4) {
                spriteNum = 5;
            }
            spriteCounter = 0; // Reset sprite counter
        }
    }

    /**
     * Simulates the player shooting a bullet.
     * Includes placeholder logic for handling bullet firing.
     */
    public void shoot() {
        System.out.println("Player shot a bullet!"); // Log for debug
        // Logic for firing a bullet can be added here.
    }

    /**
     * Decreases the player's health by the specified damage amount.
     * If health drops to 0 or below, the player is defeated.
     *
     * @param damage The amount of damage taken.
     */
    public void takeDamage(int damage) {
        this.health -= damage; // Decrease health
        if (this.health <= 0) {
            this.health = 0; // Ensure health does not go below 0
            System.out.println(name + " has been defeated!");
        }
    }

    /**
     * Adds points to the player's score.
     *
     * @param points The number of points to add to the score.
     */
    public void addScore(int points) {
        this.score += points; // Increase score
    }


    private BufferedImage pSprite;
    private double xPos;
    private double yPos;
    private int width;
    private int height;
    private Rectangle rect;

    public Player(double xPos, double yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        //for collision
        rect = new Rectangle((int) xPos, (int) yPos, width, height);

        try {
            URL url = this.getClass().getResource("src/resources/img/spaceShip/default/default1.png");
            pSprite= ImageIO.read(url);
        }catch (IOException e){}

    }

    public void draw(Graphics2D g){
        g.drawImage(pSprite, (int) xPos, (int) yPos, width, height, null);
    }
    public void update(double delta){

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
