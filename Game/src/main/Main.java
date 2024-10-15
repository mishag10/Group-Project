package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PixelMash");

        // Create the GamePanel1 instance (no parameters)
        GamePanel1 gamePanel1 = new GamePanel1();

        // Add the game panel to the window
        window.add(gamePanel1);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Start the game thread
        gamePanel1.startGameThread();
    }
}
