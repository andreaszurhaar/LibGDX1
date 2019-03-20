package com.game.AI.Astar;

import com.game.Board.Area;

import java.util.ArrayList;
import java.util.List;


public class Node {

    public String name;
    public List<Edge> connections;
    public List<Node> neighbours;
    private Area object;
    public Node parent;
    public final int id;
    private static int nextID = 0;

    public int xcoord, ycoord;
    private Node targetNode;
    private boolean start;
    private boolean target;
    public double hCost = 0;
    public double fCost = 0;
    public double gCost = 1;

    public Node(Area structure, int x, int y){
        neighbours = new ArrayList<Node>();
        connections = new ArrayList<Edge>();
        this.object = structure;
        xcoord = x;
        ycoord = y;
        id = nextID;
        nextID++;
    }

    public Node(int x, int y){
        connections = new ArrayList<Edge>();
        neighbours = new ArrayList<Node>();
        target = true;
        targetNode = this;
        xcoord = x;
        ycoord = y;
        id = nextID;
        nextID++;
    }

    public Node (int x, int y, Node target)
    {
        connections = new ArrayList<Edge>();
        neighbours = new ArrayList<Node>();
        targetNode = target;
        xcoord = x;
        ycoord = y;
        id = nextID;
        nextID++;
    }

    public void setStructure(Area object)
    {
        this.object = object;
    }

    public void setTarget()
    {
        target = true;
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
        hCost = Math.abs(xcoord - targetNode.xcoord) + Math.abs(ycoord - targetNode.ycoord);
        //System.out.println("Calculating estimatedCostTarget to be: " + hCost );
        return hCost;
    }

    public double calcTotalCost(Node node)
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

    public double findEdge(Node node)
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

    public boolean isEqual(Node node)
    {
        if (node.xcoord == this.xcoord && node.ycoord == this.ycoord)
        {
            return true;
        }
        return false;
    }

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
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

    public void addConnection(Edge edge, Node neighbour)
    {
        //System.out.println("Edge we're adding: " + edge.id);
        //System.out.println("Neighbour of current node: " + neighbour.id);
        neighbours.add(neighbour);
        connections.add(edge);
    }

}
