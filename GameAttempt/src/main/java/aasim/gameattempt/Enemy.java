/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aasim.gameattempt;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Duration;
import java.util.Random;
/**
 *
 * @author 14048
 */
public class Enemy extends Sprite {

    public Enemy(int x, int y) {
        super(x, y);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (dead) {
                    this.stop();
                }
                update();
            }
        };

        timer.start();
        setSpeed(Math.random());
    }

    boolean goLeft = true;
    boolean goUp = true;
    double screenX = Screen.getPrimary().getBounds().getWidth();
    double screenY = Screen.getPrimary().getBounds().getHeight();
    double playerX, playerY;
    double xDist = Math.abs(this.getX() - playerX);
    double yDist = Math.abs(this.getY() - playerY);
    Random rand = new Random();
    

    //double angle = Math.sin(C);//angle that the player is from enemy
    private void update() {
    	double xDist = Math.abs(this.getX() - playerX);
        double yDist = Math.abs(this.getY() - playerY);
        if (dead) {
            if (!this.getImage().equals(deadAnimation)) {
                this.setImage(deadAnimation);
            }
            PauseTransition hide = new PauseTransition(Duration.seconds(0.5));
            hide.setOnFinished(eh -> {
                this.setVisible(false);
                ((Pane) this.getScene().getRoot()).getChildren().remove(this);
                Sprite.collisions.remove(this);
            });
            hide.play();
            return;
        }

        for (Sprite x : this.players) {
            playerX = x.getX();
            playerY = x.getY();

        }
        

        //ENEMY AI
        System.out.println(yDist);
        if(xDist<300 && yDist<300) {
        	aware();
        }
        else {
        	unaware();
        }
        	   /**
		        if (playerY > this.getY()) {
		            this.moveDown();
		        }
		        if (playerY < this.getY()) {
		            this.moveUp();
		        }
		        if (playerX > this.getX()) {
		            this.moveRight();
		        }
		        if (playerX < this.getX()) {
		            this.moveLeft();
		        }
        **/
        
        
       // Enemy POV (visibility)
        
        for (Sprite x : POV) {
        	if(x != this) {
        		 if (x.intersects(this.getBoundsInParent())) {
                     double differenceX = x.getX() - this.getX();
                     double differenceY = x.getY() - this.getY();
                     if (differenceX > 10) {//will stop enemies and players from colliding, unless walked into for now
                         while (x.intersects(this.getBoundsInParent())) {
                        	 
                        	 
                         }
                     }
        	
        }
        	}
        }
        	
        
       
        
        // Collisions

        for (Sprite x : collisions) {
            if (x != this) {
                if (x.intersects(this.getBoundsInParent())) {
                    double differenceX = x.getX() - this.getX();
                    double differenceY = x.getY() - this.getY();
                    if (differenceX > 10) {//will stop enemies and players from colliding, unless walked into for now
                        while (x.intersects(this.getBoundsInParent())) {
                            this.moveLeft();
                            x.moveRight();
                        }
                    }
                    if (differenceX < -10) {
                        while (x.intersects(this.getBoundsInParent())) {
                            this.moveRight();
                            x.moveLeft();
                        }
                    }
                    if (differenceY > 5) {
                        while (x.intersects(this.getBoundsInParent())) {
                            this.moveUp();
                            x.moveDown();
                        }
                    }
                    if (differenceY < -5) {
                        while (x.intersects(this.getBoundsInParent())) {
                            this.moveDown();
                            x.moveUp();
                        }
                    }

                }
            }
        }
    }
    //enemy unaware
    public void unaware() {
    	int unawareDirection = rand.nextInt(5);
    	switch(unawareDirection) {
    	case 1:
    		this.moveLeft();
    		unaware();
    		break;
    	case 2:
    		this.moveRight();
    		unaware();
    		break;
    	case 3:
    		this.moveUp();
    		unaware();
    		break;
    	case 4:
    		this.moveDown();
    		unaware();
    		break;
    	}
    	
    }
    
    //enemy aware
    public void aware() {
    	if (playerY > this.getY()) {
            this.moveDown();
        }
        if (playerY < this.getY()) {
            this.moveUp();
        }
        if (playerX > this.getX()) {
            this.moveRight();
        }
        if (playerX < this.getX()) {
            this.moveLeft();
        }
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
