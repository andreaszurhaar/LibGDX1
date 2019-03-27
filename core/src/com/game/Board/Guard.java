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
public class Guard extends Agent {

	public float speed;
	public float angle;
	public float soundRange;
    SpriteReader reader = new SpriteReader();

	
	public Guard(float x, float y, float width, float height) {
		super(x, y, width, height);
		viewAngle.setToRandomDirection();
		speed = 1;
		soundRange = 0;
		viewRange = 6f;
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
		this.rotation = (float) -Math.toRadians(Math.random()*turningCircle/2);
		//System.out.println("to: "+speed+"  "+angle);
		seeing = false;
	}
	
	@Override
	public void drawTexture(SpriteBatch sb, int xReduc, int yReduc) {
		sb.end();
	   	renderer.begin(ShapeType.Line);
	   	if(seeing) {renderer.setColor(1, 0, 0, 1);}
	   	else {renderer.setColor(1, 1, 0, 1);}
	   	renderer.arc(xCenter, yCenter, viewRange*4,viewAngle.angle()-(viewRadius/2),viewRadius);
	   	renderer.end();
		sb.begin();
		seeing = false;
		super.drawTexture(sb, xReduc, yReduc);
	}
}
