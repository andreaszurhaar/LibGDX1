package com.game.AI.Astar;

import com.game.Board.Area;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Famke Nouwens
 */

public class NodeNew {

    public String name;
    public List<EdgeNew> connections;
    public List<NodeNew> neighbours;
    public NodeNew parent;
    public final int id;
    private static int nextID = 0;

    public float xcoord, ycoord;
    private NodeNew targetNode;
    private boolean start;
    private boolean target;
    public double hCost = 0;
    public double fCost = 0;
    public double gCost = 1;

    public NodeNew(float x, float y){
        connections = new ArrayList<EdgeNew>();
        neighbours = new ArrayList<NodeNew>();
        target = true;
        targetNode = this;
        xcoord = x;
        ycoord = y;
        id = nextID;
        nextID++;
    }

    public NodeNew (float x, float y, NodeNew target)
    {
        connections = new ArrayList<EdgeNew>();
        neighbours = new ArrayList<NodeNew>();
        targetNode = target;
        xcoord = x;
        ycoord = y;
        id = nextID;
        nextID++;
    }

    public void setTarget()
    {
        target = true;
        targetNode = this;
    }

    public void setStart()
    {
        start = true;
    }

    public boolean isTarget()
    {
        if (target) {
            return true;
        }
        return false;
    }

    public boolean isStart()
    {
        if (start) {
            return true;
        }
        return false;
    }

    //we take the number of 'squares' (nodes) away from the target
    //IMPORTANT: if we take the euclidean distance, we need to add diagonal edges to the graph!!!!
    public double estimatedCostTarget()
    {
//        System.out.println("Our coordinates: " + xcoord +","+ycoord);
//        System.out.println("Target coordinates: " +targetNode.xcoord + ","+ targetNode.ycoord);
        hCost = Math.sqrt((xcoord - targetNode.xcoord)*(xcoord - targetNode.xcoord) + (ycoord - targetNode.ycoord)*(ycoord - targetNode.ycoord));
//        System.out.println("Calculating distances to be: " + hCost );
        return hCost;
    }

    public double calcTotalCost(NodeNew destNode)
    {
        hCost = estimatedCostTarget(); //cost to get to target
        if (hasParent())
        {
            double cost = findEdge(destNode); //gCost is the edgeweight
//            System.out.println("parent of node " + this.id + " is " + parent.id);
            gCost = parent.gCost + cost;

//            System.out.println("gCost of node " +this.id + " is " + cost);
//            System.out.println("gCost of parent of node " +this.id + " is " + parent.gCost);
//            System.out.println("total gCost of node " +this.id + " is " + gCost);
            fCost = hCost + gCost;
        }
        else
        {
            gCost = findEdge(destNode); //gCost is the edge weight
//            System.out.println("gCost of node " +this.id + " is " + gCost);
            fCost = hCost + gCost;
        }
        return fCost;
    }

    public double findEdge(NodeNew destNode)
    {
        for (int i = 0; i<neighbours.size();i++)
        {
            if (neighbours.get(i).isEqual(destNode))
            {
//                System.out.println("Weight of edge is: " + connections.get(i).weight + " when calculating total cost for node " + this.id);
                return connections.get(i).weight;
            }
        }
        //System.out.println("we return edge weight = 10000 if edge doesn't exist");
        return 10000;
    }

    public void setTotalCost(int cost)
    {
        fCost = cost;
        if (fCost == 0)
        {
            gCost = 0;
            hCost = 0;
        }
    }

    public boolean isEqual(NodeNew node)
    {
        if (node.xcoord == this.xcoord && node.ycoord == this.ycoord)
        {
            return true;
        }
        return false;
    }

    public NodeNew getParent()
    {
        return parent;
    }

    public void setParent(NodeNew parent)
    {
        this.parent = parent;
    }

    public boolean hasParent()
    {
        if (parent == null)
        {
            return false;
        }
        return true;
    }

    public void addConnection(EdgeNew edge, NodeNew neighbour)
    {
        //System.out.println("Edge we're adding: " + edge.id);
        //System.out.println("Neighbour of current node: " + neighbour.id);
        neighbours.add(neighbour);
        connections.add(edge);
    }

}
