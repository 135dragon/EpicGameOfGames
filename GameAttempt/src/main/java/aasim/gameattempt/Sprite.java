/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aasim.gameattempt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 *
 * @author 14048
 */
public class Sprite extends ImageView {

    boolean dead = false;
    final String type;
    int speed = 20;
    public static ArrayList<Sprite> collisions = new ArrayList<>();
    public static ArrayList<Sprite> players = new ArrayList<Sprite>();
    FileInputStream fis;
    Image img, leftWalk, rightWalk;

    Sprite(int x, int y, String type, Color color) {
        if (!type.equals("wall")) {
            try {
                this.fis = new FileInputStream("char_walk_left.gif");
                img = new Image(fis, 50, 50, false, false);
                this.setImage(img);
                this.fis = new FileInputStream("char_walk_left.gif");
                leftWalk = new Image(fis, 50, 50, false, false);
                this.fis = new FileInputStream("char_walk_right.gif");
                rightWalk = new Image(fis, 50, 50, false, false);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        collisions.add(this);
        this.type = type;
        setX(x);
        setY(y);
    }
    
   

    void moveLeft() {
        setX(getX() - speed);
        this.setImage(leftWalk);
        
    }

    void moveRight() {
        setX(getX() + speed);
        this.setImage(rightWalk);
    }

    void moveUp() {
        setY(getY() - speed);
    }

    void moveDown() {
        setY(getY() + speed);
    }
}
