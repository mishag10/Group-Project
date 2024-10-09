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
        // Check if the player1 has reached any of the corners
        int tolerance = 24; // Optional tolerance for checking if at corner
        return (Math.abs(gamePanel1.player1.worldX) < tolerance && Math.abs(gamePanel1.player1.worldY) < tolerance) ||
                (Math.abs(gamePanel1.player1.worldX - (gamePanel1.screenWidth - gamePanel1.tileSize)) < tolerance && Math.abs(gamePanel1.player1.worldY) < tolerance) ||
                (Math.abs(gamePanel1.player1.worldX) < tolerance && Math.abs(gamePanel1.player1.worldY - (gamePanel1.screenHeight - gamePanel1.tileSize)) < tolerance) ||
                (Math.abs(gamePanel1.player1.worldX - (gamePanel1.screenWidth - gamePanel1.tileSize)) < tolerance && Math.abs(gamePanel1.player1.worldY - (gamePanel1.screenHeight - gamePanel1.tileSize)) < tolerance);
    }

    private boolean playerTouchedByLightCycle() {
        int tolerance = 24; // Adjust tolerance as needed

        // Check if any light cycle has touched the player1 within a certain tolerance
        for (LightCycle cycle : gamePanel1.lightCycles) {
            if (Math.abs(cycle.x - gamePanel1.player1.worldX) < tolerance &&
                    Math.abs(cycle.y - gamePanel1.player1.worldY) < tolerance) {
                return true;
            }
        }
        return false;
    }

    private void transitionToNextGame() {
        // Transition to the next game or another application
        System.out.println("You have completed the level! Transitioning to the next game...");

        // Stop the game panel and the thread safely
        gamePanel1.setVisible(false);

        // Interrupt the thread if it's still running
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
