package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Desert extends GameObject {
    public Desert(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("desert.png");
        this.width = 30;
        this.height = 30;
    }
}
