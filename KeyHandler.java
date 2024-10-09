package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public GamePanel gamePanel;
    public UI ui;

    public KeyHandler(GamePanel gamePanel, UI ui) {
        this.gamePanel = gamePanel;
        this.ui = ui;
    }

    public boolean downPressed, leftPressed, upPressed, rightPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gamePanel.hasFocus()) {
            gamePanel.requestFocusInWindow();  // Regain focus if lost
        }

        int key = e.getKeyCode();

        // Handle key events based on game state
        if (gamePanel.gameState == gamePanel.titleState) {
            if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                if (ui.commandNum == 0) {
                    gamePanel.gameState = gamePanel.playState;  // Switch to play state
                    System.out.println("Game state changed to playState"); // Debug output
                }
            } else if (key == KeyEvent.VK_ESCAPE) {
                System.exit(0);  // Close the game when Escape is pressed
            }
        } else if (gamePanel.gameState == gamePanel.playState) {
            // Handle movement keys
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // Handle movement key release
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for now
    }
}
