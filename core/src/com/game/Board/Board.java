/**
 * 
 */
package com.game.Board;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

/**
 * @author Lukas Padolevicius
 *
 */
public class Board {
	
	private ArrayList<Integer>[][] positionTracker;
	private ArrayList<Area> territories;
	private ArrayList<Agent> agents;
	public final static int fps = 60;
	private final int VISUAL_RANGE = 20;
	public final static int BOARD_WIDTH = 80;
	public final static int BOARD_HEIGHT = 40;
	private Intersector intersector;
	private boolean updateAgentMoveToPoint;
	private Point2D currentPoint;
	
	public Board() {
		intersector = new Intersector();
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
		
		//System.out.println(positionTracker.length);
		return positionTracker;
	}
	
	public void putInAgents(ArrayList<Agent> ags) {
		int counter = 0;
		for(int a=0; a<ags.size(); a++) {
			System.out.println("agents ai in board " + ags.get(a).ai);
			agents.add(ags.get(a));
			System.out.println("agents ai after adding to board " + agents.get(counter).ai);
			counter++;
		}
	}
	
	public void updateAgents() {
		
		//update every agent's x,y coordinates and rotate his view angle
		for(int a=0; a<agents.size(); a++) {
			//check collision with all nearby structures
			boolean collided = false;
			
			//update angle
			float rot = agents.get(a).getRotation();
			agents.get(a).rotate(rot/fps);
			System.out.println("Agent " + agents.get(a) + " has AI: " + agents.get(a).ai);
			
			
			float speed = agents.get(a).getSpeed()/fps;
			double angle = (double) agents.get(a).getAngleRad();
			float newX = agents.get(a).getX()+(float) Math.cos(angle)*speed;
			float newY = agents.get(a).getY()+(float) Math.sin(angle)*speed;
				
			for(int i=0; i<territories.size(); i++) {
				
				Rectangle projected = new Rectangle(newX,newY,agents.get(a).area.width,agents.get(a).area.height);
				if(territories.get(i).intersects(projected)) {collided = true;}
				//System.out.println("collided");}
			}
			for(int i=0; i<agents.size(); i++) {
				if(a!=i) {
					Rectangle projected = new Rectangle(newX,newY,agents.get(a).area.width,agents.get(a).area.height);
					if(agents.get(i).intersects(projected)) {collided = true;}
				}
				
			}
			
			//move the agent if it's not colliding
			if(!collided) {
				agents.get(a).setPos(newX,newY);

			}
			if(!updateAgentMoveToPoint) {
				agents.get(a).triggerStep();
			}
			else {
				agents.get(a).triggerStepTowardPoint(agents.get(a).getDestPoint());
			}

		}
		
		for(int a=0; a<agents.size(); a++) {
			
	        ArrayList<Integer> sub = new ArrayList<Integer>();
	        
	        for(int m=0; m<territories.size(); m++) {
	        	sub.add(m);
	        }
	        
	        //look for agents seeing agents
	        for(int i=0; i<agents.size(); i++) {
				if(i!=a) {
					float range = agents.get(a).viewRange;
					Vector2 dis = distPointToRect(agents.get(a).xCenter,agents.get(a).yCenter,agents.get(i).area);
					Vector2 vec = new Vector2(agents.get(a).viewAngle);
					vec.scl(range);
					Vector2 pos = new Vector2(agents.get(a).xCenter,agents.get(a).yCenter);
					Vector2 fullVec = (new Vector2(pos)).add(vec);
	
					if(dis.len() < range) {
						Vector2 rightVec = new Vector2(vec);
						rightVec.rotate(agents.get(a).viewRadius/2);
						Vector2 leftVec = new Vector2(vec);
						leftVec.rotate(-agents.get(a).viewRadius/2);
						Vector2 fullRightVec = (new Vector2(pos)).add(rightVec);
						Vector2 fullLeftVec = (new Vector2(pos)).add(leftVec);
						if(intersectVectAndRect(vec,agents.get(i).area,pos.x,pos.y)
								|| intersectVectAndRect(leftVec,agents.get(i).area,pos.x,pos.y)
								|| intersectVectAndRect(rightVec,agents.get(i).area,pos.x,pos.y)
								|| intersectVectAndRect(new Vector2(vec.x-leftVec.x,vec.y-leftVec.y),agents.get(i).area,pos.x,pos.y)
								|| intersectVectAndRect(new Vector2(vec.x-rightVec.x,vec.y-rightVec.y),agents.get(i).area,pos.x,pos.y)) {
							agents.get(a).see(agents.get(i));
						}
					}
		        }
	        }
			
			//repeat for agents seeing structures
			for(int i=0; i<sub.size(); i++) {
				
				float range = Math.max(agents.get(a).viewRange, territories.get(sub.get(i)).viewDistance);
				Vector2 dis = distPointToRect(agents.get(a).xCenter,agents.get(a).yCenter,territories.get(sub.get(i)).area);
				Vector2 vec = new Vector2(agents.get(a).viewAngle);
				vec.scl(range);
				Vector2 pos = new Vector2(agents.get(a).xCenter,agents.get(a).yCenter);
				Vector2 fullVec = (new Vector2(pos)).add(vec);

				if(dis.len() < range*4) {
					Vector2 rightVec = new Vector2(vec);
					rightVec.rotate(agents.get(a).viewRadius/2);
					Vector2 leftVec = new Vector2(vec);
					leftVec.rotate(-agents.get(a).viewRadius/2);
					Vector2 fullRightVec = (new Vector2(pos)).add(rightVec);
					Vector2 fullLeftVec = (new Vector2(pos)).add(leftVec);
					Rectangle poly = territories.get(sub.get(i)).area;
					if(intersectVectAndRect(vec,poly,pos.x,pos.y)
							|| intersectVectAndRect(leftVec,poly,pos.x,pos.y)
							|| intersectVectAndRect(rightVec,poly,pos.x,pos.y)
							|| intersectVectAndRect(new Vector2(vec.x-leftVec.x,vec.y-leftVec.y),poly,pos.x,pos.y)
							|| intersectVectAndRect(new Vector2(vec.x-rightVec.x,vec.y-rightVec.y),poly,pos.x,pos.y)) {
						agents.get(a).see(territories.get(sub.get(i)));

					}
				}
			}
		}
		
		generateSounds();
		
	}

