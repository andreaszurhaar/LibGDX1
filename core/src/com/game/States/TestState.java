package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.AI.AI;
import com.game.AI.Astar.AStarNew;
import com.game.AI.GuardCirclePatrolling;
import com.game.AI.GuardPatrolling;
import com.game.AI.HeuristicAI;
import com.game.AI.IntruderBasicMovement;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Board;
import com.game.Board.Guard;
import com.game.Board.Structure;
import com.game.Objects.Ground;
import com.game.Objects.Play;
import com.game.Readers.SpriteReader;
import com.game.Readers.TestWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TestState extends State {

    public ArrayList<Area> structures;
    public ArrayList<Agent> agents;
    public ArrayList<Agent> guards;
    public ArrayList<Agent> intruders;
    public ArrayList<Structure> walls;

    private BitmapFont font;
    private float deltaTime = -10;
    CharSequence str;

    public Texture background;
    public Texture wall;
    public Play play;
    public String name;
    public TextureRegion tR;
    public SpriteReader reader;
    public Board board;
    public Ground ground;
    public GuardPatrolling guardPatrol;
    public static final float X_REDUC = MapState.X_REDUC;
    public static final float Y_REDUC = MapState.Y_REDUC;
    public double timeLimit = 60.00;
    String intruderAI;
    String guardAI;
    int counter;
    TestWriter testWriter;

    public TestState(GameStateManager gsm, ArrayList<Area> structures, ArrayList<Agent> agents, ArrayList<Structure> walls, String guardAI, String intruderAI, int counter) {
        super(gsm);
        font = new BitmapFont();
        this.counter = counter + 1;
        font.setColor(Color.WHITE);
        this.intruderAI = intruderAI;
        this.guardAI = guardAI;
        wall = new Texture("wall.png");
        play = new Play(865, 545);
        ground = new Ground(0, 0);
        reader = new SpriteReader();
        try {
            tR = reader.getImage(100, 100, 25, 25);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //separate the agents and structures
        this.structures = structures;
        this.agents = agents;
        this.walls = walls;

        for (int i = 0; i < this.walls.size(); i++) {
            this.structures.add(this.walls.get(i));
        }

        guards = new ArrayList<Agent>();
        intruders = new ArrayList<Agent>();

        for (int i = 0; i < this.agents.size(); i++) {
            if (this.agents.get(i) instanceof Guard) {
                if (guardAI == "Patrolling") {
                    AI agentAI = new GuardPatrolling();
                    this.agents.get(i).setAI(agentAI);
                    agentAI.setAgent(this.agents.get(i));
                    this.agents.get(i).ai.setArea(400, 200);
                    this.agents.get(i).ai.setStructures(structures);
                    guards.add(this.agents.get(i));
                } else if (guardAI == "Circle patrolling") {
                    AI agentAI = new GuardCirclePatrolling();
                    this.agents.get(i).setAI(agentAI);
                    agentAI.setAgent(this.agents.get(i));
                    this.agents.get(i).ai.setArea(400, 200);
                    this.agents.get(i).ai.setStructures(structures);
                    guards.add(this.agents.get(i));
                } else if (guardAI == "Heatmap patrolling") {
                    AI agentAI = new HeuristicAI();
                    this.agents.get(i).setAI(agentAI);
                    ((HeuristicAI) agentAI).setPattern("heatmap");
                    agentAI.setAgent(this.agents.get(i));
                    guards.add(agents.get(i));
                } else if (guardAI == "Random patrolling") {
                    AI agentAI = new HeuristicAI();
                    this.agents.get(i).setAI(agentAI);
                    ((HeuristicAI) agentAI).setPattern("random");
                    agentAI.setAgent(this.agents.get(i));
                    guards.add(agents.get(i));
                } else {
                    System.out.println("Unrecognised AI name: " + guardAI);
                    System.exit(0);
                }
            } else {
                if (intruderAI == "Basic") {
                    AI agentAI = new IntruderBasicMovement();
                    this.agents.get(i).setAI(agentAI);
                    agentAI.setAgent(this.agents.get(i));
                    this.agents.get(i).ai.setArea(400, 200);
                    this.agents.get(i).ai.setStructures(structures);
                    intruders.add(agents.get(i));
                } else if (intruderAI == "A*") {
                    AI agentAI = new AStarNew(structures);
                    this.agents.get(i).setAI(agentAI);
                    agentAI.setAgent(this.agents.get(i));
                    this.agents.get(i).ai.setArea(400, 200);
                    this.agents.get(i).ai.setStructures(structures);
                    intruders.add(agents.get(i));
                } else if (intruderAI == "Heuristic AI") {
                    AI agentAI = new HeuristicAI();
                    this.agents.get(i).setAI(agentAI);
//	                ((HeuristicAI) agentAI).setPattern("snake");
//	                agentAI.setAgent(agents.get(i));
                    //((HeuristicAI) agentAI).setPattern("closest");
                    ((HeuristicAI) agentAI).setPattern("closest");
                    agentAI.setAgent(this.agents.get(i));
                    intruders.add(agents.get(i));
                    this.agents.get(i).ai.setArea(400, 200);
                    this.agents.get(i).ai.setStructures(structures);
                } else {
                    System.out.println("Unrecognised AI name: " + intruderAI);
                    System.exit(0);
                }
            }

            /**
             * Giving each guard the arraylist of guards so that
             * they are accesible for communication
             */
            for (int j = 0; j < guards.size(); j++) {
                guards.get(j).setAgentList(guards);
            }

        }
    }



    @Override
    public void handleInput() {

        if(counter < 100) {
            if (board.timeOfTracking != 0) {
                try {
                    testWriter = new TestWriter("Test.txt", str,"lost",  )
                    gsm.push(new TestState(gsm, structures,agents, walls,guardAI, intruderAI, counter));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    testWriter = new TestWriter("Test.txt", str,"won",  )
                    gsm.push(new TestState(gsm, structures,agents, walls,guardAI, intruderAI, counter));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            if (board.timeOfTracking != 0) {
                try {
                    testWriter = new TestWriter("Test.txt", str,"lost",  )
                    System.exit(0);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    testWriter = new TestWriter("Test.txt", str,"won",  )
                    System.exit(0);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        board.updateAgents();

        for(int i = 0; i < intruders.size(); i++){
            intruders.get(i).ai.updatedSeenLocations();
        }

        for(int i = 0; i < guards.size(); i++){
            guards.get(i).ai.updatedSeenLocations();
        }

        deltaTime += Gdx.graphics.getDeltaTime();
        str = Float.toString(deltaTime);
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

    }
}
