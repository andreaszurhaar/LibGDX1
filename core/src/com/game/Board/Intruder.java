/**
 * Graphical Object and State Machine for an intruder surveillance agent
 */
package com.game.Board;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.game.AI.AI;
import com.game.AI.Escape;
import com.game.AI.MoveAwayFromSound;
import com.game.AI.MoveToTarget;
import com.game.Readers.SpriteReader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	
	public void timedSprint(int frames) {
		if(restCount == 0 && frames/Board.fps < 6) {
			sprintCount = frames;
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
		if(sprintCount != 0) {
			sprintCount--;
		} else if(restCount != 0) {restCount--;}

		if(speed < 0.5) {
			soundRange = 1;
		} else if(speed < 1) {
			soundRange = 3;
		} else if(speed < 2) {
			soundRange = 5;
		} else {
			soundRange = 10;
		}

		this.speed = ai.getSpeed()*Board.fps;
		this.rotation = ai.getRotation()*Board.fps;
		if(sprintCount != 0) {
			if(speed > 3) {speed = 3;}
			if(rotation > 10) {rotation = 10;}
			else if(rotation < -10) {rotation = -10;}
		} else {
			if(speed > 1.4f) {speed = 1.4f;}
			if(rotation > 180) {rotation = 180;}
			else if(rotation < -180) {rotation = -180;}	
		}
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
	public void see(Area object){
		if(!(Math.abs(rotation) > 45)) {
			seeing = true;
		}
		if(!(ai instanceof MoveToTarget) && object instanceof TargetArea){
			ai = new MoveToTarget(this, new Vector2(object.xPos + 0.5f * object.area.getWidth(), object.yPos + 0.5f * object.area.getHeight()), ai);
		}

	}

	@Override
	public void see(Agent agent) {

		if (!(Math.abs(rotation) > 45)) {
			seeing = true;
			if (!(ai instanceof Escape) && !(ai instanceof MoveToTarget) && agent instanceof Guard) {
                ArrayList<Area> storedStructures = ai.seenStructures;
				ai = new Escape(this, agent, ai, storedStructures);
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
		if (!(ai instanceof Escape) && !(ai instanceof  MoveAwayFromSound) && !(ai instanceof MoveToTarget)){
			ArrayList<Area> storedStructures = ai.seenStructures;
			ai = new MoveAwayFromSound(this, directionAngle, ai, storedStructures);
	  }
	}

	public void addStructures(Area structure){
		structures.add(structure);
	}

}