	public void generateSounds() {
		for (int i = 0; i < positionTracker.length; i++) {
			for (int j = 0; j < positionTracker[0].length; j++) {
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
			}
		}
		//check for agents hearing agents
		for(int i=0; i<agents.size(); i++) {
			SoundOccurence s = new SoundOccurence(System.currentTimeMillis(),agents.get(i).xCenter,agents.get(i).yCenter);
			for(int j=0; j<agents.size(); j++) {
				if(i!=j) {
					Agent a = agents.get(j);
					//check if distance between sound and agent is within the sound range
					if (distPointToRect(s.xpos,s.ypos,a.area).len() < s.soundRange) {
						a.hearSound(estimateDirection(s,a.xCenter,a.yCenter));
						//System.out.println("heard sound between: "+i+"  and "+j+"   "+Math.random());
					}
				}
			}
		}
	}

	public void checkIfAgentHears(SoundOccurence s) {
		for (Agent a : agents) {
			//check if distance between sound and agent is within the sound range
			if (distPointToRect(s.xpos,s.ypos,a.area).len() < s.soundRange) {
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
	
	public Polygon rectToPoly(Rectangle rect)  {
        float[] f1 = {rect.x, rect.y,rect.x + rect.width, rect.y,rect.x + rect.width, rect.y + rect.height,rect.x, rect.y + rect.height};
        Polygon poly = new Polygon(f1);
        return poly;
    }
	
	/**simplified method for distance between a point outside a rectangle and the rectangle
	 */
	public Vector2 distPointToRect(float xPos, float yPos, Rectangle rect) {
		float x = xPos;
		float y = yPos;
		if(x<rect.x) {x=rect.x;}
		else if(x>rect.x+rect.width) {x=rect.x+rect.width;}
		if(y<rect.y) {y=rect.y;}
		else if(y>rect.y+rect.height) {y=rect.y+rect.height;}
		
		Vector2 v = new Vector2(x-xPos,y-yPos);
		return v;
	}
	
	public boolean intersectVectAndRect(Vector2 vector, Rectangle rect, float x, float y) {
		boolean contains = false;
		float xP = vector.x/100;
		float yP = vector.y/100;
		for(int i=0; i<100; i++) {
			if(rect.contains(x+i*xP,y+i*yP)) {contains = true;}
		}
		return contains;
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setUpdateAgentMoveToPoint(boolean updateAgentMoveToPoint) {
		this.updateAgentMoveToPoint = updateAgentMoveToPoint;
	}

	public void setCurrentPoint(Point2D currentPoint) {
		this.currentPoint = currentPoint;
	}
}