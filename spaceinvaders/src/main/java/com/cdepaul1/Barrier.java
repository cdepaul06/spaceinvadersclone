/*
 * Chris DePaul
 * 5/8/2023
 * IT-36311 Advanced Java Programming
 * Barrier
 */

package com.cdepaul1;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Barrier {
    public static final int BARRIER_WIDTH = 60;
    public static final int BARRIER_HEIGHT = 30;
    public static final int BARRIER_HEALTH = 5;

    private double x;
    private double y;
    private int health;

    public Barrier(double x, double y) {
        this.x = x;
        this.y = y;
        this.health = BARRIER_HEALTH;
    }

    // Draw the barrier on the canvas
    public void draw(GraphicsContext gc) {
        if (health > 0) {
            gc.setFill(Color.GREEN);
            gc.fillRect(x, y, BARRIER_WIDTH, BARRIER_HEIGHT);
        }
    }

    // Get the bounds of the barrier for collision detection
    public Bounds getBounds() {
        return new Rectangle(x, y, BARRIER_WIDTH, BARRIER_HEIGHT).getBoundsInLocal();
    }

    // Reduce the health of the barrier when hit by a projectile
    public void hit() {
        health--;
    }

    // Check if the barrier is still alive based on its health
    public boolean isAlive() {
        return health > 0;
    }
}
