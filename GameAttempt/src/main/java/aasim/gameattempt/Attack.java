/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aasim.gameattempt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author 14048
 */
public class Attack extends Sprite {

    Sprite owner;
    MouseEvent e;

    public Attack(double x, double y, Sprite owner, MouseEvent e) {
        super(x, y);
        Sprite.collisions.remove(this);
        width = 60;
        height = 20;
        //Set the image
        try {
            fis = new FileInputStream("swordthrust.gif");
            img = new Image(fis, width, height, false, false);
            this.setImage(img);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Detect Collisions
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isVisible()) {
                    this.stop();
                }
                update();
            }

        };
        //Calculate angle
        double angle = calcAngle(e.getX(), this.getX(), this.getX(), this.getX() + 100, e.getY(), this.getY(), this.getY(), this.getY());
        //Rotate attack based off of angle, center the pivot point based on player sprite.
        Rotate rotate = new Rotate(angle, owner.getX() + (owner.width / 2), owner.getY() + (owner.height / 2));
        this.getTransforms().add(rotate);
        //
        this.owner = owner;
        timer.start();
        //Stun the player and remove the attack after .8 second
        attack();
    }

    private void attack() {
        owner.stunned = true;
        PauseTransition hide = new PauseTransition(Duration.seconds(0.7));
        hide.setOnFinished(eh -> {
            this.setVisible(false);
            ((Pane) this.getScene().getRoot()).getChildren().remove(this);
            owner.stunned = false;
        });
        hide.play();

    }
    boolean counter = false;

    private void update() {
        if (counter) {
            return;
        }
        for (Sprite x : collisions) {
            if (x != this && x != owner && x.getClass() != this.getClass()) {
                if (x.intersects(this.getBoundsInParent())) {
//                    x.setImage(deadAnimation);
                    x.dead = true;
                    counter = true;
                    break;
                }
            }
        }

    }

    private double calcAngle(double x1, double x2, double x3, double x4, double y1, double y2, double y3, double y4) {
        double angle = 0;
        double slope1 = (y2 - y1) / (x2 - x1);
        double slope2 = (y4 - y3) / (x4 - x3);
        angle = Math.atan((slope1 - slope2) / (1 + slope1 * slope2));
        angle = Math.toDegrees(angle);
        if (x1 < this.getX()) {
            angle += 180;
        }
        System.out.println(angle);
        if (Double.isNaN(angle)) {
            if (e.getY() < owner.getY()) {
                angle = -90;
            } else if (e.getY() > owner.getY()) {
                angle = -90;
            }
        }
        return angle;
    }
}
