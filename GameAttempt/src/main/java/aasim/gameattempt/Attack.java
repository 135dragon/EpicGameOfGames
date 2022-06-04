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
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author 14048
 */
public class Attack extends Sprite {

    Sprite owner;
    private double mouseX, mouseY;
    Rectangle hitbox;

    public Attack(double x, double y, Sprite owner, double mouseX, double mouseY) {
        super(x, y, 60, 20);
        //Width determines how 'long' the attack is, height determines how wide it is. 
        //Dumb, I know. Just deal with it
        width = 60;
        height = 20;
        hitbox = new Rectangle(x, y, 0, height);
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
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        double angle = calcAngle(mouseX, this.getX(), this.getX(), this.getX() + 100, mouseY, this.getY(), this.getY(), this.getY());
        //Rotate attack based off of angle, center the pivot point based on player sprite.
        Rotate rotate = new Rotate(angle, owner.getX() + (owner.width / 2), owner.getY() + (owner.height / 2));
        this.getTransforms().add(rotate);
        hitbox.getTransforms().add(rotate);
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
    private boolean counter = false; //Counter to detect if an enemy has already died (and if has, don't kill any more). Disable if you want attacks to 'pierce' enemies
    private double t = 0;

    private void update() {
        t += 0.012;

        if (counter) {
//            hitbox.setWidth(0);
            return;
        }
        //For every frame in attack gif, change the hitbox
        //SwordThrust has 7 frames and takes 0.7 seconds to complete, so each frame is visible for only 0.1 seconds

        if (t >= 0 && t <= 0.1) {
            //Hitbox change
            hitbox.setWidth(0);
        } else if (t >= 0.1 && t <= 0.2) {
            //Hitbox change
            hitbox.setWidth(0.3 * this.width);
        } else if (t >= 0.2 && t <= 0.3) {
            //Hitbox change
            hitbox.setWidth(0.5 * this.width);
        } else if (t >= 0.3 && t <= 0.4) {
            //Hitbox change
            hitbox.setWidth(1 * this.width);
        } else if (t >= 0.4 && t <= 0.5) {
            //Hitbox change
            hitbox.setWidth(0.5 * this.width);
        } else if (t >= 0.5 && t <= 0.6) {
            //Hitbox change
            hitbox.setWidth(0.3 * this.width);
        } else if (t >= 0.6 && t <= 0.7) {
            //Hitbox change
            hitbox.setWidth(0 * this.width);
        }

        //
        for (Sprite x : collisions) {
            if (x != this && x != owner && x.getClass() != this.getClass()) {
                if (x.intersects(hitbox.getBoundsInParent())) {
                    x.addHealth(-35);
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
//        System.out.println(angle);
        if (Double.isNaN(angle)) {
            if (mouseY < owner.getY()) {
                angle = -90;
            } else if (mouseY > owner.getY()) {
                angle = -90;
            }
        }
        return angle;
    }
}
