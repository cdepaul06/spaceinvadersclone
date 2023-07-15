/*
 * Chris DePaul
 * 5/8/2023
 * IT-36311 Advanced Java Programming
 * Game
 */

package com.cdepaul1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;

import java.util.ArrayList;
import java.util.Iterator;

public class Game extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private Player player;
    private Alien[][] aliens; // 2D array to store aliens
    private ArrayList<Bullet> bullets; // List to store player bullets
    private ArrayList<Explosion> explosions; // List to store explosions
    private ArrayList<Barrier> barriers; // List to store barriers
    private ArrayList<AlienProjectile> alienProjectiles; // List to store alien projectiles
    private GameStatus gameStatus; // Object to track game status (score and lives)
    private GameOverScreen gameOverScreen; // Object to display game over screen
    private double timeSinceLastProjectile; // Track time since last alien projectile was fired
    private GameState gameState = GameState.MENU; // Track current game state (menu, playing, or game over)

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Space Invaders");

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT); // fill background with black
        root.getChildren().add(canvas);
        gameStatus = new GameStatus();
        gameOverScreen = new GameOverScreen();
        Scene scene = new Scene(root);
        root.setStyle("-fx-background-color: black;"); // set background color to black
        primaryStage.setScene(scene);
        primaryStage.show();

        player = new Player((WIDTH - Player.PLAYER_WIDTH) / 2, HEIGHT - Player.PLAYER_HEIGHT - 20); // create player
                                                                                                    // object
        timeSinceLastProjectile = 0;

        initializeAliens(); // create and place aliens on the screen
        bullets = new ArrayList<>(); // create list to store player bullets
        explosions = new ArrayList<>(); // create list to store explosions
        alienProjectiles = new ArrayList<>(); // create list to store alien projectiles
        initializeBarriers(); // create barriers
        prepareGameLoop(); // start game loop
        registerInputHandlers(scene); // register input handlers to handle user input
        gameState = GameState.MENU; // set initial game state to menu
    }

    private void initializeBarriers() {
        // Create new ArrayList to hold Barrier objects
        barriers = new ArrayList<>();

        // Create 4 new Barrier objects and add them to the barriers ArrayList
        for (int i = 0; i < 4; i++) {
            barriers.add(new Barrier(120 + i * 180, HEIGHT - 150));
        }
    }

    private void initializeAliens() {
        // Create new 2D Alien array to hold Alien objects
        aliens = new Alien[5][11];

        // Create 55 new Alien objects and add them to the aliens 2D array
        for (int row = 0; row < aliens.length; row++) {
            for (int col = 0; col < aliens[row].length; col++) {
                double x = 60 + col * (Alien.ALIEN_WIDTH + 20);
                double y = 60 + row * (Alien.ALIEN_HEIGHT + 20);
                aliens[row][col] = new Alien(x, y);
            }
        }
    }

    private void prepareGameLoop() {
        // Create an AnimationTimer to handle the game loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update the game state based on the current game state
                switch (gameState) {
                    case MENU:
                        updateMenu();
                        break;
                    case PLAYING:
                        update();
                        break;
                    case GAME_OVER:
                        updateGameOver();
                        break;
                }
                // Render the game after updating the state
                render();
            }
        };
        // Start the game loop
        gameLoop.start();
    }

    private void registerInputHandlers(Scene scene) {
        // Register a key press event handler for the scene
        scene.setOnKeyPressed((KeyEvent event) -> {
            // Handle the input based on the current game state
            switch (gameState) {
                case MENU:
                    handleMenuInput(event);
                    break;
                case PLAYING:
                    handlePlayingInput(event);
                    break;
                case GAME_OVER:
                    handleGameOverInput(event);
                    break;
            }
        });
    }

    private void handleMenuInput(KeyEvent event) {
        // Start the game when the user presses the ENTER key
        if (event.getCode() == KeyCode.ENTER) {
            gameState = GameState.PLAYING;
        }
    }

    @SuppressWarnings("incomplete-switch")
    private void handlePlayingInput(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                // Move the player left when the user presses the LEFT arrow key
                player.moveLeft();
                break;
            case RIGHT:
                // Move the player right when the user presses the RIGHT arrow key
                player.moveRight();
                break;
            case SPACE:
                // Fire a bullet when the user presses the SPACE key
                fireBullet();
                break;
        }
    }

    private void handleGameOverInput(KeyEvent event) {
        // Restart the game when the user presses the ENTER key
        if (event.getCode() == KeyCode.ENTER) {
            resetGame();
            gameState = GameState.PLAYING;
        }
    }

    private void resetGame() {
        // Reset the player position and game status
        player.resetPosition();
        gameStatus.reset();

        // Re-initialize the aliens, bullets, explosions, alien projectiles, and
        // barriers
        initializeAliens();
        bullets.clear();
        explosions.clear();
        alienProjectiles.clear();
        initializeBarriers();
    }

    private void updateAlienDirection() {
        boolean changeDirection = false;

        for (int row = 0; row < aliens.length; row++) {
            for (int col = 0; col < aliens[row].length; col++) {
                Alien alien = aliens[row][col];
                if (alien.isAlive()) {
                    // Check if an alien has reached the left or right edge of the screen
                    if (alien.getX() <= 0 && Alien.direction == Alien.Direction.LEFT) {
                        changeDirection = true;
                        break;
                    } else if (alien.getX() + Alien.ALIEN_WIDTH >= WIDTH && Alien.direction == Alien.Direction.RIGHT) {
                        changeDirection = true;
                        break;
                    }
                }
            }
            if (changeDirection) {
                break;
            }
        }

        // If an alien has reached the edge of the screen, change their direction and
        // move them down
        if (changeDirection) {
            Alien.direction = Alien.direction == Alien.Direction.LEFT ? Alien.Direction.RIGHT : Alien.Direction.LEFT;
            for (int row = 0; row < aliens.length; row++) {
                for (int col = 0; col < aliens[row].length; col++) {
                    Alien alien = aliens[row][col];
                    alien.setY(alien.getY() + Alien.ALIEN_HEIGHT / 2);
                }
            }
        }
    }

    private void fireBullet() {
        // Calculate the x and y coordinates for the bullet to start from the center of
        // the player
        double bulletX = player.getX() + (Player.PLAYER_WIDTH - Bullet.BULLET_WIDTH) / 2;
        double bulletY = player.getY() - Bullet.BULLET_HEIGHT;

        // Create a new bullet with an initial velocity of -2 (moves upwards)
        bullets.add(new Bullet(bulletX, bulletY, -2));
    }

    private void updatePlaying() {
        // Update game objects (e.g., player, aliens, bullets) here
        for (int row = 0; row < aliens.length; row++) {
            for (int col = 0; col < aliens[row].length; col++) {
                aliens[row][col].update();
            }
        }

        updateAlienDirection();

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();

            // Check if the bullet is out of the screen
            if (bullet.getY() < 0 || bullet.getY() > HEIGHT) {
                bulletIterator.remove();
            } else {
                // Check collisions with aliens
                for (int row = 0; row < aliens.length; row++) {
                    for (int col = 0; col < aliens[row].length; col++) {
                        Alien alien = aliens[row][col];
                        if (alien.isAlive() && alien.getBounds().intersects(bullet.getBounds())) {
                            alien.setAlive(false);
                            bullet.setFired(false);
                            bulletIterator.remove();

                            gameStatus.increaseScore(5); // Update the score

                            double explosionX = alien.getBounds().getMinX();
                            double explosionY = alien.getBounds().getMinY();
                            explosions.add(new Explosion(explosionX, explosionY));
                        }
                    }
                }

                // Check collisions with barriers
                for (Barrier barrier : barriers) {
                    if (barrier.isAlive() && barrier.getBounds().intersects(bullet.getBounds())) {
                        barrier.hit();
                        bullet.setFired(false);
                        bulletIterator.remove();
                    }
                }
            }
        }

        // Update explosions
        Iterator<Explosion> explosionIterator = explosions.iterator();
        while (explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            explosion.update();

            // Remove inactive explosions
            if (!explosion.isActive()) {
                explosionIterator.remove();
            }
        }

        // Update alien projectiles
        Iterator<AlienProjectile> alienProjectileIterator = alienProjectiles.iterator();
        Random random = new Random();
        while (alienProjectileIterator.hasNext()) {
            AlienProjectile alienProjectile = alienProjectileIterator.next();
            alienProjectile.update();

            // Check if the projectile is out of the screen
            if (alienProjectile.getY() > HEIGHT) {
                alienProjectileIterator.remove();
            } else {
                // Check collisions with barriers
                boolean hasCollidedWithBarrier = false;
                for (Barrier barrier : barriers) {
                    if (barrier.isAlive() && barrier.getBounds().intersects(alienProjectile.getBounds().getMinX(),
                            alienProjectile.getBounds().getMinY(), alienProjectile.getBounds().getWidth(),
                            alienProjectile.getBounds().getHeight())) {

                        barrier.hit();
                        alienProjectile.setActive(false);
                        alienProjectileIterator.remove();
                        hasCollidedWithBarrier = true;
                        break;
                    }
                }

                // Check collision with the player, only if it hasn't collided with a barrier
                if (!hasCollidedWithBarrier && player.getBounds().intersects(alienProjectile.getBounds().getMinX(),
                        alienProjectile.getBounds().getMinY(), alienProjectile.getBounds().getWidth(),
                        alienProjectile.getBounds().getHeight())) {

                    // Decrease player's lives and remove alien projectile
                    gameStatus.decreaseLives();
                    alienProjectile.setActive(false);
                    alienProjectileIterator.remove();

                    // Pause the game for the next round
                    gameLoop.stop();
                    try {
                        Thread.sleep(5000); // Pause for 5 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gameLoop.start();
                }
            }
        }

        // Update the time since the last projectile
        double elapsedTime = 1 / 60.0; // Assuming 60 FPS
        timeSinceLastProjectile += elapsedTime;

        // Fire alien projectiles every 3-4 seconds
        if (timeSinceLastProjectile >= 3.0) {
            int randomRow = random.nextInt(aliens.length);
            int randomCol = random.nextInt(aliens[randomRow].length);
            Alien alien = aliens[randomRow][randomCol];
            if (alien.isAlive()) {
                double projectileX = alien.getX() + (Alien.ALIEN_WIDTH - AlienProjectile.PROJECTILE_WIDTH) / 2;
                double projectileY = alien.getY() + Alien.ALIEN_HEIGHT;
                alienProjectiles.add(new AlienProjectile(projectileX, projectileY));
                timeSinceLastProjectile = 0;
            }
        }
        // Check game over condition (e.g., player's lives run out or aliens reach the
        // player)
        if (gameStatus.getLives() <= 0 ||
                isAlienReachedPlayer()) {
            gameState = GameState.GAME_OVER;
        }
    }

    private boolean isAlienReachedPlayer() {
        // Implement logic to check if any alien has reached the player
        for (int row = 0; row < aliens.length; row++) {
            for (int col = 0; col < aliens[row].length; col++) {
                Alien alien = aliens[row][col];
                if (alien.isAlive() && alien.getY() + Alien.ALIEN_HEIGHT >= player.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateMenu() {

    }

    private void update() {

        // Call the appropriate update method based on the current game state
        switch (gameState) {
            case MENU:
                updateMenu();
                break;
            case PLAYING:
                updatePlaying();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    private void updateGameOver() {
        // Ran out of time and I wanted to at least turn in what I had (the project is
        // already late).
    }

    private void render() {

        // Clear the screen
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        switch (gameState) {
            case MENU:
                // Set the text color and font
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("Arial", FontWeight.BOLD, 36));

                // Draw the title text
                String title = "SPACE INVADERS";
                Text titleText = new Text(title);
                titleText.setFont(gc.getFont());
                double titleWidth = titleText.getLayoutBounds().getWidth();
                gc.fillText(title, (WIDTH - titleWidth) / 2, HEIGHT / 2 - 50);

                // Draw the instruction text
                gc.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
                String instruction = "Press ENTER to start";
                Text instructionText = new Text(instruction);
                instructionText.setFont(gc.getFont());
                double instructionWidth = instructionText.getLayoutBounds().getWidth();
                gc.fillText(instruction, (WIDTH - instructionWidth) / 2, HEIGHT / 2 + 20);
                break;
            case PLAYING:
                if (gameStatus.getLives() <= 0) {
                    gameOverScreen.draw(gc, WIDTH, HEIGHT, gameStatus.getScore());
                } else {

                    // Clear the screen
                    gc.clearRect(0, 0, WIDTH, HEIGHT);

                    // Draw game objects (e.g., player, aliens, bullets) here
                    player.draw(gc);

                    for (int row = 0; row < aliens.length; row++) {
                        for (int col = 0; col < aliens[row].length; col++) {
                            aliens[row][col].draw(gc);
                        }
                    }

                    // Draw the game status on the screen
                    gc.setFill(Color.WHITE);
                    gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
                    gc.fillText("Score: " + gameStatus.getScore(), 10, 20);
                    gc.fillText("Lives: " + gameStatus.getLives(), 10, 40);
                    gc.fillText("Level: " + gameStatus.getLevel(), 10, 60);

                    for (Bullet bullet : bullets) {
                        bullet.draw(gc);
                    }

                    for (Explosion explosion : explosions) {
                        explosion.draw(gc);
                    }

                    for (AlienProjectile alienProjectile : alienProjectiles) {
                        alienProjectile.draw(gc);
                    }

                    for (Barrier barrier : barriers) {
                        barrier.draw(gc);
                    }
                }
            case GAME_OVER:
                // Game Over rendering code
                break;
        }
    }
}
