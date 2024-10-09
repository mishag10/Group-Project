package character;

import main.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LightCycle {
    GamePanel1 gamePanel1;
    public int x, y; // Current position
    public int speed; // Current speed
    BufferedImage lightCycleImage;

    public LightCycle(GamePanel1 gamePanel1, int cycleNumber, int startX, int startY) {
        this.gamePanel1 = gamePanel1;

        // Load the light cycle image
        try {
            if (cycleNumber == 0) {
                lightCycleImage = ImageIO.read(getClass().getResourceAsStream("/NPC/TRON_lightCycles(C).png"));
            } else if (cycleNumber == 1) {
                lightCycleImage = ImageIO.read(getClass().getResourceAsStream("/NPC/TRON_lightCycles(Y).png"));
            }
            // Removed up and down cycles
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set initial position and speed
        this.x = startX;
        this.y = startY;
        this.speed = 2; // Base speed
    }

    public void update() {
        // Get player1 position
        int playerX = gamePanel1.player1.getWorldX();
        int playerY = gamePanel1.player1.getWorldY();

        // Calculate direction towards the player1
        if (Math.abs(x - playerX) > Math.abs(y - playerY)) {
            // Move horizontally first
            if (x < playerX) {
                x += speed;
            } else {
                x -= speed;
            }
        } else {
            // Move vertically first
            if (y < playerY) {
                y += speed;
            } else {
                y -= speed;
            }
        }

        // Boundary checks
        if (x < 0) x = 0;
        if (x > gamePanel1.screenWidth - 70) x = gamePanel1.screenWidth - 70; // 70 is the width of the light cycle image
        if (y < 0) y = 0;
        if (y > gamePanel1.screenHeight - 70) y = gamePanel1.screenHeight - 70; // 70 is the height of the light cycle image

        // Check for collision with player1
        if (Math.abs(x - playerX) < 48 && Math.abs(y - playerY) < 48) {
            gamePanel1.gameState = gamePanel1.gameOverState;
        }

        // Avoid overlapping with other light cycles
        for (LightCycle otherCycle : gamePanel1.lightCycles) {
            if (otherCycle != this && Math.abs(x - otherCycle.x) < 48 && Math.abs(y - otherCycle.y) < 48) {
                // Move away from the other cycle
                if (x < otherCycle.x) {
                    x -= speed; // Move left
                } else {
                    x += speed; // Move right
                }
                if (y < otherCycle.y) {
                    y -= speed; // Move up
                } else {
                    y += speed; // Move down
                }
            }
        }

        // Handle collision with pillars
        for (Pillar pillar : gamePanel1.pillars) {
            if (Math.abs(x - pillar.x) < 70 && Math.abs(y - pillar.y) < 70) {
                // Move back in the opposite direction or stop movement
                if (x < pillar.x) {
                    x -= speed; // Move left
                } else {
                    x += speed; // Move right
                }
                if (y < pillar.y) {
                    y -= speed; // Move up
                } else {
                    y += speed; // Move down
                }
            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(lightCycleImage, x, y, 70, 70, null); // Draw the light cycle
    }
}
