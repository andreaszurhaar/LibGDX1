package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Cop extends GameObject {
    public Cop(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("cop.png");
        this.width = 30;
        this.height = 30;
    }
}
