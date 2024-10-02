package character;

import main.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    public KeyHandler keyHandler;

    // Player images for movement and idle states
    private BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    private BufferedImage idle1, idle2, idle3;
    private int spriteCounter;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;
        setDefaultValues();
        loadPlayerImages();
    }

    // Initialize default player values
    public void setDefaultValues() {
        setWorldX(365);  // Starting position X
        setWorldY(255);  // Starting position Y
        setSpeed(2);     // Movement speed
        direction = "down"; // Default facing direction
        isMoving = false; // Player starts idle
    }

    // Get the collision box of the player
    public Rectangle getCollisionBox() {
        return new Rectangle(getWorldX(), getWorldY(), gamePanel.tileSize, gamePanel.tileSize);
    }

    // Load player images (movement and idle)
    public void loadPlayerImages() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_down2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_left2.png"));

            idle1 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_idle1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_idle2.png"));
            idle3 = ImageIO.read(getClass().getResourceAsStream("/ball/ball_idle3.png"));
        } catch (IOException e) {
            e.printStackTrace(); // Log any loading errors
        }
    }

    // Update player state
    public void update() {
        isMoving = false;

        // Temporary variables to hold the player's next position
        int nextX = getWorldX();
        int nextY = getWorldY();

        // Handle movement input
        if (keyHandler.upPressed) {
            direction = "up";
            if (getWorldY() - getSpeed() >= 0) {
                nextY -= getSpeed();
                isMoving = true;
            }
        }
        if (keyHandler.downPressed) {
            direction = "down";
            if (getWorldY() + getSpeed() + gamePanel.tileSize <= gamePanel.screenHeight) {
                nextY += getSpeed();
                isMoving = true;
            }
        }
        if (keyHandler.leftPressed) {
            direction = "left";
            if (getWorldX() - getSpeed() >= 0) {
                nextX -= getSpeed();
                isMoving = true;
            }
        }
        if (keyHandler.rightPressed) {
            direction = "right";
            if (getWorldX() + getSpeed() + gamePanel.tileSize <= gamePanel.screenWidth) {
                nextX += getSpeed();
                isMoving = true;
            }
        }

        // Create a rectangle representing the player's next position
        Rectangle nextPlayerBox = new Rectangle(nextX, nextY, gamePanel.tileSize, gamePanel.tileSize);

        // Perform collision detection with obstacles (e.g., pillars or walls)
        boolean collision = false;
        for (Pillar pillar : gamePanel.pillars) {  // Assuming you have a list of Pillars in GamePanel
            if (nextPlayerBox.intersects(pillar.getCollisionBox())) {
                collision = true;
                break;  // Stop checking if a collision is found
            }
        }

        // Only update the player's position if no collision is detected
        if (!collision) {
            setWorldX(nextX);
            setWorldY(nextY);
        }

        // Handle animation updates
        if (isMoving) {
            spriteCounter++;
            if (spriteCounter >= 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        } else {
            idleCounter++;
            if (idleCounter >= 15) {
                idleNum = (idleNum == 3) ? 1 : idleNum + 1;
                idleCounter = 0;
            }
        }
    }

    // Draw the player on the screen
    public void draw(Graphics2D g2d) {
        BufferedImage image = null;

        // If player is moving, select appropriate movement sprite
        if (isMoving) {
            switch (direction) {
                case "up":
                    image = (spriteNum == 1) ? up1 : up2;
                    break;
                case "down":
                    image = (spriteNum == 1) ? down1 : down2;
                    break;
                case "left":
                    image = (spriteNum == 1) ? left1 : left2;
                    break;
                case "right":
                    image = (spriteNum == 1) ? right1 : right2;
                    break;
            }
        } else {
            // If player is idle, cycle through idle sprites
            switch (idleNum) {
                case 1:
                    image = idle1;
                    break;
                case 2:
                    image = idle2;
                    break;
                case 3:
                    image = idle3;
                    break;
            }
        }

        // Draw the player at the current world position
        g2d.drawImage(image, getWorldX(), getWorldY(), gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
