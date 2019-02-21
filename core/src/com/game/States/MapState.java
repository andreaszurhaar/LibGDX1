package com.game.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.CopsAndRobbers;

import java.util.ArrayList;

public class MapState extends State {


    public ArrayList<Object> gameObjects;
    public Texture background;
    public Texture cop;
    public Texture target;
    public Texture wall;


    public MapState(GameStateManager gsm){
        super(gsm);
        background = new Texture("desert.png");
        cop = new Texture("cop.png");
        target = new Texture("lookout.png");
        wall = new Texture("wall.png");

    }
    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0, CopsAndRobbers.WIDTH,CopsAndRobbers.HEIGHT);
        sb.draw(cop,10,570, 50,50);
        sb.draw(target,60,270, 100,100);


        sb.end();

    }
}
