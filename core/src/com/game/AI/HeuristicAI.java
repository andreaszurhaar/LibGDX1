package com.game.AI;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AI.Astar.AStarNew;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Board;
import com.game.Board.Guard;
import com.game.Board.Intruder;
import com.game.Board.MapDivider;
import com.game.Board.OuterWall;
import com.game.CopsAndRobbers;
import com.game.States.MapState;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.Stack;

public class HeuristicAI extends AI {

    private Agent agent;
    private Vector2 point;
    private float guardAngle, prevAngle;
    private Random rand = new Random();
    private ArrayList<Area> structures;
    //    private float areaWidth,areaHeight;
    private final int FACTOR = 20, AVERYBIGNUMBER = 500, Y_FACTOR = 20, X_FACTOR = 49, DEGREE_RANGE = 90; //number of squares that we want
    public final static int BOARD_WIDTH = 400;
    public final static int BOARD_HEIGHT = 200;
    private ArrayList<Vector2> explorationPoints;
    private String pattern;
    public static final float X_REDUC = MapState.X_REDUC;
    public static final float Y_REDUC = MapState.Y_REDUC;
    public boolean startingPos, guardSeen;
    public ArrayList<Area> exploredStructures;
    private Vector2 currentExplorationPoint;
    private Direction currentDirection = Direction.NORTH;
    private ArrayList<Point2D.Float> cornerPoints;

    public HeuristicAI(Agent agent)
    {
        this.agent = agent;
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
        seenStructures = new ArrayList<Area>();
        exploredStructures = new ArrayList<Area>();
        startingPos = false;
        structures = new ArrayList<Area>();
        cornerPoints = new ArrayList<Point2D.Float>();
        explorationSetUp();
    }

    public HeuristicAI()
    {
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new Instruction();
        seenStructures = new ArrayList<Area>();
        exploredStructures = new ArrayList<Area>();
        startingPos = false;
        //structures = new ArrayList<Area>();
        cornerPoints = new ArrayList<Point2D.Float>();
        explorationSetUp();

    }
/*

    public HeuristicAI(Agent agent, float areaWidth, float areaHeight)
    {
        this.agent = agent;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        exploration();
    }
*/

    public void explorationSetUp() {

        explorationPoints = new ArrayList<Vector2>();
        float tempX = (BOARD_WIDTH/MapState.X_REDUC) / FACTOR;
        float tempY = (650/MapState.Y_REDUC) / FACTOR;
        for (int i = 1; i < X_FACTOR; i++) {
            for (int j = 1; j < Y_FACTOR; j++) {
                explorationPoints.add(new Vector2(i * tempX + 0.5f * tempX, j * tempY + 0.5f * tempY));
            }
        }

        //TODO uncomment for evenly spaced points
//        explorationPoints = new ArrayList<Vector2>();
//        float tempX = 10;
//        float tempY = 10;
//        for (int i = 1; i < BOARD_WIDTH/tempX - 1; i++) {
//            for (int j = 1; j < BOARD_HEIGHT/tempY - 1; j++) {
//                explorationPoints.add(new Vector2(i * tempX + 0.5f * tempX, j * tempY + 0.5f * tempY));
//            }
//        }


        for(int i = 0; i < explorationPoints.size(); i++){
            System.out.print("x = " + explorationPoints.get(i).x + " " + " y = " + explorationPoints.get(i).y );
            System.out.println(" ");
        }

        currentExplorationPoint = explorationPoints.get(0);
    }

    public void explorationSetUpGuards(){
        explorationPoints = new ArrayList<Vector2>();
        float tempX = 10;
        float tempY = 10;

        float areaWidth = cornerPoints.get(2).x - cornerPoints.get(0).x;
        int nrOfSquaresWidth = (int) (areaWidth / tempX);
        float areaHeight = cornerPoints.get(2).y - cornerPoints.get(0).y;
        int nrOfSquaresHeight = (int) (areaHeight / tempY);

        for (int i = 1; i < nrOfSquaresWidth - 1; i++) {
            for (int j = 1; j < nrOfSquaresHeight - 1; j++) {
                explorationPoints.add(new Vector2(cornerPoints.get(0).x + (i * tempX + 0.5f * tempX), cornerPoints.get(0).y +  (j * tempY + 0.5f * tempY)));
            }
        }

        for(int i = 0; i < explorationPoints.size(); i++){
            System.out.print("x = " + explorationPoints.get(i).x + " " + " y = " + explorationPoints.get(i).y );
            System.out.println(" ");
        }
    }

