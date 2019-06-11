package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Objects.GameObject;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;

public abstract class AI {

    public Stack<Float> rotation;
    public Stack<Float> speed;
    public Agent agent;
    public Instruction instruction;


    public AI(){};

    public abstract float getRotation();

    public abstract float getSpeed();

    public abstract void setAgent(Agent agent);

    public abstract void setStructures(ArrayList<Area> structures);

    public abstract void setArea(float areaWidth, float areaHeight);

    public abstract void reset();
    
    public abstract void seeArea(Area area);

    public abstract void seeAgent(Agent agent);

    public void setCornerPoints(ArrayList<Point2D.Float> cornerPoints){

    }

    public abstract void moveToPoint(Vector2 point);

}
