package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;

public abstract class GameObject {

    public int xPos;
    public int yPos;
    public int width;
    public int height;
    public Texture texture;
    public String name;

    public GameObject(int xPos, int yPos, String texture,int width,int height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        name = texture;
        this.texture = new Texture(texture);
    }

    public void setX(int xPos){this.xPos = xPos;}
    public void setY(int yPos){this.yPos = yPos;}





}
