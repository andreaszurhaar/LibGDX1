/**
 * 
 */
package com.game.Board;

import java.io.IOException;

import com.game.AI.AI;
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
    SpriteReader reader = new SpriteReader();
    public int sprintCount = 0;
	public int restCount = 0;
	private AI ai;

	public Intruder(float x, float y, float width, float height) {
		super(x, y, width, height);
		viewAngle.setToRandomDirection();
		speed = 1;
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
		this.speed = 1.4f;//(float) Math.random()*1.4f;
		rotation = (float) Math.toRadians(Math.random()*turningCircle/2);
		if(Math.random() < 0.05) {triggerSprint();}
		if(sprintCount != 0) {
			speed = 3f;
			sprintCount--;
			
			if(rotation > 10) {rotation = 10;} 
			else if(rotation < -10) {rotation = -10;}
			
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

	public void setAI(AI ai){this.ai = ai;}
}
