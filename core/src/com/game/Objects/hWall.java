package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class hWall extends GameObject{
    public hWall(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("wall.png");
        this.width = 100;
        this.height = 20;

    }
}
