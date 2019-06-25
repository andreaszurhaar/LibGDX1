/**
 * 
 */
package com.game.Board;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.game.AI.AI;
//import com.game.AI.CopsCenters;
import com.game.AI.Escape;
import com.game.AI.GuardPatrolling;
import com.game.AI.GuardPreventCollision;
import com.game.AI.InvestigateSound;
import com.game.AI.MoveAwayFromSound;
import com.game.AI.Tracking;
import com.game.AI.TrackingLongDistance;
import com.game.AI.Astar.AStarNew;
import com.game.Readers.SpriteReader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author Lukas Padolevicius
 * @author Andreas Zurhaar
 */
public class Guard extends Agent {

	private Point2D.Float locationIntruder;
	private float angleIntruder, prevAngle;
	private Tracking tracking;
	private ArrayList<Area> seenStructures = new ArrayList<Area>(); //TODO: we never update the seen structures
	public SpriteReader reader = new SpriteReader();
	private final int ALLOWED_DISTANCE_ERROR = 10;
	private boolean reachedCentre;
	private double timeOfLastMessage;
	private final double INTER_MESSAGE_TIME = 5; //in seconds
	private final float RADIUS = 400;


	public Guard(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.width = width;
		this.height = height;
		speed = 1;
		maxSpeed = 1.4f;
		soundRange = 0;
		viewRange = 6f + width / 2;
		name = "2";
		timeOfLastMessage = Integer.MIN_VALUE;
		try {
			this.texture = reader.getImage(65, 255, 30, 33);
			this.noticeSound = reader.getImage(135, 425, 20, 20);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Guard(float x, float y, float width, float height, ArrayList<Area> structures) {
		super(x, y, width, height);
		seenStructures = structures;
		speed = 1;
		maxSpeed = 1.4f;
		soundRange = 0;
		viewRange = 6f + width / 2;
		name = "2";
		timeOfLastMessage = Float.MIN_VALUE;
		try {
			this.texture = reader.getImage(65, 255, 30, 33);
			this.noticeSound = reader.getImage(135, 425, 20, 20);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setSeenStructures(ArrayList<Area> structures)
	{
		seenStructures = structures;
	}

	public float getSpeed() {
		return speed;
	}

	public float getAngle() {
		return angle;
	}

	public void triggerStep() {
		
		if(idlecount > 0) {
			speed = 0;
			rotation = 0;
			idlecount--;
			return;
		}

		if (speed < 0.5) {
			soundRange = 1;
		} else if (speed < 1) {
			soundRange = 3;
		} else if (speed < 2) {
			soundRange = 5;
		} else {
			soundRange = 10;
		}

		this.speed = ai.getSpeed() * Board.fps;
		this.rotation = ai.getRotation() * Board.fps;

		if (this.speed == 0)
			framesStationaryCounter++;
		else
			framesStationaryCounter = 0;

		if (ai instanceof AStarNew) {
			if (speed == 0 && rotation == 0) {
				AStarNew aiConv = (AStarNew) ai;
				if (xCenter < 200) {
					aiConv.runAgain(this.xCenter, this.yCenter, 150, 100);
				} else {
					aiConv.runAgain(this.xCenter, this.yCenter, 100, 100);
				}
			}
		}
		if(speed > 1.4f) {speed = 1.4f;}
		if(rotation > 180) {rotation = 180;}
		else if(rotation < -180) {rotation = -180;}
	}

	@Override
	public void drawTexture(SpriteBatch sb, float xReduc, float yReduc) {
		sb.end();
	   	renderer.begin(ShapeType.Line);
	   	if(seeing) {renderer.setColor(1, 0, 0, 1);}
	   	else {renderer.setColor(1, 1, 0, 1);}
	   	if(idlecount == 0) {renderer.arc(xCenter*xReduc, yCenter*yReduc, this.viewRange*xReduc,this.viewAngle.angle()-(this.viewRadius/2),this.viewRadius,20);}
		if (ai instanceof Tracking) {
			Tracking guardcopy = (Tracking) this.ai;
			renderer.setColor(0, 1, 0, 0);
			renderer.rectLine(xCenter * xReduc, yCenter * yReduc, (xCenter + guardcopy.showvect.x) * xReduc, (yCenter + guardcopy.showvect.y) * yReduc, 2);
		}
		renderer.setColor(0, 1, 1, 1);
		renderer.rect(xPos * xReduc - 1, yPos * yReduc - 1, area.width * xReduc + 2, area.height * yReduc + 2);
		renderer.end();
		sb.begin();
		seeing = false;

		super.drawTexture(sb, xReduc, yReduc);
	}

	public void communicate(Point2D.Float locIntruder) {
		locationIntruder = locIntruder;
	}

	public void communicate(float angle) {
		angleIntruder = angle;
	}

	@Override
	public void setAI(AI ai) {
		this.ai = ai;
	}

	@Override
	public void see(Agent agent) {

		if (!(Math.abs(rotation) > 45)) {
			seeing = true;
			/** Switching to tracking
			 */
			if (!(ai instanceof Tracking) && agent instanceof Intruder) {
				ai = new Tracking(this, agent, ai);
			}
			/**
			 * Communicating the intruder's location to all other guards every X seconds
			 */
			if (agent instanceof Intruder) {
				if (System.currentTimeMillis() > (timeOfLastMessage + INTER_MESSAGE_TIME * 1000)) {
					timeOfLastMessage = System.currentTimeMillis();
					for (int i = 0; i < agentList.size(); i++) {
						Agent currentGuard = agentList.get(i);
						if ((computeDistance(currentGuard,this)<RADIUS) && currentGuard != this && currentGuard instanceof Guard) {
							if((currentGuard instanceof Guard)) {
								ArrayList<Area> storedStructures = currentGuard.ai.seenStructures;
								currentGuard.ai = new TrackingLongDistance((Guard) currentGuard, new Vector2(agent.xPos, agent.yPos), currentGuard.ai, storedStructures);
							}
						}
					}
				}
			}
			if(agent instanceof Guard && !(ai instanceof Tracking) && !(ai instanceof TrackingLongDistance) /*&& computeDistance(this, agent) < 100*/){
				ai = new GuardPreventCollision(this, agent, ai, ai.seenStructures);
			}
			ai.seeAgent(agent);
		}
	}

	public float computeDistance(Agent a1, Agent a2)
	{
		Vector2 vect1 = new Vector2(a1.xCenter, a1.yCenter);
		Vector2 vect2 = new Vector2(a2.xCenter, a2.yCenter);
		return vect1.dst(vect2);
	}

	@Override
	/**
	 * When a guard hears a sound, it moves towards its direction for X seconds before returning back to patrolling
	 */
	public void hearSound(float directionAngle) {

		prevAngle = directionAngle;

		if (!(directionAngle < (prevAngle + 20) && directionAngle > (prevAngle - 20)) && framesStationaryCounter < 30) {

			hearing = true;
			/**
			 * We don't want to switch our AI when the guard is tracking
			 */
			if (!(ai instanceof Tracking) && !(ai instanceof TrackingLongDistance) && !(ai instanceof InvestigateSound)) {
				ai = new InvestigateSound(this, directionAngle, ai);
			}
		}

	}


}
