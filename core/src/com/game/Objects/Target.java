package com.game.Objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class Target extends GameObject {


    public Target(int xPos, int yPos) {
        super(xPos, yPos);
        SpriteReader reader = new SpriteReader();
        this.width = 100;
        this.height = 100;
        try {
            this.texture = reader.getImage(36,228,20,20);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
