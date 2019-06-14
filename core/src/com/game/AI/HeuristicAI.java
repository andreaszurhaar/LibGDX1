package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Board;
import com.game.Board.MapDivider;
import com.game.CopsAndRobbers;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class HeuristicAI extends AI {

    private Agent agent;
    private Vector2 point;
    private Random rand = new Random();
//    private float areaWidth,areaHeight;
    private final int FACTOR = 20, AVERYBIGNUMBER = 500; //number of squares that we want
    public final static int BOARD_WIDTH = 400;
    public final static int BOARD_HEIGHT = 200;
    private ArrayList<Point2D.Float> explorationPoints;

    private String pattern = "random";

    public HeuristicAI()
    {
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
        explorationSetUp();
    }
/*

    public HeuristicAI(Agent agent, float areaWidth, float areaHeight)
    {
        this.agent = agent;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        exploration();
    }
*/

    public void explorationSetUp()
    {
        explorationPoints = new ArrayList<Point2D.Float>();
        float tempX = BOARD_WIDTH /FACTOR;
        float tempY = BOARD_HEIGHT/FACTOR;
        for (int i = 0; i<FACTOR;i++){
            for (int j = 0; j<FACTOR; j++)
            {
                explorationPoints.add(new Point2D.Float(i*tempX+0.5f*tempX, j*tempY+0.5f*tempY));
            }
        }
    }

    public void exploration(){
        if (pattern.equals("snake")){
            System.out.println("pattern is snake");
            point = snakeMovement();
        }
        else if (pattern.equals("random")){
            System.out.println("pattern is random");
            point = randomMovement();
        }
        else{

        }
        instruction.translate(point, agent, true);
        rotation = instruction.getRotations();
        speed = instruction.getSpeeds();
    }

    private Vector2 snakeMovement(){
        return null;
    }

    private Vector2 randomMovement(){
        //find the angle which we can turn to
        float angle = rand.nextInt(360);
        //create a point outside the map according to the angle
        Vector2 vector =  new Vector2((float) (agent.xCenter + AVERYBIGNUMBER*Math.cos(Math.toRadians(angle))),(float) (agent.yCenter + AVERYBIGNUMBER*Math.sin(Math.toRadians(angle))));
        System.out.println("vector: " + vector.x + "," + vector.y);
        return vector;
    }

    @Override
    public float getRotation() {
        if (rotation.empty()){
            exploration();
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
        if (speed.empty()){
            exploration();
        }
        else
        {
            //System.out.print("  and rotation: "+rotation.peek());
            return rotation.pop();
        }
        return 0f;
    }

    @Override
    public void setAgent(Agent agent) {
        this.agent = agent;
        exploration();
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
