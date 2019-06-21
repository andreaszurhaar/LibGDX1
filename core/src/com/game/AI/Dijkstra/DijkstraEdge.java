/**
 * 
 */
package com.game.AI.Dijkstra;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Lukas Padolevicius
 *
 */
public class DijkstraEdge {
	
	public DijkstraNode start;
	public DijkstraNode end;
	public float length;
	public float angle;
	
	public DijkstraEdge(DijkstraNode startNode, DijkstraNode endNode) {
		start = startNode;
		end = endNode;
		start.addNeighbour(this);
		end.addNeighbour(this);
		Vector2 v = new Vector2(end.x-start.x,end.y-start.y);
		length = v.len();
		angle = v.angle();
    	//System.out.println("MADE AN EDGE BETWEEN: "+start.x+"  "+start.y+"  and:  "+end.x+"   "+end.y+"  of length: "+length);
	}
	
	public DijkstraEdge(DijkstraNode startNode, DijkstraNode endNode, float traversalTime) {
		start = startNode;
		end = endNode;
		start.addNeighbour(this);
		end.addNeighbour(this);
		length = traversalTime;
	}
	
}
