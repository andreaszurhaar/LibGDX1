package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class LookOut extends GameObject {
    public LookOut(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 150;
        this.height = 150;
        try {
            this.texture = reader.getImage(65,417,30,30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
