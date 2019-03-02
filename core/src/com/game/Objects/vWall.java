package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class vWall extends GameObject {
    public vWall(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 20;
        this.height = 100;
        try {
            this.texture = reader.getImage(178,191,15,33);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
