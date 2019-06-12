package com.game.AI.Astar;

public class EdgeNew {

    public NodeNew start;
    public NodeNew end;
    public float weight;
    public final int id;
    private static int nextID = 1;

    public EdgeNew(NodeNew start, NodeNew end)
    {
        this.start = start;
        this.end = end;
        id = nextID;
        nextID++;
    }

    public EdgeNew(NodeNew start, NodeNew end, float weight) {
        this.start = start;
        id = nextID;
        nextID++;
        this.end = end;
        this.weight = weight;
        System.out.println("start node is:" + start.id);
        System.out.println("end node is:" + end.id);
        start.addConnection(this, end);
        end.addConnection(this,start);
    }

    public void changeWeight(float weight)
    {
        this.weight = weight;
    }




}
