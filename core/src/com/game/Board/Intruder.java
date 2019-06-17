/**
 * 
 */
package com.game.Board;

import java.io.IOException;
import java.util.ArrayList;

import com.game.AI.AI;
import com.game.AI.Escape;
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
public class Intruder extends Agent {
	
	public float speed;
	public float angle;
	public float soundRange;
	public ArrayList<Area> structures;
    SpriteReader reader = new SpriteReader();
    public int sprintCount = 0;
	public int restCount = 0;

	public Intruder(float x, float y, float width, float height) {
		super(x, y, width, height);
		viewAngle.setAngle(-45);
		this.width = width;
		this.height = height;
		//viewAngle.setToRandomDirection();
		speed = 1;
		rotation = 0;
		soundRange = 0;
		name = "1";
		viewRange = 7.5f + width/2;
        try {
            this.texture = reader.getImage(225,255,30,33);
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

	public void triggerSprint() {
		if(restCount == 0 ) {
			sprintCount = 5*Board.fps;
			restCount = 10*Board.fps;
		}
	}
	
	public void triggerStep() {
		
		if(idlecount > 0) {
			speed = 0;
			rotation = 0;
			idlecount--;
			return;
		}
		this.speed = 1.4f;//(float) Math.random()*1.4f;
//		rotation = -10;
//		/*
//		rotation = (float) Math.random()*turningCircle/4;
//		if(Math.random() < 0.3) {rotation = -rotation;}
//		if(Math.random() < 0.001) {triggerSprint();}
//		*/
//
//		if(sprintCount != 0) {
//			speed = 3f;
//			sprintCount--;
//
//			if(rotation > 10) {rotation = 10;}
//			else if(rotation < -10) {rotation = -10;}
//
//		} else if(restCount != 0) {restCount--;}

		if(speed < 0.5) {
			soundRange = 1;
		} else if(speed < 1) {
			soundRange = 3;
		} else if(speed < 2) {
			soundRange = 5;
		} else {
			soundRange = 10;
		}

		this.speed = ai.getSpeed()*Board.fps;//(float) (Math.random()*1.4f);
		this.rotation = ai.getRotation()*Board.fps;
	}

    @Override
    public void setAI(AI ai){ this.ai = ai;}

	@Override
	public void drawTexture(SpriteBatch sb, float xReduc, float yReduc) {
		sb.end();
	   	renderer.begin(ShapeType.Line);
	   	if(seeing) {renderer.setColor(1, 0, 0, 1);}
	   	else {renderer.setColor(1, 1, 0, 1);}
	   	if(idlecount == 0) {renderer.arc(xCenter*xReduc, yCenter*yReduc, this.viewRange*xReduc,this.viewAngle.angle()-(this.viewRadius/2),this.viewRadius,20);}
	   	renderer.setColor(1, 0, 1, 1);
		renderer.rect(xPos*xReduc-1,yPos*yReduc-1,area.width*xReduc+2,area.height*yReduc+2);
	   	renderer.end();
		sb.begin();
		seeing = false;
		super.drawTexture(sb, xReduc, yReduc);
	}

	@Override
	public void see(Agent agent) {

		if (!(Math.abs(rotation) > 45)) {
			//TODO "if you turn more than 45 degrees/second you don't see anything for the turning time --plus half a second--"
			seeing = true;
			//TODO maybe should replace Escape with new Escape AI if another guard is seen
			if (!(ai instanceof Escape) && agent instanceof Guard) {
				ai = new Escape(this, agent, ai);
			}
		}
	}

	@Override
	/**
	 * When an intruder hears a sound, it moves away from its direction for X seconds before returning back to its original AI
	 */
	public void hearSound(float directionAngle){
		hearing = true;
		/**
		 * We don't want to switch our AI when the guard is escaping (i.e. moving away from a guard after it has seen it)
		 */
		if (!(ai instanceof Escape) && !(ai instanceof  MoveAwayFromSound)){
			ArrayList<Area> storedStructures = this.ai.seenStructures;
			ai = new MoveAwayFromSound(this, directionAngle, ai, storedStructures);
	  }
	}

	public void addStructures(Area structure){
		structures.add(structure);
	}
}