    public void exploration() {
        if (pattern.equals("closest")) {
            point = closestUnknown();
        }
//        else if (pattern.equals("random")) {
//            point = randomMovement();
//        }
        else if (pattern.equals("all")) {
            point = allOptions();
        }
        else if (pattern.equals("heatmap")){
            point = heatMapMovement();
        }

        AStarNew astar = new AStarNew(seenStructures);
        astar.setAgent(agent);
        astar.runAgain(agent.xPos,agent.yPos,point.x,point.y);
        rotation = astar.getRotationStack();
        speed = astar.getSpeedStack();
        System.out.println("agent xPos = " + agent.xPos);
        System.out.println("agent yPos = " + agent.yPos);
        System.out.println("target xPos = " + point.x);
        System.out.println("target yPos = " + point.y);
        System.out.println("rotation stack = " + rotation.size());
        System.out.println("speed stack = " + speed.size());
    }

    private Vector2 closestUnknown() {

        //Checks if there are sturctures that need to be explored and moves to them
        if (exploredStructures.size() > 0){


            for(int i = 0; i < explorationPoints.size(); i++){
                if(exploredStructures.get(0).area.contains(explorationPoints.get(i))){
                    explorationPoints.remove(i);
                }
            }
            if(exploredStructures.get(0).xPos > 12 && exploredStructures.get(0).xPos < 388 && exploredStructures.get(0).yPos < 195 && exploredStructures.get(0).yPos > 15){
                float distance = 100000;
                int index = 0;
                for (int i = 0; i < explorationPoints.size(); i++) {
                    float temp_distance = explorationPoints.get(i).dst2(exploredStructures.get(0).xPos, exploredStructures.get(0).yPos);
                    if (temp_distance < distance) {
                        distance = temp_distance;
                        index = i;
                    }
                }
                    point = explorationPoints.get(index);
                exploredStructures.remove(0);
                return point;
            }
            exploredStructures.remove(0);

        }
        //Checks to see if the agent is in a starting corner
        if(startingPos == false) {
            float startingX = agent.getX();
            float startingY = agent.getY();
            point = new Vector2(startingX, startingY);
            if (point.x > 200) {
                if (point.y > 100) {
                    point = new Vector2(380, 180);
                    startingPos = true;
                } else {
                    point = new Vector2(380, 30);
                    startingPos = true;
                }
            } else {
                if (point.y > 100) {
                    point = new Vector2(30, 180);
                    startingPos = true;
                } else {
                    point = new Vector2(30, 30);
                    startingPos = true;
                }
            }
        }//Moves to the closest exploration point if there is nothing interesting to search
        else {
            float distance = 100000;
            int index = 0;
            for (int i = 0; i < explorationPoints.size(); i++) {
                float temp_distance = explorationPoints.get(i).dst2(agent.xPos,agent.yPos);
                if(temp_distance < distance ){
                    distance = temp_distance;
                    index = i;
                }
            }

            point = explorationPoints.get(index);
        }

        return point;
    }

    //Useless since it's already in alloptions
/*    private Vector2 randomMovement() {
        //creates range so the agent doesn't move in the direction it came from (currently at least 90 degrees in a different direction)
        float angle;
        if (guardSeen){
            //adjust upper and lower so that is directly in the opposite direction of the guard
            angle = guardAngle + 180;
        }
        else {
            int upper = (int) prevAngle + DEGREE_RANGE;
            int lower = (int) prevAngle - DEGREE_RANGE;
            angle = (float) rand.nextInt(upper - lower) + lower;

        }
        if (angle>360)
        {
            angle = angle - 360;
        }
        prevAngle = angle;
        //create a point outside the map according to the angle
        Vector2 vector =  new Vector2((float) (agent.xCenter + AVERYBIGNUMBER*Math.cos(Math.toRadians(angle))),(float) (agent.yCenter + AVERYBIGNUMBER*Math.sin(Math.toRadians(angle))));
        System.out.println("vector: " + vector.x + "," + vector.y);
        return vector;
    }*/

