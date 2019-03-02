package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.CopsAndRobbers;
import com.game.Objects.Cop;
import com.game.Objects.Door;
import com.game.Objects.GameObject;
import com.game.Objects.LookOut;
import com.game.Objects.Robber;
import com.game.Objects.Target;
import com.game.Objects.hWall;
import com.game.Objects.vWall;
import com.game.Readers.SpriteReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainState extends State {

    public ArrayList<GameObject> gameObjects;
    public Texture background;
    public Texture wall;
    public Texture play;
    public String name;
    public BitmapFont font;
    public TextureRegion tR;
    public SpriteReader reader;

    public MainState(GameStateManager gsm, ArrayList<GameObject> gameObjects) {
        super(gsm);
        this.gameObjects = gameObjects;
        font = new BitmapFont();
        font.setColor(Color.BLACK);
      //  background = new Texture("desert.png");
        wall = new Texture("wall.png");
        play = new Texture("play.png");
        reader = new SpriteReader();
        try {
            tR = reader.getImage(100,100,25,25);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void handleInput() {


        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Exception e) {
                System.out.println("Error");
            }
            // sb.draw(play,850,535, 120,120);
            if (Gdx.input.getX() > 850 && Gdx.input.getY() < 535) {
                gsm.pop();
            }
            int x = (int) Math.floor(Gdx.input.getX());
            int y = (int) Math.floor((CopsAndRobbers.HEIGHT - Gdx.input.getY()));

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
       // sb.draw(tR, 0, 0, CopsAndRobbers.WIDTH, CopsAndRobbers.HEIGHT);
        sb.draw(play, 850, 535, 120, 120);
        sb.draw(wall, 0, 520, 1000, 20);
        sb.draw(wall, 820, 520, 20, 180);


        for (int i = 0; i < gameObjects.size(); i++) {
            sb.draw(gameObjects.get(i).texture, gameObjects.get(i).xPos, gameObjects.get(i).yPos, gameObjects.get(i).width, gameObjects.get(i).height);
        }

        sb.end();

    }

    public void dispose() {
        font.dispose();
    }
}