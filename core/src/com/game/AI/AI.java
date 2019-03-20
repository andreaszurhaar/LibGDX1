package com.game.AI;

import com.game.Board.Agent;
import com.game.Objects.GameObject;

import java.util.ArrayList;

public abstract class AI {

    public AI(){};

    public abstract void move(ArrayList<Agent> agent);

	
}
