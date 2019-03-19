/**
 * 
 */
package com.game.Board;

import java.io.IOException;

import com.game.Readers.SpriteReader;

/**
 * @author Lukas Padolevicius
 *
 */
public class Intruder extends Agent {
	
	public float speed;
	public float angle;
	public float soundRange;
    SpriteReader reader = new SpriteReader();

	public Intruder(float x, float y) {
		super(x, y);
		viewAngle.setToRandomDirection();
		speed = 1;
		soundRange = 0;
        try {
            this.texture = reader.getImage(225,255,30,33);
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	public void setPos(float x, float y) {
		xPos = x;
		yPos = y;
   	}
	
	public float getSpeed() {
		return speed;
	}
	
	public float getAngle() {
		return angle;
	}

	public void triggerStep() {
		this.speed = (float) Math.random()*2f;
		this.angle = angle + 0.02f+(float) (Math.random()*0.9f-0.45f)/20;//(float) (Math.random()*0.9f-0.45f)/5;
	}
	
}
