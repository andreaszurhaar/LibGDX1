
package com.game.AI.Astar;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.game.AI.AI;
import com.game.AI.ChainInstruction;
import com.game.AI.Instruction;
import com.game.Board.Agent;
import com.game.Board.Area;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Famke Nouwens
 */

public class AStarNew extends AI {

    private float startx, starty, targetx, targety;
    private NodeNew start, target;
    private List<NodeNew> open = new ArrayList<NodeNew>();
    private List<NodeNew> closed = new ArrayList<NodeNew>();
    private GraphNew map;
    private double lowestCost = 1000.0;
    private ArrayList<Rectangle2D.Float> rectangles;
    private ArrayList<Vector2> pathReg;
    private ArrayList<NodeNew> path;
    private ArrayList<Area> structures;
    private int counter;
    private Agent intruder;
    private ChainInstruction instruction;

    public AStarNew(ArrayList<Rectangle2D.Float> rectangles, float startx, float starty, float targetx, float targety) {
    	speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new ChainInstruction();
        structures = new ArrayList<Area>();
        this.rectangles = rectangles;
        this.targetx = targetx;
        this.targety = targety;
        this.startx = startx;
        this.starty = starty;
        setStart(startx, starty);
        //Take random target coordinates
        setTarget(targetx, targety);
        createInitialGraph();
        start();
    }

    public AStarNew(ArrayList<Area> structures) {
    	speed = new Stack<Float>();
        rotation = new Stack<Float>();
        instruction = new ChainInstruction();
        this.structures = structures;
        rectangles = new ArrayList<Rectangle2D.Float>();
    }

    public void createInitialGraph() {

//        System.out.println("we create the map (createInitialGraph)");
        map = new GraphNew(rectangles, target, start);
//        System.out.println("the nodes are: ");
//        for (NodeNew n : map.nodes)
//        {
//            System.out.println("node " + n.id + " with "+ n.xcoord +","+n.ycoord);
//        }
//        System.out.println("and the edges are: ");
//        for (EdgeNew e : map.edges)
//        {
//            System.out.println("edge between " +e.start.id + " ("+ e.start.xcoord +","+e.start.ycoord + ")" +" and " +e.end.id + " ("+ e.end.xcoord +","+e.end.ycoord +")");
//        }
    }

    //use same structures and map, but just running it with a different start and end position
    public void runAgain(float startx, float starty, float targetx, float targety){
        pathReg.clear();
        open.clear();
        closed.clear();
//        System.out.println("target is set to: " + targetx + "," + targety);
//        System.out.println("start is set to: " + startx + "," + starty);
        map.addNode(0,startx, starty); //type = 0 means its the start node
        map.addNode(1, targetx, targety); //type = 1 means its the target node
        setTarget(targetx, targety);
        setStart(startx,starty);
        start();
    }

    public void setTarget(float x, float y) {
//        System.out.println("We set a target node to " + x + "," + y);
        target = map.setTargetNode(x, y);
    }

    public void setStart(float x, float y) {
        //System.out.println(map.setStartNode(x,y));
//        System.out.println("We set a start node to " + x + "," + y);
        start = map.setStartNode(x, y);
    }

