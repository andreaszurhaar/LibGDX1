package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.AI.Astar.AStarNew;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Guard;
import com.game.Board.Intruder;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Stack;

public class GuardPreventCollision extends AI{

    private Guard guard;
    private Stack<Float> speeds = new Stack<Float>();
    private Stack<Float> rotations = new Stack<Float>();
    private float enemyx;
    private float enemyy;
    private AI previousAI;
    private int trackcounter;
    private ArrayList<Vector2> previousPos;
    private Instruction instruct = new Instruction();
    private ArrayList<Rectangle2D.Float> rectangles;
    private final double MOVE_AWAY_FROM_GUARD_TIME = 1; //in seconds

    public GuardPreventCollision(Guard guard, Agent opponent, AI storeAI, ArrayList<Area> seenStructures)
    {
        this.guard = guard;
        previousAI = storeAI;
        enemyx = opponent.xCenter;
        enemyy = opponent.yCenter;
        previousPos = new ArrayList<Vector2>();
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
        rectangles = new ArrayList<Rectangle2D.Float>();
        this.seenStructures = seenStructures;
        moveAway();
    }

    public void moveAway(){

        Vector2 differenceVector = new Vector2(guard.xCenter - enemyx, guard.yCenter - enemyy);
        double differenceAngleRadian = differenceVector.angleRad();

        Vector2 destPoint = new Vector2((float) (guard.xCenter + MOVE_AWAY_FROM_GUARD_TIME * guard.getSpeed() * Math.cos(differenceAngleRadian)), (float) (guard.yCenter + MOVE_AWAY_FROM_GUARD_TIME * guard.getSpeed() * Math.sin(differenceAngleRadian)));

        //intruder.triggerSprint();

        if(previousAI instanceof HeuristicAI) {
            for (Area a : seenStructures)
            {
                rectangles.add(new Rectangle2D.Float(a.xPos,a.yPos,a.getMaxX()-a.xPos, a.getMaxY()-a.yPos));
            }
            AStarNew astar = new AStarNew(rectangles,guard.xCenter, guard.yCenter, destPoint.x, destPoint.y, guard);
            rotation = astar.getRotationStack();
            speed = astar.getSpeedStack();

        }
        else {
            instruction.translate(destPoint, guard, false);
            rotation = instruction.getRotations();
            speed = instruction.getSpeeds();
        }
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

    @Override
    public void updatedSeenLocations() {

    }
}



