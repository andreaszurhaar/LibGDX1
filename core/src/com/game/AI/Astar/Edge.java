package com.game.AI.Astar;

/**
 * @author Famke Nouwens
 */

public class Edge {

    public Node start;
    public Node end;
    public double weight;
    public final int id;
    private static int nextID = 1;

    public Edge(Node start, Node end)
    {
        this.start = start;
        this.end = end;
        id = nextID;
        nextID++;
    }

    public Edge(Node start, Node end, double weight) {
        this.start = start;
        id = nextID;
        nextID++;
        this.end = end;
        this.weight = weight;
        //System.out.println("start node is:" + start.id);
        //System.out.println("end node is:" + end.id);
        start.addConnection(this, end);
        end.addConnection(this,start);
    }

    public void changeWeight(double weight)
    {
        this.weight = weight;
    }


}
