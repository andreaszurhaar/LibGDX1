/**
 * 
 */
package com.game.Board;

import java.io.IOException;

import com.game.Readers.SpriteReader;

/**
 * @author Lukas Padolevicius
 * @author Andreas Zurhaar
 */
public class Guard extends Agent {

	public float speed;
	public float angle;
	public float soundRange;
    SpriteReader reader = new SpriteReader();

	
	public Guard(float x, float y, float width, float height) {
		super(x, y, width, height);
		viewAngle.setToRandomDirection();
		speed = 1;
		soundRange = 0;
		try {
	        this.texture = reader.getImage(65,255,30,33);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	
	public float getSpeed() {
		return speed;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void triggerStep() {
		//System.out.println("activated trigger and changed speed from: "+speed+"  "+angle);
		this.speed = (float) (Math.random()*2f);
		rotation = (float) (Math.random()*45/10);//this.angle = angle - 0.03f;//(float) (Math.random()*0.9f-0.45f)/5;
		//System.out.println("to: "+speed+"  "+angle);
	}

}
