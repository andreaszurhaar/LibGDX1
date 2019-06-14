/**
 * 
 */
package com.game.Board;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.game.AI.AI;
import com.game.States.MapState;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Lukas Padolevicius
 *
 */

public class Agent extends AssetManager {

	public Rectangle area;
	public volatile float xPos;
	public volatile float yPos;
	public volatile float xCenter;
	public volatile float yCenter;
	public Vector2 viewAngle;
	public float speed;
	public float rotation;
	public float angle;
	public float turningCircle;
	public float viewRadius;
	public float viewRange;
	public float maxSpeed;
	public float soundRange;
	public SoundOccurence lastHeardSound;
	public boolean hearing;
	public boolean seeing;
	public int hearingCount;
	public boolean collided;

	public String name;
    public TextureRegion texture;
    public ShapeRenderer renderer;
    public TextureRegion noticeSound;
	private Point2D.Float centerLocation;
	public AI ai;
	protected ArrayList<Agent> agentList;
	
	
	public Agent(float x, float y, float width, float height) {
        area = new Rectangle(x,y,width,height);
        xPos = x;
		yPos = y;
		xCenter = xPos+width/2;
		yCenter = yPos+height/2;
		viewAngle = new Vector2(1,1);
		turningCircle = 180;
		viewRadius = 45;
		hearing = false;
		renderer = new ShapeRenderer();
		maxSpeed = 1.4f;
		speed = 0f;
	}
	
	/* 
	 * copy constructor for instructor classes
	 * 
	 */
	public Agent(Agent ag) {
		
        area = new Rectangle(ag.xPos,ag.yPos,ag.area.getWidth(),ag.area.getHeight());
        xPos = ag.xPos;
		yPos = ag.yPos;
		xCenter = xPos+ag.area.getWidth()/2;
		yCenter = yPos+ag.area.getHeight()/2;
		viewAngle = ag.viewAngle;
		turningCircle = ag.turningCircle;
		viewRadius = ag.viewRadius;
		hearing = ag.hearing;
		renderer = ag.renderer;
		maxSpeed = ag.maxSpeed;
		
	}
	
	public float getX() {
		return xPos;
	}
	
	public float getY() {
		return yPos;
	}
	
	public void setPos(float x, float y) {

		//System.out.println("Updating xPos to " + x);
		//System.out.println("Updating yPos to " + y);
		xPos = x;
		yPos = y;
		this.xCenter = xPos+this.area.width/2;
		this.yCenter = yPos+this.area.height/2;

		//System.out.println("Updating xCenter to " + xCenter);
		//System.out.println("Updating yCenter to " + yCenter);
		area.setPosition(x,y);
   	}
	
	public boolean intersects(Rectangle rect) {
		return Intersector.overlaps(rect,this.area);
   	}
	
	public float getAngle() {
		return viewAngle.angle();
	}

	public float getAngleRad() {
		return viewAngle.angleRad();
	}

	public float getRotation() {
		return rotation;
	}

	public void rotate(float ang) {
		viewAngle.rotate(ang);
	}
	
	public void setAngle(float ang) {
		viewAngle.setAngle(ang);
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float sp) {
		speed = sp;
	}
	
	public void hearSound(float directionAngle) {
		hearing = true;
		//heard a sound
		//System.out.println("heard sound");
	}
	
	public void see(Area object) {
		if(!(Math.abs(rotation) > 45)) {
			seeing = true;
			//saw object
			//System.out.println("saw something");
			ai.seeArea(object);
		}
	}
	
	public void see(Agent agent) {
		if(!(Math.abs(rotation) > 45)) {
			seeing = true;
			//saw object
			//System.out.println("saw something");
			ai.seeAgent(agent);
		}

	}
	
	public void triggerStep() {	}

	public void triggerStepTowardPoint(Point2D point){}

    public void setX(int xPos){
		System.out.println("Updating xPos to " + xPos);
        this.xPos += xPos;
        //bounds.setX((int) this.xPos);
    }
    public void setY(int yPos){

		System.out.println("Updating yPos to " + yPos);
        this.yPos += yPos;
        //bounds.setY((int) this.yPos);
    }
    public void setName(String string){this.name  =string;}

	public void setLastHeardSound(SoundOccurence lastHeardSound) {
		this.lastHeardSound = lastHeardSound;
	}
	
	public void drawTexture(SpriteBatch sb, float xReduc, float yReduc) {
    	sb.draw(texture, xPos*xReduc, yPos*yReduc, 
    			(float) area.getWidth()*xReduc, (float) area.getHeight()*yReduc);
    	if(hearing == true) {
    		hearingCount++;
    		if(hearingCount>10) {
    			hearingCount = 0;
    			hearing = false;
    		}
    		sb.draw(noticeSound, xPos*xReduc+area.getWidth()*xReduc/4, yPos*yReduc+area.getHeight()*xReduc*1.25f, 
        			(float) area.getWidth()*xReduc/2, (float) area.getHeight()*yReduc/2);
    	}
    }

//	public void setDestPoint(Point2D destPoint) {
//		this.destPoint = destPoint;
//	}
//
//	public Point2D getDestPoint() {
//		return destPoint;
//	}

	public void setAI(AI ai){ this.ai = ai;}

	public void setCenterLocation(Point2D.Float centerLocation) {
		this.centerLocation = centerLocation;
		//System.out.println("GUARD CENTER LOCATION: " + centerLocation.x + " " + centerLocation.y);
	}

    public Point2D.Float getCenterLocation() {
        return centerLocation;
    }

	public boolean isCollided() {
		return collided;
	}

	public void setCollided(boolean collided) {
		this.collided = collided;
	}

	public void setAgentList(ArrayList<Agent> agentList) {
		this.agentList = agentList;
	}


	
}
