package com.game.Objects;

import com.badlogic.gdx.math.Rectangle;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class VDoor extends GameObject {
    public VDoor(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();

        this.width = 20;
        this.height = 30;
        bounds = new Rectangle(xPos,yPos,width,height);
        try {
            this.texture = reader.getImage(106,450,12,30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
