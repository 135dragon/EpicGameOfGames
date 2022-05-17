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
import java.util.*;

/**
 *
 * @author 14048
 */
public class Sprite extends ImageView {

    boolean dead = false;
    double speed = 2;
    Random rand = new Random();
    public static ArrayList<Sprite> collisions = new ArrayList<>();
    public static ArrayList<Sprite> POV = new ArrayList<>();
    public static ArrayList<Sprite> players = new ArrayList<Sprite>();
    
    FileInputStream fis;
    Image img;
    Image leftWalk, rightWalk, deadAnimation;
    boolean stunned = false;
    int height = 40, width = 30; //Aspect ratio is 4/3

    Sprite(double x, double y) {
//        super(new Image("https://w7.pngwing.com/pngs/34/292/png-transparent-sunglasses-thug-life-cool-miscellaneous-angle-white.png")); //random image, change later
        try {
            this.fis = new FileInputStream("char_walk_left.gif");
            img = new Image(fis, width, height, false, false);
            this.setImage(img);
            this.fis = new FileInputStream("char_walk_left.gif");
            leftWalk = new Image(fis, width, height, false, false);
            this.fis = new FileInputStream("char_walk_right.gif");
            rightWalk = new Image(fis, width, height, false, false);
            this.fis = new FileInputStream("dead.gif");
            deadAnimation = new Image(fis, width, height, false, false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
        }

        collisions.add(this);
        setX(x);
        setY(y);
    }

    void moveLeft() {
        if (stunned || dead) {
            return;
        }
        setX(getX() - speed);
        this.setImage(leftWalk);
    }

    void moveRight() {
        if (stunned || dead) {
            return;
        }
        setX(getX() + speed);
        this.setImage(rightWalk);
    }

    void moveUp() {
        if (stunned || dead) {
            return;
        }
        setY(getY() - speed);
    }

    void moveDown() {
        if (stunned || dead) {
            return;
        }
        setY(getY() + speed);
    }

	
}

