package aasim.gameattempt;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Player extends Sprite {

    public boolean upPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean downPressed;

    Player(double x, double y) {

        super(x, y, 30, 40); //w:h is 3:4
        Sprite.players.add(this);
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
    }

    private void update() {
        //Death
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
//        t += 0.016;
        //Player Movement
        if (upPressed) {
            this.moveUp();
        }
        if (leftPressed) {
            this.moveLeft();
        }
        if (rightPressed) {
            this.moveRight();
        }
        if (downPressed) {
            this.moveDown();
        }
//        if (t > 2) {
//            t = 0;
//        }

        //Collisions
        for (Sprite x : collisions) {
            if (x != this) {
                if (x.intersects(this.getBoundsInParent())) {
                    double differenceX = x.getX() - this.getX();
                    double differenceY = x.getY() - this.getY();
                    if (differenceX > 1) {//will stop enemies and players from colliding, unless walked into for now
//                        while (x.intersects(this.getBoundsInParent())) {
                        this.moveLeft();
                        x.moveRight();
//                        }
                    }
                    if (differenceX < -1) {
//                        while (x.intersects(this.getBoundsInParent())) {
                        this.moveRight();
                        x.moveLeft();
//                        }
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

    public void attack(MouseEvent e) {
        if (stunned) {
            return;
        }
        Attack a1 = new Attack(this.getX(), this.getY(), this, e);
        ((Pane) this.getScene().getRoot()).getChildren().addAll(a1);
    }

}
