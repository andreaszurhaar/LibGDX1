package com.game.AI;
/**
 * AI used by guard agents when actively pursuing an intruder agent
 */
import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Guard;
import com.game.Board.Intruder;
import com.game.Board.Area;
import com.game.Board.Board;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Lukas Padolevicius
 */

public class Tracking extends AI {

    private Guard guard;
    private float rotation;
    private float speed;
    private float angle;
    private Stack<Float> speeds = new Stack<Float>();
    private Stack<Float> rotations = new Stack<Float>();
    public Vector2 showvect;
    private float enemyx;
    private float enemyy;
    private AI previousAI;
    private int trackcounter;
    private ArrayList<Vector2> previousPos;
    public boolean predictive = true;
    private Instruction instruct = new Instruction();
    private int recalcInterval;
    public int intervalLimit = 60;
    public long timeToTrack = 0;

    public Tracking(Guard guard, Agent opponent,AI storeAI)
    {
    	timeToTrack = System.currentTimeMillis();
    	this.guard = guard;
    	previousAI = storeAI;
    	enemyx = opponent.xCenter;
    	enemyy = opponent.yCenter;
    	previousPos = new ArrayList<Vector2>();
        trackIntruder();
        trackcounter = 0;
        recalcInterval = 0;
    }

    public void trackIntruder()
    {
    	boolean following = false;
    	if(!speeds.isEmpty() && !rotations.isEmpty() && recalcInterval < intervalLimit) {
    		recalcInterval++;
    		speed = speeds.pop()*Board.fps;
    		rotation = rotations.pop()*Board.fps;
    		following = true;
    	}
    	Vector2 toEnemy = new Vector2(enemyx,enemyy);
    	previousPos.add(toEnemy);
    	if (!following) {
    		recalcInterval = 0;
	    	Vector2 toTarget = computeInterception(previousPos);
	        angle = toTarget.angle(guard.viewAngle);
	        showvect = toTarget;
	        if(angle >= 0 && angle < 45) {
	        	speed = 1.4f;
	        	rotation = -45;
	        } else if(angle < 0 && angle > -45) {
	        	speed = 1.4f;
	        	rotation = 45;
	        } else if(angle < 0) {
	        	speed = 0.2f;
	        	rotation = 180;
	        } else {
	        	speed = 0.2f;
	        	rotation = -180;
	        }
    	}
    }
    
    public Vector2 computeInterception(ArrayList<Vector2> prevPos) {
    	if(prevPos.size() < 31 || predictive == false) {
        	return new Vector2(enemyx-guard.xCenter,enemyy-guard.yCenter);
    	}
    	
    	Vector2 vec1 = new Vector2(prevPos.get(prevPos.size()-21).x-prevPos.get(prevPos.size()-31).x,prevPos.get(prevPos.size()-21).y-prevPos.get(prevPos.size()-31).y);
    	Vector2 vec2 = new Vector2(prevPos.get(prevPos.size()-11).x-prevPos.get(prevPos.size()-21).x,prevPos.get(prevPos.size()-11).y-prevPos.get(prevPos.size()-21).y);
    	Vector2 vec3 = new Vector2(prevPos.get(prevPos.size()-1).x-prevPos.get(prevPos.size()-11).x,prevPos.get(prevPos.size()-1).y-prevPos.get(prevPos.size()-11).y);
    	
    	float turn1 = vec2.angleRad(vec1);
    	float turn2 = vec3.angleRad(vec2);
    	float deltaTurn = turn2-turn1;
    	
    	float dist1 = vec2.len();
    	float dist2 = vec3.len();
    	float deltaDist = dist2-dist1;
    	
    	Vector2 currPoint = new Vector2(prevPos.get(prevPos.size()-1).x,prevPos.get(prevPos.size()-1).y);
    	float currAngle = vec3.angleRad();
    	float currTurn = turn2;
    	float currDist = dist2;
    	
    	int instCount = 0;
    	while (prevPos.get(prevPos.size()-1).dst(guard.xCenter,guard.yCenter) > ((float) (instCount*12) * (guard.maxSpeed/Board.fps)) && instCount < 1000) {
    		instCount++;
    		
    		//update angle and distance to travel
    		currTurn = currTurn+deltaTurn;
    		currDist = currDist+deltaDist;
    		currAngle = currAngle+currTurn;

    		float newx = currPoint.x+(float) Math.cos((double) currAngle)*currDist;
    		float newy = currPoint.y+(float) Math.sin((double) currAngle)*currDist;
    		
    		currPoint.set(newx,newy);
    	}
    	
    	instruct.translate(currPoint,guard, true);
    	speeds = instruct.getSpeeds();
    	rotations = instruct.getRotations();
   	return new Vector2(currPoint.x-guard.xCenter,currPoint.y-guard.yCenter);
    }
    
    public float getRotation(){
        return (float) rotation/Board.fps;
    };

    public float getSpeed(){
    	trackcounter++;
    	if(trackcounter > 120) {
    		previousAI.reset();
    		guard.setAI(previousAI);
    		return 0;
    	}
    	trackIntruder();
        return (float) speed/Board.fps;
    };

    public void setAgent(Agent agent){

    };

    public void setStructures(ArrayList<Area> structures){

    };

    public void setArea(float width, float height){

    }
    
    @Override
    public void reset() {
        speed = 0;
        rotation = 0;    
    }

    
    @Override
    public void seeArea(Area area) {
    	
    }

    @Override
    public void seeAgent(Agent agent) {
    	if(agent instanceof Intruder) {
        	trackcounter = 3;
	    	enemyx = agent.xCenter;
	    	enemyy = agent.yCenter;
    	}
    }

	@Override
	public void updatedSeenLocations() {

	}

}
