package com.game.AI.Astar;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Area;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Famke Nouwens
 */

public class GraphNew {

    public ArrayList<NodeNew> nodes;
    public ArrayList<EdgeNew> edges;
    List<NodeNew> neighbours = new ArrayList<NodeNew>();
    private final float OFFSET = 0.1f;
    private NodeNew target, start;
    private ArrayList<Rectangle2D.Float> rectangles;

    public GraphNew(ArrayList<Rectangle2D.Float> rectangles, NodeNew target, NodeNew start) {
        this.rectangles = rectangles;
        neighbours.clear();
//        System.out.println("Target coordinates:" + target.xcoord +","+target.ycoord);
        this.target = target;
        this.start = start;
        createVertices();
    }

    public void createVertices()
    {
        nodes = new ArrayList<NodeNew>();
        //System.out.println("We're creating the vertices");
        for (Rectangle2D.Float r : rectangles) {
            //We add/subtract one pixel from the coordinates of the points to allow the intersection method to find allowable paths/edges to form
//            System.out.println("For rectangle: " +r);
            nodes.add(new NodeNew(r.x-OFFSET,r.y-OFFSET,target));
//            System.out.println("adding node with coords " + r.x+","+r.y);
            nodes.add(new NodeNew(r.x-OFFSET, r.y+r.height+OFFSET,target));
//            System.out.println("adding node with coords " + r.x+","+(r.y+r.height));
            nodes.add(new NodeNew(r.x+r.width+OFFSET, r.y+r.height+OFFSET,target));
//            System.out.println("adding node with coords " + (r.x+r.width)+","+r.y);
            nodes.add(new NodeNew(r.x+r.width+OFFSET, r.y-OFFSET,target));
//            System.out.println("adding node with coords " + (r.x+r.width)+","+(r.y+r.height));
        }
//        System.out.println("Target coordinates:" + target.xcoord +","+target.ycoord);
        nodes.add(new NodeNew(target.xcoord, target.ycoord));
        nodes.add(new NodeNew(start.xcoord, start.ycoord,target));
        addEdges();
    }

    public void addEdges(){
        edges = new ArrayList<EdgeNew>();
        for (NodeNew node1 : nodes){
            for (NodeNew node2 : nodes){
                if (node1.xcoord != node2.xcoord && node1.ycoord != node2.ycoord){
                    if (edgePossible(node1, node2)) {
                        //edge weight is distance between coords
                        float weight = (float) Math.sqrt(((node1.xcoord - node2.xcoord) * (node1.xcoord - node2.xcoord)) + ((node1.ycoord - node2.ycoord) * (node1.ycoord - node2.ycoord)));
//                        System.out.println("Weight is:" + weight);
                        EdgeNew edge = new EdgeNew(node1, node2, weight);
//                        System.out.println("Adding new edge to edge list");
                        edges.add(edge);
                    }
                }
            }
        }
    }

    public boolean edgePossible(NodeNew node1, NodeNew node2){
//        System.out.println("Checking if edge is possible between " + node1.xcoord +","+node1.ycoord +" and " + node2.xcoord +","+node2.ycoord );
        Line2D.Float line2D = new Line2D.Float(new Point2D.Float(node1.xcoord, node1.ycoord), new Point2D.Float(node2.xcoord, node2.ycoord));
//        System.out.println("Our line: "+line2D.x1 +"," + line2D.y1 + " and " +line2D.x2 +"," + line2D.y2);
        //TODO: if vector intersects with area, we cannot create the edge
        for (Rectangle2D.Float r : rectangles){
//            System.out.println("Rectangle is:" + r);
            if(line2D.intersects(r)){
//                System.out.println("we intersect");
                return false;
            }
        }
        return true;
    }

    public NodeNew setStartNode(float x, float y)
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
        System.out.println("We return null for start node");
        return null;
    }

    public NodeNew setTargetNode(float x, float y)
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
        System.out.println("We return null for target node");
        return null;
    }

    public void printEdgeList()
    {
        for (int i = 0; i<edges.size(); i++) {

            //System.out.println("edge " + i + " has start node " + edges.get(i).start.id + " and end node " + edges.get(i).end.id);
        }
    }

    public List<NodeNew> findAdjacentNodes(NodeNew node)
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

