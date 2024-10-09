package character;

import main.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player1 extends Entity1 {
    public KeyHandler1 keyHandler1;

    // Player images for movement and idle states
    private BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    private BufferedImage idle1, idle2, idle3;
    private int spriteCounter;

    public Player1(GamePanel1 gamePanel1, KeyHandler1 keyHandler1) {
        super(gamePanel1);
        this.keyHandler1 = keyHandler1;
        setDefaultValues();
        loadPlayerImages();
    }

    // Initialize default player1 values
    public void setDefaultValues() {
        setWorldX(365);  // Starting position X
        setWorldY(255);  // Starting position Y
        setSpeed(2);     // Movement speed
        direction = "down"; // Default facing direction
        isMoving = false; // Player starts idle
    }

    // Get the collision box of the player1
    public Rectangle getCollisionBox() {
        return new Rectangle(getWorldX(), getWorldY(), gamePanel1.tileSize, gamePanel1.tileSize);
    }

    // Load player1 images (movement and idle)
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

    // Update player1 state
    public void update() {
        isMoving = false;

        // Temporary variables to hold the player1's next position
        int nextX = getWorldX();
        int nextY = getWorldY();

        // Handle movement input
        if (keyHandler1.upPressed) {
            direction = "up";
            nextY -= getSpeed();
            isMoving = true;
        }
        if (keyHandler1.downPressed) {
            direction = "down";
            nextY += getSpeed();
            isMoving = true;
        }
        if (keyHandler1.leftPressed) {
            direction = "left";
            nextX -= getSpeed();
            isMoving = true;
        }
        if (keyHandler1.rightPressed) {
            direction = "right";
            nextX += getSpeed();
            isMoving = true;
        }

        // Create separate rectangles for collision detection along each axis
        Rectangle nextPlayerBoxX = new Rectangle(nextX, getWorldY(), gamePanel1.tileSize, gamePanel1.tileSize);
        Rectangle nextPlayerBoxY = new Rectangle(getWorldX(), nextY, gamePanel1.tileSize, gamePanel1.tileSize);

        // Check for horizontal collision
        boolean horizontalCollision = false;
        for (Pillar pillar : gamePanel1.pillars) {
            if (nextPlayerBoxX.intersects(pillar.getCollisionBox())) {
                horizontalCollision = true;
                break;
            }
        }

        // If no horizontal collision, update X position
        if (!horizontalCollision) {
            setWorldX(nextX);
        }

        // Check for vertical collision
        boolean verticalCollision = false;
        for (Pillar pillar : gamePanel1.pillars) {
            if (nextPlayerBoxY.intersects(pillar.getCollisionBox())) {
                verticalCollision = true;
                break;
            }
        }

        // If no vertical collision, update Y position
        if (!verticalCollision) {
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

    // Draw the player1 on the screen
    public void draw(Graphics2D g2d) {
        BufferedImage image = null;

        // If player1 is moving, select appropriate movement sprite
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
            // If player1 is idle, cycle through idle sprites
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

        // Draw the player1 at the current world position
        g2d.drawImage(image, getWorldX(), getWorldY(), gamePanel1.tileSize, gamePanel1.tileSize, null);
    }
}
