/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aasim.gameattempt;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameStart extends Application {

    Scene scene;
    Sprite e1 = new Sprite(-100, 650, 30, 40);

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane bp = new BorderPane();
        scene = new Scene(bp);
        scene.getStylesheets().add("file:General.css");

        //Background Image
        File myFile = new File("background.png");
        URL url = myFile.toURI().toURL();
        bp.setStyle("-fx-background-image: url('" + url.toExternalForm() + "'); -fx-background-size: cover;");
        //Menu Item + Options
        VBox container = new VBox();
        container.setMaxSize(250, 225);
        container.setId("MainMenu");
        Label title = new Label("\nEpic Game of Games");
        title.setId("title");
        Label undertitle = new Label("The most epic of all the epic games");
        Separator separator1 = new Separator();
        separator1.setPadding(new Insets(10));
        Label play = new Label("\tPlay");
        Label cont = new Label("\tContinue");
        Label settings = new Label("\tSettings");
        Label exit = new Label("\tExit");

        HBox playContainer = new HBox(play);
        HBox contContainer = new HBox(cont);
        HBox settingsContainer = new HBox(settings);
        HBox exitContainer = new HBox(exit);

        playContainer.setId("button");
        contContainer.setId("button");
        settingsContainer.setId("button");
        exitContainer.setId("button");
        container.getChildren().addAll(title, undertitle, separator1, playContainer, contContainer, settingsContainer, exitContainer);
        container.setAlignment(Pos.TOP_CENTER);
        bp.setCenter(container);

        //
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        bp.getChildren().add(e1);
        //
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        playContainer.setOnMouseClicked(eh -> {
            scene = new Level();
            stage.setScene(scene);
            stage.hide();
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.show();
            timer.stop();
        });
        contContainer.setOnMouseClicked(eh -> {

        });
        settingsContainer.setOnMouseClicked(eh -> {

        });
        exitContainer.setOnMouseClicked(eh -> {
            System.exit(0);
            System.out.println("there is no exit");
        });

    }

    private double counter = 0;

    private void update() {
        counter += 0.012;
        if (counter < 10) {
            e1.moveRight();
            System.out.println("move right");
        }
        if (counter >= 10) {
            e1.moveLeft();
            System.out.println("move left");
        }
        if (counter >= 20) {
            counter = 0;

        }
    }

    public static void main1(String[] args) {
        launch(args);
    }
}
