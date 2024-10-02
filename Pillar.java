package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pillar {
    public int x, y; // Position of the pillar
    BufferedImage pillarImage;
    public static final int SIZE = 200; // Size of the pillar

    public Pillar(int x, int y) {
        this.x = x;
        this.y = y;

        // Load the pillar image
        try {
            pillarImage = ImageIO.read(getClass().getResourceAsStream("/NPC/Pillar.png")); // Replace with your pillar image path
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(pillarImage, x, y, SIZE, SIZE, null); // Draw the pillar
    }

    // Returns the collision box for this pillar
    public Rectangle getCollisionBox() {
        return new Rectangle(x, y, SIZE, SIZE);  // Collision area is the same as the image size
    }
}
