/**
 * 
 */
package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Board;

import java.util.Stack;


/**
 * @author Lukas Padolevicius
 *
 */
public class Instruction {
		
	private Stack<Float> rotations;
	private Stack<Float> speeds;
	
	public Instruction() {
		rotations = new Stack();
		speeds = new Stack();
	}
	
	public void translate(Vector2 positions, Agent agent) {
		
		//find the length to traverse and how much to turn
		float pathLength = positions.len();
		float turnAngle = agent.viewAngle(positions);
		
		//prepare to invert turning if the shortest angle is negative
		boolean positive = true;
		if (turnAngle < 0) {
			positive = false;
			turnAngle = -turnAngle;
		}
		
		//find the number of times to turn at maximum and the leftover small turn
		float maxTurn = agent.turningCircle/Board.fps;
		float ufTurn = turnAngle/maxTurn;
		int turncount = (int) ufTurn;
		float leftoverTurn = turnAngle - ((float) turncount * maxTurn);
		
		//invert back the values as needed
		if(!positive) {
			maxTurn = -maxTurn;
			leftoverTurn = -leftoverTurn;
		}
		for(int i=0; i < turncount; i++) {
			rotations.push(maxTurn);
			speeds.push(0f);
		}
		rotations.push(leftoverTurn);
		speeds.push(0f);
		
		//now repeat for only speed
		float maxWalk = agent.maxSpeed/Board.fps;
		float ufWalk = pathLength/maxWalk;
		int walkcount = (int) ufWalk;
		float leftoverSpeed = maxWalk - ((float) walkcount * maxWalk);
		
		for(int i=0; i < walkcount; i++) {
			rotations.push(0f);
			speeds.push(maxWalk);
		}
		rotations.push(0f);
		speeds.push(leftoverSpeed);
		
	}
	
	public Stack<Float> getRotations() {
		Stack<Float> rots = rotations;
		rotations = new Stack<Float>();
		return rots;
	}
	
	public Stack<Float> getSpeeds() {
		Stack<Float> sps = speeds;
		speeds = new Stack<Float>();
		return sps;
	}
}
