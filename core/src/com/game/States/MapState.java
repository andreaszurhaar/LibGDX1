package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.CopsAndRobbers;
import com.game.Objects.GameObject;
import com.game.Objects.Target;
import com.game.Objects.Wall;
import com.game.Objects.Water;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MapState extends State {


    public ArrayList<GameObject> gameObjects;
    public Texture background;
    public Texture cop;
    public Texture target;
    public Texture wall;
    public String name;


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
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            //why ?
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (Exception e) {
                System.out.println("Error");
            }
            if (Gdx.input.getY() <= 100) {
                int x = (int) Math.floor(Gdx.input.getX() / 100);
                int y = (int) Math.floor((CopsAndRobbers.HEIGHT - Gdx.input.getY()) / 100);

                int convX = x * 100 + 35;
                int convY = y * 100 + 35;

                name = "target.png";


            }

            if(name == "target.png"){
                int x = (int) Math.floor(Gdx.input.getX());
                int y = (int) Math.floor((CopsAndRobbers.HEIGHT - Gdx.input.getY()));

                gameObjects.add(new Target(x,y,name));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
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
