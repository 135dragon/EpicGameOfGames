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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Lighting;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class GameStart extends Application {

    private Pane root = new Pane();

    private double t = 0;

    private Player player = new Player(300, 750);
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

        Enemy e1 = new Enemy(100, 100);
        Enemy e2 = new Enemy(200, 100);
        Enemy e3 = new Enemy(300, 100);
        Enemy e4 = new Enemy(400, 100);
        Enemy e5 = new Enemy(500, 100);
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
            player.attack(e);
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main1(String[] args) {
        launch(args);
    }
}
