package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.AI.TestAI;
import com.game.AI.TestAI2;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Guard;
import com.game.Board.Intruder;
import com.game.Board.SentryTower;
import com.game.Board.Structure;
import com.game.Board.TargetArea;
import com.game.Board.LowVisionArea;
import com.game.CopsAndRobbers;
import com.game.GameLogic.Collider;
import com.game.Objects.Candle;
import com.game.Objects.Cop;
import com.game.Objects.Door;
import com.game.Objects.GameObject;
import com.game.Objects.Ground;
import com.game.Objects.LookOut;
import com.game.Objects.Play;
import com.game.Objects.Robber;
import com.game.Objects.Steps;
import com.game.Objects.Target;
import com.game.Objects.VDoor;
import com.game.Objects.Web;
import com.game.Objects.hWall;
import com.game.Objects.vWall;
import com.game.Readers.SpriteReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MapState extends State {

    public ArrayList<GameObject> menuObjects;
    public ArrayList<GameObject> activeObjects;
    public ArrayList<GameObject> copObjects;
    public ArrayList<GameObject> robberObjects;
    public ArrayList<Agent> agents;
    public ArrayList<Area> structures;
    public ArrayList<Structure> walls;
    public String name;
    public boolean vertical;
    public BitmapFont font;
    public SpriteReader reader;
    public Play play;
    public vWall vWall;
    public hWall hWall;
    public Ground ground;
    public TestAI ai;
    public TestAI2 ai2;
    public Collider collider;
    public MapState(GameStateManager gsm){
        super(gsm);
        menuObjects = new ArrayList<GameObject>();
        activeObjects = new ArrayList<GameObject>();
        copObjects = new ArrayList<GameObject>();
        robberObjects = new ArrayList<GameObject>();
        agents = new ArrayList<Agent>();
        structures = new ArrayList<Area>();
        walls = new ArrayList<Structure>();
        reader = new SpriteReader();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        ai = new TestAI();
        ai2 = new TestAI2();
        collider = new Collider();

         play = new Play(865,845);
         vWall = new vWall(820,820);
         hWall = new hWall(0,820);
         ground = new Ground(0,0);


        menuObjects.add(new Robber(5,875));
        menuObjects.add(new Cop(85,875));
        menuObjects.add(new Steps(165,875));
        menuObjects.add(new Candle(245,875));
        menuObjects.add(new hWall(325,875));
        menuObjects.add(new VDoor(405,875));
        menuObjects.add(new Door(485,875));
        menuObjects.add(new Target(565,875));
        menuObjects.add(new LookOut(645,875));
        menuObjects.add(new Web(725,875));


    }
    @Override
    public void handleInput() {

        if(Gdx.input.isKeyPressed(Input.Keys.V)) {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Exception e) {
                System.out.println("Error");
            }
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
                gsm.push(new MainState(gsm,structures,agents));
            }
            if (Gdx.input.getY() >= 150) {
            float x = (float) Math.floor(Gdx.input.getX()/5f);
            float y = (float) Math.floor((CopsAndRobbers.HEIGHT - Gdx.input.getY())/4f);


               if(this.name == "target") {
                    structures.add(new TargetArea(x, y,20,20));
                }

                if(name == "steps" ){
                    activeObjects.add(new Steps((int) x,(int) y));
                }
                if(name == "wall" ){
                    if(vertical == true){
                        walls.add(new Structure(x,y,20,100,false));
                        structures.add(walls.get(walls.size()-1));
                        
                    }
                    else{
                    	walls.add(new Structure(x,y,100,20,true));
                        structures.add(walls.get(walls.size()-1));
                    }
                }
                if(name == "robber" ){
                    agents.add(new Intruder(x, y));
                }
                if(name == "candle" ){
                    structures.add(new LowVisionArea(x,y,20,40));
                }
                if(name == "lookout" ){
                    structures.add(new SentryTower(x, y, 50, 50));
                }
                if(name == "Vdoor" ){
                	for(int i=0; i<walls.size(); i++) {
                		if(walls.get(i).contains(x,y)) {
                			walls.get(i).placeDoor(x,y);
                		}
                	}
                }
                if(name == "door" ){
                	for(int i=0; i<walls.size(); i++) {
                		if(walls.get(i).contains(x,y)) {
                			walls.get(i).placeDoor(x,y);
                		}
                	}
                }
                if(name == "cop" ){
                    agents.add(new Guard(x, y));
                }
                if(name == "web" ){
                    activeObjects.add(new Web((int) x,(int) y));
                }


            }
        }


        //ai.move(agents);

        //ai2.move(robberObjects);

        robberObjects = collider.copVsRobber(robberObjects, copObjects);
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
        sb.draw(ground.texture,ground.xPos,ground.yPos, CopsAndRobbers.WIDTH,CopsAndRobbers.HEIGHT);
        sb.draw(play.texture,play.xPos,play.yPos, 100,100);
        sb.draw(hWall.texture,hWall.xPos,hWall.yPos, 1000,20);
        sb.draw(vWall.texture,vWall.xPos,vWall.yPos, 20,180);

        for(int i =0; i < menuObjects.size(); i++ ){
            sb.draw(menuObjects.get(i).texture, menuObjects.get(i).xPos,menuObjects.get(i).yPos,75,75);
            font.draw(sb,String.valueOf(count), menuObjects.get(i).xPos+30,menuObjects.get(i).yPos-10);
            count++;

        }

        for(int i =0; i < activeObjects.size(); i++ ){
            sb.draw(activeObjects.get(i).texture, activeObjects.get(i).xPos*5f,activeObjects.get(i).yPos*4f,activeObjects.get(i).width,activeObjects.get(i).height);
        }
        
        for(int i =0; i < agents.size(); i++ ){
            sb.draw(agents.get(i).texture, agents.get(i).xPos*5f,agents.get(i).yPos*4f,30,30);
        }
        
        for(int i =0; i < structures.size(); i++ ){
            structures.get(i).drawTexture(sb,5f,4f);
        }
        
        
        /*
        for(int i =0; i < copObjects.size(); i++ ){
            sb.draw(copObjects.get(i).texture, copObjects.get(i).xPos,copObjects.get(i).yPos,copObjects.get(i).width,copObjects.get(i).height);
        }

        for(int i =0; i < robberObjects.size(); i++ ){
            sb.draw(robberObjects.get(i).texture, robberObjects.get(i).xPos,robberObjects.get(i).yPos,robberObjects.get(i).width,robberObjects.get(i).height);
        }
		**/

        sb.end();


    }

    public void dispose(){
        font.dispose();

        for(int i = 0; i < menuObjects.size(); i++){
           menuObjects.get(i).dispose();
        }
        for(int i = 0; i < activeObjects.size(); i++){
            activeObjects.get(i).dispose();
        }
        for(int i = 0; i < copObjects.size(); i++){
            copObjects.get(i).dispose();
        }
        for(int i = 0; i < robberObjects.size(); i++){
            robberObjects.get(i).dispose();
        }
        /*
        for(int i = 0; i < structures.size(); i++){
            structures.get(i).dispose();
        }
        for(int i = 0; i < agents.size(); i++){
            agents.get(i).dispose();
        }*/
    }
}
