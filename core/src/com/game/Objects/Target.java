package com.game.Objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Target extends GameObject {


    public Target(int xPos, int yPos) {
        super(xPos, yPos);
        this.texture = new Texture("target.png");
        this.width = 120;
        this.height = 120;

    }
}
