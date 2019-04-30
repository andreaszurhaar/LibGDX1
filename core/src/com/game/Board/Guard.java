/**
 * 
 */
package com.game.Board;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AI.GuardPatrolling;
import com.game.Readers.SpriteReader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


/**
 * @author Lukas Padolevicius
 * @author Andreas Zurhaar
 */
public class Guard extends Agent {

	public float speed;
	public float angle;
	public float soundRange;

    public SpriteReader reader = new SpriteReader();
	private final int ALLOWED_DISTANCE_ERROR = 10;
	private boolean reachedCentre;

	
	public Guard(float x, float y, float width, float height) {
		super(x, y, width, height);
		//viewAngle.setToRandomDirection();
		speed = 1;
		soundRange = 0;
		viewRange = 6f;
		name = "2";
		try {
	        this.texture = reader.getImage(65,255,30,33);
            this.noticeSound = reader.getImage(135,425,20,20);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	
	public float getSpeed() {
		return speed;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void triggerStep() {

		//System.out.println("activated trigger and changed speed from: "+speed+"  "+angle);
		this.speed = 1.4f;//(float) (Math.random()*1.4f);
		if(speed < 0.5) {
			soundRange = 1;
		} else if(speed < 1) {
			soundRange = 3;
		} else if(speed < 2) {
			soundRange = 5;
		} else {
			soundRange = 10;
		}
		this.rotation = (float) -Math.toRadians(Math.random()*turningCircle/2);
		//System.out.println("to: "+speed+"  "+angle);
	}

	public void triggerStepTowardPoint(Point2D point){

			//System.out.println("activated trigger and changed speed from: "+speed+"  "+angle);
			this.speed = 100;//(float) (Math.random()*1.4f);
			if(speed < 0.5) {
				soundRange = 1;
			} else if(speed < 1) {
				soundRange = 3;
			} else if(speed < 2) {
				soundRange = 5;
			} else {
				soundRange = 10;
			}

		float centreX = (float) point.getX();
		float centreY = (float) point.getY();

		if(reachedCentre){
			this.speed = 1.4f;
			//patrolInArea();

			//this.rotation = (float) -Math.toRadians(Math.random()*this.turningCircle/2);
			//this.angle = 0;
		}

		if(!reachedCentre && (Math.sqrt(((this.xPos - centreX) * (this.xPos  - centreX)) + ((this.yPos  - centreY) * (this.yPos - centreY))) < ALLOWED_DISTANCE_ERROR)){
			reachedCentre = true;
			//this.rotation = (float) -Math.toRadians(Math.random()*this.turningCircle/2);
			//this.angle = 0;
		}



		seeing = false;
	}
	
	@Override
	public void drawTexture(SpriteBatch sb, float xReduc, float yReduc) {
		sb.end();
	   	renderer.begin(ShapeType.Line);
	   	if(seeing) {renderer.setColor(1, 0, 0, 1);}
	   	else {renderer.setColor(1, 1, 0, 1);}
	   	renderer.arc(xCenter*xReduc, yCenter*yReduc, viewRange*xReduc,viewAngle.angle()-(viewRadius/2),viewRadius,20);
	   	renderer.end();
		sb.begin();
		seeing = false;
		super.drawTexture(sb, xReduc, yReduc);
	}

	public void patrolling()
	{
		float testWidth = 200;
		float testHeight = 100;
		GuardPatrolling patrol = new GuardPatrolling(testWidth,testHeight,this);

	}




}
