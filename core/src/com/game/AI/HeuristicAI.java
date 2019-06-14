package com.game.AI;

import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Board;
import com.game.Board.MapDivider;
import com.game.CopsAndRobbers;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class HeuristicAI extends AI {

    private Agent agent;
//    private float areaWidth,areaHeight;
    private final int FACTOR = 20; //number of squares that we want
    public final static int BOARD_WIDTH = 400;
    public final static int BOARD_HEIGHT = 200;
    private ArrayList<Point2D.Float> explorationPoints;
    private String pattern;

    public HeuristicAI(Agent agent)
    {
        this.agent = agent;
        exploration();
    }

    public HeuristicAI()
    {
        exploration();
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

    public void exploration()
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

        if (pattern.equals("snake")){

        }
        else if (pattern.equals("random")){
            randomMovement();
        }

    }

    private void randomMovement(){

    }

    @Override
    public float getRotation() {
        return 0;
    }

    @Override
    public float getSpeed() {
        return 0;
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

    }

    @Override
    public void seeAgent(Agent agent) {

    }

}
