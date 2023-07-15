/*
 * Chris DePaul
 * 5/8/2023
 * IT-36311 Advanced Java Programming
 * Player
 */

package com.cdepaul1;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {
    // Constants for the player's dimensions and speed
    public static final int PLAYER_WIDTH = 60;
    public static final int PLAYER_HEIGHT = 20;
    public static final int PLAYER_SPEED = 5;

    // Instance variables for the player's position
    private double x;
    private double y;

    // Constructor for creating a new Player object
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Method for moving the player to the left
    public void moveLeft() {
        x -= 5;
        if (x < 0)
            x = 0;
    }

    // Method for moving the player to the right
    public void moveRight() {
        x += 5;
        if (x > Game.WIDTH - PLAYER_WIDTH)
            x = Game.WIDTH - PLAYER_WIDTH;
    }

    // Method for drawing the player on the canvas
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    // Method for getting the player's bounds for collision detection
    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    // Getter method for the player's x position
    public double getX() {
        return x;
    }

    // Getter method for the player's y position
    public double getY() {
        return y;
    }

    // Method for resetting the player's position to a specific x and y coordinate
    public void resetPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Method for resetting the player's position to the center bottom of the screen
    public void resetPosition() {
        this.x = (Game.WIDTH - PLAYER_WIDTH) / 2;
        this.y = Game.HEIGHT - PLAYER_HEIGHT - 20;
    }
}
