package main;

import character.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pillar extends Entity1 {
    private BufferedImage image;

    public Pillar(GamePanel1 gamePanel1, int x, int y) {
        super(gamePanel1); // Pass the gamePanel1 instance to the superclass
        this.worldX = x;
        this.worldY = y;
        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/NPC/Pillar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, worldX, worldY, 15, 70, null);
    }
}
