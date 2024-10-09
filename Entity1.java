package character;

import main.GamePanel1;

public class Entity1 {
    // Position and movement variables
    public int worldX;
    public int worldY;
    public int speed;
    public String direction;
    public boolean isMoving;

    // Sprite counters for animations
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int idleCounter = 0;
    public int idleNum = 1;

    // Reference to the GamePanel
    GamePanel1 gamePanel1;

    // Life variables
    private int maxLife;
    private int endLife;

    // Constructor
    public Entity1(GamePanel1 gamePanel1) {
        this.gamePanel1 = gamePanel1;
        // Initialize life variables to default values
        this.maxLife = 3; // Example default
        this.endLife = 0; // Example default
    }

    // Getter and setter methods
    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    // Life management methods
    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getEndLife() {
        return endLife;
    }

    public void setEndLife(int endLife) {
        this.endLife = endLife;
    }
}
