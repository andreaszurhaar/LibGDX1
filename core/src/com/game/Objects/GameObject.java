package com.game.Objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game.AI.AI;

public abstract class GameObject extends AssetManager {

    public int xPos;
    public int yPos;
    public int width;
    public int height;
    public TextureRegion texture;
    public String name;
    public Rectangle bounds;
    public Body body;
    public AI ai;

    public GameObject(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setX(int xPos){
        this.xPos += xPos;
        bounds.setX(this.xPos);
    }
    public void setY(int yPos){
        this.yPos += yPos;
        bounds.setY(this.yPos);
    }
    public void setName(String string){this.name  =string;}





}
