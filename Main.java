package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PixelMash");

        // Create an instance of UI
        UI ui = new UI(null); // Temporarily set gamePanel to null

        // Pass the UI instance to GamePanel constructor
        GamePanel gamePanel = new GamePanel(ui);
        ui.gamePanel = gamePanel; // Assign gamePanel to UI after instantiation

        window.add(gamePanel);

        window.pack(); // The pack() allows the window to be sized to fit the preferred size and layouts of its subcomponents, the GamePanel

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
