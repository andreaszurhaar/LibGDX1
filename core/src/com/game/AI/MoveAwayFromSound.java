package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Guard;
import com.game.Board.Intruder;

import java.util.ArrayList;
import java.util.Stack;

public class MoveAwayFromSound extends AI {

    private Intruder intruder;
    private float angle;
    private Stack<Float> speeds = new Stack<Float>();
    private Stack<Float> rotations = new Stack<Float>();
    public Vector2 showvect;
    private AI previousAI;
    private float directionAngle;
    private Instruction instruction;
    private final double MOVING_AWAY_FROM_SOUND_TIME = 7.14; //in seconds, should be at most 10/1.4 = 7.14 because a sound can be heard at most 10 meters away, and guards move at 1.4m/s

    public MoveAwayFromSound(Intruder intruder, float directionAngle, AI storeAI) {
        this.intruder = intruder;
        this.directionAngle = directionAngle;
        previousAI = storeAI;
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
        moveAwayFromSound();
    }

    public void moveAwayFromSound(){
        /**
         * We create a destination point for the instruction class based on: the max speed of the agent, the directionAngle and the amount of time we want to move away from the sound before going back to patrolling
         */
        //TODO find out why speed = 0
        double directionAngleRadian = Math.toRadians(directionAngle);
        double oppositeAngleRadian = directionAngleRadian + Math.PI;

        Vector2 destPoint = new Vector2((float) (intruder.xCenter + MOVING_AWAY_FROM_SOUND_TIME * intruder.getSpeed() * Math.cos(oppositeAngleRadian)), (float) (intruder.yCenter + MOVING_AWAY_FROM_SOUND_TIME * intruder.getSpeed() * Math.sin(oppositeAngleRadian)));

        instruction.translate(destPoint, intruder, false);
        rotation = instruction.getRotations();
        speed = instruction.getSpeeds();
    }

    @Override
    public float getRotation() {
        if (rotation.empty()){
            previousAI.reset();
            intruder.setAI(previousAI);
            return intruder.ai.getSpeed();
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
            intruder.setAI(previousAI);
            return intruder.ai.getSpeed();
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
