package com.game.Board;

import com.badlogic.gdx.math.Rectangle;
import com.game.CopsAndRobbers;
import com.game.Objects.hWall;

import java.util.ArrayList;
import java.util.Random;

import static com.game.States.MapState.X_REDUC;
import static com.game.States.MapState.Y_REDUC;

public class RandomMapGenerator {

    private int minNrOfStructures;
    private int maxNrOfStructures;
    private int minNrOfGuards;
    private int maxNrOfGuards;
    private int minNrOfTargets;
    private int maxNrOfTargets;
    private final int ADJUSTED_BOARD_WIDTH = 360;
    private final int ADJUSTED_BOARD_HEIGHT = 160;
    //the fractions of frequency of each type of structure
    private final double WALLS_FREQUENCY = 0.6;
    private final double TOWER_FREQUENCY = 0.2;
    private final double CANDLE_FREQUENCY = 0.2;
    //private final double DOORS_FREQUENCY = 0.1;
    //private final double WINDOW_FREQUENCY = 0.1;
    private ArrayList<Area> structures;
    private ArrayList<Agent> agents;


    /**
     * Create random maps with the following parameters:
     * Minimum/maximum amount of structures
     * Minimum/maximum amount of agents
     */

    public RandomMapGenerator(int minNrOfStructures, int maxNrOfStructures, int minNrOfGuards, int maxNrOfGuards, int minNrOfTargets, int maxNrOfTargets){
        this.minNrOfStructures = minNrOfStructures;
        this.maxNrOfStructures = maxNrOfStructures;
        this.minNrOfGuards = minNrOfGuards;
        this.maxNrOfGuards = maxNrOfGuards;
        this.minNrOfTargets = minNrOfTargets;
        this.maxNrOfTargets = maxNrOfTargets;
        structures = new ArrayList<Area>();
        agents = new ArrayList<Agent>();


        generateStructureList();
        generateAgentList();
        //generateIntruder();
    }

    public ArrayList<Agent> generateAgentList() {
        /**
         * Generate a number of agents from U(minNrOfAgents, maxNrOfAgents) with random x and y coordinates
         */

        Random r = new Random();
        int nrOfGuards = r.nextInt(maxNrOfGuards + 1 - minNrOfGuards) + minNrOfGuards;

        for(int i = 0; i < nrOfGuards; i++) {
            int guardXpos = r.nextInt(ADJUSTED_BOARD_WIDTH) + 20; //random x between 20 and 380
            int guardYpos = r.nextInt(ADJUSTED_BOARD_HEIGHT) + 20; //random y between 20 and 180
            if (!checkOverlap(new Rectangle(guardXpos, guardYpos, 15 / X_REDUC, 15 / Y_REDUC))) {
                agents.add(new Guard(guardXpos, guardYpos, 15 / X_REDUC, 15 / Y_REDUC));
            }
            else{
                i--; //dont increment counter if we didn't add the agent
            }
        }

        //TODO use 1 intruder instead
        boolean intruderAdded = false;
        while(!intruderAdded) {
            int intruderXpos = r.nextInt(ADJUSTED_BOARD_WIDTH) + 20; //random x between 20 and 380
            int intruderYpos = r.nextInt(ADJUSTED_BOARD_HEIGHT) + 20; //random y between 20 and 180

            if (!checkOverlap(new Rectangle(intruderXpos, intruderYpos, 15 / X_REDUC, 15 / Y_REDUC))) {
                agents.add(new Intruder(intruderXpos, intruderYpos, 15 / X_REDUC, 15 / Y_REDUC));
                intruderAdded = true;
            }
        }


//        for(int i = 0; i < 1; i++) {
//            int intruderXpos = r.nextInt(ADJUSTED_BOARD_WIDTH) + 20; //random x between 20 and 380
//            int intruderYpos = r.nextInt(ADJUSTED_BOARD_HEIGHT) + 20; //random y between 20 and 180
//            if (!checkOverlap(new Rectangle(intruderXpos, intruderYpos, 15 / X_REDUC, 15 / Y_REDUC))) {
//                agents.add(new Intruder(intruderXpos, intruderYpos, 15 / X_REDUC, 15 / Y_REDUC));
//            }
//            else{
//                i--;
//            }
//        }
        return agents;
    }
//
//    public ArrayList<Agent> generateIntruder(){
//        for(int i = 0; i < 1; i++) {
//            int intruderXpos = r.nextInt(ADJUSTED_BOARD_WIDTH) + 20; //random x between 20 and 380
//            int intruderYpos = r.nextInt(ADJUSTED_BOARD_HEIGHT) + 20; //random y between 20 and 180
//            if (!checkOverlap(new Rectangle(intruderXpos, intruderYpos, 15 / X_REDUC, 15 / Y_REDUC))) {
//                agents.add(new Guard(intruderXpos, intruderYpos, 15 / X_REDUC, 15 / Y_REDUC));
//            }
//            else{
//                i--;
//            }
//        }
//    }



