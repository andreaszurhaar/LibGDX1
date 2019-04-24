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
     * @author Yvar Hulshof
     *
     */

    private MapDivider mp;
    private ArrayList<Point2D> centres;
    private Board board;
    private ArrayList<Agent> guards;
    private final int ALLOWED_DISTANCE_ERROR = 10;

    public GuardPatrolling(Board board){
        this.board = board;
        //board.setUpdateAgentMoveToPoint(true);
        guards = new ArrayList<Agent>();
        guards = findGuards();
        int nrCops = guards.size();
        mp = new MapDivider(nrCops);
        centres = mp.getCentres();
        if(nrCops > 0) initializeGuardLocations();
    }

    public void initializeGuardLocations(){
        for(int i = 0; i < guards.size(); i++){
            board.setUpdateAgentMoveToPoint(true);
            Point2D currentCentre = centres.get(i);
            Agent currentGuard = guards.get(i);
            currentGuard.setDestPoint(currentCentre);
            //Vector2 guardRequiredVector = new Vector2((float)100 - currentGuard.getX(), (float) 100 - currentGuard.getY());
            Vector2 guardRequiredVector = new Vector2((float)currentCentre.getX() - currentGuard.getX(), (float) currentCentre.getY() - currentGuard.getY());
            currentGuard.setAngle(guardRequiredVector.angle());
            System.out.println("centreX: " + currentCentre.getX() + " guardX: " + currentGuard.getX() + " centreY: " + currentCentre.getY() + " guardY: " + currentGuard.getY());
            System.out.println("guard: " + i + " angle: " + guardRequiredVector.angle());
            float guardX = currentGuard.getX();
            float guardY = currentGuard.getY();
            float centreX = (float) currentCentre.getX();
            float centreY = (float) currentCentre.getY();

           // if(Math.sqrt(((guardX - centreX) * (guardX - centreX)) + ((guardY - centreY) * (guardY - centreY))) > ALLOWED_DISTANCE_ERROR)
           //      currentGuard.rotation = (float) -Math.toRadians(Math.random()*currentGuard.turningCircle/2);
        }
    }
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
        //Point2D.Float currentPoint = areaCenter;

        Point2D.Float currentPoint = (Point2D.Float) super.getDestPoint();
        System.out.println("Area center is " + currentPoint.x + ", " +currentPoint.y);
        ArrayList<Point2D.Float> seenPoints = new ArrayList<Point2D.Float>();
        seenPoints.add(currentPoint);
        while (seenPoints.size() != (areaWidth*areaHeight)) {
            System.out.println("Current point is " + currentPoint.x + ", " +currentPoint.y);
            addSeenPoints(seenPoints);
            //go to point that is close and not seen yet
            currentPoint = findClosestPoint(currentPoint);
            //change vision cone to the direction of the closest point
            Vector2 point = new Vector2(currentPoint.x, currentPoint.y);
            super.setAngle(point.angle());
            //walk to that point & do everything again
        }
    }

    public ArrayList<Agent> findGuards() {
        ArrayList<Agent> agents = board.getAgents();
        for (Agent a : agents) {
            if (a instanceof Guard)
                guards.add(a);
        }
        return guards;
    }

    public void addSeenPoints(ArrayList<Point2D.Float> seenPoints)
    {
        for (Point2D.Float p : areaPoints) {
            if (Math.sqrt(p.x * p.x + p.y * p.y) < viewRange) {
                Vector2 point = new Vector2(p.x - viewAngle.x, p.y - viewAngle.y);
                if (point.angle() < (0.5*viewRadius)) {
                    seenPoints.add(p);
                    areaPoints.remove(p);
                }
            }
        }
    }

    public Point2D.Float findClosestPoint(Point2D.Float currentPoint)
    {
        System.out.println("Current point is " + currentPoint.x + ", " +currentPoint.y);
        Point2D.Float temp = new Point2D.Float(currentPoint.x, currentPoint.y);
        int i = 1;
        boolean foundPoint = false;
        while (!foundPoint) {
            for (Point2D.Float p : areaPoints) {
                if (p.x == temp.x + i && p.y == temp.y
                        || p.x == temp.x && p.y == temp.y + i
                        || p.x == temp.x + i && p.y == temp.y + i
                        || p.x == temp.x - i && p.y == temp.y
                        || p.x == temp.x && p.y == temp.y - i
                        || p.x == temp.x - i && p.y == temp.y - i
                        || p.x == temp.x - i && p.y == temp.y + i
                        || p.x == temp.x + i && p.y == temp.y - i) {
                    foundPoint = true;
                    return p;
                }
            }
            i++;
        }
        System.out.println("There are no unseen closest points, so we return back to the centre of the area");
        return areaCenter;

    }





}
