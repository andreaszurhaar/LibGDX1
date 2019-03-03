/**
 * 
 */
package com.game.Board;

/**
 * @author Lukas Padolevicius
 *
 */
public class Intruder extends Agent {
	
	private float speed;
	private float angle;
	
	public Intruder(float x, float y) {
		super(x, y);
	}
	
	public void triggerStep() {
		speed = (float) Math.random()*1.4f;
		angle = (float) Math.random()*360-180;
	}
	
}