    public ArrayList<Area> generateStructureList() {

        structures.add(new OuterWall(20/X_REDUC,0,(CopsAndRobbers.WIDTH-40)/X_REDUC,20/Y_REDUC));
        structures.add(new OuterWall(20/X_REDUC,(CopsAndRobbers.HEIGHT-150)/Y_REDUC,(CopsAndRobbers.WIDTH-40)/X_REDUC,20/Y_REDUC));
        structures.add(new OuterWall(0,0,20/X_REDUC,(CopsAndRobbers.HEIGHT-130)/Y_REDUC));
        structures.add(new OuterWall((CopsAndRobbers.WIDTH-20)/X_REDUC,0,20/X_REDUC,(CopsAndRobbers.HEIGHT-130)/Y_REDUC));

        Random r = new Random();
        int nrOfStructures = r.nextInt(maxNrOfStructures + 1 - minNrOfStructures) + minNrOfStructures;

        for (int i = 0; i < nrOfStructures; i++) {

            double randomNr = r.nextDouble();

            if (randomNr < WALLS_FREQUENCY / 2) {
                //creating a horizontal wall
                boolean structureAdded = false;
                int structureWidth = 20; //hWall.width;
                int structureHeight = 100;

                while(!structureAdded) {
                    int structureXpos = r.nextInt(ADJUSTED_BOARD_WIDTH) + 20; //random x between 20 and 380
                    int structureYpos = r.nextInt(ADJUSTED_BOARD_HEIGHT) + 20; //random y between 20 and 180

                    if (!checkOverlap(new Rectangle(structureXpos, structureYpos, structureWidth / X_REDUC, structureHeight / Y_REDUC))) {
                        structures.add(new Structure(structureXpos, structureYpos, structureWidth / X_REDUC, structureHeight / Y_REDUC, true));
                        structureAdded = true;
                    }
                }
            }
            else if (randomNr < WALLS_FREQUENCY){
                //creating a vertical wall
                boolean structureAdded = false;
                int structureWidth = 100;
                int structureHeight = 20;

                while(!structureAdded) {
                    int structureXpos = r.nextInt(ADJUSTED_BOARD_WIDTH) + 20; //random x between 20 and 380
                    int structureYpos = r.nextInt(ADJUSTED_BOARD_HEIGHT) + 20; //random y between 20 and 180

                    if (!checkOverlap(new Rectangle(structureXpos, structureYpos, structureWidth / X_REDUC, structureHeight / Y_REDUC))) {
                        structures.add(new Structure(structureXpos, structureYpos, structureWidth / X_REDUC, structureHeight / Y_REDUC, false));
                        structureAdded = true;
                    }
                }
            }
            else if (randomNr < WALLS_FREQUENCY + TOWER_FREQUENCY){
                //creating a guard tower
                boolean structureAdded = false;
                int structureWidth = 50;
                int structureHeight = 50;

                while(!structureAdded) {
                    int structureXpos = r.nextInt(ADJUSTED_BOARD_WIDTH) + 20; //random x between 20 and 380
                    int structureYpos = r.nextInt(ADJUSTED_BOARD_HEIGHT) + 20; //random y between 20 and 180

                    if (!checkOverlap(new Rectangle(structureXpos, structureYpos, structureWidth / X_REDUC, structureHeight / Y_REDUC))) {
                        structures.add(new SentryTower(structureXpos, structureYpos, structureWidth / X_REDUC, structureHeight / Y_REDUC));
                        structureAdded = true;
                    }
                }
            }
            else if (randomNr < WALLS_FREQUENCY + TOWER_FREQUENCY + CANDLE_FREQUENCY){
                //creating a candle
                boolean structureAdded = false;
                int structureWidth = 20;
                int structureHeight = 40;

                while(!structureAdded) {
                    int structureXpos = r.nextInt(ADJUSTED_BOARD_WIDTH) + 20; //random x between 20 and 380
                    int structureYpos = r.nextInt(ADJUSTED_BOARD_HEIGHT) + 20; //random y between 20 and 180

                    if (!checkOverlap(new Rectangle(structureXpos, structureYpos, structureWidth / X_REDUC, structureHeight / Y_REDUC))) {
                        structures.add(new LowVisionArea(structureXpos, structureYpos, structureWidth / X_REDUC, structureHeight / Y_REDUC));
                        structureAdded = true;
                    }
                }
            }
        }


        int nrOfTargets = r.nextInt(maxNrOfTargets + 1 - minNrOfTargets) + minNrOfTargets;

        for(int i = 0; i < nrOfTargets; i++) {
            int targetXpos = r.nextInt(ADJUSTED_BOARD_WIDTH) + 20; //random x between 20 and 380
            int targetYpos = r.nextInt(ADJUSTED_BOARD_HEIGHT) + 20; //random y between 20 and 180
            if (!checkOverlap(new Rectangle(targetXpos, targetYpos, 40 / X_REDUC, 40 / Y_REDUC))) {
                structures.add(new TargetArea(targetXpos, targetYpos, 40 / X_REDUC, 40 / Y_REDUC));
            }
            else{
                i--; //dont increment counter if we didn't add the agent
            }
        }

        return structures;
    }


        public boolean checkOverlap(Rectangle rec) {

            //TODO change
            //ArrayList<Area> walls = null;

            boolean overlap = false;
            for(int a=0; a<11 ;a++) {
                for(int b=0; b<11 ;b++) {
                    float x = rec.x + rec.width/10*a;
                    float y = rec.y + rec.height/10*b;
                    for(int i = 0; i < structures.size(); i++) {
                        if(structures.get(i).area.contains(x,y)) {
                            return true;
                        }
                    }

//                    for(int i = 0; i < walls.size(); i++) {
//                        if(walls.get(i).area.contains(x,y)) {
//                            return true;
//                        }
//                    }

                    for(int j = 0; j < agents.size(); j++) {
                        if(agents.get(j).area.contains(x,y)) {
                            return true;
                        }
                    }
                }
            }
            return overlap;
        }


}
