/**
 * 
 */
package com.game.Board;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.math.Rectangle;


/**
 * @author Lukas Padolevicius
 *
 */
public class Board {
	
	private ArrayList<Integer>[][] positionTracker;
	private ArrayList<Area> territories;
	private ArrayList<Agent> agents;
	private int fps = 10;
	private final int VISUAL_RANGE = 20;
	
	public Board() {
		territories = new ArrayList<Area>();
		agents = new ArrayList<Agent>();
		positionTracker = new ArrayList[40][40];
		for(int i=0; i<40; i++) {
			for(int j=0; j<40; j++) {
				positionTracker[i][j] = new ArrayList();
				
			}
		}
		generateSounds();
	}
	
	
	public void setUp(ArrayList<Area> entities) {
		
		territories = entities;
		
		//set entity references in the appropriate squares
		for(int i=0; i<entities.size(); i++) {
			
			//define the range of the structure
			int minX = (int) entities.get(i).getMinX()/5;
			int maxX = (int) entities.get(i).getMaxX()/5;
			int minY = (int) entities.get(i).getMinY()/5;
			int maxY = (int) entities.get(i).getMaxY()/5;
			
			//extend presence depending on the structure type
			int range = 2;
			if(entities.get(i) instanceof SentryTower) {
				range = 4;
			}
			
			//mark the presence of the structures in the tracker
			for(int a=minX-range; a<maxX+range+1; a++) {
				for(int b=minY-range; b<maxY+range+1; b++) {
					if(a>=0 && a<40 && b>=0 && b<40) {
						positionTracker[a][b].add(i);
					}
				}
			}
		}
	}
		
	public ArrayList[][] getTrackerBoard() {
		
		System.out.println(positionTracker.length);
		return positionTracker;
	}
	
	public void putInAgents(ArrayList<Agent> ags) {
		
		for(int a=0; a<ags.size(); a++) {
			agents.add(ags.get(a));
		}
	}
	
	public void updateAgents() {
				
		//update every agent's x,y coordinates and rotate his view angle
		for(int a=0; a<agents.size(); a++) {
			
			//find agent's array cell in positionTracker
			int x = (int) agents.get(a).getX()/5;
			int y = (int) agents.get(a).getY()/5;
			
			//update angle
			float rot = agents.get(a).getRotation();
			agents.get(a).rotate(rot/fps);
			
			
			float speed = agents.get(a).getSpeed()/fps;
			double angle = (double) agents.get(a).getAngleRad();
			float newX = agents.get(a).getX()+(float) Math.cos(angle)*speed;
			float newY = agents.get(a).getY()+(float) Math.sin(angle)*speed;
			System.out.println("projected x: "+newX+"  y: "+newY);
			
			//check collision with all nearby structures
			boolean collided = false;
			
			System.out.println("x: "+x+"  y: "+y);
			System.out.println("agentX: "+agents.get(a).area.getX()+"  agentY: "+agents.get(a).area.getX());
			System.out.println("agentX: "+agents.get(a).area.getWidth()+"  agentY: "+agents.get(a).area.getHeight());
			
			for(int i=0; i<positionTracker[x][y].size(); i++) {
				
				System.out.println("agentX: "+territories.get(positionTracker[x][y].get(i)).getMinX()+"  agentY: "+territories.get(positionTracker[x][y].get(i)).getMinY()+
						"strX: "+territories.get(positionTracker[x][y].get(i)).getMaxX()+"  strY: "+territories.get(positionTracker[x][y].get(i)).getMaxY());
				System.out.print(territories.get(positionTracker[x][y].get(i)).intersects(agents.get(a).area));
				Rectangle projected = new Rectangle(newX,newY,agents.get(a).area.width,agents.get(a).area.height);
				if(territories.get(positionTracker[x][y].get(i)).intersects(projected)) {collided = true;
				System.out.println("collided");}

				/*
				if(territories.get(positionTracker[x][y].get(i)).contains(newX,newY)
						|| territories.get(positionTracker[x][y].get(i)).contains(newX,newY+30)
						|| territories.get(positionTracker[x][y].get(i)).contains(newX+30,newY)
						|| territories.get(positionTracker[x][y].get(i)).contains(newX+30,newY+30)) {collided = true;
				System.out.println("collided");}
				*/
			}
			
			//move the agent if it's not colliding
			if(!collided) {
				
				//System.out.println("compare x: "+agents.get(a).getX()+" ; "+newX);
				//System.out.println("compare y: "+agents.get(a).getY()+" ; "+newY);
				agents.get(a).setPos(newX,newY);
				System.out.println("position of agent "+a+": "+newX+" ; "+newY);
				System.out.println("position tracker of size: "+positionTracker[x][y].size());
				//System.out.println("position of agent becomes: "+a+" was: "+agents.get(a).getX()+" ; "+agents.get(a).getY()+"  with angle: "+agents.get(a).getAngle()+"  and speed: "+agents.get(a).getSpeed());
			}
			System.out.println("position of agent was: "+a+" was: "+agents.get(a).getX()+" ; "+agents.get(a).getY()+"  with angle: "+agents.get(a).getAngleRad()+"  and speed: "+agents.get(a).getSpeed());
			agents.get(a).triggerStep();
			System.out.println("position of agent becomes: "+a+" was: "+agents.get(a).getX()+" ; "+agents.get(a).getY()+"  with angle: "+agents.get(a).getAngleRad()+"  and speed: "+agents.get(a).getSpeed());

			
			
			
		}		
	}

