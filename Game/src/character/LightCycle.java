package character;

import main.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class LightCycle extends Entity1 {
    private BufferedImage image;
    private double speed;
    private double maxSpeed; // Maximum speed
    private double acceleration; // Rate of speed increase
    private double randomMovementFactor; // Factor to add randomness to movement

    public LightCycle(GamePanel1 gamePanel1, int id, int startX, int startY) {
        super(gamePanel1);
        setWorldX(startX);
        setWorldY(startY);
        speed = 0.5; // Initial speed for light cycles
        maxSpeed = 3.0; // Set a maximum speed
        acceleration = 0.01; // Rate of speed increase
        randomMovementFactor = 0.1; // Adjust this for more or less randomness
        loadImage(); 
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/NPC/TRON_lightCycles(C).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        move(); 
        checkCollisionWithPlayer(); 
    }

    private void move() {
        // Get the player's position
        int playerX = gamePanel1.player1.getWorldX();
        int playerY = gamePanel1.player1.getWorldY();
    
        // Calculate the difference in position
        double deltaX = playerX - worldX;
        double deltaY = playerY - worldY;
        
        // Calculate the angle towards the player
        double angle = Math.atan2(deltaY, deltaX);
        
        // Add some randomness to the movement direction
        angle += (Math.random() - 0.5) * randomMovementFactor; // Randomly deviate the angle
        
        // Gradually increase speed, ensuring it doesn't decrease
        if (speed < maxSpeed) {
            speed += acceleration; // Increase speed
        }
        
        // Move in the direction of the player
        worldX += speed * Math.cos(angle); // Move in X direction
        worldY += speed * Math.sin(angle); // Move in Y direction

        // Optional: Wrap around the screen
        if (worldX > gamePanel1.screenWidth) {
            worldX = 0;
        } else if (worldX < 0) {
            worldX = gamePanel1.screenWidth - gamePanel1.tileSize;
        }
        
        if (worldY > gamePanel1.screenHeight) {
            worldY = 0;
        } else if (worldY < 0) {
            worldY = gamePanel1.screenHeight - gamePanel1.tileSize;
        }
    }

    private void checkCollisionWithPlayer() {
        Rectangle lightCycleBox = new Rectangle(worldX, worldY, gamePanel1.tileSize, gamePanel1.tileSize);
        Rectangle playerBox = new Rectangle(gamePanel1.player1.getWorldX(), gamePanel1.player1.getWorldY(), gamePanel1.tileSize, gamePanel1.tileSize);
        
        if (lightCycleBox.intersects(playerBox)) {
            gamePanel1.player1.loseLife(); // Player loses a life if hit by a light cycle
            gamePanel1.resetGame(); // Reset the game after collision
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, worldX, worldY, gamePanel1.tileSize, gamePanel1.tileSize, null);
    }
}
