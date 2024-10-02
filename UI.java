package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UI {

    BufferedImage FullHeart, EmptyHeart;
    public GamePanel gamePanel;
    public Graphics2D g2d;
    public Font titleFont, normalFont;
    public BufferedImage titleScreenImage;
    public int commandNum = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        titleFont = new Font("Arial", Font.BOLD, 72);  // For title text
        normalFont = new Font("Arial", Font.PLAIN, 24); // For normal text

        // Load the title screen image
        try {
            titleScreenImage = ImageIO.read(getClass().getResourceAsStream("/Background/titleScreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        this.g2d = g2d;

        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        }
    }

    public void resetCommandNum(){
        commandNum = 0;
    }

    private void drawTitleScreen() {
        // Draw the title screen background image if available
        if (titleScreenImage != null) {
            g2d.drawImage(titleScreenImage, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);
        } else {
            // Draw a fallback background color if image is not found
            g2d.setColor(new Color(246, 1, 157));
            g2d.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        }

        // Draw title text
        g2d.setFont(titleFont);  // Set font for the title
        g2d.setColor(new Color(1, 238, 246));
        String text = "PixelMash";
        int x = getXForCenteredText(text);
        int y = gamePanel.screenHeight / 2 - 100;
        g2d.drawString(text, x, y);

        // Draw "press" text
        g2d.setFont(normalFont);  // Set font for normal text
        g2d.setColor(new Color(255, 255, 255));
        String pressText = "press";
        x = getXForCenteredText(pressText);
        y = gamePanel.screenHeight / 2 + 70;
        g2d.drawString(pressText, x, y);

        // Draw "ENTER" text
        g2d.setColor(new Color(26, 246, 1));
        String enterText = "ENTER";
        x = getXForCenteredText(enterText);
        y += g2d.getFontMetrics().getHeight();  // Move down for next line
        g2d.drawString(enterText, x, y);
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }
}
