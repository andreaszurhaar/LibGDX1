package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public class LookOut extends GameObject {
    public LookOut(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("LookOut.png");
        this.width = 150;
        this.height = 150;
    }
}
