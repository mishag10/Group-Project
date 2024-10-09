package main;

import character.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

public class GamePanel1 extends JPanel implements Runnable {
    // Screen settings
    public final int tileSize = 48;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // FPS settings
    public int FPS = 60;

    // Player and Light Cycles
    public Player1 player1;  // Declare Player variable
    public LightCycle[] lightCycles = new LightCycle[2]; // Now only 2 light cycles
    Thread gameThread;

    // Game state
    public int gameState;
    public int titleState = 0;
    public final int playState = 1;
    public final int gameOverState = 2;

    // UI instance
    public UI ui;

    public KeyHandler1 keyHandler1;
    private BufferedImage background;
    private BufferedImage titleScreen; // Title screen image
    public ArrayList<Pillar> pillars = new ArrayList<>(); // Pillars list
    private Random random = new Random();

    public GamePanel1(UI ui) { // Modify constructor to accept UI
        this.ui = ui; // Initialize the UI instance
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        // Initialize keyHandler1 and add it as a KeyListener
        keyHandler1 = new KeyHandler1(this, ui);
        this.addKeyListener(keyHandler1);

        // Initialize player1
        player1 = new Player1 (this, keyHandler1);

        // Initialize light cycles with different starting positions
        lightCycles[0] = new LightCycle(this, 0, 0, 0);  // Left cycle
        lightCycles[1] = new LightCycle(this, 1, screenWidth - tileSize, screenHeight - tileSize); // Right cycle

        // Add pillars to the game at random positions
        addRandomPillars(5); // Example: Add 5 random pillars

        // Load the title screen image
        loadTitleScreenImage();

        // Load the background image
        loadBackgroundImage();

        // Set the initial game state to titleState
        gameState = titleState;

        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void loadTitleScreenImage() {
        try {
            titleScreen = ImageIO.read(getClass().getResourceAsStream("/Background/TitleScreen.png")); // Update with your title screen image path
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/Background/Background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addRandomPillars(int count) {
        for (int i = 0; i < count; i++) {
            // Generate random coordinates for the pillars
            int randomX = random.nextInt(maxScreenCol) * tileSize; // Ensure positions are in tile size increments
            int randomY = random.nextInt(maxScreenRow) * tileSize;
            pillars.add(new Pillar(randomX, randomY));
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            while (delta >= 1) {
                update();
                repaint();
                delta--;
            }
            repaint();
        }
    }

    public void update() {
        if (gameState == playState) {
            player1.update(); // Update player1
            for (LightCycle lightCycle : lightCycles) {
                lightCycle.update();  // Update each light cycle
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (gameState == titleState) {
            ui.draw(g2d);
        } else if (gameState == playState) {
            g2d.drawImage(background, 0, 0, screenWidth, screenHeight, null); // Draw background
            player1.draw(g2d);  // Draw the player1
            // Draw each light cycle
            for (LightCycle lightCycle : lightCycles) {
                lightCycle.draw(g2d);  // Call the draw method of each light cycle
            }

            // Draw each pillar
            for (Pillar pillar : pillars) {
                pillar.draw(g2d);  // Draw each pillar on the screen
            }
        }
        g2d.dispose();
    }
}
