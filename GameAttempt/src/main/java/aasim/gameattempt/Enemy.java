/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aasim.gameattempt;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Duration;

/**
 *
 * @author 14048
 */
public class Enemy extends Sprite {

    public Enemy(int x, int y) {
        super(x, y);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (dead) {
                    this.stop();
                }
                update();
            }
        };

        timer.start();
        setSpeed(Math.random());
    }

    boolean goLeft = true;
    boolean goUp = true;
    double screenX = Screen.getPrimary().getBounds().getWidth();
    double screenY = Screen.getPrimary().getBounds().getHeight();
    double playerX, playerY;
    //double C = Math.abs(this.getX()-playerX)/(Math.abs(this.getY()-playerY));//used to determine angle 
    double xDist = Math.abs(this.getX() - playerX);
    double yDist = Math.abs(this.getY() - playerY);

    //double angle = Math.sin(C);//angle that the player is from enemy
    private void update() {
        if (dead) {
            if (!this.getImage().equals(deadAnimation)) {
                this.setImage(deadAnimation);
            }
            PauseTransition hide = new PauseTransition(Duration.seconds(0.5));
            hide.setOnFinished(eh -> {
                this.setVisible(false);
                ((Pane) this.getScene().getRoot()).getChildren().remove(this);
                Sprite.collisions.remove(this);
            });
            hide.play();
            return;
        }

        for (Sprite x : this.players) {
            playerX = x.getX();
            playerY = x.getY();

        }

        //ENEMY AI
        /*
         * if (this.getX() > 0 && goLeft) { this.moveLeft(); } else { goLeft =
         * false; }
         *
         * if (this.getX() < screenX - 25 && !goLeft) {
         * this.moveRight();
         * } else {
         * goLeft = true;
         * }
         *
         * if (this.getY() > 0 && goUp) { this.moveUp(); } else { goUp = false;
         * }
         *
         * if (this.getY() < screenY - 50 && !goUp) { this.moveDown(); } else {
         * goUp = true; }
         *
         */
        if (playerY > this.getY()) {
            this.moveDown();
        }
        if (playerY < this.getY()) {
            this.moveUp();
        }
        if (playerX > this.getX()) {
            this.moveRight();
        }
        if (playerX < this.getX()) {
            this.moveLeft();
        }
        // Collisions

        for (Sprite x : collisions) {
            if (x != this) {
                if (x.intersects(this.getBoundsInParent())) {
                    double differenceX = x.getX() - this.getX();
                    double differenceY = x.getY() - this.getY();
                    if (differenceX > 10) {//will stop enemies and players from colliding, unless walked into for now
                        while (x.intersects(this.getBoundsInParent())) {
                            this.moveLeft();
                            x.moveRight();
                        }
                    }
                    if (differenceX < -10) {
                        while (x.intersects(this.getBoundsInParent())) {
                            this.moveRight();
                            x.moveLeft();
                        }
                    }
                    if (differenceY > 5) {
                        while (x.intersects(this.getBoundsInParent())) {
                            this.moveUp();
                            x.moveDown();
                        }
                    }
                    if (differenceY < -5) {
                        while (x.intersects(this.getBoundsInParent())) {
                            this.moveDown();
                            x.moveUp();
                        }
                    }

                }
            }
        }
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
