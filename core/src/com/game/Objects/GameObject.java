package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObject {

    public int xPos;
    public int yPos;
    public int width;
    public int height;
    public Texture texture;
    public String name;
    public Rectangle bounds;
    public Body body;

    public GameObject(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setX(int xPos){this.xPos = xPos;}
    public void setY(int yPos){this.yPos = yPos;}
    public void setName(String string){this.name  =string;}





}
