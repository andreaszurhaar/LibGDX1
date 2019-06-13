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
	
	public void translate(Vector2 destination, Agent agent) {
		
		//find the length to traverse and how much to turn
		System.out.println("Our destination: "+ destination.x +","+destination.y );
		System.out.println("Our current position:" + agent.xCenter +","+agent.yCenter);
		Vector2 positions = new Vector2((destination.x-agent.xCenter),(destination.y-agent.yCenter));
		System.out.println("Distance to travel:" + positions.x +","+positions.y);
		float pathLength = positions.len();
		float turnAngle = agent.viewAngle.angle(positions);
		System.out.println("TURNING FOR: "+turnAngle+"  AND MOVING FOR: "+pathLength);
		
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
		float leftoverSpeed = pathLength - ((float) walkcount * maxWalk);
		
		for(int i=0; i < walkcount; i++) {
			rotations.push(0f);
			speeds.push(maxWalk);
		}
		rotations.push(0f);
		speeds.push(leftoverSpeed);
		
		Stack<Float> reverseRotations = new Stack<Float>();
		Stack<Float> reverseSpeeds = new Stack<Float>();

		while(!speeds.isEmpty()) {
			reverseRotations.push(rotations.pop());
			reverseSpeeds.push(speeds.pop());
		}
		speeds = reverseSpeeds;
		rotations = reverseRotations;
		
	}
	
	public void translate(Vector2 destination, Vector2 start, float angle, float turningCircle, float maxSpeed) {
		
		while(angle < -180 || angle > 180) {
			if(angle < -180) {angle = angle+360;}
			if(angle > 180) {angle = angle-360;}
			
		}
		
		//find the length to traverse and how much to turn
		Vector2 positions = new Vector2((destination.x-start.x),(destination.y-start.y));
		float pathLength = positions.len();
		float turnAngle = positions.angle()-angle;
		System.out.println("TURNING FOR: "+turnAngle+"  AND MOVING FOR: "+pathLength);
		
		//prepare to invert turning if the shortest angle is negative
		boolean positive = true;
		if (turnAngle < 0) {
			positive = false;
			turnAngle = -turnAngle;
		}
		
		//find the number of times to turn at maximum and the leftover small turn
		float maxTurn = turningCircle/Board.fps;
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
		float maxWalk = maxSpeed/Board.fps;
		float ufWalk = pathLength/maxWalk;
		int walkcount = (int) ufWalk;
		float leftoverSpeed = pathLength - ((float) walkcount * maxWalk);
		
		for(int i=0; i < walkcount; i++) {
			rotations.push(0f);
			speeds.push(maxWalk);
		}
		rotations.push(0f);
		speeds.push(leftoverSpeed);
		
		Stack<Float> reverseRotations = new Stack<Float>();
		Stack<Float> reverseSpeeds = new Stack<Float>();

		while(!speeds.isEmpty()) {
			reverseRotations.push(rotations.pop());
			reverseSpeeds.push(speeds.pop());
		}
		speeds = reverseSpeeds;
		rotations = reverseRotations;
		
	}
	
	public Stack<Float> getRotations() {
		System.out.println("This stack rotation is of size: "+rotations.size());
		Stack<Float> rots = rotations;
		rotations = new Stack<Float>();
		return rots;
	}
	
	public Stack<Float> getSpeeds() {
		System.out.println("This other stack is of size: "+speeds.size());
		Stack<Float> sps = speeds;
		speeds = new Stack<Float>();
		return sps;
	}
}
