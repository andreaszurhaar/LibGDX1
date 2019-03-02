package com.game.Objects;

import com.game.Readers.SpriteReader;

import java.io.IOException;

public class Steps extends GameObject {
    public Steps(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 50;
        this.height = 50;
        try {
            this.texture = reader.getImage(97,417,30,30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
