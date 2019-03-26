/**
 * 
 */
package com.game.Board;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


/**
 * @author Lukas Padolevicius
 *
 */
public class Board {
	
	private ArrayList<Integer>[][] positionTracker;
	private ArrayList<Area> territories;
	private ArrayList<Agent> agents;
	private final static int fps = 10;
	private final int VISUAL_RANGE = 20;
	private final static int BOARD_WIDTH = 200;
	private final static int BOARD_HEIGHT = 100;
	
	public Board() {
		territories = new ArrayList<Area>();
		agents = new ArrayList<Agent>();
		positionTracker = new ArrayList[BOARD_WIDTH][BOARD_HEIGHT];
		for(int i=0; i<BOARD_WIDTH; i++) {
			for(int j=0; j<BOARD_HEIGHT; j++) {
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
					if(a>=0 && a<BOARD_WIDTH && b>=0 && b<BOARD_HEIGHT) {
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
			//check collision with all nearby structures
			boolean collided = false;
			
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
			//System.out.println("projected x: "+newX+"  y: "+newY);
			
			//System.out.println("x: "+x+"  y: "+y);
			//System.out.println("agentX: "+agents.get(a).area.getX()+"  agentY: "+agents.get(a).area.getX());
			//System.out.println("agentX: "+agents.get(a).area.getWidth()+"  agentY: "+agents.get(a).area.getHeight());
					
			for(int o=0; o<6; o++) {	
				for(int p=0; p<6; p++) {
					for(int i=0; i<positionTracker[x+o][y+p].size(); i++) {
						
						//System.out.println("agentX: "+territories.get(positionTracker[x][y].get(i)).getMinX()+"  agentY: "+territories.get(positionTracker[x][y].get(i)).getMinY()+
							//	"strX: "+territories.get(positionTracker[x][y].get(i)).getMaxX()+"  strY: "+territories.get(positionTracker[x][y].get(i)).getMaxY());
						//System.out.print(territories.get(positionTracker[x][y].get(i)).intersects(agents.get(a).area));
						Rectangle projected = new Rectangle(newX,newY,agents.get(a).area.width,agents.get(a).area.height);
						if(territories.get(positionTracker[x+o][y+p].get(i)).intersects(projected)) {collided = true;
						System.out.println("collided");}
		
						/*
						if(territories.get(positionTracker[x][y].get(i)).contains(newX,newY)
								|| territories.get(positionTracker[x][y].get(i)).contains(newX,newY+30)
								|| territories.get(positionTracker[x][y].get(i)).contains(newX+30,newY)
								|| territories.get(positionTracker[x][y].get(i)).contains(newX+30,newY+30)) {collided = true;
						System.out.println("collided");}
						*/
					}
					for(int i=0; i<agents.size(); i++) {
						if(a==i) { 
						} else {
							Rectangle projected = new Rectangle(newX,newY,agents.get(a).area.width,agents.get(a).area.height);
							if(agents.get(i).intersects(projected)) {collided = true;}
						}
						
					}
				}
				
			}
					
			//move the agent if it's not colliding
			if(!collided) {
				
				//System.out.println("compare x: "+agents.get(a).getX()+" ; "+newX);
				//System.out.println("compare y: "+agents.get(a).getY()+" ; "+newY);
				agents.get(a).setPos(newX,newY);
				//System.out.println("position of agent "+a+": "+newX+" ; "+newY);
				//System.out.println("position tracker of size: "+positionTracker[x][y].size());
				//System.out.println("position of agent becomes: "+a+" was: "+agents.get(a).getX()+" ; "+agents.get(a).getY()+"  with angle: "+agents.get(a).getAngle()+"  and speed: "+agents.get(a).getSpeed());
			}
			//System.out.println("position of agent was: "+a+" was: "+agents.get(a).getX()+" ; "+agents.get(a).getY()+"  with angle: "+agents.get(a).getAngleRad()+"  and speed: "+agents.get(a).getSpeed());
			agents.get(a).triggerStep();
			//System.out.println("position of agent becomes: "+a+" was: "+agents.get(a).getX()+" ; "+agents.get(a).getY()+"  with angle: "+agents.get(a).getAngleRad()+"  and speed: "+agents.get(a).getSpeed());	
		}
		generateSounds();
		
	}

	public void generateSounds() {
		for (int i = 0; i < positionTracker.length; i++) {
			for (int j = 0; j < positionTracker[0].length; j++) {
				//for (int k = 0; k < 25; k++) {
					Random rand = new Random();
					if (rand.nextDouble() < 0.1/(fps*60)) {
						//are we using xpos and ypos or positiontracker to store coordinates?
						//SoundOccurence s = new SoundOccurence(System.currentTimeMillis(), positionTracker[i][j], k);
						float xpos = (float) ((i* BOARD_WIDTH/5)+Math.random()*5);
						float ypos = (float) ((j* BOARD_HEIGHT/5)+Math.random()*5);
						SoundOccurence s = new SoundOccurence(System.currentTimeMillis(), xpos, ypos);
						//System.out.println("Sound generated in square " + i + " " +  j + " in cell " + k);
						checkIfAgentHears(s);
					}
				//}
			}
		}
		//check for agents hearing agents
		for(int i=0; i<agents.size(); i++) {
			SoundOccurence s = new SoundOccurence(System.currentTimeMillis(),agents.get(i).xCenter,agents.get(i).yCenter);
			for(int j=0; j<agents.size(); j++) {
				if(i!=j) {
					Agent a = agents.get(j);
					//check if distance between sound and agent is within the sound range
					if (distPointToRect(s.xpos,s.ypos,a.area) < s.soundRange+20) {
						a.hearSound(estimateDirection(s,a.xCenter,a.yCenter));
						System.out.println("heard sound between: "+i+"  and "+j+"   "+Math.random());
					}
				}
			}
		}
	}

	public void checkIfAgentHears(SoundOccurence s) {
		for (Agent a : agents) {
			//check if distance between sound and agent is within the sound range
			if (distPointToRect(s.xpos,s.ypos,a.area) < s.soundRange) {
				a.hearSound(estimateDirection(s,a.getX(),a.getY()));
				//System.out.println("heard sound");
			}
		}
	}

	public float estimateDirection(SoundOccurence s, float xPos, float yPos) {
		//TODO this is where we would approximate the direction using
		//a normal distributed uncertainty with a standard deviation of 10
		//currently the direction is found perfectly
		Vector2 vector = new Vector2(s.xpos-xPos,s.ypos-yPos);
		return vector.angle();
		
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
	
	/**simplified method for distance between a point outside a rectangle and the rectangle
	 */
	public float distPointToRect(float xPos, float yPos, Rectangle rect) {
		float x = xPos;
		float y = yPos;
		if(x<rect.x) {x=rect.x;}
		else if(x>rect.x+rect.width) {x=rect.x+rect.width;}
		if(y<rect.y) {y=rect.y;}
		else if(y>rect.y+rect.height) {y=rect.y+rect.height;}
		
		Vector2 v = new Vector2(x-xPos,y-yPos);
		return v.len();
	}

}

