package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Area;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;

public class Tracking extends AI {

    private Point2D.Float opponentPosition;
    private float angle;

    public Tracking(Agent opponent)
    {
        this.opponentPosition = opponentPosition;
        Vector2 point = new Vector2(opponentPosition.x, opponentPosition.y);
        //setAngle(point.angle());
        angle = trackIntruder();
        this.instruction = new Instruction();
    }

    public float trackIntruder()
    {

        //call a-star for fastest route to intruder?
        //OR
        //just keep updating the angle and speed to the general direction of the intruder

        return angle;
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
        speed = new Stack<Float>();
        rotation = new Stack<Float>();    
    }

    
    @Override
    public void seeArea(Area area) {
    	
    }

    @Override
    public void seeAgent(Agent agent) {
   
    }
}
