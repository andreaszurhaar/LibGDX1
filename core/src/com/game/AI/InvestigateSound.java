package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Guard;

import java.util.ArrayList;
import java.util.Stack;

public class InvestigateSound extends AI{

    private Guard guard;
    private float angle;
    private Stack<Float> speeds = new Stack<Float>();
    private Stack<Float> rotations = new Stack<Float>();
    public Vector2 showvect;
    private AI previousAI;
    private float directionAngle;
    private Instruction instruction;
    private final double INVESTIGATING_SOUND_TIME = 7.14; //should be at most 10/1.4 = 7.14 because a sound can be heard at most 10 meters away, and guards move at 1.4m/s

    public InvestigateSound(Guard guard, float directionAngle, AI storeAI) {
        this.guard = guard;
        this.directionAngle = directionAngle;
        previousAI = storeAI;
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
        moveTowardSound();
    }

    public void moveTowardSound(){
        /**
         * We create a destination point for the instruction class based on: the max speed of the agent, the directionAngle and the amount of time we want to move towards the sound before going back to patrolling
         */
        //TODO fix creation of destPoint, right now it seems like the agent hears the SoundOccurence it creates itself and that locations is being used to create destPoint
        Vector2 destPoint = new Vector2((float) (guard.xPos + INVESTIGATING_SOUND_TIME * guard.getSpeed() * Math.cos(Math.toRadians(directionAngle))), (float) (guard.yPos + INVESTIGATING_SOUND_TIME * guard.getSpeed() * Math.sin(Math.toRadians(directionAngle))));

        instruction.translate(destPoint, guard, false);
        rotation = instruction.getRotations();
        speed = instruction.getSpeeds();
    }

    @Override
    public float getRotation() {
        if (rotation.empty()){
            previousAI.reset();
            guard.setAI(previousAI);
            return guard.ai.getSpeed();
        }
        else
        {
            return rotation.pop();
        }
    }

    @Override
    public float getSpeed() {
        if (speed.empty()){
            previousAI.reset();
            guard.setAI(previousAI);
            return guard.ai.getSpeed();
        }
        else
        {
            return speed.pop();
        }
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
}
