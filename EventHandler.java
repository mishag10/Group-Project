package main;

import character.*;

public class EventHandler {

    GamePanel gamePanel;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkEvent() {
        if (playerAtCorner()) {
            transitionToNextGame();
        }

        if (playerTouchedByLightCycle()) {
            gamePanel.gameState = gamePanel.gameOverState;
        }
    }

    private boolean playerAtCorner() {
        // Check if the player has reached any of the corners
        int tolerance = 24; // Optional tolerance for checking if at corner
        return (Math.abs(gamePanel.player.worldX) < tolerance && Math.abs(gamePanel.player.worldY) < tolerance) ||
                (Math.abs(gamePanel.player.worldX - (gamePanel.screenWidth - gamePanel.tileSize)) < tolerance && Math.abs(gamePanel.player.worldY) < tolerance) ||
                (Math.abs(gamePanel.player.worldX) < tolerance && Math.abs(gamePanel.player.worldY - (gamePanel.screenHeight - gamePanel.tileSize)) < tolerance) ||
                (Math.abs(gamePanel.player.worldX - (gamePanel.screenWidth - gamePanel.tileSize)) < tolerance && Math.abs(gamePanel.player.worldY - (gamePanel.screenHeight - gamePanel.tileSize)) < tolerance);
    }

    private boolean playerTouchedByLightCycle() {
        int tolerance = 24; // Adjust tolerance as needed

        // Check if any light cycle has touched the player within a certain tolerance
        for (LightCycle cycle : gamePanel.lightCycles) {
            if (Math.abs(cycle.x - gamePanel.player.worldX) < tolerance &&
                    Math.abs(cycle.y - gamePanel.player.worldY) < tolerance) {
                return true;
            }
        }
        return false;
    }

    private void transitionToNextGame() {
        // Transition to the next game or another application
        System.out.println("You have completed the level! Transitioning to the next game...");

        // Stop the game panel and the thread safely
        gamePanel.setVisible(false);

        // Interrupt the thread if it's still running
        if (gamePanel.gameThread != null && gamePanel.gameThread.isAlive()) {
            gamePanel.gameThread.interrupt();
            gamePanel.gameThread = null;
        }

        // Optionally trigger external game process (like launching another game)
        try {
            // Example: Runtime.getRuntime().exec("path_to_next_game");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
