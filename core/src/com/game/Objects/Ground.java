package com.game.Objects;

import com.badlogic.gdx.math.Rectangle;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class Ground extends GameObject {
    public Ground(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 30;
        this.height = 20;
        bounds = new Rectangle(xPos,yPos,width,height);
        try {
            this.texture = reader.getImage(58,292,26,28);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
