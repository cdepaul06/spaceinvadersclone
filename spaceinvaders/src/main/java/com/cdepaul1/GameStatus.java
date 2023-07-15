/*
 * Chris DePaul
 * 5/8/2023
 * IT-36311 Advanced Java Programming
 * GameStatus
 */

package com.cdepaul1;

public class GameStatus {
    private int score; // Current score
    private int lives; // Number of remaining lives
    private int level; // Current level
    private final int INITIAL_LIVES = 3; // Initial number of lives

    public GameStatus() {
        this.score = 0; // Initialize score to 0
        this.lives = 3; // Initialize lives to 3
        this.level = 1; // Initialize level to 1
    }

    public int getScore() {
        return score; // Return current score
    }

    public void setScore(int score) {
        this.score = score; // Set score to given value
    }

    public int getLives() {
        return lives; // Return number of remaining lives
    }

    public void setLives(int lives) {
        this.lives = lives; // Set number of remaining lives to given value
    }

    public int getLevel() {
        return level; // Return current level
    }

    public void setLevel(int level) {
        this.level = level; // Set current level to given value
    }

    public void increaseScore(int value) {
        this.score += value; // Increase score by given value
    }

    public void decreaseLives() {
        this.lives--; // Decrease number of remaining lives by 1
    }

    public void increaseLevel() {
        this.level++; // Increase current level by 1
    }

    public void reset() {
        score = 0; // Reset score to 0
        lives = INITIAL_LIVES; // Reset number of remaining lives to initial value
    }
}
