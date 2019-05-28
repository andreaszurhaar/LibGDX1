package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Guard;
import com.game.Board.Area;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;


public class Tracking extends AI {

    private Guard guard;
    private float rotation;
    private float speed;
    private float angle;

    public Tracking(Guard guard, Agent opponent)
    {
    	this.guard = guard;
        Vector2 toEnemy = new Vector2(opponent.xCenter-guard.xCenter,opponent.yCenter-guard.yCenter);
        angle = toEnemy.angle(guard.viewAngle);
        
        
        
    }

    public void trackIntruder()
    {
        
    }
    
    



    public float getRotation(){
        return -1;
    };

    public float getSpeed(){
        return -1;
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
    	
    }
}
