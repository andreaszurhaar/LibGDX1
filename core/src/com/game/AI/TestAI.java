package com.game.AI;

import com.game.Objects.GameObject;

public class TestAI extends AI {

    public TestAI(){

    }
    @Override
    public void move(GameObject object) {

        if(object.xPos > 1000){
            object.xPos = 0;
        }

        object.setX(1);

    }
}
