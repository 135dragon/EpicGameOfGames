/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aasim.gameattempt;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author 14048
 */
public class Level extends Scene {

    //The actual level itself
    Pane root = new Pane();
    //Timer counter for update() loop
    private double t = 0;
    //Player
    private Player player = new Player(300, 750);
    //Player Controls - w  a  s  d
    boolean upPressed, downPressed, leftPressed, rightPressed;

    public Level() {
        super(new Pane());
        root.getChildren().add(player);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        //Add Listeners to Controls (wasd, lClick)
        setListeners();
        //Build the level (Add Enemies, Walls / More tiles)
        buildLevel();
        this.setRoot(root);
    }

    private void setListeners() {
        this.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    player.leftPressed = true;
                    break;
                case D:
                    player.rightPressed = true;
                    break;
                case W:
                    player.upPressed = true;
                    break;
                case S:
                    player.downPressed = true;
                    break;

            }
        });
        this.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case A:
                    player.leftPressed = false;
                    break;
                case D:
                    player.rightPressed = false;
                    break;
                case W:
                    player.upPressed = false;
                    break;
                case S:
                    player.downPressed = false;
                    break;
            }
        });
        this.setOnMouseMoved(e -> {
            if (e.getX() < player.getX()) {
                player.setImage(player.leftWalk);
            } else {
                player.setImage(player.rightWalk);
            }

        });

        this.setOnMouseClicked(e -> {
            player.attack(e);
            System.out.println(e.getX() + " " + e.getY());
        });
    }

    private void buildLevel() {
        Enemy e1 = new Enemy(100, 100);
        Enemy e2 = new Enemy(200, 100);
        Enemy e3 = new Enemy(300, 100);
        Enemy e4 = new Enemy(400, 100);
        Enemy e5 = new Enemy(500, 100);
        for (int i = 0; i < 1024; i += 16) {
            Wall wall = new Wall(i, 313);
            root.getChildren().add(wall);
        }
        root.getChildren().addAll(e1, e2, e3, e4, e5);
    }

    private void update() {
    }
}
