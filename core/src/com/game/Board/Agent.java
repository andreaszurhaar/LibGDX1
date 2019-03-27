/**
 * 
 */
package com.game.Board;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.game.States.MapState;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


/**
 * @author Lukas Padolevicius
 *
 */

public class Agent extends AssetManager {

	public Rectangle area;
	public float xPos;
	public float yPos;
	public float xCenter;
	public float yCenter;
	public Vector2 viewAngle;
	public float speed;
	public float rotation;
	public float turningCircle;
	public float viewRadius;
	public float viewRange;
	private float maxSpeed;
	public float soundRange;
	public SoundOccurence lastHeardSound;
	public boolean hearing;
	public boolean seeing;
	public int hearingCount;

	public String name;
    public TextureRegion texture;
    public ShapeRenderer renderer;
    public TextureRegion noticeSound;
	
	
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
	}
	
	public float getX() {
		return xPos;
	}
	
	public float getY() {
		return yPos;
	}
	
	public void setPos(float x, float y) {
		xPos = x;
		yPos = y;
		xCenter = xPos+this.area.width/2;
		yCenter = yPos+this.area.height/2;
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
		seeing = true;
		//saw object
		System.out.println("saw something");
	}
	
	public void see(Agent object) {
		seeing = true;
		//saw object
		System.out.println("saw something");
	}
	
	public void triggerStep() {	}

    public void setX(int xPos){
        this.xPos += xPos;
        //bounds.setX((int) this.xPos);
    }
    public void setY(int yPos){
        this.yPos += yPos;
        //bounds.setY((int) this.yPos);
    }
    public void setName(String string){this.name  =string;}

	public void setLastHeardSound(SoundOccurence lastHeardSound) {
		this.lastHeardSound = lastHeardSound;
	}
	
	public void drawTexture(SpriteBatch sb, int xReduc, int yReduc) {
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
	

}
