package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Water extends GameObject {

    public Water(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("water.png");
        this.width = 50;
        this.height = 50;
    }
}
