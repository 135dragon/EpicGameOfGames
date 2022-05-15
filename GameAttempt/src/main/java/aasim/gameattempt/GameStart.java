/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aasim.gameattempt;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import javafx.animation.PauseTransition;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class GameStart extends Application {

    private Pane root = new Pane();

    private double t = 0;

    private Player player = new Player(300, 750, 40, 40, "player", Color.BLUE);
    boolean upPressed, downPressed, leftPressed, rightPressed;

    private Parent createContent() {
        root.setPrefSize(600, 800);
        root.getChildren().add(player);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        Enemy e1 = new Enemy(100, 100, "enemy", Color.RED);
        Enemy e2 = new Enemy(200, 100, "enemy", Color.RED);
        Enemy e3 = new Enemy(300, 100, "enemy", Color.RED);
        Enemy e4 = new Enemy(400, 100, "enemy", Color.RED);
        Enemy e5 = new Enemy(500, 100, "enemy", Color.RED);
        root.getChildren().addAll(e1, e2, e3, e4, e5);

        timer.start();

        return root;
    }

    private void update() {
        t += 0.016;
        //Player Movement
        if (upPressed) {
            player.moveUp();
        }
        if (leftPressed) {
            player.moveLeft();
        }
        if (rightPressed) {
            player.moveRight();
        }
        if (downPressed) {
            player.moveDown();
        }
        if (t > 2) {
            t = 0;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    leftPressed = true;
                    break;
                case D:
                    rightPressed = true;
                    break;
                case W:
                    upPressed = true;
                    break;
                case S:
                    downPressed = true;
                    break;
            }
        });
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case A:
                    leftPressed = false;
                    break;
                case D:
                    rightPressed = false;
                    break;
                case W:
                    upPressed = false;
                    break;
                case S:
                    downPressed = false;
                    break;
            }
        });
        scene.setOnMouseMoved(e -> {
            if (e.getX() < player.getX()) {
                player.setImage(player.leftWalk);
            } else {
                player.setImage(player.rightWalk);
            }

        });

        scene.setOnMouseClicked(e -> {
            Line line = new Line(e.getX(), e.getY(), player.getX(), player.getY());
            Line line2 = new Line(player.getX(), player.getY(), player.getX() + 100, player.getY());

//            double angle1 = Math.atan2(line.getEndY() - line.getStartY(), line.getEndX() - line.getStartX());
//            double angle2 = Math.atan2(line2.getEndY() - line2.getStartY(), line2.getEndX() - line2.getStartX());
            double angle = calcAngle(line.getStartX(), line.getEndX(), line2.getStartX(), line2.getEndX(), line.getStartY(), line.getEndY(), line2.getStartY(), line2.getEndY());
            Attack a1 = new Attack((int) player.getX(), (int) player.getY(), "enemy", Color.RED);
            a1.setRotate(angle * 270);
            root.getChildren().addAll(line, line2, a1);
            PauseTransition hideCircle = new PauseTransition(Duration.seconds(1));
            hideCircle.setOnFinished(eh -> {
                a1.setVisible(false);
                line.setVisible(false);
                line2.setVisible(false);
            });
            hideCircle.play();
        });

        stage.setScene(scene);
        stage.show();
    }

    public double calcAngle(double x1, double x2, double x3, double x4, double y1, double y2, double y3, double y4) {
        double C = Math.abs(x1 - x2) / (Math.abs(y1 - y2));//used to determine angle 
        double angle = Math.sin(C);//angle that the player is from enemy
        return angle;
    }

    public static void main1(String[] args) {
        launch(args);
    }
}
