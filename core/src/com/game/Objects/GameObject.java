package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public abstract class GameObject {

    public int xPos;
    public int yPos;
    public Texture texture;

    public GameObject(int xPos, int yPos, String texture){
        this.xPos = xPos;
        this.yPos = yPos;
        this.texture = new Texture(texture);
    }

    public void setX(int xPos){this.xPos = xPos;}
    public void setY(int yPos){this.yPos = yPos;}





}
