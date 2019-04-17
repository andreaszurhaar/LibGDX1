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
		
		for(int a=0; a<ags.size(); a++) {
			agents.add(ags.get(a));
		}
	}
	
	public void updateAgents() {
		/*
		Vector2 test = new Vector2(20,30);
		Vector2 testi = new Vector2(test);
		Vector2 test2 = new Vector2(-10,10);
		Vector2 testf = testi.add(test2);
		
		Rectangle re = new Rectangle(10,35,20,20);
		System.out.println(intersector.intersectLinePolygon(test,testf,rectToPoly(re)));

		System.out.println(test.x);
		System.out.println(test.y);
		System.out.println(testi.x);
		System.out.println(testi.y);
		System.out.println(testf.x);
		System.out.println(testf.y);
		System.exit(0);
			*/	
		//update every agent's x,y coordinates and rotate his view angle
		for(int a=0; a<agents.size(); a++) {
			//check collision with all nearby structures
			boolean collided = false;
			
			//update angle
			float rot = agents.get(a).getRotation();
			agents.get(a).rotate(rot/fps);
			
			
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
			if(!updateAgentMoveToPoint) agents.get(a).triggerStep();
			else agents.get(a).triggerStepTowardPoint(currentPoint);

		}
		
		for(int a=0; a<agents.size(); a++) {
						
			
			/*
	        //find agent's array cell in positionTracker
	        int x = (int) agents.get(a).getX()/5;
	        int y = (int) agents.get(a).getY()/5;
	        int endX = (int) (agents.get(a).getX()+agents.get(a).area.width)/5;
	        int endY = (int) (agents.get(a).getY()+agents.get(a).area.height)/5;
	        //System.out.println(x+"   "+y+"   "+endX+"   "+endY);
	        
	        x=Math.max(x-6,0);
	        y=Math.max(y-6,0);
	        endX=Math.min(endX+6,BOARD_WIDTH-1);
	        endY=Math.min(endY+6,BOARD_HEIGHT-1);
	        */
	        
	        
	        ArrayList<Integer> sub = new ArrayList<Integer>();
	        
	        for(int m=0; m<territories.size(); m++) {
	        	sub.add(m);
	        }
	        
	        /*
	        ArrayList<Integer> sub2 = positionTracker[endX][y];
	        ArrayList<Integer> sub3 = positionTracker[x][endY];
	        ArrayList<Integer> sub4 = positionTracker[endX][endY];
	        for(int u=0; u<sub2.size(); u++) {
	        	boolean cont = false;
	        	for(int o=0; o<sub.size(); o++) {
	        		if(sub.get(o) == sub2.get(u)) {cont = true;}
	        	}
	        	if(!cont) {sub.add(sub2.get(u));}
	        }
	        
	        for(int u=0; u<sub3.size(); u++) {
	        	boolean cont = false;
	        	for(int o=0; o<sub.size(); o++) {
	        		if(sub.get(o) == sub3.get(u)) {cont = true;}
	        	}
	        	if(!cont) {sub.add(sub3.get(u));}
	        }
	        
	        for(int u=0; u<sub4.size(); u++) {
	        	boolean cont = false;
	        	for(int o=0; o<sub.size(); o++) {
	        		if(sub.get(o) == sub4.get(u)) {cont = true;}
	        	}
	        	if(!cont) {sub.add(sub4.get(u));}
	        }
	        
	        */
	        
	        
	        //look for agents seeing agents
	        for(int i=0; i<agents.size(); i++) {
				if(i!=a) {
					//System.out.println("agent at x: "+agents.get(a).xCenter+"  y: "+agents.get(a).yCenter+"   saw this: "+i);
					float range = agents.get(a).viewRange;
					Vector2 dis = distPointToRect(agents.get(a).xCenter,agents.get(a).yCenter,agents.get(i).area);
					Vector2 vec = new Vector2(agents.get(a).viewAngle);
					vec.scl(range);
					//System.out.println(vec.len()+"    length   x: "+vec.x+"  y: "+vec.y);
					//System.out.println(dis.len()+"    lengthDist x: "+dis.x+"  y: "+dis.y);
					Vector2 pos = new Vector2(agents.get(a).xCenter,agents.get(a).yCenter);
					Vector2 fullVec = (new Vector2(pos)).add(vec);
					//System.out.println(fullVec.len()+"    lengthfull x: "+fullVec.x+"  y: "+fullVec.y);
	
					if(dis.len() < range) {
						//intersector.intersectLinePolygon();
						
						//System.out.println("agent "+a+"  sawwwwwwwwwwwwwwwwwwwwww this: "+i);
						Vector2 rightVec = new Vector2(vec);
						rightVec.rotate(agents.get(a).viewRadius/2);
						Vector2 leftVec = new Vector2(vec);
						leftVec.rotate(-agents.get(a).viewRadius/2);
						Vector2 fullRightVec = (new Vector2(pos)).add(rightVec);
						Vector2 fullLeftVec = (new Vector2(pos)).add(leftVec);
						//System.out.println(rightVec.len()+"    lengthright   x: "+rightVec.x+"  y: "+rightVec.y);
						//System.out.println(leftVec.len()+"    lengthleft   x: "+leftVec.x+"  y: "+leftVec.y);
						//System.out.println(fullRightVec.len()+"    lengthfullRigh   x: "+fullRightVec.x+"  y: "+fullRightVec.y);
						//System.out.println(fullLeftVec.len()+"    lengthfullLeft  x: "+fullLeftVec.x+"  y: "+fullLeftVec.y);
	
						/*
						if(Math.abs(vec.angle(agents.get(a).viewAngle)) < agents.get(a).viewRadius/2 
								|| agents.get(i).contains(agents.get(a).xCenter+rightVec.x*vec.len(), agents.get(a).xCenter+rightVec.y*vec.len())
								|| agents.get(i).contains(agents.get(a).yCenter+leftVec.x*vec.len(), agents.get(a).yCenter+leftVec.y*vec.len())) {
							*/
						
						/*
						Polygon poly = rectToPoly(agents.get(i).area);
						if(intersector.intersectLinePolygon(pos,fullVec,poly)
								|| intersector.intersectLinePolygon(pos,fullRightVec,poly)
								|| intersector.intersectLinePolygon(pos,fullLeftVec,poly)
								|| intersector.intersectLinePolygon(fullVec,fullRightVec,poly)
								|| intersector.intersectLinePolygon(fullVec,fullLeftVec,poly)) {
						*/
						if(intersectVectAndRect(vec,agents.get(i).area,pos.x,pos.y)
								|| intersectVectAndRect(leftVec,agents.get(i).area,pos.x,pos.y)
								|| intersectVectAndRect(rightVec,agents.get(i).area,pos.x,pos.y)
								|| intersectVectAndRect(new Vector2(vec.x-leftVec.x,vec.y-leftVec.y),agents.get(i).area,pos.x,pos.y)
								|| intersectVectAndRect(new Vector2(vec.x-rightVec.x,vec.y-rightVec.y),agents.get(i).area,pos.x,pos.y)) {
							agents.get(a).see(agents.get(i));
							/*
							System.out.println();
							System.out.println("  position = "+agents.get(i).xCenter+"    y: "+agents.get(i).yCenter);
							System.out.println("  position = "+agents.get(a).xCenter+"    y: "+agents.get(a).yCenter);
							System.out.println(fullVec.len()+"    full x: "+fullVec.x+"  y: "+fullVec.y);
							System.out.println(vec.len()+"    length   x: "+vec.x+"  y: "+vec.y);
							System.out.println(dis.len()+"    Dist x: "+dis.x+"  y: "+dis.y);
							System.out.println(rightVec.len()+"    right   x: "+rightVec.x+"  y: "+rightVec.y);
							System.out.println(leftVec.len()+"    left   x: "+leftVec.x+"  y: "+leftVec.y);
							System.out.println(fullRightVec.len()+"    fullRight   x: "+fullRightVec.x+"  y: "+fullRightVec.y);
							System.out.println(fullLeftVec.len()+"    fullLeft  x: "+fullLeftVec.x+"  y: "+fullLeftVec.y);
							System.out.println("go from: "+pos.x+"  to: "+fullVec.x+"    from: "+pos.y+"  to: "+fullVec.y);
							Rectangle roi = poly.getBoundingRectangle();
							System.out.println(" polygon of :"+i+"  x: "+roi.x+"  y: "+roi.y+"   wid: "+roi.width+"  hei: "+roi.height);
							System.out.println("go from: "+pos.x+"  to: "+fullVec.x+"    from: "+pos.y+"  to: "+fullVec.y);
							System.out.println("its: center "+intersector.intersectLinePolygon(pos,fullVec,poly)+" right "+intersector.intersectLinePolygon(pos,fullRightVec,poly)
							+" left "+intersector.intersectLinePolygon(pos,fullLeftVec,poly)+" rightForw "+intersector.intersectLinePolygon(fullVec,fullRightVec,poly)+" leftForw "+intersector.intersectLinePolygon(fullVec,fullLeftVec,poly));
							*/
						}
					}
		        }
	        }
				
				//repeat for agents seeing structures
				for(int i=0; i<sub.size(); i++) {
					
					//System.out.println("agent at x: "+agents.get(a).xCenter+"  y: "+agents.get(a).yCenter+"   saw this: "+i);
					float range = Math.max(agents.get(a).viewRange, territories.get(sub.get(i)).viewDistance);
					Vector2 dis = distPointToRect(agents.get(a).xCenter,agents.get(a).yCenter,territories.get(sub.get(i)).area);
					Vector2 vec = new Vector2(agents.get(a).viewAngle);
					vec.scl(range);
										Vector2 pos = new Vector2(agents.get(a).xCenter,agents.get(a).yCenter);
					Vector2 fullVec = (new Vector2(pos)).add(vec);

					if(dis.len() < range*4) {
						//intersector.intersectLinePolygon();
						
						//System.out.println("agent "+a+"  sawwwwwwwwwwwwwwwwwwwwww this: "+i);
						Vector2 rightVec = new Vector2(vec);
						rightVec.rotate(agents.get(a).viewRadius/2);
						Vector2 leftVec = new Vector2(vec);
						leftVec.rotate(-agents.get(a).viewRadius/2);
						Vector2 fullRightVec = (new Vector2(pos)).add(rightVec);
						Vector2 fullLeftVec = (new Vector2(pos)).add(leftVec);
						
						/*
						if(Math.abs(vec.angle(agents.get(a).viewAngle)) < agents.get(a).viewRadius/2 
								|| territories.get(sub.get(i)).contains(agents.get(a).xCenter+rightVec.x*vec.len(), agents.get(a).xCenter+rightVec.y*vec.len())
								|| territories.get(sub.get(i)).contains(agents.get(a).yCenter+leftVec.x*vec.len(), agents.get(a).yCenter+leftVec.y*vec.len())) {
							*/
						Rectangle poly = territories.get(sub.get(i)).area;
						if(intersectVectAndRect(vec,poly,pos.x,pos.y)
								|| intersectVectAndRect(leftVec,poly,pos.x,pos.y)
								|| intersectVectAndRect(rightVec,poly,pos.x,pos.y)
								|| intersectVectAndRect(new Vector2(vec.x-leftVec.x,vec.y-leftVec.y),poly,pos.x,pos.y)
								|| intersectVectAndRect(new Vector2(vec.x-rightVec.x,vec.y-rightVec.y),poly,pos.x,pos.y)) {
							agents.get(a).see(territories.get(sub.get(i)));
						}
						/*
						if(intersector.intersectLinePolygon(pos,fullVec,poly)
								|| intersector.intersectLinePolygon(pos,fullRightVec,poly)
								|| intersector.intersectLinePolygon(pos,fullLeftVec,poly)
								|| intersector.intersectLinePolygon(fullVec,fullRightVec,poly)
								|| intersector.intersectLinePolygon(fullVec,fullLeftVec,poly)) {
							agents.get(a).see(territories.get(sub.get(i)));
							//Rectangle are = territories.get(sub.get(i)).area;
							//System.out.println("  rect number: "+i+"  x: "+are.x+"  y: "+are.y+"  width: "+are.width+"  height: "+are.height);
						}
						*/
					}
			}
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