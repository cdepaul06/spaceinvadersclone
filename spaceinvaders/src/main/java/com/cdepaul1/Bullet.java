/*
 * Chris DePaul
 * 5/8/2023
 * IT-36311 Advanced Java Programming
 * Bullet
 */

package com.cdepaul1;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet {
    // Constants for the bullet size
    public static final int BULLET_WIDTH = 4;
    public static final int BULLET_HEIGHT = 12;

    // Bullet position, speed and firing status
    private double x;
    private double y;
    private double speed;
    private boolean isFired;

    // Constructor
    public Bullet(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.isFired = true;
    }

    // Getters for bullet position
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Update the bullet position based on its speed
    public void update() {
        y += speed;
    }

    // Draw the bullet if it is fired
    public void draw(GraphicsContext gc) {
        if (isFired) {
            gc.setFill(Color.YELLOW);
            gc.fillRect(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        }
    }

    // Get the bounds of the bullet
    public Bounds getBounds() {
        return new Rectangle(x, y, BULLET_WIDTH, BULLET_HEIGHT).getBoundsInLocal();
    }

    // Check if the bullet is fired
    public boolean isFired() {
        return isFired;
    }

    // Set the firing status of the bullet
    public void setFired(boolean fired) {
        isFired = fired;
    }
}
