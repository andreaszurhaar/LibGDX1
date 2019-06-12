
package com.game.AI.Astar;

import com.badlogic.gdx.math.Vector2;
import com.game.AI.AI;
import com.game.AI.Instruction;
import com.game.Board.Agent;
import com.game.Board.Area;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AStarNew extends AI {

    private NodeNew start;
    private NodeNew target;
    private List<NodeNew> open = new ArrayList<NodeNew>();
    private List<NodeNew> closed = new ArrayList<NodeNew>();
    private GraphNew map;
    private double lowestCost = 1000.0;
    private ArrayList<Rectangle2D.Float> rectangles;
    private ArrayList<NodeNew> path;
    private int counter;
    private Agent intruder;

    public AStarNew(ArrayList<Rectangle2D.Float> rectangles, float startx, float starty, float targetx, float targety)
    {
        this.rectangles = rectangles;
        target = new NodeNew(targetx, targety);
        start = new NodeNew(startx, starty);
        createInitialGraph(rectangles);
        setStart(startx, starty);
        //Take random target coordinates
        setTarget(targetx, targety);
        start();
    }

    public void createInitialGraph(ArrayList areas)
    {
        map = new GraphNew(rectangles, target, start);
    }

    public void setTarget(float x, float y)
    {
//        System.out.println("We set a target node to " + x + "," + y);
        target = map.setTargetNode(x,y);
    }

    public void setStart(float x, float y)
    {
        //System.out.println(map.setStartNode(x,y));
//        System.out.println("We set a start node to " + x + "," + y);
        start = map.setStartNode(x,y);
    }

    public void start()
    {
        System.out.println("Start loc is: " + start.xcoord + ", "+ start.ycoord);
        System.out.println("Target loc is: " + target.xcoord + ", "+ target.ycoord);
        //System.out.println("Running the a-star algorithm");
        open.add(start);
        start.setTotalCost(0);
        int steps = 0;
        NodeNew currentNode = start;
        while (!open.isEmpty())
        {
            lowestCost = 100000;
            for (int j = 0; j<open.size(); j++)
            {
                if (open.get(j).fCost < lowestCost) //find the lowest cost
                {
                    lowestCost = open.get(j).fCost;
                    currentNode = open.get(j);
                }
            }
            open.remove(currentNode);

            List<NodeNew> neighbours = map.findAdjacentNodes(currentNode);
            for (int i=0; i<neighbours.size(); i++ )
            {
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

                if(neighbours.get(i).isEqual(target))
                {
                    break;
                }

                else if (!open.contains(neighbours.get(i)) && !closed.contains(neighbours.get(i)))
                {
                    open.add(neighbours.get(i));
                }
                /*else if (closed.contains(neighbours.get(i)))
                {
                    System.out.println("Closed-list contains node");

                }
                else{ //the neighbour is already in the open list
                    //if the total cost of reaching the node in this way is lower than of that one in the open list, we need to update it
                    //HOWEVER i think it gets updated automatically because of the way i wrote my node-class
                    System.out.println("Open-list contains node");
                    Node currentNodeOpen = null;
                    for (int j = 0; j<open.size(); j++)
                    {
                        if (open.get(j).xcoord == neighbours.get(i).xcoord && open.get(j).ycoord == neighbours.get(i).ycoord )
                        {
                            currentNodeOpen = neighbours.get(i);
                        }
                    }
                    if (currentNodeOpen.fCost > neighbours.get(i).fCost)
                    {
                        open.remove(currentNodeOpen);
                        open.add(neighbours.get(i));
                    }
                }*/

            }
            closed.add(currentNode);
            steps++;

        }
        if (!currentNode.isEqual(target))
        {
            //System.out.println("Failure");
        }

        printPath(target);
    }

    public void printPath(NodeNew node)
    {
        NodeNew temp = node;
        System.out.println("The path to be taken from target node " + target.id +" is: " );
        path = new ArrayList<NodeNew>();
        path.add(target);
        while (temp.hasParent())
        {
            System.out.print("Node " + temp.id + " with coords: " + temp.xcoord +","+ temp.ycoord);
            //Path starting from last node to start node
            path.add(temp);
            System.out.println(" to ");
            temp = temp.getParent();
        }
        path.add(start);
        System.out.println("start node " + start.id+" with coords: " + temp.xcoord +","+ temp.ycoord);
        counter = path.size();
    }



//    public float getRotation(NodeNew temp) {
//        Vector2 tempVector = new Vector2(temp.xcoord, temp.ycoord);
//        Vector2 startVector = new Vector2();
//        return ;
//    }

    public void getNextPoint(){
        Vector2 nextPoint = new Vector2(path.get(counter).xcoord, path.get(counter).ycoord);
        instruction.translate(nextPoint, intruder);
        counter--;
        rotation = instruction.getRotations();
        speed = instruction.getSpeeds();
    }

    @Override
    public float getRotation() {
        if (rotation.empty()){
            getNextPoint();
        }
        else
        {
            return rotation.pop();
        }
        return rotation.pop();
    }

    @Override
    public float getSpeed() {
        if (speed.empty()){
            getNextPoint();
        }
        else
        {
            return speed.pop();
        }
        return speed.pop();    }


    @Override
    public void setAgent(Agent agent) {
        intruder = agent;
    }

    @Override
    public void setStructures(ArrayList<Area> structures) {    }

    @Override
    public void setArea(float areaWidth, float areaHeight) {

    }

    @Override
    public void reset() {
        speed = new Stack<Float>();
        rotation = new Stack<Float>();
    }

    @Override
    public void seeArea(Area area) {

    }

    @Override
    public void seeAgent(Agent agent) {

    }

}
