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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author 14048
 */
public class Attack extends Sprite {

    public Attack(int x, int y, String type, Color color) {
        super(x, y, type, color);
        try {
            fis = new FileInputStream("swordthrust.gif");
            img = new Image(fis, 32, 32, false, false);
            this.setImage(img);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isVisible()) {
                    this.stop();
                    removeAttackFromCollision();
                }
                update();
            }

        };

        timer.start();
    }

    private void update() {

        for (Sprite x : collisions) {
            if (x != this) {
                if (x.intersects(this.getBoundsInParent())) {
                    x.setImage(deadAnimation);
                    x.dead = true;
                }
            }
        }

    }

    private void removeAttackFromCollision() {
        Sprite.collisions.remove(this);
    }
}
