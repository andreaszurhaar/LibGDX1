package com.game.AI;

import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Objects.GameObject;

import java.util.ArrayList;
import java.util.Stack;

public abstract class AI {

    public Stack rotation;
    public Stack speed;
    public Agent agent;

    public AI(){};

    public abstract float getRotation();

    public abstract float getSpeed();

    public abstract void setAgent(Agent agent);

    public abstract void setStructures(ArrayList<Object> Object);


    
}
