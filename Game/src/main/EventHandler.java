package main;

import character.*;


public class EventHandler {
    GamePanel1 gamePanel1;

    public EventHandler(GamePanel1 gamePanel1) {
        this.gamePanel1 = gamePanel1;
    }

    public void checkEvent() {
        if (playerAtCorner()) {
            transitionToNextGame();
        }

        if (playerTouchedByLightCycle()) {
            gamePanel1.gameState = gamePanel1.gameOverState;
        }
    }

    private boolean playerAtCorner() {
        int tolerance = 24;
        return (Math.abs(gamePanel1.player1.worldX) < tolerance && Math.abs(gamePanel1.player1.worldY) < tolerance) ||
                (Math.abs(gamePanel1.player1.worldX - (gamePanel1.screenWidth - gamePanel1.tileSize)) < tolerance && Math.abs(gamePanel1.player1.worldY) < tolerance) ||
                (Math.abs(gamePanel1.player1.worldX) < tolerance && Math.abs(gamePanel1.player1.worldY - (gamePanel1.screenHeight - gamePanel1.tileSize)) < tolerance) ||
                (Math.abs(gamePanel1.player1.worldX - (gamePanel1.screenWidth - gamePanel1.tileSize)) < tolerance && Math.abs(gamePanel1.player1.worldY - (gamePanel1.screenHeight - gamePanel1.tileSize)) < tolerance);
    }

    private boolean playerTouchedByLightCycle() {
        int tolerance = 24;
        for (LightCycle cycle : gamePanel1.lightCycles) {
            if (Math.abs(cycle.worldX - gamePanel1.player1.worldX) < tolerance &&
                Math.abs(cycle.worldY - gamePanel1.player1.worldY) < tolerance) {
                System.out.println("Player hit by Light Cycle! Restarting...");
                gamePanel1.gameState = gamePanel1.titleState; // or set it to gameOverState if you have a specific screen
                gamePanel1.player1.setDefaultValues(); // Reset player position
                return true;
            }
        }
        return false;
    }

    private void transitionToNextGame() {
        System.out.println("You have completed the level! Transitioning to the next game...");

        // Set the game state to black screen
        gamePanel1.setBlackScreen();

        // Pause for a brief moment to display the black screen
        try {
            Thread.sleep(1000); // Adjust this duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Now transition to the next game or level
        gamePanel1.setVisible(false);

        if (gamePanel1.gameThread != null && gamePanel1.gameThread.isAlive()) {
            gamePanel1.gameThread.interrupt();
            gamePanel1.gameThread = null;
        }

        // Optionally trigger external game process (like launching another game)
        try {
            // Example: Runtime.getRuntime().exec("path_to_next_game");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
