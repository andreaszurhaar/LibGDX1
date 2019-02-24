package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Hut extends GameObject {
    public Hut(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("hut.png");
        this.width = 100;
        this.height = 100;
    }
}
