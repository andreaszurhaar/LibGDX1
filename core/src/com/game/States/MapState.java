package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.CopsAndRobbers;
import com.game.Objects.Cop;
import com.game.Objects.Door;
import com.game.Objects.GameObject;
import com.game.Objects.Hut;
import com.game.Objects.LookOut;
import com.game.Objects.Plant;
import com.game.Objects.Play;
import com.game.Objects.Robber;
import com.game.Objects.Target;
import com.game.Objects.Water;
import com.game.Objects.hWall;
import com.game.Objects.vWall;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MapState extends State {


    public ArrayList<GameObject> gameObjects;
    public ArrayList<GameObject> activeObjects;

    public Texture background;
    public Texture cop;
    public Texture target;
    public Texture wall;
    public Texture robber;
    public Texture vWall;
    public Texture water;
    public Texture plant;
    public Texture lookout;
    public Texture hWall;
    public Texture hut;
    public Texture door;
    public Texture play;
    public String name;
    public boolean horizontal;
    public BitmapFont font;


    public MapState(GameStateManager gsm){
        super(gsm);
        gameObjects = new ArrayList<GameObject>();
        activeObjects = new ArrayList<GameObject>();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        background = new Texture("desert.png");
        cop = new Texture("cop.png");
        target = new Texture("lookout.png");
        wall = new Texture("wall.png");
        robber = new Texture("robber.png");
        play = new Texture("play.png");
        vWall = new Texture("wall.png");
        plant = new Texture("plant.png");
        lookout = new Texture("lookout.png");
        hWall = new Texture("wall.png");
        hut = new Texture("hut.png");
        door = new Texture("door.png");
        water = new Texture("water.png");

        gameObjects.add(new Robber(10,575));
        gameObjects.add(new Cop(95,575));
        gameObjects.add(new LookOut(170,575));
        gameObjects.add(new Hut(245,575));
        gameObjects.add(new Plant(320,575));
        gameObjects.add(new vWall(395,575));
        gameObjects.add(new Door(470,575));
        gameObjects.add(new Target(545,575));
        gameObjects.add(new LookOut(620,575));
        gameObjects.add(new Water(695,575));




      //  gameObjects.add(new Water(150,570,"water.png"));


    }
    @Override
    public void handleInput() {



        if(Gdx.input.isKeyPressed(Input.Keys.H)) {
            if(horizontal == true){
                horizontal = false;
            }
            else{
                horizontal = true;
            }

        }

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            this.name = "robber.png";
         ////   System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            this.name = "cop.png";
           // System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            this.name = "lookout.png";
           // System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            this.name = "hut.png";
           // System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            this.name = "plant.png";
         //   System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            this.name = "wall.png";
          //  System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_7)) {
            this.name = "door.png";
          //  System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_8)) {
            this.name = "target.png";
            System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_9)) {
            this.name = "glass.png";
          //  System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            this.name = "water.png";
            //  System.out.println("Made it");
        }



        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (Exception e) {
                System.out.println("Error");
            }
           // if (Gdx.input.getY() >= 100) {
            int x = (int) Math.floor(Gdx.input.getX());
            int y = (int) Math.floor((CopsAndRobbers.HEIGHT - Gdx.input.getY()));


               if(this.name == "target.png") {
                    activeObjects.add(new Target(x, y));
                }

                if(name == "water.png" ){
                    activeObjects.add(new Water(x, y));
                }
                if(name == "wall.png" ){
                    if(horizontal == true){
                        activeObjects.add(new hWall(x,y));
                    }
                    else{
                        activeObjects.add(new vWall(x,y));
                    }
                }
                if(name == "robber.png" ){
                    activeObjects.add(new Robber(x, y));
                }
                if(name == "plant.png" ){
                    activeObjects.add(new Plant(x, y));
                }
                if(name == "lookout.png" ){
                    activeObjects.add(new LookOut(x, y));
                }
                if(name == "hut.png" ){
                    activeObjects.add(new Hut(x, y));
                }
                if(name == "door.png" ){
                    activeObjects.add(new Door(x, y));
                }
                if(name == "cop.png" ){
                    activeObjects.add(new Cop(x, y));
                }
                if(name == "water.png" ){
                    activeObjects.add(new Water(x, y));
                }


            }
        //}
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        int count = 1;
        sb.begin();
        sb.draw(background,0,0, CopsAndRobbers.WIDTH,CopsAndRobbers.HEIGHT);
        sb.draw(play,850,535, 120,120);
        sb.draw(wall,0,520, 1000,20);
        sb.draw(wall,820,520, 20,180);


        for(int i =0; i < gameObjects.size(); i++ ){
            sb.draw(gameObjects.get(i).texture, gameObjects.get(i).xPos,gameObjects.get(i).yPos,75,75);
            font.draw(sb,String.valueOf(count), gameObjects.get(i).xPos+30,gameObjects.get(i).yPos-10);
            count++;

        }

        for(int i =0; i < activeObjects.size(); i++ ){
            sb.draw(activeObjects.get(i).texture, activeObjects.get(i).xPos,activeObjects.get(i).yPos,activeObjects.get(i).width,activeObjects.get(i).height);
        }


        sb.end();

    }

    public void dispose(){
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).texture.dispose();
        }
        for(int i = 0; i < activeObjects.size(); i++){
            activeObjects.get(i).texture.dispose();
        }
        font.dispose();
    }
}
