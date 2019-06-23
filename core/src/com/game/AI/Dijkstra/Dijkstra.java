/**
 * 
 */
package com.game.AI.Dijkstra;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.math.Vector2;
import com.game.AI.ChainInstruction;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.SentryTower;
import com.game.Board.Structure;

/**
 * @author Lukas Padolevicius
 *
 */
public class Dijkstra {
	
	
	public ArrayList<DijkstraNode> nodeGraph;
	public ArrayList<DijkstraEdge> edgeGraph;
    private ArrayList<Rectangle2D.Float> obstacles;
    public Stack<Float> speeds;
    public Stack<Float> rotations;
	
	
	
	public Dijkstra(ArrayList<Area> structures, Agent agent, float goalx, float goaly) {
		ChainInstruction ins = new ChainInstruction();
		ArrayList<Vector2> path = run(structures,agent,goalx,goaly);
		ins.translate(path, agent);
		speeds = ins.getSpeeds();
		rotations = ins.getRotations();
	}
	
	public ArrayList<Vector2> run(ArrayList<Area> structures, Agent agent, float goalx, float goaly) {
		float angleToScoreConversion = agent.maxSpeed/agent.turningCircle;
		nodeGraph = new ArrayList<DijkstraNode>();
		edgeGraph = new ArrayList<DijkstraEdge>();
		obstacles = new ArrayList<Rectangle2D.Float>();
		nodeGraph.add(new DijkstraNode(agent.xCenter,agent.yCenter));
		for(int i=0; i<structures.size(); i++) {
			if(structures.get(i) instanceof SentryTower) {
				Rectangle2D.Float rec = new Rectangle2D.Float(structures.get(i).area.x-agent.area.width/2,structures.get(i).area.y-agent.area.height/2,
						structures.get(i).area.width+agent.area.width,structures.get(i).area.height+agent.area.height);
				//System.out.println("MADE A RECTANGLE OF X: "+rec.x+"  Y: "+rec.y+"   width: "+rec.width+"   height: "+rec.height);
				obstacles.add(rec);
				nodeGraph.add(new DijkstraNode((float) (rec.x-0.0001),(float) (rec.y-0.0001)));
				nodeGraph.add(new DijkstraNode((float) (rec.x-0.0001),(float) (rec.y+rec.getHeight()+0.0001)));
				nodeGraph.add(new DijkstraNode((float) (rec.x+rec.getWidth()+0.0001),(float) (rec.y-0.0001)));
				nodeGraph.add(new DijkstraNode((float) (rec.x+rec.getWidth()+0.0001),(float) (rec.y+rec.getHeight()+0.0001)));
			} else if(structures.get(i) instanceof Structure) {
				Rectangle2D.Float rec = new Rectangle2D.Float(structures.get(i).area.x-agent.area.width/2,structures.get(i).area.y-agent.area.height/2,
						structures.get(i).area.width+agent.area.width,structures.get(i).area.height+agent.area.height);
				//System.out.println("MADE A RECTANGLE OF X: "+rec.x+"  Y: "+rec.y+"   width: "+rec.width+"   height: "+rec.height);
				obstacles.add(rec);
				nodeGraph.add(new DijkstraNode((float) (rec.x-0.0001),(float) (rec.y-0.0001)));
				nodeGraph.add(new DijkstraNode((float) (rec.x-0.0001),(float) (rec.y+rec.getHeight()+0.0001)));
				nodeGraph.add(new DijkstraNode((float) (rec.x+rec.getWidth()+0.0001),(float) (rec.y-0.0001)));
				nodeGraph.add(new DijkstraNode((float) (rec.x+rec.getWidth()+0.0001),(float) (rec.y+rec.getHeight()+0.0001)));
			}
		}
		nodeGraph.add(new DijkstraNode(goalx,goaly));
		for(int i=0; i<nodeGraph.size(); i++) {
			nodeGraph.get(i).score = 10000000f;
		}
		nodeGraph.get(0).score = 0;
		nodeGraph.get(0).setEntryAngle(agent.viewAngle.angle());
		nodeGraph.get(0).setBestPath(new ArrayList<DijkstraNode>());
		
		//set up edges
		ArrayList<DijkstraNode> fakenodeGraph = new ArrayList<DijkstraNode>();
		fakenodeGraph.addAll(nodeGraph);
		for(int y=0; y<nodeGraph.size(); y++) {
			for(int z=0; z<nodeGraph.size(); z++) {
				if(z!=y && fakenodeGraph.contains(nodeGraph.get(y)) && fakenodeGraph.contains(nodeGraph.get(z))) {
			        Line2D.Float line = new Line2D.Float(nodeGraph.get(z).x,nodeGraph.get(z).y,nodeGraph.get(y).x,nodeGraph.get(y).y);
			        boolean intersects = false;
			        for(int i=0; i<obstacles.size(); i++) {
			        	if(obstacles.get(i).intersectsLine(line)) {intersects = true;}
			        }
			        if(!intersects) {
			        	edgeGraph.add(new DijkstraEdge(nodeGraph.get(y),nodeGraph.get(z)));
//			        	nodeGraph.get(y).addNeighbour(edgeGraph.get(edgeGraph.size()-1));
//			        	nodeGraph.get(z).addNeighbour(edgeGraph.get(edgeGraph.size()-1));
			        }
				}
			}
			fakenodeGraph.remove(nodeGraph.get(y));
		}
		
		ArrayList<DijkstraNode> nodesToExamine = new ArrayList<DijkstraNode>();
		for(int a=0; a<nodeGraph.size(); a++) {
			nodesToExamine.add(nodeGraph.get(a));
		}
		
		DijkstraNode current = nodesToExamine.get(0);
		boolean foundGoal = false;
		while(!foundGoal) {
			int nextIndex = 0;
			float bestScore = 10000;
			//begin traversal
			for(int i=0; i<nodesToExamine.size(); i++) {
				if(nodesToExamine.get(i).score < bestScore) {
					nextIndex = i;
					bestScore = nodesToExamine.get(i).score;
				}
			}
			if(bestScore == 10000) {
				//System.out.println("ERROR: Unreachable target");
				ArrayList<Vector2> result = new ArrayList<Vector2>();
				result.add(new Vector2(agent.xCenter,agent.yCenter));
				return result;
			}
			if(nextIndex == nodesToExamine.size()-1) {foundGoal = true;}
			current = nodesToExamine.remove(nextIndex);
			//System.out.println("CHECKING INDEX: "+nextIndex+" for value: "+bestScore);
			examineNode(current,angleToScoreConversion);
		}
		ArrayList<Vector2> result = new ArrayList<Vector2>();
		for(int i=0; i<current.bestPathToHere.size(); i++) {
			result.add(new Vector2(current.bestPathToHere.get(i).x,current.bestPathToHere.get(i).y));
			//System.out.println("NODE NUMBER: "+i+"   "+"has score:  "+current.bestPathToHere.get(i).score);
		}
		//System.out.println("END NODE HAS SCORE: "+current.score);
		return result;
		
	}
	
