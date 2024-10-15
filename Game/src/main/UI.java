package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {
    GamePanel1 gamePanel1;
    private BufferedImage titleScreen;
    private BufferedImage gameBackground; 

    public UI(GamePanel1 gamePanel1) {
        this.gamePanel1 = gamePanel1;
        loadTitleScreenImage();     // Load the title screen image
        loadGameBackground();       // Load the game background image
    }

    // Method to load the title screen image
    private void loadTitleScreenImage() {
        try {
            titleScreen = ImageIO.read(getClass().getResourceAsStream("/res/Background/titleScreen.png"));
            if (titleScreen == null) {
                System.err.println("Failed to load title screen image.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException occurred while loading the title screen image.");
        }
    }

    // Method to load the game background image
    private void loadGameBackground() {
        try {
            gameBackground = ImageIO.read(getClass().getResourceAsStream("/res/Background/Background.png"));
            if (gameBackground == null) {
                System.err.println("Failed to load game background image.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException occurred while loading the game background image.");
        }
    }

    // Method to draw the UI elements
    public void draw(Graphics2D g2d) {
        if (gamePanel1.gameState == gamePanel1.titleState) {
            drawTitleScreen(g2d);
        } else if (gamePanel1.gameState == gamePanel1.playState) {
            drawGameScreen(g2d);
        }

        // Draw player's lives in any game state (except title state)
        if (gamePanel1.gameState != gamePanel1.titleState) {
            drawPlayerLives(g2d);
        }
    }

    // Method to draw the title screen
    private void drawTitleScreen(Graphics2D g2d) {
        // Draw the title screen image (with scaling to fit the screen)
        if (titleScreen != null) {
            g2d.drawImage(titleScreen, 0, 0, gamePanel1.screenWidth, gamePanel1.screenHeight, null);
        } else {
            // Fallback: fill screen with red if the image fails to load
            g2d.setColor(Color.RED);
            g2d.fillRect(0, 0, gamePanel1.screenWidth, gamePanel1.screenHeight);
        }

        // Draw the text over the title screen
        g2d.setFont(new Font("Arial", Font.ITALIC, 20));
        g2d.setColor(Color.WHITE);
        String text0 = "press";
        g2d.drawString(text0,350, 350);
        String text2 = "to start";
        g2d.drawString(text2,350, 388);

        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(new Color(59, 245, 235));
        String text1 = "ENTER";
        g2d.drawString(text1,350, 370);

        g2d.setFont(new Font("Arial", Font.CENTER_BASELINE,  15));
        g2d.setColor(Color.WHITE);
        String text3 = "press ESC to leave at anytime";
        g2d.drawString(text3,540, 560);
        
    }

    // Method to draw the game screen
    private void drawGameScreen(Graphics2D g2d) {
        // Draw the game background
        if (gameBackground != null) {
            g2d.drawImage(gameBackground, 0, 0, gamePanel1.screenWidth, gamePanel1.screenHeight, null);
        } else {
            // Clear the screen if the background is missing
            g2d.clearRect(0, 0, gamePanel1.screenWidth, gamePanel1.screenHeight);
        }

        // Add any additional game UI elements here
        drawGameUI(g2d);
    }

    // Method to draw player's lives
    private void drawPlayerLives(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Lives: " + gamePanel1.player1.lives, 360, 30); // Top-left corner
    }

    // Example method to draw additional game UI elements
    private void drawGameUI(Graphics2D g2d) {
        // Implement your game UI drawing logic here, e.g., score, health bars, etc.
    }
}
