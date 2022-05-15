package aasim.gameattempt;

import javafx.scene.paint.Color;

public class Player extends Sprite {

	Player(int x, int y, int w, int h, String type, Color color) {
		super(x, y, type, color);
		this.players.add(this);
		
		// TODO Auto-generated constructor stub
	}

	

	public double position(){
		
		return this.getX();
	}
	
}
