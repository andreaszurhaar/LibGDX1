
package com.game.AI.Astar;


import java.util.ArrayList;
import java.util.List;
import com.game.Board.*;

//We're assuming for now that the agent has perfect information

public class Graph {

    public List<Node> nodes;
    public List<Edge> edges;
    List<Node> neighbours = new ArrayList<Node>();
    private Node target;
    private ArrayList<Area> structures;
    private final int mapSize = 200;

    public Graph(ArrayList<Area> structures, Node target) {
        this.structures = structures;
        neighbours.clear();
        this.target = target;
        createVertices(structures);
    }

    public void createVertices(ArrayList<Area> structures)
    {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        //System.out.println("We're creating the vertices");
        int counter = 0;
        int row = 0;
        for (int i = 0; i<mapSize; i++)
        {
            for (int j = 0; j<mapSize; j++) {

                    nodes.add(new Node(i, j, target));
                    //System.out.println("create node " + nodes.get(counter+row*mapSize).id);

                    if (counter > 0) {
                        //System.out.println("We add a new edge");
                        edges.add(new Edge(nodes.get(counter - 1 + row * mapSize), nodes.get(counter + row * mapSize), 1));
                    }
                    if (row != 0) {
                        //System.out.println("We add a new edge");
                        edges.add(new Edge(nodes.get(counter + row * mapSize), nodes.get(counter + row * mapSize - mapSize), 1));
                    }
                    counter++;
                    if (counter > (mapSize - 1)) {
                        counter = 0;
                        row++;
                    }
            }
        }
        deleteStructureNodes();

    }

    public void deleteStructureNodes()
    {
        for(int k = 0; k<structures.size(); k++) {
            int startX = (int) structures.get(k).getMinX();
            int startY = (int)structures.get(k).getMinY();
            int endX = (int) structures.get(k).getMaxX();
            int endY = (int) structures.get(k).getMaxY();

            int tempX = startX;
            int tempY = startY;
            int cnt = 0;

            while (tempX != endX)
            {
                while (tempY != endY)
                {
                    int index =(int) tempX + mapSize*tempY;
                    cnt++;
                    if (cnt>0)
                    {
                        //Remove the edges within a structure
                        edges.remove(tempX+tempY*mapSize);
                    }
                    for (int i = 0; i<edges.size();i++)
                    {
                        if (edges.get(i).start.isEqual(nodes.get(index)) || edges.get(i).end.isEqual(nodes.get(index)))
                        {
                            edges.remove(i);
                        }
                    }
                    nodes.remove(index);
                    tempY++;
                }
                tempY = startY;
                tempX++;
            }
        }
    }

    public Node setStartNode(int x, int y)
    {
        //System.out.println("Setting the start node");
        for (int i = 0; i<nodes.size();i++)
        {
            if (nodes.get(i).xcoord == x && nodes.get(i).ycoord == y)
            {
                nodes.get(i).setStart();
                return nodes.get(i);
            }
        }
        return null;
    }

    public Node setTargetNode(int x, int y)
    {
        //System.out.println("Setting the target node");
        for (int i = 0; i<nodes.size();i++)
        {
            if (nodes.get(i).xcoord == x && nodes.get(i).ycoord == y)
            {
                nodes.get(i).setTarget();
                return nodes.get(i);
            }
        }
        return null;
    }

    public void printEdgeList()
    {
        for (int i = 0; i<edges.size(); i++) {

        //System.out.println("edge " + i + " has start node " + edges.get(i).start.id + " and end node " + edges.get(i).end.id);
        }
    }

    public List<Node> findAdjacentNodes(Node node)
    {
        neighbours.clear();

        //System.out.println("Finding the neighbours of node " + node.xcoord + " , " + node.ycoord);

        for (int i = 0; i<edges.size(); i++)
        {
            if (edges.get(i).start == node)
            {
                neighbours.add(edges.get(i).end);
            }
            else if  (edges.get(i).end == node) {
                neighbours.add(edges.get(i).start);
            }
        }
        return neighbours;
    }

}
