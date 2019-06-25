package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class hWall extends GameObject{
    public hWall(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 100;
        this.height = 20;
        bounds = new Rectangle(xPos,yPos,width,height);
        try {
            this.texture = reader.getImage(225,191,60,32);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
