package aasim.gameattempt;

import javafx.animation.PauseTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Player extends Sprite {

    Player(double x, double y) {
        super(x, y);
        this.players.add(this);
        // TODO Auto-generated constructor stub

    }

    public void attack(MouseEvent e) {
        if (stunned) {
            return;
        }
        Attack a1 = new Attack(this.getX(), this.getY(), this, e);
        ((Pane) this.getScene().getRoot()).getChildren().addAll(a1);
    }

}
