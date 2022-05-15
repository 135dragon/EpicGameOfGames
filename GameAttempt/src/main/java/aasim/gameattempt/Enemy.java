/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aasim.gameattempt;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

/**
 *
 * @author 14048
 */
public class Enemy extends Sprite {

	 public Enemy(int x, int y, String type, Color color) {
	        super(x, y, type, color);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();
        setSpeed(1);
    }

    boolean goLeft = true;
    boolean goUp = true;
    double screenX = Screen.getPrimary().getBounds().getWidth();
    double screenY = Screen.getPrimary().getBounds().getHeight();
    double playerX, playerY; 
    double C = Math.abs(this.getX()-playerX)/(Math.abs(this.getY()-playerY));//used to determine angle 
    double angle = Math.sin(C);//angle that the player is from enemy
    private void update() {
        for (Sprite x : this.players) {
            playerX = x.getX();
            playerY = x.getY();  
           
            
            
           
          }
        
        //ENEMY AI
       /**
        if (this.getX() > 0 && goLeft) {
            this.moveLeft();
        } else {
            goLeft = false;
        }

        if (this.getX() < screenX - 25 && !goLeft) {
            this.moveRight();
        } else {
            goLeft = true;
        }

        if (this.getY() > 0 && goUp) {
            this.moveUp();
        } else {
            goUp = false;
        }

        if (this.getY() < screenY - 50 && !goUp) {
            this.moveDown();
        } else {
            goUp = true;
        }
        **/
        
       
        if(playerY > this.getY()) {
      	  this.moveDown();
        } 
        if(playerY < this.getY() ) {
      	  this.moveUp();
        } 
        if(playerX > this.getX() ) {
      	  this.moveRight();
        } 
        if(playerX < this.getX() ) {
      	  this.moveLeft();
        }else 
        
      //System.out.println(playerX + ", " + playerY);
       
        
        

        // Collisions
        for (Sprite x : collisions) {
            if (x != this) {
                if (x.intersects(this.getBoundsInParent())) {
                    goLeft = !goLeft;
                    goUp = !goUp;
                }
            }
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
