/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aasim.gameattempt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author 14048
 */
public class Wall extends Sprite {

    Rectangle hitbox;

    public Wall(double x, double y) {
        super(x, y);
        Sprite.collisions.add(this);
        try {

            this.fis = new FileInputStream("wall.jpg");
            img = new Image(fis, 32, 32, false, true);
            this.setImage(img);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void moveDown() {
        return;
    }

    @Override
    void moveLeft() {
        return;
    }

    @Override
    void moveRight() {
        return;
    }

    @Override
    void moveUp() {
        return;
    }

}
