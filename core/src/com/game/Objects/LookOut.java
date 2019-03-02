package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class LookOut extends GameObject {
    public LookOut(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 50;
        this.height = 50;
        bounds = new Rectangle(xPos,yPos,width,height);
        try {
            this.texture = reader.getImage(65,417,30,30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
