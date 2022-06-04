/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aasim.gameattempt;

import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author 14048
 */
public class Level extends Scene {

    //level menu components
    StackPane sp = new StackPane(); //Layering Mechanism
    Pane root = new Pane(); // The Actual Level
    GridPane escapeMenu = new GridPane(); // Escape Menu 

    //Timer counter for update() loop
    private double t = 0;
    //Player
    private Player player = new Player(1024 / 2, 1024 / 2);
    //Player Controls - w  a  s  d
    boolean win = false;
    boolean isEscapeMenu = false;

    public Level() {
        super(new Pane());
        root.setBackground(Background.EMPTY);
        sp.setBackground(Background.EMPTY);
        sp.setEventDispatcher(root.eventDispatcherProperty().get());
        root.getChildren().add(player);
        //Escape Menu
        escapeMenu.setVisible(false);
        escapeMenu.setBackground(new Background(new BackgroundFill(Paint.valueOf("black"), CornerRadii.EMPTY, Insets.EMPTY)));
        escapeMenu.setOpacity(0.9);
        Label contBtn = new Label("Continue");
        Label exitBtn = new Label("Exit");
        VBox buttonContainer = new VBox(contBtn, exitBtn);
        escapeMenu.getChildren().add(buttonContainer);
        contBtn.setOnMouseClicked(eh -> {
            isEscapeMenu = false;
            isEscapeMenu();
        });
        exitBtn.setOnMouseClicked(eh -> {
            System.exit(0);
        });

        buttonContainer.setPadding(new Insets(10));
        buttonContainer.setAlignment(Pos.CENTER);
        escapeMenu.setAlignment(Pos.CENTER);
        //Health Bar
        StackPane hb = new StackPane();
        Rectangle background = new Rectangle(200, 50);
        background.setFill(Paint.valueOf("black"));
        player.currentHealth.setTranslateX(5);
        player.currentHealth.setTranslateY(-5);
        player.currentHealth.setFill(Paint.valueOf("red"));

        player.healthTxt.setTranslateX(10);
        player.healthTxt.setTranslateY(-5);
        hb.getChildren().addAll(background, player.currentHealth, player.healthTxt);
        hb.setAlignment(Pos.BOTTOM_LEFT);
        //
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (update()) {
                    this.stop();
                }
            }
        };
        timer.start();
        //Add Listeners to Controls (wasd, lClick)
        setListeners();
        //Build the level (Add Enemies, Walls / More tiles)
        buildLevel();
        sp.getChildren().addAll(root, hb, escapeMenu);

        this.setRoot(sp);
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
                case UP:
                    player.cameraUp = true;
                    break;
                case DOWN:
                    player.cameraDown = true;
                    break;
                case LEFT:
                    player.cameraLeft = true;
                    break;
                case RIGHT:
                    player.cameraRight = true;
                    break;
                case ESCAPE:
                    isEscapeMenu = !isEscapeMenu;
                    isEscapeMenu();
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
                case UP:
                    player.cameraUp = false;
                    break;
                case DOWN:
                    player.cameraDown = false;
                    break;
                case LEFT:
                    player.cameraLeft = false;
                    break;
                case RIGHT:
                    player.cameraRight = false;
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
        this.setOnScroll(eh -> {
            double scrollAmt = 0;
            if (eh.getDeltaY() > 0) {
                scrollAmt = .1;
            } else {
                scrollAmt = -.1;
            }
            root.setScaleY(root.getScaleY() + scrollAmt);
            root.setScaleX(root.getScaleX() + scrollAmt);
        });
//        this.setOnMouseClicked(eh -> {
//            Event.fireEvent(root, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
//                    0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false,
//                    false, false, false, false, false, false, null)
//            );
//        });

        root.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            player.attack(x, y);
        });

    }

    private void buildLevel() {
        Enemy e1 = new Enemy(100, 100);
        Enemy e2 = new Enemy(200, 100);
        Enemy e3 = new Enemy(300, 100);
        Enemy e4 = new Enemy(400, 100);
        Enemy e5 = new Enemy(500, 100);
        for (int i = 0; i < 1024; i += 32) {
            Wall wall = new Wall(i, 0);
            Wall wall1 = new Wall(0, i);
            Wall wall2 = new Wall(i, 1024);
            Wall wall3 = new Wall(1024, i);
            root.getChildren().addAll(wall, wall1, wall2, wall3);
        }
        root.getChildren().addAll(e1, e2, e3, e4, e5);
    }

    double counter = 0;

    private boolean update() {
        counter += .012;
        boolean allDead = true;
        //Only checks for the win condition every 5 seconds
        if (counter > 5) {
            for (Node x : root.getChildren()) {
                try {
                    Enemy enemy = (Enemy) x;
                    if (!enemy.dead) {
                        allDead = false;
                    }
                } catch (Exception e) {

                }
            }
            if (allDead) {
                Label label = new Label("You Win!");
                label.setLayoutX(512);
                label.setLayoutY(512);
                root.getChildren().add(label);
                System.out.println("You win!");
                return true;
            }
        }

        return false;
    }

    private void isEscapeMenu() {

        if (isEscapeMenu) {
            escapeMenu.setVisible(true);
            System.out.println("yes");
        } else {
            escapeMenu.setVisible(false);
            System.out.println("no");
        }

    }
}
