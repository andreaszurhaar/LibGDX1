package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Robber extends GameObject {
    public Robber(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("robber.png");
        this.width = 30;
        this.height = 30;
    }
}
