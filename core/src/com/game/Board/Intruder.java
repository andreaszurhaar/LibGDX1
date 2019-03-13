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
	
	private float speed;
	private float angle;
    SpriteReader reader = new SpriteReader();

	public Intruder(float x, float y) {
		super(x, y);
        try {
            this.texture = reader.getImage(225,255,30,33);
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	public void triggerStep() {
		speed = (float) Math.random()*1.4f;
		angle = (float) Math.random()*360-180;
	}
	
}
