package Model;

import Controller.InputHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameState {
    private Player player;               // Player instance
    private List<Enemy> enemies;         // List of enemies
    private List<Bullet> bullets;        // List of bullets
    private boolean isGameOver;          // Flag for game-over state
    private int score;                   // Player's score
    private long lastBulletTime = 0;     // Tracks the time the last bullet was fired
    private static final int BULLET_COOLDOWN = 300; // Cooldown in milliseconds
    private int level;                   // Current level
    private int enemiesPerLevel;         // Number of enemies per level

    public GameState(String username, InputHandler inputHandler) {
        player = new Player(username, inputHandler);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        isGameOver = false;
        score = 0;
        level = 1;
        enemiesPerLevel = 6;  // Initial number of enemies per level
        initializeEnemies();   // Initialize enemies for the first level
    }

    /**
     * Initializes enemies at the start of the game.
     */
    private void initializeEnemies() {
        int enemyWidth = 40;  // Example width of each enemy
        int enemyHeight = 30; // Example height of each enemy
        int rows = 4;         // Keep the number of rows fixed
        int cols = enemiesPerLevel; // More enemies will spawn as level increases

        int spacingX = 20;    // Horizontal spacing between enemies
        int spacingY = 15;    // Vertical spacing between rows

        // Now enemies will spread horizontally
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                int x = 50 + col * (enemyWidth + spacingX); // Spread horizontally
                int y = 50 + row * (enemyHeight + spacingY); // Vertical spacing stays constant
                // Spawn enemies with increasing speed
                enemies.add(new Enemy(x, y, enemyWidth, enemyHeight, level * 2)); // Speed of enemies increases
            }
        }
        System.out.println("Enemies initialized: " + enemies.size());
    }

    /**
     * Updates the game state, including player, bullets, and enemies.
     */
    public void update() {
        if (isGameOver) return;

        // Update player actions
        player.update();

        // Handle shooting with cooldown
        long currentTime = System.currentTimeMillis();
        if (player.getInputHandler().shootPressed && currentTime - lastBulletTime >= BULLET_COOLDOWN) {
            bullets.add(player.shootBullet());
            lastBulletTime = currentTime; // Update the last bullet time
        }

        // Update bullets and handle collisions
        updateBullets();

        // Update enemies and their behavior
        updateEnemies();

        // Check if the game is over
        checkGameOver();

        // If the enemies are all destroyed, move to the next level
        if (enemies.stream().noneMatch(Enemy::isAlive)) {
            levelUp();
        }
    }

    /**
     * Increases the level, spawns more enemies, and increases their speed.
     */
    private void levelUp() {
        level++; // Increase the level
        enemiesPerLevel += 2; // Add more enemies for the new level
        System.out.println("Level " + level + " - New enemies spawn with increased speed!");

        // Clear old enemies and add new ones
        enemies.clear(); // Remove old enemies
        initializeEnemies(); // Initialize new enemies for the new level

        // After new enemies are added, they should start moving.
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.update(); // Update each new enemy to start moving
            }
        }
    }


    /**
     * Updates the bullets' positions and handles their interactions.
     */
    private void updateBullets() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();

            // Remove bullets that are out of bounds
            if (bullet.isOutOfBounds()) {
                bulletIterator.remove();
                continue;
            }

            // Check for collisions with enemies
            if (bullet.isPlayerBullet()) {
                handleBulletEnemyCollisions(bulletIterator, bullet);
            }
        }
    }

    /**
     * Handles collisions between player bullets and enemies.
     */
    private void handleBulletEnemyCollisions(Iterator<Bullet> bulletIterator, Bullet bullet) {
        for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext(); ) {
            Enemy enemy = enemyIterator.next();
            if (enemy.isAlive() && bullet.collidesWith(enemy)) {
                enemy.takeDamage(); // Reduce enemy health
                bulletIterator.remove(); // Remove the bullet
                score += 10; // Increment score for destroying an enemy

                if (!enemy.isAlive()) {
                    enemyIterator.remove();
                    System.out.println("Enemy destroyed at position (" + enemy.getX() + ", " + enemy.getY() + ")");
                    System.out.println("Enemy destroyed! Score: " + score);
                }
                break; // Exit after handling collision
            }
        }
    }

    /**
     * Handles collision between player bullets and enemies.
     */
    private void handlePlayerEnemyCollisions() {
        for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext(); ) {
            Enemy enemy = enemyIterator.next();
            if (enemy.isAlive() && player.collidesWith(enemy)) {
                enemy.takeDamage(); // Reduce enemy health
                score += 10; // Increment score for destroying an enemy
                player.takeDamage(10);

                if (!enemy.isAlive()) {
                    enemyIterator.remove();
                    System.out.println("Enemy destroyed at position (" + enemy.getX() + ", " + enemy.getY() + ")");
                }
                break; // Exit after handling collision
            }
        }
    }

    /**
     * Updates enemies' positions and interactions.
     */
    private void updateEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.update();
            }
        }
        handlePlayerEnemyCollisions();
    }

    /**
     * Checks for game-over conditions.
     */
    private void checkGameOver() {
        // If player's health is 0, the game is over.
        if (player.getHealth() <= 0) {
            isGameOver = true;
            System.out.println("Game Over! Player health reached 0.");
        } else if (enemies.stream().noneMatch(Enemy::isAlive)) {
            // All enemies are defeated, level completed
            System.out.println("Level " + level + " completed!");
            levelUp();  // Move to next level
        }
    }

    // Getters and Setters
    public Player getPlayer() {return player;}

    public void setPlayer(Player player) {this.player = player;}

    public List<Enemy> getEnemies() {return enemies;}

    public List<Bullet> getBullets() {return bullets;}

    public void addBullet(Bullet bullet) {bullets.add(bullet);}

    public boolean isGameOver() {return isGameOver;}

    public int getScore() {return score;}
}
