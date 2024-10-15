package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler1 implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    private GamePanel1 gamePanel1; // Reference to the GamePanel1 instance

    // Constructor that accepts a GamePanel1 instance
    public KeyHandler1(GamePanel1 gamePanel1) {
        this.gamePanel1 = gamePanel1; // Set the gamePanel1 reference
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Handle movement keys
        if (key == KeyEvent.VK_W) {
            upPressed = true;
        } else if (key == KeyEvent.VK_S) {
            downPressed = true;
        } else if (key == KeyEvent.VK_A) {
            leftPressed = true;
        } else if (key == KeyEvent.VK_D) {
            rightPressed = true;
        }

        // Handle game start and exit
        if (key == KeyEvent.VK_ENTER) {
            if (gamePanel1.gameState == gamePanel1.titleState) {
                gamePanel1.gameState = gamePanel1.playState; // Start the game
                gamePanel1.requestFocusInWindow(); // Request focus for key inputs
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0); // Exit the game
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // Handle movement keys release
        if (key == KeyEvent.VK_W) {
            upPressed = false;
        } else if (key == KeyEvent.VK_S) {
            downPressed = false;
        } else if (key == KeyEvent.VK_A) {
            leftPressed = false;
        } else if (key == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
