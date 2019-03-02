package com.game.Objects;

import com.badlogic.gdx.math.Rectangle;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class Web extends GameObject {
    public Web(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 20;
        this.height = 20;
        bounds = new Rectangle(xPos,yPos,width,height);
        try {
            this.texture = reader.getImage(32,292,26,28);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
