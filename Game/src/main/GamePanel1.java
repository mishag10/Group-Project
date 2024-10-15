package main;

import character.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel1 extends JPanel implements Runnable {
    // Screen settings
    public final int tileSize = 48; 
    public final int maxScreenCol = 16; 
    public final int maxScreenRow = 12; 
    public final int screenWidth = tileSize * maxScreenCol; 
    public final int screenHeight = tileSize * maxScreenRow; 

    public int FPS = 60; 

    public Player1 player1;
    public LightCycle[] lightCycles = new LightCycle[2]; 

    public int titleState = 0; 
    public final int playState = 1; 
    public int gameOverState = 2; 
    public int gameState = titleState; 

    Thread gameThread; 

    public UI ui;
    public KeyHandler1 keyHandler1; 
    public ArrayList<Pillar> pillars = new ArrayList<>(); 
    private Random random = new Random(); 

    public GamePanel1() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setLayout(null);

        keyHandler1 = new KeyHandler1(this);
        this.addKeyListener(keyHandler1);

        player1 = new Player1(this, keyHandler1); 
        lightCycles[0] = new LightCycle(this, 0, 0, 0);
        lightCycles[1] = new LightCycle(this, 1, screenWidth - tileSize, screenHeight - tileSize); 

        addRandomPillars(5);  

        ui = new UI(this); 
        gameState = titleState; 

        requestFocusInWindow(); 
        startGameThread(); 
    }

    private void addRandomPillars(int count) {
        for (int i = 0; i < count; i++) {
            int randomX = random.nextInt(maxScreenCol) * tileSize; 
            int randomY = random.nextInt(maxScreenRow) * tileSize; 
            Pillar pillar = new Pillar(this, randomX, randomY); // Pass GamePanel1 instance and coordinates
            pillars.add(pillar);
        }
    }
    
    public void startGameThread() {
        gameThread = new Thread(this); 
        gameThread.start(); 
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; 
        double nextDrawTime = System.nanoTime() + drawInterval; 

        while (gameThread != null) {
            update(); 
            repaint(); 

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = Math.max(remainingTime, 0); // Ensure non-negative
                Thread.sleep((long) remainingTime / 1000000); 
                nextDrawTime += drawInterval; 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        System.out.println("Current game state: " + gameState);
        if (gameState == playState) {
            player1.update(pillars);
            for (LightCycle cycle : lightCycles) {
                cycle.update(); 
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;
    
        // Draw the UI elements (including background)
        ui.draw(g2d);
    
        // Check game state before drawing game objects
        if (gameState == playState) {
            for (Pillar pillar : pillars) {
                pillar.draw(g2d); 
            }
            for (LightCycle cycle : lightCycles) {
                cycle.draw(g2d); 
            }
            player1.draw(g2d); 
        }
    }
    


    public void resetGame() {
        player1.reset(); 
        for (int i = 0; i < lightCycles.length; i++) {
            lightCycles[i] = new LightCycle(this, i, random.nextInt(maxScreenCol) * tileSize, random.nextInt(maxScreenRow) * tileSize);
        }
        pillars.clear(); // Clear existing pillars before adding new ones
        addRandomPillars(5); 
    }

    public void setBlackScreen() {
        Graphics g = getGraphics();
        if (g != null) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, screenWidth, screenHeight);
            g.dispose();
        }
        repaint(); // Trigger a repaint to show the black screen
    }
}
