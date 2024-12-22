package View;

import Utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainMenu extends JFrame {
    private JTextField usernameField;    // Text field for username input
    private JButton startGameButton;    // Start Game button
    private JButton showScoreboardButton; // Show Scoreboard button
    private JButton lowerSoundButton;    // Lower volume button
    private JButton higherSoundButton;   // Increase volume button
    private final SoundManager soundManager; // SoundManager instance
    private Image backgroundImage;  // Field to store the background image

    public MainMenu() {
        soundManager = SoundManager.getInstance();

        // Frame setup
        setTitle("Space Invaders");
        setSize(700, 500);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("resources/img/mainMenu.png"));
        } catch (IOException e) {
            System.err.println("Background image not found: " + e.getMessage());
        }

        // Create a custom JPanel to handle background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);  // Call to the super method to paint components
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panel.setLayout(null);  // Use absolute positioning for components
        setContentPane(panel);  // Set the custom panel as the content pane

        // Welcome user label
        JLabel welcomeLabel = new JLabel("Welcome to Space Invaders!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(150, 50, 400, 50);
        panel.add(welcomeLabel);

        // Username label
        JLabel usernameLabel = new JLabel("Enter your username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameLabel.setBounds(200, 150, 200, 30);
        panel.add(usernameLabel);

        // Username text field
        usernameField = new JTextField();
        usernameField.setBounds(400, 150, 150, 30);
        panel.add(usernameField);

        // Start Game button
        startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        startGameButton.setBounds(250, 250, 200, 40);
        panel.add(startGameButton);

        // Show Scoreboard button
        showScoreboardButton = new JButton("Show Scoreboard");
        showScoreboardButton.setFont(new Font("Arial", Font.BOLD, 16));
        showScoreboardButton.setBounds(250, 320, 200, 40);
        panel.add(showScoreboardButton);

        // Lower sound button
        lowerSoundButton = new JButton("Lower Sound");
        lowerSoundButton.setFont(new Font("Arial", Font.BOLD, 14));
        lowerSoundButton.setBounds(150, 400, 150, 40);
        panel.add(lowerSoundButton);

        // Higher sound button
        higherSoundButton = new JButton("Higher Sound");
        higherSoundButton.setFont(new Font("Arial", Font.BOLD, 14));
        higherSoundButton.setBounds(400, 400, 150, 40);
        panel.add(higherSoundButton);

        // Attach button listeners for volume control
        lowerSoundButton.addActionListener(e -> soundManager.decreaseVolume());
        higherSoundButton.addActionListener(e -> soundManager.increaseVolume());

        // Play background music
        soundManager.playBackgroundMusic("resources/sound/SpaceWeed319.wav"); // Load background music
        soundManager.loop(); // Start looping background music

        // Make the frame visible
        setVisible(true);
    }

    // Getter methods
    public JTextField getUsernameField() {
        return usernameField;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public JButton getStartGameButton() {
        return startGameButton;
    }

    public JButton getShowScoreboardButton() {
        return showScoreboardButton;
    }

    public JButton getLowerSoundButton() {
        return lowerSoundButton;
    }

    public JButton getHigherSoundButton() {
        return higherSoundButton;
    }
}
