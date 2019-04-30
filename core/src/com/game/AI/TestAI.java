package com.game.AI;

import com.game.Board.Agent;
import com.game.Objects.Cop;
import com.game.Objects.GameObject;

import java.util.ArrayList;

public class TestAI extends AI {

    public TestAI(){

    }
    @Override
    public void move(ArrayList<Agent> agents) {
        for(int i = 0; i < agents.size(); i++) {
            if (agents.get(i).xPos > 1000) {
                agents.get(i).xPos = 0;
            }

            agents.get(i).setX(1);
        }

    }

}