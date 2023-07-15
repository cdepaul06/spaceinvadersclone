/*
 * Chris DePaul
 * 5/8/2023
 * IT-36311 Advanced Java Programming
 * AlienProjectile
 */

package com.cdepaul1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

public class AlienProjectile {
    public static final int PROJECTILE_WIDTH = 4;
    public static final int PROJECTILE_HEIGHT = 10;
    public static final double SPEED = 1.0;

    private double x;
    private double y;
    private boolean active;

    // Constructor for an AlienProjectile object
    public AlienProjectile(double x, double y) {
        this.x = x;
        this.y = y;
        this.active = true;
    }

    // Method to update the position of the AlienProjectile
    public void update() {
        y += SPEED;
    }

    // Method to draw the AlienProjectile on the given GraphicsContext object
    public void draw(GraphicsContext gc) {
        if (active) {
            gc.setFill(Color.RED);
            gc.fillRect(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        }
    }

    // Method to get the bounds of the AlienProjectile for collision detection
    public Bounds getBounds() {
        return new Rectangle(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT).getBoundsInLocal();
    }

    // Getter method for the 'active' instance variable
    public boolean isActive() {
        return active;
    }

    // Setter method for the 'active' instance variable
    public void setActive(boolean active) {
        this.active = active;
    }

    // Getter method for the 'x' instance variable
    public double getX() {
        return x;
    }

    // Getter method for the 'y' instance variable
    public double getY() {
        return y;
    }
}
