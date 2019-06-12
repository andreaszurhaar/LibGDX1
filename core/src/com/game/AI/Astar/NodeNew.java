package com.game.AI.Astar;

import com.game.Board.Area;

import java.util.ArrayList;
import java.util.List;

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
        hCost = Math.abs(xcoord - targetNode.xcoord) + Math.abs(ycoord - targetNode.ycoord);
        //System.out.println("Calculating estimatedCostTarget to be: " + hCost );
        return hCost;
    }

    public double calcTotalCost(NodeNew node)
    {
        hCost = estimatedCostTarget();
        if (hasParent())
        {
            double cost = findEdge(node);
            gCost = parent.gCost + cost;
            fCost = hCost + gCost;
        }
        else
        {
            gCost = findEdge(node);
            fCost = hCost + gCost;
        }
        return fCost;
    }

    public double findEdge(NodeNew node)
    {
        for (int i = 0; i<neighbours.size();i++)
        {
            if (neighbours.get(i).isEqual(node))
            {
                //System.out.println("Weight of edge is: " + connections.get(i).weight);
                return connections.get(i).weight;
            }
        }
        return 1;
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
