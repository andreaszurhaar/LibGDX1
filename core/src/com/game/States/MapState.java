package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.CopsAndRobbers;
import com.game.Objects.Candle;
import com.game.Objects.Cop;
import com.game.Objects.Door;
import com.game.Objects.GameObject;

import com.game.Objects.LookOut;
import com.game.Objects.Play;
import com.game.Objects.Robber;
import com.game.Objects.Steps;
import com.game.Objects.Target;
import com.game.Objects.VDoor;
import com.game.Objects.Water;
import com.game.Objects.Web;
import com.game.Objects.hWall;
import com.game.Objects.vWall;
import com.game.Readers.SpriteReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MapState extends State {

    public ArrayList<GameObject> gameObjects;
    public ArrayList<GameObject> activeObjects;
    public TextureRegion background;
    public String name;
    public boolean vertical;
    public BitmapFont font;
    public SpriteReader reader;
    public Play play;
    public vWall vWall;
    public hWall hWall;

    public MapState(GameStateManager gsm){
        super(gsm);
        gameObjects = new ArrayList<GameObject>();
        activeObjects = new ArrayList<GameObject>();
        reader = new SpriteReader();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        try {
            background = reader.getImage(58,292,26,28);
        } catch (IOException e) {
            e.printStackTrace();
        }

         play = new Play(865,545);
         vWall = new vWall(820,520);
         hWall = new hWall(0,520);


        gameObjects.add(new Robber(5,575));
        gameObjects.add(new Cop(85,575));
        gameObjects.add(new Steps(165,575));
        gameObjects.add(new Candle(245,575));
        gameObjects.add(new hWall(325,575));
        gameObjects.add(new VDoor(405,575));
        gameObjects.add(new Door(485,575));
        gameObjects.add(new Target(565,575));
        gameObjects.add(new LookOut(645,575));
        gameObjects.add(new Web(725,575));


    }
    @Override
    public void handleInput() {

        if(Gdx.input.isKeyPressed(Input.Keys.V)) {
            if(vertical == true){
                vertical = false;
            }
            else{
                vertical  = true;
            }

        }

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            this.name = "robber";
         ////   System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            this.name = "cop";
           // System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            this.name = "steps";
           // System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            this.name = "candle";
           // System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            this.name = "wall";
         //   System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            this.name = "Vdoor";
          //  System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_7)) {
            this.name = "door";
          //  System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_8)) {
            this.name = "target";
            System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_9)) {
            this.name = "lookout";
          //  System.out.println("Made it");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            this.name = "web";
            //  System.out.println("Made it");
        }



        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Exception e) {
                System.out.println("Error");
            }
           // sb.draw(play,850,535, 120,120);
            if(Gdx.input.getX() > 860 && Gdx.input.getY() < 100){
                dispose();
                gsm.push(new MainState(gsm,activeObjects));
            }
            if (Gdx.input.getY() >= 150) {
            int x = (int) Math.floor(Gdx.input.getX());
            int y = (int) Math.floor((CopsAndRobbers.HEIGHT - Gdx.input.getY()));


               if(this.name == "target") {
                    activeObjects.add(new Target(x, y));
                }

                if(name == "steps" ){
                    activeObjects.add(new Steps(x, y));
                }
                if(name == "wall" ){
                    if(vertical == true){
                        activeObjects.add(new vWall(x,y));
                    }
                    else{
                        activeObjects.add(new hWall(x,y));
                    }
                }
                if(name == "robber" ){
                    activeObjects.add(new Robber(x, y));
                }
                if(name == "candle" ){
                    activeObjects.add(new Candle(x, y));
                }
                if(name == "lookout" ){
                    activeObjects.add(new LookOut(x, y));
                }
                if(name == "Vdoor" ){
                   activeObjects.add(new VDoor(x, y));
                }
                if(name == "door" ){
                    activeObjects.add(new Door(x, y));
                }
                if(name == "cop" ){
                    activeObjects.add(new Cop(x, y));
                }
                if(name == "web" ){
                    activeObjects.add(new Web(x, y));
                }


            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
      //  dispose();
    }

    @Override
    public void render(SpriteBatch sb) {
        int count = 1;
        sb.begin();
        sb.draw(background,0,0, CopsAndRobbers.WIDTH,CopsAndRobbers.HEIGHT);
        sb.draw(play.texture,play.xPos,play.yPos, 100,100);
        sb.draw(hWall.texture,hWall.xPos,hWall.yPos, 1000,20);
        sb.draw(vWall.texture,vWall.xPos,vWall.yPos, 20,180);

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
        font.dispose();

        for(int i = 0; i < gameObjects.size(); i++){
           gameObjects.get(i).dispose();
        }
    }
}
