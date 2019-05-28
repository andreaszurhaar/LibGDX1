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

    public Tracking(Guard guard, Agent opponent,AI storeAI)
    {
    	this.guard = guard;
    	previousAI = storeAI;
    	enemyx = opponent.xCenter;
    	enemyy = opponent.yCenter;
        trackIntruder();
        trackcounter = 0;
    }

    public void trackIntruder()
    {
    	System.out.print("retracking");
    	Vector2 toEnemy = new Vector2(enemyx-guard.xCenter,enemyy-guard.yCenter);
        angle = toEnemy.angle(guard.viewAngle);
        showvect = toEnemy;
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
    
    



    public float getRotation(){
        return (float) rotation/Board.fps;
    };

    public float getSpeed(){
    	trackcounter++;
    	System.out.println("got speed with counter: "+trackcounter);
    	if(trackcounter > 10) {
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
