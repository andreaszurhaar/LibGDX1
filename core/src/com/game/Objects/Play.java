package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Play extends GameObject {
    public Play(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("play.png");
        this.width = 100;
        this.height = 100;
    }
}
