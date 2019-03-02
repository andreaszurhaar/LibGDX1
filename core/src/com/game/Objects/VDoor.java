package com.game.Objects;

import com.game.Readers.SpriteReader;

import java.io.IOException;

public class VDoor extends GameObject {
    public VDoor(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();

        this.width = 30;
        this.height = 100;
        try {
            this.texture = reader.getImage(106,450,12,30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
