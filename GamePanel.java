package main;

import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{ // This is an extention class of JPanel
    // Screen settings
    final int originalTileSize = 16; // 16 by 16
    final int scale = 3; // scaled by 3 

    public final int tileSize = originalTileSize * scale; // 48 by 48 tile size This is the actual tile size
    final int maxScreenCol = 16; // This is the maximum size of the screen in the y coordinate direction
    final int maxScreenRow = 12; // This is the maximum size of the screen in the x coordinate direction
    final int Width= tileSize * maxScreenCol; // 768 pixels (16 x 48)
    final int Height= tileSize * maxScreenRow;// 576 pixels (12 x 48)

    //FPS
    int FPS = 60;

    KeyHandler keyHandle = new KeyHandler();
    //This object Thread is responsible for the time/clock of the gaame.
    Thread gameThread;
    Player player = new Player(this, keyHandle);
    //Set player's default postion
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4; // This moves by 4 pixels

    public GamePanel () {
        this.setPreferredSize(new Dimension(Width, Height));
        this.setBackground(Color.black); // The default background colour is black
        this.setDoubleBuffered(true); // This is for better game performance
        this.addKeyListener(keyHandle); // Recognizes the keyHandler
        this.setFocusable(true); // So teh Game Panel can focus on to recieve the key input
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); //Automatically call the run method
    }
    @Override
    public void run() { //thread causes the object's run method to be called in that separately executing thread.
        double drawInterval  = 1000000000/FPS; //This means 1 second divided by 60 fps or 0.016667 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        long currentTime;
        long lastTime = System.nanoTime();
        long timer = 0;
        int drawCount = 0;
        // We will create this game loop
        while(gameThread != null) { 
            currentTime = System.nanoTime();  
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            // System.out.println("The game has begun!");
            if(timer/ drawInterval >= 1){
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS: "+ drawCount);
                drawCount = 0;
                timer = 0;
            }
            // 1 UPDATE: update information such as a character postions
            update();
            // 2 DRAW: the screen with updated information
            repaint(); // Calling paintComponent method

       
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
        
            }
        }
    }

    public void update() {

        player.update();
    }

    @Override
    public void paintComponent(Graphics graphics) { // Built in method in java

        super.paintComponent(graphics);

        Graphics2D graphics2 = (Graphics2D)graphics;

        player.draw(graphics2);

        graphics2.dispose(); // releases any system resources that it is using, this saves memory
    }
}
