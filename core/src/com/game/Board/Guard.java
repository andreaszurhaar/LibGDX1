/**
 * 
 */
package com.game.Board;

import java.io.IOException;

import com.game.Readers.SpriteReader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
            this.noticeSound = reader.getImage(135,425,20,20);
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
		this.speed = 1.4f;//(float) (Math.random()*1.4f);
		this.rotation = (float) -Math.toRadians(Math.random()*turningCircle/2);
		//System.out.println("to: "+speed+"  "+angle);
	}
	

}
