package com.game.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.CopsAndRobbers;
import com.game.Objects.GameObject;
import com.game.Objects.Wall;
import com.game.Objects.Water;

import java.util.ArrayList;

public class MapState extends State {


    public ArrayList<GameObject> gameObjects;
    public Texture background;
    public Texture cop;
    public Texture target;
    public Texture wall;


    public MapState(GameStateManager gsm){
        super(gsm);
        gameObjects = new ArrayList<GameObject>();
        background = new Texture("desert.png");
        cop = new Texture("cop.png");
        target = new Texture("lookout.png");
        wall = new Texture("wall.png");
        gameObjects.add(new Wall(80,570,"wall.png"));
        gameObjects.add(new Water(150,570,"water.png"));


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

        for(int i =0; i < gameObjects.size(); i++ ){
            sb.draw(gameObjects.get(i).texture, gameObjects.get(i).xPos,gameObjects.get(i).yPos,50,50);
        }


        sb.end();

    }
}
