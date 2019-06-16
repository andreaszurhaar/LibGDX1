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
    public boolean running = false;
    private boolean reachedCenter;

    public GuardPatrolling(){
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
    }

    public GuardPatrolling(Guard guard)
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
      //  System.out.println("areacenter of patrolling is: " + areaCenter.x +","+areaCenter.y);
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
      //  System.out.println("Area center is " + currentPoint.x + ", " +currentPoint.y);
        seenPoints = new ArrayList<Point2D.Float>();
    }

    public void patrol()
    {
    	//System.out.println("Started Patrolling");

        if((!reachedCenter && (Math.sqrt(((guard.getX() - guard.getCenterLocation().x) * (guard.getX() - guard.getCenterLocation().x)) + ((guard.getY() - guard.getCenterLocation().y) * (guard.getY() - guard.getCenterLocation().y))) > ALLOWED_DISTANCE_ERROR))
            && !guard.isCollided())   {

        	ChainInstruction chain = new ChainInstruction();
        	ArrayList<Vector2> chainar = new ArrayList<Vector2>();
            chainar.add(new Vector2(guard.getCenterLocation().x, guard.getCenterLocation().y));
        	//chainar.add(new Vector2(380,180));
        	//chainar.add(new Vector2(220,100));
        	//chainar.add(new Vector2(220,120));
        	//chainar.add(new Vector2(180,120));
        	chain.translate(chainar, guard);
            rotation = chain.getRotations();
            speed = chain.getSpeeds();

        	/*
            Vector2 centerVector = new Vector2(guard.getCenterLocation().x, guard.getCenterLocation().y);
            *//*
        	Vector2 centerVector = new Vector2(180,100);
            instruction.translate(centerVector, guard);
            rotation = instruction.getRotations();
            speed = instruction.getSpeeds();
       */
        }
        else {
            reachedCenter = true;

            Point2D.Float currentPoint = new Point2D.Float(guard.xPos, guard.yPos);
            seenPoints.add(currentPoint);
           // System.out.println("Current point is " + currentPoint.x + ", " + currentPoint.y);
            addSeenPoints(seenPoints);

            //go to point that is close and not seen yet
            Point2D.Float temp = findClosestPoint(currentPoint);
            Vector2 point = new Vector2(temp.x, temp.y);

           // System.out.println("going to point: " + temp.x + " " + temp.y + "   from point: " + guard.xCenter + " " + guard.yCenter);
            instruction.translate(point, guard, false);
            rotation = instruction.getRotations();
            speed = instruction.getSpeeds();
        }

        //System.out.println("Stack speed size " + speed.size());
//        for (float speed : speed){
//            System.out.println("Speed: " + speed);
//        }
        //System.out.println("Stack rotation size " + rotation.size());
//        for (float rotation : rotation){
//            System.out.println("Rotation: " + rotation);
//        }
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
          //  System.out.println("we remove point " + p + " from the areapoints");
            areaPoints.remove(p);
        }
    }

    public Point2D.Float findClosestPoint(Point2D.Float currentPoint)
    {
        Point2D.Float temp = new Point2D.Float((int)currentPoint.x, (int)currentPoint.y);
      //  System.out.println("Temp: " + temp.x + "," + temp.y);
        int i = 1;
        boolean foundPoint = false;
        while (!foundPoint) {
            //System.out.println("While-loop");
            for (Point2D.Float p : areaPoints) {
                //System.out.println("Possible area point: " + p.x + ", " + p.y);
                if (p.x == temp.x + i && p.y == temp.y) {
                   // System.out.println("We take point: " + (temp.x+i) + ", " + temp.y);
                    foundPoint = true;
                    areaPoints.remove(p);
                    return p;
                }
                else if (p.x == temp.x && p.y == temp.y + i){
                   // System.out.println("We take point: " + (temp.x) + ", " + (temp.y+i));
                    foundPoint = true;
                    areaPoints.remove(p);
                    return p;
                }
                else if (p.x == temp.x + i && p.y == temp.y + i ){
                   // System.out.println("We take point: " + (temp.x+i) + ", " + (temp.y+i));
                    foundPoint = true;
                    areaPoints.remove(p);
                    return p;
                }
                else if (p.x == temp.x - i && p.y == temp.y){
                  //  System.out.println("We take point: " + (temp.x-i) + ", " + (temp.y));
                    foundPoint = true;
                    areaPoints.remove(p);
                    return p;
                }
                else if (p.x == temp.x && p.y == temp.y - i){
                   // System.out.println("We take point: " + (temp.x) + ", " + (temp.y-i));
                    foundPoint = true;
                    areaPoints.remove(p);
                    return p;
                }
                else if (p.x == temp.x - i && p.y == temp.y - i)
                {
                  //  System.out.println("We take point: " + (temp.x-i) + ", " + (temp.y-i));
                    foundPoint = true;
                    areaPoints.remove(p);
                    return p;
                }
                else if (p.x == temp.x - i && p.y == temp.y + i)
                {
                   // System.out.println("We take point: " + (temp.x-i) + ", " + (temp.y+i));
                    foundPoint = true;
                    areaPoints.remove(p);
                    return p;
                }
                else if(p.x == temp.x + i && p.y == temp.y - i) {
                  //  System.out.println("We take point: " + (temp.x+i) + ", " + (temp.y-i));
                    foundPoint = true;
                    areaPoints.remove(p);
                    return p;
                }
            }
            i+=5;
        }
       // System.out.println("There are no unseen closest points, so we return back to the centre of the area");
        return areaCenter;

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
        return 0f;
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
        return 0f;    
    }

    @Override
    public void setAgent(Agent agent) {
        this.guard = (Guard) agent;
    }

    public void setStructures(ArrayList<Area> structures){
        this.structures = structures;
        System.out.println("we run patrolInArea and set the currentPoint to the area of the center");
        patrolInArea();
    }
    
    @Override
    public void reset() {
        speed = new Stack<Float>();
        rotation = new Stack<Float>();    
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
