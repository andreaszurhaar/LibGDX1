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

public class GuardPatrolling extends AI {

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
    private CopsCenters copsCenters;
    private Vector2 bestCenterVector;
    private boolean reachedCenter;

//should get an area of patrolling with (either center + height + width, or a rectangle)
    public GuardPatrolling(float areaWidth, float areaHeight, Guard guard, CopsCenters copsCenters){
        setAgent(guard);
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        areaCenter = new Point2D.Float(0.5f*areaWidth, 0.5f*areaHeight);
        this.copsCenters = copsCenters;

        //TODO set this later
        ArrayList<Agent> guards = copsCenters.getGuards();
        int guardIndex = -1;
        for(int i = 0; i < guards.size(); i++){
            if(guard == guards.get(i)){
                guardIndex = i;
            }
        }
        Point2D.Float bestCenter = copsCenters.getCenters().get(guardIndex);
        bestCenterVector = new Vector2(bestCenter.x, bestCenter.y);

    }

    public GuardPatrolling(Guard guard)
    {
        setAgent(guard);
    }

    public void setArea(float areaWidth, float areaHeight)
    {
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        areaCenter = new Point2D.Float(0.5f*areaWidth, 0.5f*areaHeight);
    }

    public void patrolInArea()
    {
        areaPoints = new ArrayList<Point2D.Float>();

        //TODO remove structure points from area points

        //Assuming we know the areaborders of where we are patrolling
        for (int i = 0; i<areaWidth; i++)
        {
            for (int j=0; j<areaHeight;j++)
            {
                areaPoints.add(new Point2D.Float(i,j));
            }
        }
        for(int k = 0; k<structures.size(); k++) {
                int startX = (int) structures.get(k).getMinX();
                int startY = (int)structures.get(k).getMinY();
                int endX = (int) structures.get(k).getMaxX();
                int endY = (int) structures.get(k).getMaxY();

                for(int i = startX; i<endX; i++)
                {
                    for(int j=startY; j<endY; j++)
                    {
                        for (int z = 0; z<areaPoints.size(); z++)
                        {
                            if (i == areaPoints.get(z).x & j == areaPoints.get(z).y)
                            {
                              areaPoints.remove(z);
                            }
                        }
                    }
                }

        }
        Point2D.Float currentPoint = areaCenter;
        System.out.println("Area center is " + currentPoint.x + ", " +currentPoint.y);
        seenPoints = new ArrayList<Point2D.Float>();
    }

    public void patrol()
    {
        //TODO initialize guards positions
        //check which index current guard has, get center with same index
        //only do this if guard has not yet reached center for the first time

        if(!reachedCenter && (Math.sqrt(((guard.getX() - bestCenterVector.x) * (guard.getX() - bestCenterVector.x)) + ((guard.getY() - bestCenterVector.y) * (guard.getY() - bestCenterVector.y))) > ALLOWED_DISTANCE_ERROR)) {
            instruction.translate(bestCenterVector, guard);
            rotation = instruction.getRotations();
            speed = instruction.getSpeeds();
        }
        else{
            reachedCenter = true;
        }


        Point2D.Float currentPoint = new Point2D.Float(guard.xPos, guard.yPos);
        seenPoints.add(currentPoint);
        //System.out.println("Current point is " + currentPoint.x + ", " +currentPoint.y);
        addSeenPoints(seenPoints);
        //go to point that is close and not seen yet
        Point2D.Float temp = findClosestPoint(currentPoint);
        currentPoint.x = temp.x;
        currentPoint.y = temp.y;
        //System.out.println("Closest point is " + currentPoint.x + ", " +currentPoint.y);
        //change vision cone to the direction of the closest point
        Vector2 point = new Vector2(currentPoint.x, currentPoint.y);
        //TODO call extra class to update angle into stack
        instruction.translate(point, guard);
        rotation = instruction.getRotations();
        speed = instruction.getSpeeds();
        //walk to that point & do everything again
    }

    public void addSeenPoints(ArrayList<Point2D.Float> seenPoints)
    {
        for (Point2D.Float p : areaPoints) {
            if (Math.sqrt(p.x * p.x + p.y * p.y) < guard.viewRange) {
                Vector2 point = new Vector2(p.x - guard.viewAngle.x, p.y - guard.viewAngle.y);
                if (point.angle() < (0.5*guard.viewRadius)) {
                    seenPoints.add(p);
                }
            }
        }
        for (Point2D.Float p: seenPoints)
        {
            areaPoints.remove(p);
        }
    }

    public Point2D.Float findClosestPoint(Point2D.Float currentPoint)
    {
        Point2D.Float temp = new Point2D.Float(currentPoint.x, currentPoint.y);
        int i = 1;
        boolean foundPoint = false;
        while (!foundPoint) {
            for (Point2D.Float p : areaPoints) {
                //System.out.println("Possible area points: " + p.x + ", " + p.y);
                if (p.x == temp.x + i && p.y == temp.y) {
                    System.out.println("We take point: " + (temp.x+i) + ", " + temp.y);
                    foundPoint = true;
                    return p;
                }
                else if (p.x == temp.x && p.y == temp.y + i){
                    System.out.println("We take point: " + (temp.x) + ", " + (temp.y+i));
                    foundPoint = true;
                    return p;
                }
                else if (p.x == temp.x + i && p.y == temp.y + i ){
                    System.out.println("We take point: " + (temp.x+i) + ", " + (temp.y+i));
                    foundPoint = true;
                    return p;
                }
                else if (p.x == temp.x - i && p.y == temp.y){
                    System.out.println("We take point: " + (temp.x-i) + ", " + (temp.y));
                    foundPoint = true;
                    return p;
                }
                else if (p.x == temp.x && p.y == temp.y - i){
                    System.out.println("We take point: " + (temp.x) + ", " + (temp.y-i));
                    foundPoint = true;
                    return p;
                }
                else if (p.x == temp.x - i && p.y == temp.y - i)
                {
                    System.out.println("We take point: " + (temp.x-i) + ", " + (temp.y-i));
                    foundPoint = true;
                    return p;
                }
                else if (p.x == temp.x - i && p.y == temp.y + i)
                {
                    System.out.println("We take point: " + (temp.x-i) + ", " + (temp.y+i));
                    foundPoint = true;
                    return p;
                }
                else if(p.x == temp.x + i && p.y == temp.y - i) {
                    System.out.println("We take point: " + (temp.x+i) + ", " + (temp.y-i));
                    foundPoint = true;
                    return p;
                }
            }
            i++;
        }
        System.out.println("There are no unseen closest points, so we return back to the centre of the area");
        return areaCenter;

    }

    @Override
    public float getRotation() {
        if (rotation.empty()){
            patrol();
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
            patrol();
        }
        else
        {
           return speed.pop();
        }
        return speed.pop();    }

    @Override
    public void setAgent(Agent agent) {
        this.guard = (Guard) agent;
    }

    public void setStructures(ArrayList<Area> structures){
        this.structures = structures;
        patrolInArea();
    }

    public void initalize(){

    }


}
