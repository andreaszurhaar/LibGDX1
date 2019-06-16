package com.game.AI;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Board;
import com.game.Board.Intruder;
import com.game.Board.MapDivider;
import com.game.CopsAndRobbers;
import com.game.States.MapState;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.Stack;

public class HeuristicAI extends AI {

    private Agent agent;
    private Vector2 point;
    private float prevAngle, guardAngle;
    private Random rand = new Random();
    private ArrayList<Area> structures;
    //    private float areaWidth,areaHeight;
    private final int FACTOR = 20, AVERYBIGNUMBER = 500, Y_FACTOR = 20, X_FACTOR = 50, DEGREE_RANGE = 90 ;
    public final static int BOARD_WIDTH = 400;
    public final static int BOARD_HEIGHT = 200;
    private ArrayList<Vector2> explorationPoints;
    private String pattern;
    public static final float X_REDUC = MapState.X_REDUC;
    public static final float Y_REDUC = MapState.Y_REDUC;
    public boolean startingPos, guardSeen;
    public ArrayList<Area> seenStructures;

    public HeuristicAI(Agent agent)
    {
        this.agent = agent;
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
        seenStructures = new ArrayList<Area>();
        startingPos = false;
        structures = new ArrayList<Area>();
        explorationSetUp();
    }

    public HeuristicAI()
    {
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
        seenStructures = new ArrayList<Area>();
        startingPos = false;
        structures = new ArrayList<Area>();
        explorationSetUp();

    }

    public void explorationSetUp() {

        explorationPoints = new ArrayList<Vector2>();
        float tempX = (BOARD_WIDTH/MapState.X_REDUC) / FACTOR;
        float tempY = (500/MapState.Y_REDUC) / FACTOR;
        for (int i = 0; i < X_FACTOR; i++) {
            for (int j = 1; j < Y_FACTOR; j++) {
                explorationPoints.add(new Vector2(i * tempX + 0.5f * tempX, j * tempY + 0.5f * tempY));
            }
        }

        for(int i = 0; i < explorationPoints.size(); i++){
            System.out.print("x = " + explorationPoints.get(i).x + " " + " y = " + explorationPoints.get(i).y );
            System.out.println(" ");
        }
    }

    public void exploration() {
        if (pattern.equals("snake")) {
            point = snakeMovement();
        } else if (pattern.equals("random")) {
            point = randomMovement();
        }

        instruction.translate(point, agent, true);
        rotation = instruction.getRotations();
        speed = instruction.getSpeeds();
    }

    private Vector2 snakeMovement() {

        if(startingPos == false) {
            float startingX = agent.getX();
            float startingY = agent.getY();
            point = new Vector2(startingX, startingY);
            if (point.x > 200) {
                if (point.y > 100) {
                    point = new Vector2(380, 180);
                    startingPos = true;
                } else {
                    point = new Vector2(380, 30);
                    startingPos = true;
                }
            } else {
                if (point.y > 100) {
                    point = new Vector2(30, 180);
                    startingPos = true;
                } else {
                    point = new Vector2(30, 30);
                    startingPos = true;
                }
            }
        }
        else {
            /*for (int i = 0; i < explorationPoints.size(); i++) {
                Rectangle area = agent.area;
                area.x += 100;
                if (area.contains(explorationPoints.get(i))) {
                    System.out.println("Made it to contains");
                    point = new Vector2(explorationPoints.get(i));
                    break;
                }

                point = explorationPoints.get()
            }*/
            Random rand = new Random();
            point = explorationPoints.get(rand.nextInt(explorationPoints.size()));
            System.out.println("x - coordinate explore = " + point.x);
            System.out.println("y - coordinate explore = " + point.y);
        }

        return point;
    }

    private Vector2 randomMovement() {
        //creates range so the agent doesn't move in the direction it came from (currently at least 90 degrees in a different direction)
        float angle;
        if (guardSeen){
            //adjust upper and lower so that is directly in the opposite direction of the guard
            angle = guardAngle + 180;
        }
        else {
            int upper = (int) prevAngle + DEGREE_RANGE;
            int lower = (int) prevAngle - DEGREE_RANGE;
            angle = (float) rand.nextInt(upper - lower) + lower;

        }
        if (angle>360)
        {
            angle = angle - 360;
        }
        prevAngle = angle;
        //create a point outside the map according to the angle
        Vector2 vector =  new Vector2((float) (agent.xCenter + AVERYBIGNUMBER*Math.cos(Math.toRadians(angle))),(float) (agent.yCenter + AVERYBIGNUMBER*Math.sin(Math.toRadians(angle))));
        System.out.println("vector: " + vector.x + "," + vector.y);
        return vector;
    }

    public boolean checkCollision(){

        return false;
    }

    @Override
    public float getRotation() {
        if (rotation.empty()) {
            exploration();
        } else {
            //System.out.print("  and rotation: "+rotation.peek());
            return rotation.pop();
        }
        return 0f;
    }

    @Override
    public float getSpeed() {
        if (speed.empty()) {
            exploration();
        } else {
            //System.out.println("  getting instruction to move with speed: "+speed.peek());
            return speed.pop();
        }
        return 0f;
    }

    @Override
    public void setAgent(Agent agent) {
        this.agent = agent;
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
        System.out.println("we see a structure");
        boolean check = false;
        //checking to see if area is in seen structures, if not it is added to the array
        if(seenStructures.size() > 0) {
            for (int i = 0; i < seenStructures.size(); i++) {
                if (area == seenStructures.get(i)) {
                    check = true;
                }
            }
            if(!check){
                seenStructures.add(area);
            }
        }
        else{
            seenStructures.add(area);
        }
        if (!structures.contains(area)) {
            structures.add(area);
        }
        speed.clear();
        rotation.clear();
    }

    @Override
    public void seeAgent(Agent agent) {
        guardSeen = true;
        guardAngle = agent.angle;
    }

    @Override
    public void updatedSeenLocations() {

        System.out.println("before update = " + explorationPoints.size());

        for(int i = 0; i < explorationPoints.size(); i++){
            if(agent.area.contains(explorationPoints.get(i))){
                explorationPoints.remove(i);
            }
        }

        System.out.println("after update = " + explorationPoints.size());

    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}