    public Vector2 allOptions(){
        //Checks if there are sturctures that need to be explored and moves to them
        if (exploredStructures.size() > 0){

            for(int i = 0; i < explorationPoints.size(); i++){
                if(exploredStructures.get(0).area.contains(explorationPoints.get(i))){
                    explorationPoints.remove(i);
                }
            }
            if(exploredStructures.get(0).xPos > 12 && exploredStructures.get(0).xPos < 388 && exploredStructures.get(0).yPos < 195 && exploredStructures.get(0).yPos > 15){

                exploredStructures.remove(0);
                return point;
            }
            exploredStructures.remove(0);

        }

        Random rand = new Random();
        int n = rand.nextInt(explorationPoints.size());
        point = explorationPoints.get(n);
        return point;
    }

    public Vector2 heatMapMovement() {
        /**
         * Choose one of the exploration points to go to based on:
         * -has it already been explored
         * -distance
         * -are we moving in the same direction as in the previous iteration
         */

        //TODO start at one of the corners

        if (exploredStructures.size() > 0) {


            for (int i = 0; i < explorationPoints.size(); i++) {
                if (exploredStructures.get(0).area.contains(explorationPoints.get(i))) {
                    explorationPoints.remove(i);
                }
            }
            if (exploredStructures.get(0).xPos > 12 && exploredStructures.get(0).xPos < 388 && exploredStructures.get(0).yPos < 195 && exploredStructures.get(0).yPos > 15) {
                float distance = 100000;
                int index = 0;
                for (int i = 0; i < explorationPoints.size(); i++) {
                    float temp_distance = explorationPoints.get(i).dst2(exploredStructures.get(0).xPos, exploredStructures.get(0).yPos);
                    if (temp_distance < distance) {
                        distance = temp_distance;
                        index = i;
                    }
                }
                point = explorationPoints.get(index);
                exploredStructures.remove(0);
                return point;
            }
            exploredStructures.remove(0);

        }

            float minPointDistance = Float.MAX_VALUE;
            ArrayList<Vector2> closestPoints = new ArrayList<Vector2>();

            //find closests points
            for (int i = 0; i < explorationPoints.size(); i++) {
                float pointDistance = currentExplorationPoint.dst(explorationPoints.get(i));
                if (currentExplorationPoint != explorationPoints.get(i) && pointDistance < minPointDistance) {
                    minPointDistance = pointDistance;
                    closestPoints.clear();
                    closestPoints.add(explorationPoints.get(i));
                } else if (currentExplorationPoint != explorationPoints.get(i) && pointDistance == minPointDistance) {
                    closestPoints.add(explorationPoints.get(i));
                }
            }

            //check where each of the closest points are with respect to the the currentExplorationPoint
            //if any have the same relativeDirection as the currentDirection, return that point
            //otherwise return closestPoints.get(0), and save the direction we are now going in
            Direction relativeDirection = currentDirection;
            for (int j = 0; j < closestPoints.size(); j++) {
                float pointX = closestPoints.get(j).x;
                float pointY = closestPoints.get(j).y;

                if (pointX == currentExplorationPoint.x && pointY > currentExplorationPoint.y)
                    relativeDirection = Direction.NORTH;
                if (pointX > currentExplorationPoint.x && pointY > currentExplorationPoint.y)
                    relativeDirection = Direction.NORTH_EAST;
                if (pointX > currentExplorationPoint.x && pointY == currentExplorationPoint.y)
                    relativeDirection = Direction.EAST;
                if (pointX > currentExplorationPoint.x && pointY < currentExplorationPoint.y)
                    relativeDirection = Direction.SOUTH_EAST;
                if (pointX == currentExplorationPoint.x && pointY < currentExplorationPoint.y)
                    relativeDirection = Direction.SOUTH;
                if (pointX < currentExplorationPoint.x && pointY < currentExplorationPoint.y)
                    relativeDirection = Direction.SOUTH_WEST;
                if (pointX < currentExplorationPoint.x && pointY == currentExplorationPoint.y)
                    relativeDirection = Direction.WEST;
                if (pointX < currentExplorationPoint.x && pointY > currentExplorationPoint.y)
                    relativeDirection = Direction.NORTH_WEST;

                if (relativeDirection == currentDirection) {
                    currentExplorationPoint = closestPoints.get(j);
                    explorationPoints.remove(currentExplorationPoint);
                    return currentExplorationPoint;
                }
            }

            if (closestPoints.get(0).x == currentExplorationPoint.x && closestPoints.get(0).y > currentExplorationPoint.y)
                relativeDirection = Direction.NORTH;
            if (closestPoints.get(0).x > currentExplorationPoint.x && closestPoints.get(0).y > currentExplorationPoint.y)
                relativeDirection = Direction.NORTH_EAST;
            if (closestPoints.get(0).x > currentExplorationPoint.x && closestPoints.get(0).y == currentExplorationPoint.y)
                relativeDirection = Direction.EAST;
            if (closestPoints.get(0).x > currentExplorationPoint.x && closestPoints.get(0).y < currentExplorationPoint.y)
                relativeDirection = Direction.SOUTH_EAST;
            if (closestPoints.get(0).x == currentExplorationPoint.x && closestPoints.get(0).y < currentExplorationPoint.y)
                relativeDirection = Direction.SOUTH;
            if (closestPoints.get(0).x < currentExplorationPoint.x && closestPoints.get(0).y < currentExplorationPoint.y)
                relativeDirection = Direction.SOUTH_WEST;
            if (closestPoints.get(0).x < currentExplorationPoint.x && closestPoints.get(0).y == currentExplorationPoint.y)
                relativeDirection = Direction.WEST;
            if (closestPoints.get(0).x < currentExplorationPoint.x && closestPoints.get(0).y > currentExplorationPoint.y)
                relativeDirection = Direction.NORTH_WEST;
            currentDirection = relativeDirection;
            currentExplorationPoint = closestPoints.get(0);

            //explorationPoints.remove(currentExplorationPoint);
            return currentExplorationPoint;
    }

