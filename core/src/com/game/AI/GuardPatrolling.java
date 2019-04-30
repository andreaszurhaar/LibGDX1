package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Board;
import com.game.Board.Guard;
import com.game.Board.MapDivider;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GuardPatrolling {
    /**
     * @author Yvar Hulshof, Famke Nouwens
     */

    private MapDivider mp;

    private Board board;
    private ArrayList<Agent> guards;
    private final int ALLOWED_DISTANCE_ERROR = 10;
    public float areaWidth, areaHeight;
    private Point2D.Float areaCenter;
    public ArrayList<Point2D.Float> areaPoints;
    private Guard guard;

//should get an area of patrolling with (either center + height + width, or a rectangle)
    public GuardPatrolling(float areaWidth, float areaHeight,Guard guard){
        this.guard = guard;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        areaCenter = new Point2D.Float(0.5f*areaWidth, 0.5f*areaHeight);
        patrolInArea();
        //board.setUpdateAgentMoveToPoint(true);
        /*
        guards = new ArrayList<Agent>();
        guards = findGuards();
        int nrCops = guards.size();
        mp = new MapDivider(nrCops);

        */
    }

    /*

    */
    public void patrolInArea()
    {
        areaPoints = new ArrayList<Point2D.Float>();
        //Assuming we know the areaborders of where we are patrolling
        for (int i = 0; i<areaWidth; i++)
        {
            for (int j=0; j<areaHeight;j++)
            {
                areaPoints.add(new Point2D.Float(i,j));
            }
        }
        Point2D.Float currentPoint = areaCenter;

        //Point2D.Float currentPoint = (Point2D.Float) guard.getDestPoint();
        System.out.println("Area center is " + currentPoint.x + ", " +currentPoint.y);
        ArrayList<Point2D.Float> seenPoints = new ArrayList<Point2D.Float>();
        seenPoints.add(currentPoint);
        while (seenPoints.size() != (areaWidth*areaHeight)) {
            System.out.println("Current point is " + currentPoint.x + ", " +currentPoint.y);
            addSeenPoints(seenPoints);
            //go to point that is close and not seen yet
            Point2D.Float temp = findClosestPoint(currentPoint);
            currentPoint.x = temp.x;
            currentPoint.y  =temp.y;
            System.out.println("Closest point is " + currentPoint.x + ", " +currentPoint.y);
            //change vision cone to the direction of the closest point
            Vector2 point = new Vector2(currentPoint.x, currentPoint.y);
            guard.setAngle(point.angle());
            //walk to that point & do everything again
        }
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
                //TODO: find out why it gets stuck on the same points
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

//TODO: make object of guard patrolling affect only 1 guard at a time

}
