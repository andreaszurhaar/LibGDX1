package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Guard;
import com.game.Board.Intruder;

import java.util.ArrayList;
import java.util.Stack;

public class MoveToTarget extends AI {

    private Intruder intruder;
    private float angle;
    public Vector2 showvect;
    private float enemyx;
    private float enemyy;
    private AI previousAI;
    private int trackcounter;
    private ArrayList previousPos;
    private boolean predictive = false;
    private Instruction instruction;
    private Vector2 targetLocation;

    public MoveToTarget(Intruder intruder, Vector2 targetLocation, AI storeAI) {
        this.intruder = intruder;
        this.targetLocation = targetLocation;
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        previousAI = storeAI;
        instruction = new Instruction();
        moveToTarget();
    }

    public void moveToTarget(){
        instruction.translate(targetLocation, intruder, false);
        rotation = instruction.getRotations();
        speed = instruction.getSpeeds();
    }

    @Override
    public float getRotation() {
        if (rotation.empty()){
            moveToTarget();
        }
        else
        {
            return rotation.pop();
        }
        return rotation.pop();
    }

    @Override
    public float getSpeed() {
        if (speed.empty()){
            previousAI.reset();
            intruder.setAI(previousAI);
            return intruder.ai.getSpeed();
        }
        else
        {
            return speed.pop();
        }
        //return speed.pop();
    }

    @Override
    public void setAgent(Agent agent) {

    }

    @Override
    public void setStructures(ArrayList<Area> structures) {

    }

    @Override
    public void setArea(float areaWidth, float areaHeight) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void seeArea(Area area) {

    }

    @Override
    public void seeAgent(Agent agent) {

    }

    @Override
    public void updatedSeenLocations() {

    }
}
