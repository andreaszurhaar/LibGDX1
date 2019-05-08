package com.game.AI;

import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Objects.GameObject;

import java.util.ArrayList;
import java.util.Stack;

public abstract class AI {

    public Stack<Float> rotation;
    public Stack<Float> speed;
    public Agent agent;

    public AI(){};

    public abstract Stack getRotation();

    public abstract Stack getSpeed();

    public abstract void setAgent(Agent agent);

    public abstract void setStructures(ArrayList<Area> structures);


    
}
