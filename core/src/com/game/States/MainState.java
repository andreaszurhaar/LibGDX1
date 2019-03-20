package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.CopsAndRobbers;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Board;
import com.game.Board.Guard;
import com.game.Board.Intruder;
import com.game.Board.OuterWall;
import com.game.Board.SentryTower;
import com.game.Board.Structure;
import com.game.Board.TargetArea;
import com.game.Objects.Cop;
import com.game.Objects.Door;
import com.game.Objects.GameObject;
import com.game.Objects.Ground;
import com.game.Objects.LookOut;
import com.game.Objects.Robber;
import com.game.Objects.Target;
import com.game.Objects.Web;
import com.game.Objects.hWall;
import com.game.Objects.vWall;
import com.game.Readers.SpriteReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainState extends State {

    public ArrayList<Area> structures;
    public ArrayList<Agent> agents;
    public Texture background;
    public Texture wall;
    public Texture play;
    public String name;
    public BitmapFont font;
    public TextureRegion tR;
    public SpriteReader reader;
    public Board board;
    public Ground ground;


    public MainState(GameStateManager gsm, ArrayList<Area> structures, ArrayList<Agent> agents) {
        super(gsm);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
      //  background = new Texture("desert.png");
        wall = new Texture("wall.png");
        play = new Texture("play.png");
        ground = new Ground(0,0);
        reader = new SpriteReader();
        try {
            tR = reader.getImage(100,100,25,25);
        } catch (IOException e) {
            e.printStackTrace();
        }
      //separate the agents and structures
        this.structures = structures;
        this.agents = agents;
        /*
        for(int i=0; i<gameObjects.size(); i++) {
	        GameObject go = gameObjects.get(i);
	        float x = go.xPos/5;
	        float y = go.yPos/5;
	        float wid = go.width/5;
	        float hei = go.height/5;
	        if(x >= 0 && x+wid <200 && y >= 0 && y+hei <200) {
	        	if(go instanceof LookOut) {
	        		structures.add(new SentryTower( x, y,wid,hei));
	        	}
	        	
	        	if(go instanceof Target) {
	        		structures.add(new TargetArea( x, y, wid, hei));
	        	}
	        	
	        	if(go instanceof hWall || go instanceof vWall) {
	        		structures.add(new Structure( x, y, wid, hei));
	        	}
	        	
	        	if(go instanceof Web) {
	        		structures.add(new OuterWall( x, y, wid, hei));
	        	}
	        	
	        	if(go instanceof Cop) {
	        		agents.add(new Guard( x, y));
	        	}
	        	
	        	if(go instanceof Robber) {
	        		System.out.println("placed in: "+ x+"   "+ y);
	        		agents.add(new Intruder( x, y));
	        	}
        	}
        }
        */
        
        
        board = new Board();
		System.out.println("got here and sizes are: "+structures.size()+"  "+agents.size());

        if(!structures.isEmpty()) {board.setUp(structures);}
        if(!agents.isEmpty()) {board.putInAgents(agents);}
        
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
            if (Gdx.input.getX() > 850 && Gdx.input.getY() < 835) {
                gsm.pop();
            }
            int x = (int) Math.floor(Gdx.input.getX());
            int y = (int) Math.floor((CopsAndRobbers.HEIGHT - Gdx.input.getY()));

        }
        board.updateAgents();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
    	
        sb.begin();
        sb.draw(ground.texture,ground.xPos,ground.yPos, CopsAndRobbers.WIDTH,CopsAndRobbers.HEIGHT);
        sb.draw(play, 850, 835, 120, 120);
        sb.draw(wall, 0, 500, 1000, 20);
        sb.draw(wall, 820, 520, 20, 180);

        for(int i =0; i < agents.size(); i++ ){
            sb.draw(agents.get(i).texture, agents.get(i).xPos*MapState.X_REDUC,agents.get(i).yPos*MapState.Y_REDUC,30,30);
        }
        
        for(int i =0; i < structures.size(); i++ ){
            structures.get(i).drawTexture(sb,MapState.X_REDUC,MapState.Y_REDUC);
        }
        
        /*
        for (int i = 0; i < gameObjects.size(); i++) {
            sb.draw(gameObjects.get(i).texture, gameObjects.get(i).xPos, gameObjects.get(i).yPos, gameObjects.get(i).width, gameObjects.get(i).height);
        }
		*/
        sb.end();

    }

    public void dispose() {
        font.dispose();
    }
}