	public void examineNode(DijkstraNode current, float angleToScoreConversion) {
		for(int i=0; i<current.neighbours.size(); i++) {
			float entryAngle = new Vector2(current.neighbours.get(i).x-current.x,current.neighbours.get(i).y-current.y).angle();
			float angleDifference = entryAngle-current.bestEntryAngle;
			if(angleDifference < 0) {
				angleDifference = angleDifference+360;
			}
			if(angleDifference >= 360) {
				angleDifference = angleDifference-360;
			}
			//System.out.println("added to weight for angle:  "+angleDifference*angleToScoreConversion);
			float adjlength = 10000;
			for(int j=0; j<current.adjacent.size(); j++) {
				if(current.adjacent.get(j).start.equals(current.neighbours.get(i)) || current.adjacent.get(j).end.equals(current.neighbours.get(i))) {
					adjlength = current.adjacent.get(j).length;
				}
			}
			//System.out.println("added to weight for distance:  "+adjlength);
			float neighbourScore = current.score+angleDifference*angleToScoreConversion+adjlength;
			if(neighbourScore < current.neighbours.get(i).score) {
				//System.out.println("better score for node:  "+current.neighbours.get(i).x+"  "+current.neighbours.get(i).y+"    from node: "+current.x+"  "+current.y+"     of scores: "+neighbourScore+"   ->  "+current.neighbours.get(i).score);
				current.neighbours.get(i).score = neighbourScore;
				current.neighbours.get(i).bestEntryAngle = entryAngle;
				current.neighbours.get(i).setBestPath(current.bestPathToHere);
			}
			current.neighbours.get(i).neighbours.remove(current);
		}
	}
	
	public float getSpeed() {
		if(!speeds.isEmpty()) {
			return speeds.pop();
		}
		return 0f;
		
	}
	
	public float getRotation() {
		if(!rotations.isEmpty()) {
			return rotations.pop();
		}
		return 0f;
		
	}
}
