package com.game.AI;

import com.game.Board.Agent;
import com.game.Board.Area;

import java.util.ArrayList;

public class IntruderBasicMovement extends AI {

    private float speed;

    public IntruderBasicMovement(){
        this.speed = 1.4f/60f;
    }

    @Override
    public float getRotation() {
        return 0;
    }

    @Override
    public float getSpeed() {
        return this.speed;
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

    @Override
    public void updatedSeenLocations() {

    }
}
