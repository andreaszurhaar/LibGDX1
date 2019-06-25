package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class Door extends GameObject {
    public Door(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 30;
        this.height = 20;
        bounds = new Rectangle(xPos,yPos,width,height);
        try {
            this.texture = reader.getImage(32,417,30,30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
