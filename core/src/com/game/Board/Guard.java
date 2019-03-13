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
public class Guard extends Agent {

	private float speed;
	private float angle;
    SpriteReader reader = new SpriteReader();

	
	public Guard(float x, float y) {
		super(x, y);

		try {
	        this.texture = reader.getImage(65,255,30,33);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	
	public void triggerStep() {
		System.out.println("activated trigger and changed speed from: "+speed+"  "+angle);
		speed = (float) Math.random()*1.4f;
		angle = (float) Math.random()*360-180;
		System.out.println("to: "+speed+"  "+angle);
	}

}
