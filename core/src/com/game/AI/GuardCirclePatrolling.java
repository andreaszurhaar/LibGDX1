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
    private int delta_x;
    private int delta_y;
    private final int NR_OF_CIRCLES = 5;
    private ArrayList<Vector2> destPoints;
    private int destIndex = 0;

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
        createDestPoints();

    }

    public void createDestPoints(){
        //find area's corner points TODO change so works for multiple guards
        delta_x = (int) (0.5 * areaWidth) / NR_OF_CIRCLES;
        delta_y = (int) (0.5 * areaHeight) / NR_OF_CIRCLES;

        destPoints = new ArrayList<Vector2>();

        for(int i = 1; i < NR_OF_CIRCLES; i++){
            destPoints.add(new Vector2(i * delta_x, i * delta_y));
            destPoints.add(new Vector2(i * delta_x, areaHeight - i * delta_y));
            destPoints.add(new Vector2(areaWidth - i * delta_x, areaHeight - i * delta_y));
            destPoints.add(new Vector2(areaWidth - i * delta_x, i * delta_y));
        }
    }

    public void patrol()
    {
        if(destIndex == destPoints.size()) destIndex = 0;
        instruction.translate(destPoints.get(destIndex), guard);
        destIndex++;
        rotation = instruction.getRotations();
        speed = instruction.getSpeeds();
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
    public void reset(){

    }

    public void seeArea(Area area){

    }

    public void seeAgent(Agent agent){

    }


}
