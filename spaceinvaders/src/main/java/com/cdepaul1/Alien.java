/*
 * Chris DePaul
 * 5/8/2023
 * IT-36311 Advanced Java Programming
 * Alien
 */

package com.cdepaul1;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Alien {
    public static final int ALIEN_WIDTH = 40;
    public static final int ALIEN_HEIGHT = 30;
    public static final int SPEED = 5;

    private double x; // x-coordinate of the alien
    private double y; // y-coordinate of the alien
    private boolean isAlive; // flag indicating whether the alien is alive or not
    private double width; // width of the alien
    private double height; // height of the alien

    public static double alienSpeed = 0.1; // speed of the aliens
    public static Direction direction = Direction.RIGHT; // direction of movement of the aliens

    public enum Direction {
        LEFT, // constant indicating left direction
        RIGHT // constant indicating right direction
    }

    public Alien(double x, double y) {
        this.x = x; // set the x-coordinate
        this.y = y; // set the y-coordinate
        this.isAlive = true; // set the alien as alive
        this.width = ALIEN_WIDTH; // set the width of the alien
        this.height = ALIEN_HEIGHT; // set the height of the alien
    }

    public void update() {
        switch (direction) {
            case LEFT:
                x -= alienSpeed; // move the alien to the left
                break;
            case RIGHT:
                x += alienSpeed; // move the alien to the right
                break;
        }
    }

    public void draw(GraphicsContext gc) {
        if (isAlive) { // if the alien is alive
            gc.setFill(Color.RED); // set the color of the alien to red
            gc.fillRect(x, y, width, height); // draw the alien on the canvas
        }
    }

    public Bounds getBounds() {
        return new Rectangle(x, y, width, height).getBoundsInLocal(); // get the bounding box of the alien
    }

    public boolean isAlive() {
        return isAlive; // return whether the alien is alive or not
    }

    public void setAlive(boolean alive) {
        isAlive = alive; // set the flag indicating whether the alien is alive or not
    }

    public double getX() {
        return x; // return the x-coordinate of the alien
    }

    public double getY() {
        return y; // return the y-coordinate of the alien
    }

    public double setX(double x) {
        return x; // set the x-coordinate of the alien
    }

    public double setY(double y) {
        return y; // set the y-coordinate of the alien
    }
}
