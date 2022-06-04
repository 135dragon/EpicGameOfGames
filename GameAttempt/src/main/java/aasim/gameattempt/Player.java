package aasim.gameattempt;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Player extends Sprite {

    public boolean upPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean downPressed;
    public double health = 100;
    public double maxHealth = 100;
    Rectangle currentHealth = new Rectangle(190, 40);
    Label healthTxt = new Label("" + health);

    public boolean cameraUp, cameraDown, cameraLeft, cameraRight;
    double startX, startY;

    Player(double x, double y) {

        super(x, y, 30, 40); //w:h is 3:4
        Sprite.players.add(this);
        Sprite.collisions.add(this);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (dead) {
                    this.stop();
                }
                update();
            }
        };
        startX = x;
        startY = y;
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
            Pane parent = (Pane) this.getParent();
            parent.setTranslateY(parent.getTranslateY() + this.speed);
        }
        if (leftPressed) {
            this.moveLeft();
            Pane parent = (Pane) this.getParent();
            parent.setTranslateX(parent.getTranslateX() + this.speed);

        }
        if (rightPressed) {
            this.moveRight();
            Pane parent = (Pane) this.getParent();
            parent.setTranslateX(parent.getTranslateX() - this.speed);
        }
        if (downPressed) {
            this.moveDown();
            Pane parent = (Pane) this.getParent();
            parent.setTranslateY(parent.getTranslateY() - this.speed);

        }
        if (cameraUp) {
            Pane parent = (Pane) this.getParent();
            parent.setTranslateY(parent.getTranslateY() + this.speed);
        }
        if (cameraDown) {
            Pane parent = (Pane) this.getParent();
            parent.setTranslateY(parent.getTranslateY() - this.speed);
        }
        if (cameraLeft) {
            Pane parent = (Pane) this.getParent();
            parent.setTranslateX(parent.getTranslateX() + this.speed);
        }
        if (cameraRight) {
            Pane parent = (Pane) this.getParent();
            parent.setTranslateX(parent.getTranslateX() - this.speed);
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
                        this.moveLeft();
                        this.moveLeft();

                        x.moveRight();
                    }
                    if (differenceX < -1) {
                        this.moveRight();
                        this.moveRight();
                        x.moveLeft();
                    }
                    if (differenceY > 5) {
                        this.moveUp();
                        this.moveUp();
                        x.moveDown();
                    }
                    if (differenceY < -5) {
                        this.moveDown();
                        this.moveDown();
                        x.moveUp();
                    }

                }
            }
        }
    }

    public void attack(double mouseX, double mouseY) {
        if (stunned) {
            return;
        }
        Attack a1 = new Attack(this.getX(), this.getY(), this, mouseX, mouseY);

        ((Pane) this.getParent()).getChildren().add(a1);
        addHealth(-5);
    }

}
