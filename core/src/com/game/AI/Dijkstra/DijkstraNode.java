/**
 * 
 */
package com.game.AI.Dijkstra;

import java.util.ArrayList;

/**
 * @author Lukas Padolevicius
 *
 */
public class DijkstraNode {
	
	public float x;
	public float y;
	public float score;
	public ArrayList<DijkstraNode> neighbours;
	public ArrayList<DijkstraEdge> adjacent;
	public ArrayList<DijkstraNode> bestPathToHere;
	public float bestEntryAngle;
	public boolean examined;
	
	public DijkstraNode(float xPos, float yPos) {
		x = xPos;
		y = yPos;
		neighbours = new ArrayList<DijkstraNode>();
		adjacent = new ArrayList<DijkstraEdge>();
		examined = false;
	}
	
	public void addNeighbour(DijkstraEdge edge) {
		adjacent.add(edge);
		if(edge.start.equals(this)) {
			neighbours.add(edge.end);
		} else {
			neighbours.add(edge.start);
		}
	}

	public void setBestPath(ArrayList<DijkstraNode> nodes) {
		bestPathToHere = new ArrayList<DijkstraNode>();
		for(int i=0; i<nodes.size(); i++) {
			bestPathToHere.add(nodes.get(i));
		}
		//bestPathToHere = nodes;
		bestPathToHere.add(this);
	}
	
	public void setEntryAngle(float ang) {
		bestEntryAngle = ang;
	}
}
