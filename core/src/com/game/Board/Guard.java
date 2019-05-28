/**
 * 
 */
package com.game.Board;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import com.game.AI.AI;
//import com.game.AI.CopsCenters;
import com.game.AI.GuardPatrolling;
import com.game.AI.Tracking;
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
	private Point2D.Float locationIntruder;
	private float angleIntruder;
    private Tracking tracking;
    private ArrayList<Area> structures;
    public SpriteReader reader = new SpriteReader();
	private final int ALLOWED_DISTANCE_ERROR = 10;
	private boolean reachedCentre;

	public Guard(float x, float y, float width, float height) {
		super(x, y, width, height);
		//viewAngle.setToRandomDirection();
		speed = 1;
		maxSpeed = 1.4f;
		soundRange = 0;
		viewRange = 6f + width/2;
		name = "2";
		try {
	        this.texture = reader.getImage(65,255,30,33);
            this.noticeSound = reader.getImage(135,425,20,20);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    public Guard(float x, float y, float width, float height, ArrayList<Area> structures) {
        super(x, y, width, height);
        this.structures = structures;
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
		//System.out.println("AI in guard is " + ai);


		//System.out.println("activated trigger and changed speed from: "+speed+"  "+angle);
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
		//System.out.println("to: "+speed+"  "+angle);
	}

	@Override
	public void drawTexture(SpriteBatch sb, float xReduc, float yReduc) {
		sb.end();
	   	renderer.begin(ShapeType.Line);
	   	if(seeing) {renderer.setColor(1, 0, 0, 1);}
	   	else {renderer.setColor(1, 1, 0, 1);}
	   	renderer.arc(xCenter*xReduc, yCenter*yReduc, viewRange*xReduc,viewAngle.angle()-(viewRadius/2),viewRadius,20);
		
		if (ai instanceof Tracking) {
			Tracking guardcopy = (Tracking) this.ai;
			renderer.setColor(0, 1, 0, 0);
			renderer.rectLine(xCenter*xReduc,yCenter*yReduc,(xCenter+guardcopy.showvect.x)*xReduc,(yCenter+guardcopy.showvect.y)*yReduc,2);
			System.out.println("VECTOR: "+guardcopy.showvect.x);
		}
		
	   	renderer.end();
		sb.begin();
		seeing = false;

		super.drawTexture(sb, xReduc, yReduc);
	}

    public void communicate(Point2D.Float locIntruder){
	    locationIntruder = locIntruder;
    }

    public void communicate(float angle){
        angleIntruder = angle;
    }

    @Override
    public void setAI(AI ai){ this.ai = ai;}

    @Override
	public void see(Agent agent) {
		if (!(ai instanceof Tracking) && agent instanceof Intruder) {
			System.out.println("saw intruder");
			ai = new Tracking(this,agent,ai);
		}
		ai.seeAgent(agent);

	}
    
    /*
    public float getX()
	{
		return xCenter;
	}

	public float getY()
	{
		return yCenter;
	}
	*/

}
