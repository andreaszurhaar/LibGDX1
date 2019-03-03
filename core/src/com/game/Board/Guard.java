/**
 * 
 */
package com.game.Board;

/**
 * @author Lukas Padolevicius
 *
 */
public class Guard extends Agent{

	private float speed;
	private float angle;
	
	public Guard(float x, float y) {
		super(x, y);
	}
	
	public void triggerStep() {
		System.out.println("activated trigger and changed speed from: "+speed+"  "+angle);
		speed = (float) Math.random()*1.4f;
		angle = (float) Math.random()*360-180;
		System.out.println("to: "+speed+"  "+angle);
	}


}
