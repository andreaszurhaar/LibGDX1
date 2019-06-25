/**
 * Class used for translating complicated decision making from AIs into sets of 
 * instructions used by logic handling classes to simulate a game
 */
package com.game.AI;

import java.util.ArrayList;
import java.util.Stack;
import com.badlogic.gdx.math.Vector2;

import com.game.Board.Agent;

/**
 * @author Lukas Padolevicius
 */
public class ChainInstruction {
	
	private Stack<Float> rotations;
	private Stack<Float> speeds;
	private Instruction instructor;
	
	public ChainInstruction() {
		rotations = new Stack();
		speeds = new Stack();
		instructor = new Instruction();
	}
	
	public void translate(ArrayList<Vector2> destinations, Agent agent) {
		
		float turningCircle = agent.turningCircle;
		float maxSpeed = agent.maxSpeed;
		Vector2 start = new Vector2(agent.xCenter,agent.yCenter);
		float startAngle = agent.viewAngle.angle();
		destinations.add(0,start);
		
		ArrayList<Stack<Float>> rotationlist = new ArrayList<Stack<Float>>();
		ArrayList<Stack<Float>> speedlist = new ArrayList<Stack<Float>>();
		
		for (int i=0; i<destinations.size()-1; i++) {
			instructor.translate(destinations.get(i+1), destinations.get(i), startAngle, turningCircle, maxSpeed);

			//prepare the reversed stacks
			Stack<Float> revrot = instructor.getRotations();
			Stack<Float> revspe = instructor.getSpeeds();
			Stack<Float> rot = new Stack();
			Stack<Float> spe = new Stack();
			int len = revrot.size();
			for(int j=0; j<len; j++) {
				rot.push(revrot.pop());
				spe.push(revspe.pop());
			}
			//append it to current stacks
			rotationlist.add(rot);
			speedlist.add(spe);
			
			//update angle
			startAngle = new Vector2(destinations.get(i+1).x-destinations.get(i).x,destinations.get(i+1).y-destinations.get(i).y).angle();
		}
		
		for(int i=rotationlist.size()-1; i>=0; i--) {
			int len = rotationlist.get(i).size();
			for(int k=0; k<len; k++) {
				rotations.push(rotationlist.get(i).pop());
				speeds.push(speedlist.get(i).pop());
			}
		}		
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
