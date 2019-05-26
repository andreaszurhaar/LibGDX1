package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Board;
import com.game.Board.Guard;
import com.game.Board.MapDivider;
import com.game.States.MainState;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.ConvolveOp;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Yvar Hulshof, Famke Nouwens
 */

public class GuardCirclePatrolling extends AI {

    private MapDivider mp;
    private Board board;
    private ArrayList<Agent> guards;
    private ArrayList<Area> areas;
    private final int ALLOWED_DISTANCE_ERROR = 10;
    public float areaWidth, areaHeight;
    private Point2D.Float areaCenter;
    public ArrayList<Point2D.Float> areaPoints, seenPoints;
    private Guard guard;
    private ArrayList<Area> structures;
    public boolean running = false;
    private boolean reachedCenter;
    private final int DELTA_X = 30;
    private final int DELTA_Y = 30;

    public GuardCirclePatrolling(){
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
    }

    public GuardCirclePatrolling(Guard guard)
    {
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
        setAgent(guard);
    }

    public void setArea(float areaWidth, float areaHeight)
    {
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        areaCenter = new Point2D.Float(0.5f*areaWidth, 0.5f*areaHeight);
        System.out.println("areacenter of patrolling is: " + areaCenter.x +","+areaCenter.y);
    }

    public void patrol()
    {
        System.out.println("area WIDTH: " + areaWidth + " area HEIGHT: " + areaHeight);
        //find area's corner points TODO change so works for multiple guards
//        Vector2 corner1 = new Vector2(DELTA_X, DELTA_Y);
//        Vector2 corner2 = new Vector2(DELTA_X, areaHeight-DELTA_Y);
//        Vector2 corner3 = new Vector2(areaWidth-DELTA_X, areaHeight-DELTA_Y);
//        Vector2 corner4 = new Vector2(areaWidth-DELTA_X, DELTA_Y);

        Vector2 corner1 = new Vector2(DELTA_Y, DELTA_X);
        Vector2 corner2 = new Vector2(areaHeight-DELTA_Y, DELTA_X);
        Vector2 corner3 = new Vector2(areaWidth-DELTA_Y, areaHeight-DELTA_X);
        Vector2 corner4 = new Vector2(DELTA_Y, areaWidth-DELTA_X);

        instruction.translate(corner4, guard);
        instruction.translate(corner3, guard);
        instruction.translate(corner2, guard);
        instruction.translate(corner1, guard);
        rotation = instruction.getRotations();
        speed = instruction.getSpeeds();
//        if(speed.empty()){
//            instruction.translate(corner2, guard);
//            rotation = instruction.getRotations();
//            speed = instruction.getSpeeds();
//        }
//
    }



    @Override
    public float getRotation() {
        if (rotation.empty()){
            if(!running) {
                patrol();
            } else {
                return 0f;
            }
        }
        else
        {
            //System.out.print("  and rotation: "+rotation.peek());
            return rotation.pop();
        }
        return rotation.pop();
    }

    @Override
    public float getSpeed() {
        //System.out.println("guard");
        if (speed.empty()){
            if(!running) {
                patrol();
            } else {
                return 0f;
            }
        }
        else
        {
            //System.out.println("  getting instruction to move with speed: "+speed.peek());
            return speed.pop();
        }
        return speed.pop();    }

    @Override
    public void setAgent(Agent agent) {
        this.guard = (Guard) agent;
    }

    public void setStructures(ArrayList<Area> structures){
        this.structures = structures;
        //System.out.println("we run patrolInArea and set the currentPoint to the area of the center");
        //patrolInArea();
    }



}
