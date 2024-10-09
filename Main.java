package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PixelMash");

        // Create an instance of UI
        UI ui = new UI(null); // Temporarily set gamePanel1 to null

        // Pass the UI instance to GamePanel constructor
        GamePanel1 gamePanel1 = new GamePanel1(ui);
        ui.gamePanel1 = gamePanel1; // Assign gamePanel1 to UI after instantiation

        window.add(gamePanel1);

        window.pack(); // The pack() allows the window to be sized to fit the preferred size and layouts of its subcomponents, the GamePanel

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel1.startGameThread();
    }
}
