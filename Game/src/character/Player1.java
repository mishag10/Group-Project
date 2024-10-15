package character;

import main.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List; 

public class Player1 extends Entity1 {
    public KeyHandler1 keyHandler1;
    private BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    private int spriteCounter;
    public int lives;

    public Player1(GamePanel1 gamePanel1, KeyHandler1 keyHandler1) {
        super(gamePanel1);
        this.keyHandler1 = keyHandler1;
        setDefaultValues();
        loadPlayerImages();
        lives = 3;
    }

    public void setDefaultValues() {
        setWorldX(365);
        setWorldY(255);
        setSpeed(2);
        direction = "down";
        isMoving = false;
    }

    // Make sure this method is defined in Entity1
    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(worldX, worldY, gamePanel1.tileSize, gamePanel1.tileSize);
    }

    public void loadPlayerImages() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/ball/ball_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/ball/ball_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/ball/ball_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/ball/ball_down2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/ball/ball_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/ball/ball_right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/ball/ball_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/ball/ball_left2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(List<Pillar> pillars) {
        if (keyHandler1.upPressed) {
            direction = "up";
            isMoving = true;
        } else if (keyHandler1.downPressed) {
            direction = "down";
            isMoving = true;
        } else if (keyHandler1.leftPressed) {
            direction = "left";
            isMoving = true;
        } else if (keyHandler1.rightPressed) {
            direction = "right";
            isMoving = true;
        } else {
            isMoving = false;
        }

        if (isMoving) {
            move(pillars);
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteCounter = 0;
            }
        }
    }

    private void move(List<Pillar> pillars) {
        int newWorldX = worldX;
        int newWorldY = worldY;

        switch (direction) {
            case "up":
                newWorldY -= speed;
                break;
            case "down":
                newWorldY += speed;
                break;
            case "left":
                newWorldX -= speed;
                break;
            case "right":
                newWorldX += speed;
                break;
        }

        Rectangle tempCollisionBox = new Rectangle(newWorldX, newWorldY, gamePanel1.tileSize, gamePanel1.tileSize);

        boolean collision = false;
        for (Pillar pillar : pillars) {
            if (tempCollisionBox.intersects(pillar.getCollisionBox())) {
                collision = true; 
                break;
            }
        }

        if (!collision) {
            if (newWorldX >= 0 && newWorldX <= gamePanel1.screenWidth - gamePanel1.tileSize &&
                newWorldY >= 0 && newWorldY <= gamePanel1.screenHeight - gamePanel1.tileSize) {
                worldX = newWorldX;
                worldY = newWorldY;
            }
        }
    }

    public void loseLife() {
        lives--; 
        if (lives <= 0) {
            lives = 0; 
            System.out.println("Game Over!");
        }
    }

    public void reset() {
        setDefaultValues(); 
        lives = 3; 
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image;
        if (direction.equals("up")) {
            image = (spriteCounter < 6) ? up1 : up2;
        } else if (direction.equals("down")) {
            image = (spriteCounter < 6) ? down1 : down2;
        } else if (direction.equals("left")) {
            image = (spriteCounter < 6) ? left1 : left2;
        } else {
            image = (spriteCounter < 6) ? right1 : right2;
        }
        g2d.drawImage(image, worldX, worldY, gamePanel1.tileSize, gamePanel1.tileSize, null);
    }
}
