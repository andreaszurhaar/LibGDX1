package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class vWall extends GameObject {
    public vWall(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("wall.png");
        this.width = 20;
        this.height = 100;
    }
}
