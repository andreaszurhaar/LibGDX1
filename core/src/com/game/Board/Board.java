/**
 * Primary logic handling class for simulation of movement and handling of map element attributes
 */
package com.game.Board;

import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AI.Tracking;
import com.game.States.MainState;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

/**
 * @author Lukas Padolevicius
 *
 */
public class Board {
	
	private ArrayList<Integer>[][] positionTracker;
	public ArrayList<Area> territories;
	private ArrayList<Agent> agents;
	public final static int fps = 60;
	private final int VISUAL_RANGE = 20;
	public final static int BOARD_WIDTH = 80;
	public final static int BOARD_HEIGHT = 40;
	private Intersector intersector;
	private Point2D currentPoint;
	public boolean gameOver = false;
	private final int RANDOM_NOISE_SOUND_RANGE = 5;
	public long victoryTime1 = 0;
	public long timeOfTracking = 0;
	public boolean predictiveTracking = true;

	private Random rand = new Random();

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
		return positionTracker;
	}
	
	public void putInAgents(ArrayList<Agent> ags) {
		int counter = 0;
		for(int a=0; a<ags.size(); a++) {
			agents.add(ags.get(a));
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
			
			float speed = agents.get(a).getSpeed()/fps;
			double angle = (double) agents.get(a).getAngleRad();

			float newX = agents.get(a).getX()+(float) Math.cos(angle)*speed;
			float newY = agents.get(a).getY()+(float) Math.sin(angle)*speed;
				
			for(int i=0; i<territories.size(); i++) {
				Rectangle projected = new Rectangle(newX,newY,agents.get(a).area.width,agents.get(a).area.height);
				if(territories.get(i).intersects(projected)) {
					if (territories.get(i) instanceof Structure) {
						Structure struct = (Structure) territories.get(i);
						ArrayList<Area> daw = struct.doorsAndWindows;
						for(int f=0; f<daw.size(); f++) {
							Vector2 vecToDaw = new Vector2((daw.get(f).xPos+daw.get(f).area.width/2)-(agents.get(a).xPos+agents.get(a).area.width/2),(daw.get(f).yPos+daw.get(f).area.height/2)-(agents.get(a).yPos+agents.get(a).area.height/2));
							if(vecToDaw.x < vecToDaw.y) {
								//horizontal door
								if(vecToDaw.len() < daw.get(f).area.height/2+agents.get(a).area.height/2+10) {
									agents.get(a).setPos(agents.get(a).xPos, agents.get(a).yPos+2*vecToDaw.y);
									if(daw.get(f) instanceof Door) {
										if(agents.get(a).speed < 0.5) {
											agents.get(a).idlecount = fps*12 + (int) (fps*rand.nextGaussian()*2+1/2);
										} else {
											agents.get(a).idlecount = fps*5;
											agents.get(a).soundRange = 5;
										}
									} else {
										agents.get(a).idlecount = fps*3;
										agents.get(a).soundRange = 10;
									}
								}
							} else {
								//vertical door
								if(vecToDaw.len() < daw.get(f).area.width/2+agents.get(a).area.width/2+10) {
									agents.get(a).setPos(agents.get(a).xPos+2*vecToDaw.x, agents.get(a).yPos);
									if(daw.get(f) instanceof Door) {
										if(agents.get(a).speed < 0.5) {
											agents.get(a).idlecount = fps*12 + (int) (fps*rand.nextGaussian()*2+1/2);
										} else {
											agents.get(a).idlecount = fps*5;
											agents.get(a).soundRange = 5;
										}
									} else {
										agents.get(a).idlecount = fps*3;
										agents.get(a).soundRange = 10;
									}
								}
							}
						}
					}
					if(territories.get(i) instanceof TargetArea && agents.get(a) instanceof Intruder) {
						if(victoryTime1 == 0) {
							victoryTime1 = System.currentTimeMillis();
						} else {
							if((System.currentTimeMillis() - victoryTime1) > 3*1000 * fps/60) {gameOver = true;}
						}
					} else if(territories.get(i) instanceof LowVisionArea) {
						agents.get(a).hidden = true;
					} else if (agents.get(a) instanceof Guard && territories.get(i) instanceof SentryTower) {
						if(!agents.get(a).inTower) {
							agents.get(a).inTower = true;
							agents.get(a).idlecount = fps*3;
							agents.get(a).viewRadius = 30;
							agents.get(a).viewRange = 15;
							agents.get(a).minViewRange = 2;
							agents.get(a).setPos(territories.get(i).xPos+territories.get(i).area.width/2-agents.get(a).area.width/2,territories.get(i).yPos+territories.get(i).area.height/2-agents.get(a).area.height/2);
						} else {
							if(agents.get(a).ai instanceof Tracking) {
								agents.get(a).soundRange = 1000;
							}
							if(agents.get(a).speed > 0) {
								agents.get(a).inTower = false;
								agents.get(a).idlecount = fps*3;
								float exitAngle = agents.get(a).viewAngle.angle();
								if (exitAngle >= 45 && exitAngle < 135) {
									agents.get(a).setPos(agents.get(a).xPos,agents.get(a).yPos+territories.get(i).area.height/2+agents.get(a).area.height/2);
								} else if (exitAngle >= 135 && exitAngle < 225) {
									agents.get(a).setPos(agents.get(a).xPos-territories.get(i).area.width/2-agents.get(a).area.width/2,agents.get(a).yPos);
								} else if (exitAngle >= 225 && exitAngle < 315) {
									agents.get(a).setPos(agents.get(a).xPos,agents.get(a).yPos-territories.get(i).area.height/2-agents.get(a).area.height/2);
								} else {
									agents.get(a).setPos(agents.get(a).xPos+territories.get(i).area.width/2+agents.get(a).area.width/2,agents.get(a).yPos);
								}
								agents.get(a).viewRange = 6f + agents.get(a).area.width/2;
								agents.get(a).minViewRange = 0;
								agents.get(a).viewRadius = 45;
							}
						}
					} else {
						agents.get(a).hidden = false;
						collided = true;
						agents.get(a).setCollided(true);
					}
				}
			}
			for(int i=0; i<agents.size(); i++) {
				if(a!=i) {
					Rectangle projected = new Rectangle(newX,newY,agents.get(a).area.width,agents.get(a).area.height);
					if(agents.get(i).intersects(projected)) {
						collided = true;
						agents.get(a).setCollided(true);
					}
				}
				
			}
			
			//move the agent if it's not colliding
			if(!collided) {
				agents.get(a).totalDistanceTravelled = agents.get(a).totalDistanceTravelled+new Vector2(agents.get(a).xPos-newX,agents.get(a).yPos-newY).len();
				agents.get(a).setPos(newX,newY);
			}
			agents.get(a).triggerStep();

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
					if(agents.get(i).hidden) {range = range/2;}
					Vector2 dis = distPointToRect(agents.get(a).xCenter,agents.get(a).yCenter,agents.get(i).area);
					Vector2 vec = new Vector2(agents.get(a).viewAngle);
					vec.scl(range);
					Vector2 pos = new Vector2(agents.get(a).xCenter,agents.get(a).yCenter);
					Vector2 fullVec = (new Vector2(pos)).add(vec);
	
					if(dis.len() < range && dis.len() > agents.get(a).minViewRange) {
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
							//check if seeing agent is guard and if seen agent is intruder, if so, and distance < 0.5m
							if((agents.get(a) instanceof Guard) && (agents.get(i) instanceof Intruder)){
								Vector2 agentAloc = new Vector2(agents.get(a).xCenter, agents.get(a).yCenter);
								Vector2 agentiloc = new Vector2(agents.get(i).xCenter, agents.get(i).yCenter);
								if(computeDist(agents.get(a).area,agents.get(i).area) < 0.5 || agentAloc.dst(agentiloc) < 0.5 + agents.get(a).width) {
									if(agents.get(a).ai instanceof Tracking) {
										Tracking trackingGuard = (Tracking) agents.get(a).ai;
										timeOfTracking = trackingGuard.timeToTrack;
										predictiveTracking = trackingGuard.predictive;
									}
									gameOver = true;
								}
							}
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

				if(dis.len() < range && dis.len() > agents.get(a).minViewRange) {
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

		//if intruder is < 0.5 meters away and guard sees intruder, it is removed from the map
		//if all intruders are removed, show game over screen


		
		generateSounds();
		
	}

	public void checkIfCaught(){
		//for each guard, check if it "catches" an intruder
		ArrayList<Agent> guards = new ArrayList<Agent>();
		ArrayList<Agent> intruders = new ArrayList<Agent>();

		for(Agent a : agents){
			if(a instanceof Guard){
				guards.add(a);
			}
			if(a instanceof Intruder){
				intruders.add(a);
			}
		}

		for(Agent guard: guards){

		}
	}

	public void generateSounds() {
		for (int i = 0; i < positionTracker.length; i++) {
			for (int j = 0; j < positionTracker[0].length; j++) {
				Random rand = new Random();
				if (rand.nextDouble() < 0.1/(fps*60)) {
					float xpos = (float) ((i* BOARD_WIDTH/5)+Math.random()*5);
					float ypos = (float) ((j* BOARD_HEIGHT/5)+Math.random()*5);
					SoundOccurence s = new SoundOccurence(System.currentTimeMillis(), xpos, ypos, RANDOM_NOISE_SOUND_RANGE);
					}
			}
		}
		//check for agents hearing agents
		for(int i=0; i<agents.size(); i++) {
			for(int j=0; j<agents.size(); j++) {
				if(i!=j) {
					Agent a = agents.get(j);
					Agent b = agents.get(i);
					//check if distance between sound and agent is within the sound range
					if (distPointToRect(b.xCenter,b.yCenter,a.area).len() < a.soundRange+b.area.width) {
						a.hearSound(estimateDirection(b.xCenter,b.yCenter,a.xCenter,a.yCenter));
					}
				}
			}
		}
	}

	public float estimateDirection(float xStart, float yStart, float xPos, float yPos) {
		Vector2 vector = new Vector2(xStart - xPos,yStart - yPos);
		float res = vector.angle()+(float) rand.nextGaussian()*10;
		return res;
	}

	public void checkIfAgentSees(){
		for(Agent a : agents){
			for(Area t: territories){
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

	public void setCurrentPoint(Point2D currentPoint) {
		this.currentPoint = currentPoint;
	}
	
	public float computeDist(Rectangle rect1, Rectangle rect2) {
		
		Vector2 vect = new Vector2((rect2.x+rect2.width/2)-(rect1.x+rect1.width/2),(rect2.y+rect2.height/2)-(rect1.y+rect1.height/2));
		float ang = vect.angle();
		while(ang > 45) {ang = ang-90f;}
		if(ang < -45) {ang = ang+90f;}
		float len = vect.len();
		float ext = (rect1.width/2+rect2.width/2)/(float) (Math.cos(Math.toRadians((double) Math.abs(ang)))); 
		return len-ext;
	}
}