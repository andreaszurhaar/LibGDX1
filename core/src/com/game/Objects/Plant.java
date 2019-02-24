package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Plant extends GameObject {
    public Plant(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("plant.png");
        this.width = 100;
        this.height = 100;
    }
}
