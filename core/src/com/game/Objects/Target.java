package com.game.Objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class Target extends GameObject {


    public Target(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 30;
        this.height = 30;
        bounds = new Rectangle(xPos,yPos,width,height);
        try {
            this.texture = reader.getImage(36,228,20,20);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