    public void start() {
//        System.out.println("Start loc is: " + start.xcoord + ", " + start.ycoord);
//        System.out.println("Target loc is: " + target.xcoord + ", " + target.ycoord);
        //System.out.println("Running the a-star algorithm");
        open.add(start);
        start.setTotalCost(0);
        int steps = 0;
        NodeNew currentNode = start;
        while (!open.isEmpty()) {
            lowestCost = 100000;
            for (int j = 0; j < open.size(); j++) {

                if (open.get(j).fCost < lowestCost) //find the lowest cost
                {
                    lowestCost = open.get(j).fCost;
                    currentNode = open.get(j);
                }
            }
            open.remove(currentNode);

            List<NodeNew> neighbours = map.findAdjacentNodes(currentNode);
            for (int i = 0; i < neighbours.size(); i++) {
                if (!closed.contains(neighbours.get(i))) {
                    if (neighbours.get(i).hasParent()) {
                        if ((currentNode.fCost + neighbours.get(i).gCost) < (neighbours.get(i).getParent().fCost + neighbours.get(i).gCost)) {
                            neighbours.get(i).setParent(currentNode);
                        }
                    } else {
                        neighbours.get(i).setParent(currentNode);
                    }
                }

                neighbours.get(i).calcTotalCost(currentNode);

                if (neighbours.get(i).isEqual(target)) {
                    //System.out.println("Target is reached");
                    open.add(target);
                    break;
                } else if (!open.contains(neighbours.get(i)) && !closed.contains(neighbours.get(i))) {
//                    System.out.println("Open-list does not contain node");
                    open.add(neighbours.get(i));
                }

            }
//            System.out.println("Adding node " + currentNode.id + " to closed-list");
            closed.add(currentNode);
            steps++;

        }
        if (!currentNode.isEqual(target)) {
            //System.out.println("Failure");
        }

        //printPath(target);
        returnPath(target);
    }

    public void printPath(NodeNew node) {
        NodeNew temp = node;
        System.out.println("The path to be taken from target node " + target.id + " is: ");
        path = new ArrayList<NodeNew>();
        path.add(target);
        while (temp.hasParent()) {
            System.out.print("Node " + temp.id + " with coords: " + temp.xcoord + "," + temp.ycoord );
            //Path starting from last node to start node
            path.add(temp);
            System.out.println(" to ");
            temp = temp.getParent();
        }
        path.add(start);
        System.out.println("start node " + start.id + " with coords: " + temp.xcoord + "," + temp.ycoord);
        counter = path.size();

    }

    public void returnPath(NodeNew temp){
        ArrayList<NodeNew> pathInv = new ArrayList<NodeNew>();
        pathInv.add(target);
        pathReg = new ArrayList<Vector2>();
        while (temp.hasParent()) {
            pathInv.add(temp);
            temp = temp.getParent();
        }

        for (int i = pathInv.size()-1; i>0; i--){
//            System.out.println("i is: " + i);
            pathReg.add(new Vector2(pathInv.get(i).xcoord, pathInv.get(i).ycoord));
        }
        instruction.translate(pathReg, intruder);
        rotation = instruction.getRotations();
        speed = instruction.getSpeeds();
//        System.out.println("Path we take is: ");
//        for (Vector2 v:pathReg) {
//            System.out.println("Coords: " + v.x + "," + v.y);
//        }
    }

    @Override
    public float getRotation() {
    	if(rotation.isEmpty()) {
    		return 0;
    	}
    	return rotation.pop();
    }
    
    public Stack<Float> getRotationStack() {
    	return rotation;
    }    
    
    public Stack<Float> getSpeedStack() {
    	return speed;
    }

    @Override
    public float getSpeed() {
    	if(speed.isEmpty()) {
    		return 0;
    	}
    	return speed.pop();    }

    @Override
    public void setAgent(Agent agent) {
    	intruder = agent;
    	for(int i=0; i<structures.size(); i++) {
        	rectangles.add(new Rectangle2D.Float(structures.get(i).xPos-agent.area.width/2,structures.get(i).yPos-agent.area.height/2
        			,structures.get(i).area.width+agent.area.width,structures.get(i).area.height+agent.area.height));
        }
    	target = new NodeNew(agent.xCenter+10, agent.yCenter+10);
        start = new NodeNew(agent.xCenter, agent.yCenter);
//        System.out.println("creating new target and start nodes");
        createInitialGraph();
//        System.out.println("we create the map (setAgent)");
        setStart(start.xcoord,start.ycoord);
        setTarget(target.xcoord, target.ycoord);
        start();
    }

    @Override
    public void setStructures(ArrayList<Area> structures) {

    }

    @Override
    public void setArea(float areaWidth, float areaHeight) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void seeArea(Area area) {

    }

    @Override
    public void seeAgent(Agent agent) {

    }

    @Override
    public void updatedSeenLocations() {

    }
}