    public boolean checkCollision(){

        return false;
    }

    @Override
    public float getRotation() {
        if (rotation.empty()) {
            exploration();
        } else {
            //System.out.print("  and rotation: "+rotation.peek());
            return rotation.pop();
        }
        return 0f;
    }

    @Override
    public float getSpeed() {
        if (speed.empty()) {
            exploration();
        } else {
            //System.out.println("  getting instruction to move with speed: "+speed.peek());
            return speed.pop();
        }
        return 0f;
    }

    @Override
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public void setStructures(ArrayList<Area> structures) {
        this.structures = structures;
    }

    @Override
    public void setArea(float areaWidth, float areaHeight) {

    }

    @Override
    public void reset() {
        for(int i = 0; i < rotation.size(); i++){
            rotation.pop();
        }

        for(int i = 0; i < speed.size(); i++){
            speed.pop();
        }
    }

    @Override
    public void seeArea(Area area) {
        System.out.println("printing some area ");

        if(!(area instanceof OuterWall)) {
            boolean check = false;
            //checking to see if area is in seen structures, if not it is added to the array
            if (seenStructures.size() > 0) {
                for (int i = 0; i < seenStructures.size(); i++) {
                    if (area == seenStructures.get(i)) {
                        check = true;
                    }
                }
                if (!check) {
                    seenStructures.add(area);
                    Area rectangle = new Area(area.xPos - agent.width / 2, area.yPos - agent.height / 2, area.area.width + agent.width, area.area.height + agent.height);
                    exploredStructures.add(rectangle);

                    reset();
                }
            } else {
                seenStructures.add(area);
                Area rectangle = new Area(area.xPos - agent.width / 2, area.yPos - agent.height / 2, area.area.width + agent.width, area.area.height + agent.height);
                exploredStructures.add(rectangle);
                exploredStructures.add(area);
                reset();
            }
        }

    }

    @Override
    public void seeAgent(Agent agent) {
            guardSeen = true;
    }

    @Override
    public void updatedSeenLocations(){

      //  System.out.println("before update = " + explorationPoints.size());

        for(int i = 0; i < explorationPoints.size(); i++){
            if(agent.area.contains(explorationPoints.get(i))){
                explorationPoints.remove(i);
            }
        }

       // System.out.println("after update = " + explorationPoints.size());
      //  System.out.println("Agent locaton x = " + agent.area.x + " " + "y loocation = " + agent.area.y);

    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setCurrentExplorationPoint(Vector2 currentExplorationPoint) {
        this.currentExplorationPoint = currentExplorationPoint;
    }

    @Override
    public void setCornerPoints(ArrayList<Point2D.Float> cornerPoints) {
        this.cornerPoints = cornerPoints;
        if(agent instanceof Guard){
            explorationSetUpGuards();
        }
        System.out.println("sout boys");
    }
}