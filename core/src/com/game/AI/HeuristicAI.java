package com.game.AI;

import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.MapDivider;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class HeuristicAI extends AI {

    private Agent agent;
//    private float areaWidth,areaHeight;
    private final int FACTOR = 200; //number of squares that we want
    private ArrayList<Point2D.Float> explorationPoints;
    private String pattern;

    public HeuristicAI(Agent agent)
    {
        this.agent = agent;
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
        MapDivider mp = new MapDivider(FACTOR);
        explorationPoints = mp.getCentres();
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
