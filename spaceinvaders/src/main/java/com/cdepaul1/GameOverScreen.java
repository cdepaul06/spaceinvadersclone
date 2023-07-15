/*
 * Chris DePaul
 * 5/8/2023
 * IT-36311 Advanced Java Programming
 * GameOverScreen
 */

package com.cdepaul1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameOverScreen {

    // Static constants for the text to be displayed
    private static final String GAME_OVER_TEXT = "Game Over";
    private static final String PRESS_ENTER_TEXT = "Press Enter to restart";
    private static final int GAME_OVER_TEXT_SIZE = 48;
    private static final int PRESS_ENTER_TEXT_SIZE = 24;

    // Method to draw the game over screen
    public void draw(GraphicsContext gc, int width, int height, int score) {

        // Fill the background with black color
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        // Draw the "Game Over" text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, GAME_OVER_TEXT_SIZE));
        gc.fillText(GAME_OVER_TEXT, width / 2 - gc.getFont().getSize() * GAME_OVER_TEXT.length() / 4, height / 2 - 50);

        // Draw the "Press Enter to restart" text
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, PRESS_ENTER_TEXT_SIZE));
        gc.fillText(PRESS_ENTER_TEXT, width / 2 - gc.getFont().getSize() * PRESS_ENTER_TEXT.length() / 4,
                height / 2 + 20);

        // Draw the final score
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        gc.fillText("Final Score: " + score,
                width / 2 - gc.getFont().getSize() * ("Final Score: " + score).length() / 4, height / 2 + 60);
    }
}
