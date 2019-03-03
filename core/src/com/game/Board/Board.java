/**
 * 
 */
package com.game.Board;

import java.util.ArrayList;


/**
 * @author Lukas Padolevicius
 *
 */
public class Board {
	
	private ArrayList<Integer>[][] positionTracker;
	private ArrayList<Area> territories;
	private ArrayList<Agent> agents;
	private int fps = 10;
	
	public Board() {
		territories = new ArrayList<Area>();
		agents = new ArrayList<Agent>();
		positionTracker = new ArrayList[40][40];
		for(int i=0; i<40; i++) {
			for(int j=0; j<40; j++) {
				positionTracker[i][j] = new ArrayList();
				
			}
		}
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
				range = 3;
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
			
			//check collision with all nearby structures
			boolean collided = false;
			for(int i=0; i<positionTracker[x][y].size(); i++) {
				if(territories.get(positionTracker[x][y].get(i)).contains(agents.get(a).getX(),agents.get(a).getY())) {collided = true;}
			}
			
			//move the agent if it's not colliding
			if(!collided) {
				float speed = agents.get(a).getSpeed()/fps;
				double angle = agents.get(a).getAngle();
				System.out.println("position of agent "+a+" was: "+agents.get(a).getX()+" ; "+agents.get(a).getY()+"  with angle: "+angle+"  and speed: "+speed);

				float newX = agents.get(a).getX()+(float) Math.cos(angle)*speed;
				float newY = agents.get(a).getY()+(float) Math.sin(angle)*speed;
				agents.get(a).setPos(newX,newY);
				System.out.println("position of agent "+a+": "+newX+" ; "+newY);
				agents.get(a).triggerStep();
			}
			
			
			
		}		
	}

}