	public void generateSounds() {
		for (int i = 0; i < positionTracker.length; i++) {
			for (int j = 0; j < positionTracker[0].length; j++) {
				for (int k = 0; k < 25; k++) {
					Random rand = new Random();
					if (rand.nextDouble() < 0.1) {
						//are we using xpos and ypos or positiontracker to store coordinates?
						//SoundOccurence s = new SoundOccurence(System.currentTimeMillis(), positionTracker[i][j], k);
						double xpos = i * 200;
						double ypos = j* 200;
						SoundOccurence s = new SoundOccurence(System.currentTimeMillis(), xpos, ypos);
						System.out.println("Sound generated in square " + i + " " +  j + " in cell " + k);
						checkIfAgentHears(s);
					}
				}
			}
		}
	}

	public void checkIfAgentHears(SoundOccurence s) {
		for (Agent a : agents) {
			//check if distance between sound and agent is within the sound range
			if (Math.sqrt((s.xpos - a.getX()) * (s.xpos - a.getX()) + (s.ypos - a.getY()) * (s.ypos - a.getY())) < s.SOUND_RANGE) {
				a.setLastHeardSound(s);
			}
		}
	}

	public void checkIfAgentSees(){
		for(Agent a : agents){
			for(Area t: territories){
				//TODO change distance calculation so that it takes every point of the terrotitory in to account
				if 		((Math.sqrt((t.getMinX() - a.getX()) * (t.getMinX() - a.getX()) + (t.getMinY() - a.getY()) * (t.getMinY() - a.getY())) < VISUAL_RANGE) ||
						(Math.sqrt((t.getMaxX() - a.getX()) * (t.getMaxX() - a.getX()) + (t.getMinY() - a.getY()) * (t.getMinY() - a.getY()))   < VISUAL_RANGE) ||
						(Math.sqrt((t.getMinX() - a.getX()) * (t.getMinX() - a.getX()) + (t.getMaxY() - a.getY()) * (t.getMaxY() - a.getY()))   < VISUAL_RANGE) ||
						(Math.sqrt((t.getMaxX() - a.getX()) * (t.getMaxX() - a.getX()) + (t.getMaxY() - a.getY()) * (t.getMaxY() - a.getY()))   < VISUAL_RANGE)){
					a.see(t);
				}

			}
		}
	}

}

