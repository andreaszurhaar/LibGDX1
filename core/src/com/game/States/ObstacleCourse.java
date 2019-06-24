package com.game.States;
/**
 * UI handling class for the Obstacle Course tests
 * 
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.AI.AI;
import com.game.AI.Astar.AStarNew;
import com.game.AI.Astar.Astar;
import com.game.AI.HeuristicAI;
import com.game.AI.IntruderBasicMovement;
import com.game.AI.CopsCenters;
import com.game.AI.GuardCirclePatrolling;
import com.game.AI.GuardPatrolling;
import com.game.AI.IntruderBasicMovement;
import com.game.Board.Guard;
import com.game.Board.Intruder;
import com.game.Board.RandomMapGenerator;
import com.game.CopsAndRobbers;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Board;
import com.game.Board.Structure;
import com.game.Objects.Ground;
import com.game.Objects.Play;
import com.game.Readers.SpriteReader;
import com.badlogic.gdx.math.Rectangle;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Lukas Padolevicius
 *
 */

public class ObstacleCourse extends State {

    public ArrayList<ArrayList<Area>> structures;
    public ArrayList<Area> struct1;
    public ArrayList<Area> struct2;
    public ArrayList<Agent> agents;
    public ArrayList<Agent> agents2;
    public ArrayList<Agent> guards;
    public ArrayList<Agent> intruders;
    public ArrayList<Structure> walls;

    private BitmapFont font;
    private float deltaTime1 = 0;
    private float deltaTime2 = 0;
    CharSequence str;

    public Texture background;
    public Texture wall;
    public Play play;
    public String name;
    public TextureRegion tR;
    public SpriteReader reader;
    public Board board;
    public Board board2;
    public Ground ground;
    public GuardPatrolling guardPatrol;
    public static final float X_REDUC = MapState.X_REDUC;
    public static final float Y_REDUC = MapState.Y_REDUC;
    public double timeLimit = 6.00;
    String intruderAI;
    String guardAI;
    public long startTime1;
    public long startTime2;
    public int iterations1 = 0;
    public int iterations2 = 0;
    public int maxIter = 100;
    public Intruder intr1;
    public Intruder intr2;


    public ObstacleCourse(GameStateManager gsm) {
        super(gsm);
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        this.intruderAI = intruderAI;
        this.guardAI = guardAI;
        wall = new Texture("wall.png");
        play = new Play(865,545);
        ground = new Ground(0,0);
        reader = new SpriteReader();
        try {
            tR = reader.getImage(100,100,25,25);
        } catch (IOException e) {
            e.printStackTrace();
        }
      //separate the agents and structures

        ObstacleGenerator gen = new ObstacleGenerator();
        gen.generate(new Rectangle(80,8,240,184), 15, 0.5f, maxIter);
        structures = gen.getAreas();
        struct1 = structures.get(iterations1);
        struct2 = structures.get(iterations2);
        agents = new ArrayList<Agent>();
        agents2 = new ArrayList<Agent>();
        intr1 = new Intruder(47f,97f,15/X_REDUC,15/Y_REDUC,false,struct1);
        intr2 = new Intruder(47f,97f,15/X_REDUC,15/Y_REDUC,true,struct2);
        agents.add(intr1);
       	agents2.add(intr2);
       	
        guards = new ArrayList<Agent>();
        intruders = new ArrayList<Agent>();
        

        board = new Board();
        board2 = new Board();
        if(!this.structures.isEmpty()) {board.setUp(struct1);}
        if(!this.structures.isEmpty()) {board2.setUp(struct2);}
        if(!this.agents.isEmpty()) {board.putInAgents(agents);}
        if(!this.agents.isEmpty()) {board2.putInAgents(agents2);}
        startTime1 = System.currentTimeMillis();
        startTime2 = System.currentTimeMillis();
    }


