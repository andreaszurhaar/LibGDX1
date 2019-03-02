package com.game.Objects;

import com.game.Readers.SpriteReader;

import java.io.IOException;

public class Candle extends GameObject {

    public Candle(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 20;
        this.height = 40;
        try {
            this.texture = reader.getImage(104,225,15,33);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
