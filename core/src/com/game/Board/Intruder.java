/**
 * 
 */
package com.game.Board;

import java.io.IOException;

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
    public int sprintCount;


	public Intruder(float x, float y, float width, float height) {
		super(x, y, width, height);
		viewAngle.setToRandomDirection();
		speed = 1;
		soundRange = 0;
		sprintCount = 0;
		name = "1";
		viewRange = 7.5f;
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

	public void triggerStep() {
		sprintCount++;
		this.speed = 1.4f;//(float) Math.random()*1.4f;
		if(speed < 0.5) {
			soundRange = 1;
		} else if(speed < 1) {
			soundRange = 3;
		} else if(speed < 2) {
			soundRange = 5;
		} else {
			soundRange = 10;
		}
		rotation = (float) Math.toRadians(Math.random()*turningCircle/2);
		seeing = false;
	}
	
	@Override
	public void drawTexture(SpriteBatch sb, float xReduc, float yReduc) {
		sb.end();
	   	renderer.begin(ShapeType.Line);
	   	if(seeing) {renderer.setColor(1, 0, 0, 1);}
	   	else {renderer.setColor(1, 1, 0, 1);}
	   	renderer.arc(xCenter*xReduc, yCenter*yReduc, viewRange*4,viewAngle.angle()-(viewRadius/2),viewRadius);
	   	renderer.end();
		sb.begin();
		seeing = false;
		super.drawTexture(sb, xReduc, yReduc);
	}
}