    @Override
    public void handleInput() {

        if (board.gameOver && iterations1 < maxIter) {
        	float time = deltaTime1;
            try {
            	FileWriter fileWriter = new FileWriter("DijkstraComputing.txt",true);
                fileWriter.append("\n");
                fileWriter.append(Float.toString(intr1.computeTime*(60/Board.fps)));
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }        	
            try {
            	FileWriter fileWriter = new FileWriter("DijkstraTotal.txt",true);
                fileWriter.append("\n");
                fileWriter.append(Float.toString(deltaTime1*(60/Board.fps)));
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }  	
        	iterations1++;
            struct1 = structures.get(iterations1);
            agents = new ArrayList<Agent>();
            intr1 = new Intruder(47f,97f,15/X_REDUC,15/Y_REDUC,false,struct1);
            agents.add(intr1);
           	board = new Board();
            if(!this.structures.isEmpty()) {board.setUp(struct1);}
            if(!this.agents.isEmpty()) {board.putInAgents(agents);}
            startTime1 = System.currentTimeMillis();
        }
        if (board2.gameOver && iterations2 < maxIter) {
        	try {
            	FileWriter fileWriter = new FileWriter("AstarComputing.txt",true);
                fileWriter.append("\n");
                fileWriter.append(Float.toString(intr2.computeTime*(60/Board.fps)));
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }        	
            try {
            	FileWriter fileWriter = new FileWriter("AstarTotal.txt",true);
                fileWriter.append("\n");
                fileWriter.append(Float.toString(deltaTime2*(60/Board.fps)));
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }  	
        	
        	iterations2++;
            struct2 = structures.get(iterations2);
            intr2 = new Intruder(47f,97f,15/X_REDUC,15/Y_REDUC,true,struct2);
            agents2 = new ArrayList<Agent>();
           	agents2.add(intr2);
            board2 = new Board();
            if(!this.structures.isEmpty()) {board2.setUp(struct2);}
            if(!this.agents.isEmpty()) {board2.putInAgents(agents2);}
            startTime2 = System.currentTimeMillis();

        }

        if(deltaTime1 > timeLimit){        	
        	iterations1++;
	        struct1 = structures.get(iterations1);
	        agents = new ArrayList<Agent>();
	        intr1 = new Intruder(42.5f,92.5f,15/X_REDUC,15/Y_REDUC,false,struct1);
	        agents.add(intr1);
	       	board = new Board();
	        if(!this.structures.isEmpty()) {board.setUp(struct1);}
	        if(!this.agents.isEmpty()) {board.putInAgents(agents);}
	        startTime1 = System.currentTimeMillis();
        }
        if(deltaTime2 > timeLimit){        	
        	iterations2++;
	        struct2 = structures.get(iterations2);
	        agents2 = new ArrayList<Agent>();
	        intr2 = new Intruder(42.5f,92.5f,15/X_REDUC,15/Y_REDUC,true,struct2);
	       	agents2.add(intr2);
	        board2 = new Board();
	        if(!this.structures.isEmpty()) {board2.setUp(struct2);}
	        if(!this.agents.isEmpty()) {board2.putInAgents(agents2);}
	        startTime2 = System.currentTimeMillis();
        }
	    board.updateAgents();
	    board2.updateAgents();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
    	
        sb.begin();
        sb.draw(ground.texture,ground.xPos,ground.yPos, CopsAndRobbers.WIDTH,CopsAndRobbers.HEIGHT);
        sb.draw(play.texture, play.xPos, play.yPos, 100, 100);
        sb.draw(wall, 0, 500, 1000, 20);
        sb.draw(wall, 820, 520, 20, 180);

        //Draws the time onto the screen
        deltaTime1 = (float) (System.currentTimeMillis()-startTime1)/1000;
        deltaTime2 = (float) (System.currentTimeMillis()-startTime2)/1000;
        str = Float.toString(deltaTime1);
        font.draw(sb, str, 100, 600);
        font.draw(sb, "TIME", 50, 600);

        //Draws all structures and agents
        for(int i =0; i < struct2.size(); i++ ){
            struct2.get(i).drawTexture(sb,MapState.X_REDUC,MapState.Y_REDUC);
        }
        
        for(int i =0; i < agents2.size(); i++ ){
            agents2.get(i).drawTexture(sb,MapState.X_REDUC,MapState.Y_REDUC);
        }

        sb.end();

    }


    public void dispose() {
        font.dispose();
    }
}