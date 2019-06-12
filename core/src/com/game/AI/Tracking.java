package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Guard;
import com.game.Board.Intruder;
import com.game.Board.Area;
import com.game.Board.Board;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;


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
    private boolean predictive = false;
    private Instruction instruct = new Instruction();

    public Tracking(Guard guard, Agent opponent,AI storeAI)
    {
    	this.guard = guard;
    	previousAI = storeAI;
    	enemyx = opponent.xCenter;
    	enemyy = opponent.yCenter;
    	previousPos = new ArrayList<Vector2>();
        trackIntruder();
        trackcounter = 0;
    }

    public void trackIntruder()
    {
    	boolean following = false;
    	if(!speeds.isEmpty() && !rotations.isEmpty()) {
    		//System.out.println("popping instrcutions");
    		speed = speeds.pop()*Board.fps;
    		rotation = rotations.pop()*Board.fps;
    		following = true;
    	}
    	//System.out.print("retracking with enemy at: "+enemyx+"   "+enemyy);
    	Vector2 toEnemy = new Vector2(enemyx,enemyy);
    	if(!previousPos.isEmpty() && previousPos.get(previousPos.size()-1).dst(toEnemy) < 0.001) {
    		return;
    	}
    	previousPos.add(toEnemy);
    	if (!following) {
	    	Vector2 toTarget = computeInterception(previousPos);
	        angle = toTarget.angle(guard.viewAngle);
	        showvect = toTarget;
	        //System.out.println("angle of "+angle);
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
    	//System.out.println("try to trigger with positions: "+prevPos.size());
    	if(prevPos.size() < 31 || predictive == false) {
        	return new Vector2(enemyx-guard.xCenter,enemyy-guard.yCenter);
    	}
    	
    	Vector2 vec1 = new Vector2(prevPos.get(prevPos.size()-21).x-prevPos.get(prevPos.size()-31).x,prevPos.get(prevPos.size()-21).y-prevPos.get(prevPos.size()-31).y);
    	Vector2 vec2 = new Vector2(prevPos.get(prevPos.size()-11).x-prevPos.get(prevPos.size()-21).x,prevPos.get(prevPos.size()-11).y-prevPos.get(prevPos.size()-21).y);
    	Vector2 vec3 = new Vector2(prevPos.get(prevPos.size()-1).x-prevPos.get(prevPos.size()-11).x,prevPos.get(prevPos.size()-1).y-prevPos.get(prevPos.size()-11).y);
    	
    	float turn1 = vec2.angle(vec1);
    	float turn2 = vec3.angle(vec2);
    	float deltaTurn = turn2-turn1;
    	
    	float dist1 = vec2.len();
    	float dist2 = vec3.len();
    	float deltaDist = dist2-dist1;
    	
    	Vector2 currPoint = new Vector2(prevPos.get(prevPos.size()-1).x,prevPos.get(prevPos.size()-1).y);
    	float currAngle = vec3.angle();
    	float currTurn = turn2;
    	float currDist = dist2;
    	//System.out.println("going from point: "+currPoint.x+"  "+currPoint.y);
    	
    	int instCount = 0;
    	while (prevPos.get(prevPos.size()-1).dst(guard.xCenter,guard.yCenter) > ((float) (instCount*20) * (guard.maxSpeed/Board.fps)) && instCount < 1000) {
    		//System.out.println("cycle for dist: "+currPoint.dst(guard.xCenter,guard.yCenter)+"   and countmutip: "+((float) (instCount*10) * (guard.maxSpeed/Board.fps)));
    		instCount++;
    		
    		//update angle and distance to travel
    		currTurn = currTurn+deltaTurn;
    		currDist = currDist+deltaDist;
    		currAngle = currAngle+currTurn;
    		
    		float newx = currPoint.x+(float) Math.cos((double) currAngle)*currDist;
    		float newy = currPoint.y+(float) Math.sin((double) currAngle)*currDist;
    		
    		currPoint.set(newx,newy);
    	}
    	
    	//System.out.println("going to point: "+currPoint.x+"  "+currPoint.y+"   by count of: "+instCount);
    	//System.exit(0);
    	instruct.translate(currPoint,guard);
    	speeds = instruct.getSpeeds();
    	rotations = instruct.getRotations();
    	for(int i=0; i<prevPos.size() ;i++) {
    		//System.out.println("the positions of i: "+i+"  and xy: "+prevPos.get(i).x+"  "+prevPos.get(i).y);
    	}
    	//System.exit(0);
    	/*
    	if(dist2 < 0.00001) {return new Vector2(enemyx-guard.xCenter,enemyy-guard.yCenter);}
    	
    	float enX = enemyx;
    	float enY = enemyy;
    	System.out.println("FROM THE START OF X: "+enemyx+"  Y: "+enemyy+"    AND WITH STEP OF DIST: "+dist1+"  and TURNING AT: "+turn1);
    	Vector2 examinedVec = new Vector2(enX-guard.xCenter,enY-guard.yCenter);
    	float cangle = examinedVec.angle();
    	
    	int countPoints = 0;
    	System.out.println("try to reach count: "+new Vector2(enX-guard.xCenter,enY-guard.yCenter).len()/(1.4f/((float) Board.fps)));
    	System.out.println("for a distance of: "+examinedVec.len());
    	while(new Vector2(enX-guard.xCenter,enY-guard.yCenter).len()/(1.4f/((float) Board.fps)) > (float) countPoints*7) {
    		countPoints++;
			enX = enX + (float) Math.cos(cangle)*dist;//(float) Board.fps;
			enY = enY + (float) Math.sin(cangle)*dist;//(float) Board.fps;
			cangle = cangle + (turn/(float) Board.fps);
    	}
    	System.out.println("TO THE END OF X: "+enX+"  Y: "+enY+"   witha count of: "+countPoints);

    	return new Vector2(enX-guard.xCenter,enY-guard.yCenter);
    	*/
    	return new Vector2(currPoint.x-guard.xCenter,currPoint.y-guard.yCenter);
    }
    
    public float getRotation(){
        return (float) rotation/Board.fps;
    };

    public float getSpeed(){
    	trackcounter++;
    	//System.out.println("got speed with counter: "+trackcounter);
    	if(trackcounter > 60) {
    		previousAI.reset();
    		guard.setAI(previousAI);
    		return guard.ai.getSpeed();
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
    	//System.out.println("saw agent with counter: "+trackcounter);
    	trackcounter = 3;
    	if(agent instanceof Intruder) {
	    	enemyx = agent.xCenter;
	    	enemyy = agent.yCenter;
    	}
    }

}
