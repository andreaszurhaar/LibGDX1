/**
 * 
 */
package com.game.Board;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Lukas Padolevicius
 *
 */

public class Agent {

	private float xPos;
	private float yPos;
	private Vector2 viewAngle;
	private float speed;
	private float rotation;
	private float maxRotation;
	private float maxSpeed;
	
	
	public Agent(float x, float y) {
		xPos = x;
		yPos = y;
		viewAngle = new Vector2(1,1);
		viewAngle.setToRandomDirection();
		speed = 0;
	}
	
	public float getX() {
		return xPos;
	}
	
	public float getY() {
		return yPos;
	}
	
	public void setPos(float x, float y) {
		xPos = x;
		yPos = y;
	}
	
	public float getAngle() {
		return viewAngle.angle();
	}

	public float getRotation() {
		return rotation;
	}

	public void rotate(float ang) {
		viewAngle.rotate(ang);
	}
	
	public void setAngle(float ang) {
		viewAngle.setAngle(ang);
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float sp) {
		speed = sp;
	}
	
	public void hearSound(float x, float y) {
		//heard a sound
	}
	
	public void see(Area object) {
		//saw object
	}
	
	public void triggerStep() {	}

}
