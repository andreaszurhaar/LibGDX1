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
    public Vector2 showvect;
    private float enemyx;
    private float enemyy;
    private AI previousAI;
    private int trackcounter;
    private ArrayList previousPos;
    private boolean predictive = false;

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
    	System.out.print("retracking");
    	Vector2 toEnemy = new Vector2(enemyx,enemyy);
    	previousPos.add(toEnemy);
    	Vector2 toTarget = computeInterception(previousPos);
        angle = toTarget.angle(guard.viewAngle);
        showvect = toTarget;
        System.out.println("angle of "+angle);
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
    
    public Vector2 computeInterception(ArrayList<Vector2> prevPos) {
    	System.out.println("try to trigger with positions: "+prevPos.size());
    	if(prevPos.size() < 15 || predictive == false) {
        	return new Vector2(enemyx-guard.xCenter,enemyy-guard.yCenter);
    	}
    	
    	Vector2 vec1 = new Vector2(prevPos.get(prevPos.size()-8).x-prevPos.get(prevPos.size()-15).x,prevPos.get(prevPos.size()-8).y-prevPos.get(prevPos.size()-15).y);
    	Vector2 vec2 = new Vector2(prevPos.get(prevPos.size()-1).x-prevPos.get(prevPos.size()-8).x,prevPos.get(prevPos.size()-1).y-prevPos.get(prevPos.size()-8).y);
    	float turn = vec2.angle(vec1);
    	float dist = vec2.len()/7;
    	
    	if(dist < 0.00001) {return new Vector2(enemyx-guard.xCenter,enemyy-guard.yCenter);}
    	
    	float enX = enemyx;
    	float enY = enemyy;
    	System.out.println("FROM THE START OF X: "+enemyx+"  Y: "+enemyy+"    AND WITH STEP OF DIST: "+dist+"  and TURNING AT: "+turn);
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
    }
    
    public float getRotation(){
        return (float) rotation/Board.fps;
    };

    public float getSpeed(){
    	trackcounter++;
    	System.out.println("got speed with counter: "+trackcounter);
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
    	System.out.println("saw agent with counter: "+trackcounter);
    	if(trackcounter > 3) {trackcounter = 3;}
    	if(agent instanceof Intruder) {
	    	enemyx = agent.xCenter;
	    	enemyy = agent.yCenter;
    	}
    }

}
