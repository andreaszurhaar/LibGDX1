package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Door extends GameObject {
    public Door(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("door.png");
        this.width = 30;
        this.height = 100;
    }
}
