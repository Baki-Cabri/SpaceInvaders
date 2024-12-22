package View;

import Controller.InputHandler;
import Model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GameRenderer extends JPanel {
    private GameState gameState; // Reference to the game state
    private InputHandler inputHandler; // Input handler for player controls
    private Player player;
    private BufferedImage backgroundImage; // Background image
    private BufferedImage gameOverImage; // Game Over image

    public GameRenderer(GameState gameState, InputHandler inputHandler) {
        this.gameState = gameState;
        this.inputHandler = inputHandler;
        this.player = gameState.getPlayer(); // Initialize player after gameState is assigned
        gameState.setPlayer(player);

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("resources/img/Background.jpeg"));
            gameOverImage = ImageIO.read(new File("resources/img/GameOver.png")); // Load Game Over image
        } catch (IOException e) {
            System.err.println("Image not found: " + e.getMessage());
        }

        // Add key listener to capture inputs
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(inputHandler);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Use Graphics2D for advanced rendering
        Graphics2D g2 = (Graphics2D) g;

        // Draw the game background
        drawBackground(g2);

        // If the game is over, show the Game Over image
        if (gameState.isGameOver()) {
            drawGameOver(g2);
        } else {
            // Draw the player, bullets, and enemies only if the game is not over
            drawPlayer(g2);
            drawBullets(g2);
            drawEnemies(g2);
        }
    }

    /**
     * Draws the background.
     */
    private void drawBackground(Graphics2D g2) {
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Draws the Game Over screen.
     */
    private void drawGameOver(Graphics2D g2) {
        if (gameOverImage != null) {
            int x = (getWidth() - gameOverImage.getWidth()) / 2;
            int y = (getHeight() - gameOverImage.getHeight()) / 2;
            g2.drawImage(gameOverImage, x, y, null);
        } else {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 48));
            g2.drawString("GAME OVER", getWidth() / 3, getHeight() / 2);
        }
    }

    /**
     * Draws the player.
     */
    private void drawPlayer(Graphics2D g2) {
        player.draw(g2);
    }

    /**
     * Draws all bullets in the game.
     */
    private void drawBullets(Graphics2D g2) {
        List<Bullet> bullets = gameState.getBullets();
        for (Bullet bullet : bullets) {
            bullet.draw(g2);
        }
    }

    /**
     * Draws all enemies in the game.
     */
    private void drawEnemies(Graphics2D g2) {
        List<Enemy> enemies = gameState.getEnemies();
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.draw(g2);
            }
        }
    }

    /**
     * Updates the game state and repaints the screen.
     */
    public void updateGame() {
        // Update game logic
        gameState.update();

        // Repaint the screen
        repaint();

        // Debugging
        System.out.println("GameRenderer repainted. Player position: (" + player.getX() + ", " + player.getY() + ")");
    }
}
