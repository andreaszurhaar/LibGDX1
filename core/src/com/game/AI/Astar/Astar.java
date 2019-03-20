
package com.game.AI.Astar;

import java.util.ArrayList;
import java.util.List;

//made for the robber
public class Astar {

    private Node start;
    private Node target = new Node(0,1);
    private List<Node> open = new ArrayList<Node>();
    private List<Node> closed = new ArrayList<Node>();
    private Graph map;
    private double lowestCost = 1000.0;

   /* public Astar(Agent agent, int startx, int starty, ArrayList areas)
    {
        this.agent = agent;
        createInitialGraph(areas);
        setStart(startx, starty);
        //Take random target coordinates
        setTarget(90,2);
        start();
    }*/

    public Astar(ArrayList areas)
    {
        createInitialGraph(areas);
        setStart(3, 3);
        //Take random target coordinates
        setTarget(0,1);
        start();
    }

    public void createInitialGraph(ArrayList areas)
    {
        map = new Graph(areas, target);
    }

    public void setTarget(int x, int y)
    {
        target = map.setTargetNode(x,y);
    }

    public void setStart(int x, int y)
    {
        start = map.setStartNode(x,y);
    }

    public void start()
    {
        System.out.println("Running the a-star algorithm");
        open.add(start);
        start.setTotalCost(0);
        int steps = 0;
        Node currentNode = start;
        while (!open.isEmpty())
        {
            lowestCost = 100;
            System.out.println("In while loop: " + steps);
            System.out.println("Open-list:" );
            for (Node n:open)
            {
                System.out.println("Node " + n.id );
            }
            for (int j = 0; j<open.size(); j++)
            {

                //System.out.println("gcost of node " + open.get(j).id + " is " + open.get(j).gCost );
                //System.out.println("hcost of node " + open.get(j).id + " is " + open.get(j).hCost );
                //System.out.println("Fcost of node " + open.get(j).id + " is " + open.get(j).fCost );
                if (open.get(j).fCost < lowestCost) //find the lowest cost
                {
                    lowestCost = open.get(j).fCost;
                    System.out.println("New lowest cost: "+ lowestCost);
                    currentNode = open.get(j);
                }
            }
            System.out.println("Node with lowest cost:" + currentNode.id);
            open.remove(currentNode);

            List<Node> neighbours = map.findAdjacentNodes(currentNode);
            System.out.println("Number of neighbours:" + neighbours.size());
            for (int i=0; i<neighbours.size(); i++ )
            {
                System.out.println("neighbour node is " + neighbours.get(i).id);
                if (!closed.contains(neighbours.get(i))) {
                    if (neighbours.get(i).hasParent()) {
                        System.out.println("Node has already a parent");
                        if ((currentNode.fCost + neighbours.get(i).gCost) < (neighbours.get(i).getParent().fCost + neighbours.get(i).gCost)) {
                            System.out.println("Currentnode as parent gives lower total cost so we update the parent");
                            neighbours.get(i).setParent(currentNode);
                        }
                    } else {
                        neighbours.get(i).setParent(currentNode);
                    }
                    System.out.println("Node " + neighbours.get(i).id + " has parent "  + neighbours.get(i).getParent().id);
                }

                neighbours.get(i).calcTotalCost(currentNode);

                if(neighbours.get(i).isEqual(target))
                {
                    System.out.println("Target is reached");
                    break;
                }

                else if (!open.contains(neighbours.get(i)) && !closed.contains(neighbours.get(i)))
                {
                    System.out.println("Open-list does not contain node");
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
            System.out.println("Adding node " + currentNode.id + " to closed-list");
            closed.add(currentNode);
            steps++;

        }
        if (!currentNode.isEqual(target))
        {
            System.out.println("Failure");
        }

        printPath(target);
    }

    public void printPath(Node node)
    {
        Node temp = node;
        System.out.println("The path to be taken from target node " + target.id +" is: " );
        while (temp.hasParent())
        {

            System.out.print("Node " + temp.id );


            //System.out.println("Node " + temp.id + " has as parent: node " + temp.getParent().id);
            System.out.println(" to ");
            temp = temp.getParent();
        }
        System.out.println("Node " + start.id);
        System.out.println("Total cost of path is: " + target.fCost );
    }


}
