package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class Play extends GameObject {
    public Play(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 100;
        this.height = 100;
        try {
            this.texture = reader.getImage(100,295,20,20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
