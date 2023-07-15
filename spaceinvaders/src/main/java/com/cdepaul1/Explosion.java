/*
 * Chris DePaul
 * 5/8/2023
 * IT-36311 Advanced Java Programming
 * Explosion
 */

package com.cdepaul1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Explosion {
    private double x;
    private double y;
    private int frame;
    private boolean isActive;

    public Explosion(double x, double y) {
        this.x = x;
        this.y = y;
        this.frame = 0;
        this.isActive = true;
    }

    public void update() {
        if (isActive) {
            // Increment the frame count
            frame++;
            // Check if the explosion should be inactive
            if (frame > 10) {
                isActive = false;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        if (isActive) {
            // Draw the explosion with multiple colored ovals
            gc.setFill(Color.ORANGE);
            gc.fillOval(x, y, 40, 40);
            gc.setFill(Color.RED);
            gc.fillOval(x + 8, y + 8, 24, 24);
            gc.setFill(Color.YELLOW);
            gc.fillOval(x + 16, y + 16, 8, 8);
        }
    }

    public boolean isActive() {
        return isActive;
    }
}
