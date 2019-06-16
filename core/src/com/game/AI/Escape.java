package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Guard;
import com.game.Board.Intruder;

import java.util.ArrayList;
import java.util.Stack;

public class Escape extends AI {

    private Intruder intruder;
    private Stack<Float> speeds = new Stack<Float>();
    private Stack<Float> rotations = new Stack<Float>();
    private float enemyx;
    private float enemyy;
    private AI previousAI;
    private int trackcounter;
    private ArrayList<Vector2> previousPos;
    private Instruction instruct = new Instruction();
    private final double MOVE_AWAY_FROM_GUARD_TIME = 5; //in seconds

    public Escape(Intruder intruder, Agent opponent, AI storeAI)
    {
        this.intruder = intruder;
        previousAI = storeAI;
        enemyx = opponent.xCenter;
        enemyy = opponent.yCenter;
        previousPos = new ArrayList<Vector2>();
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
        escapeFromGuard();
    }

    public void escapeFromGuard(){
        /**
         * After the intruder sees a guard, we first find the location of the guard with respect to the intruder,
         * from this we compute the angle of the guard with respect to the intruder
         * In order to escape the guard, the intruder wants to move with an angle opposite of this viewing angle
         */

        Vector2 differenceVector = new Vector2(intruder.xCenter - enemyx, intruder.yCenter - enemyy);
        double differenceAngleRadian = differenceVector.angleRad();

        Vector2 destPoint = new Vector2((float) (intruder.xCenter + MOVE_AWAY_FROM_GUARD_TIME * intruder.getSpeed() * Math.cos(differenceAngleRadian)), (float) (intruder.yCenter + MOVE_AWAY_FROM_GUARD_TIME * intruder.getSpeed() * Math.sin(differenceAngleRadian)));

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

    @Override
    public void updatedSeenLocations() {

    }
}


