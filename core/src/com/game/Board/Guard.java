/**
 * 
 */
package com.game.Board;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
	public float areaWidth, areaHeight;
	private Point2D.Float areaCenter;
	public ArrayList<Point2D.Float> areaPoints;
    public SpriteReader reader = new SpriteReader();
	private final int ALLOWED_DISTANCE_ERROR = 30;

	
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

		float centreX = (float) point.getX();
		float centreY = (float) point.getY();

		if(Math.sqrt(((this.xPos - centreX) * (this.xPos  - centreX)) + ((this.yPos  - centreY) * (this.yPos - centreY))) < ALLOWED_DISTANCE_ERROR){
			this.rotation = (float) -Math.toRadians(Math.random()*this.turningCircle/2);
			this.angle = 0;
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

	public void patrolInArea()
	{
		areaPoints = new ArrayList<Point2D.Float>();
		//Assuming we know the areaborders of where we are patrolling
		for (int i = 0; i<areaWidth; i++)
		{
			for (int j=0; j<areaHeight;j++)
			{
				areaPoints.add(new Point2D.Float(i,j));
			}
		}
		Point2D.Float currentPoint = areaCenter;
		ArrayList<Point2D.Float> seenPoints = new ArrayList<Point2D.Float>();
		seenPoints.add(currentPoint);
		while (seenPoints.size() != (areaWidth*areaHeight)) {

		    addSeenPoints(seenPoints);
		    //go to point that is close and not seen yet
            currentPoint = findClosestPoint(currentPoint);
            //change vision cone to the direction of the closest point
            Vector2 point = new Vector2(currentPoint.x, currentPoint.y);
            super.setAngle(point.angle());
            //walk to that point & do everything again
        }
	}

	public void addSeenPoints(ArrayList<Point2D.Float> seenPoints)
    {
        for (Point2D.Float p : areaPoints) {
            if (Math.sqrt(p.x * p.x + p.y * p.y) < viewRange) {
                Vector2 point = new Vector2(p.x - viewAngle.x, p.y - viewAngle.y);
                if (point.angle() < (0.5*viewRadius)) {
                    seenPoints.add(p);
                    areaPoints.remove(p);
                }
            }
        }
    }

    public Point2D.Float findClosestPoint(Point2D.Float currentPoint)
    {
        Point2D.Float temp = new Point2D.Float(currentPoint.x, currentPoint.y);
        int i = 1;
        boolean foundPoint = false;
        while (!foundPoint) {
            for (Point2D.Float p : areaPoints) {
                if (p.x == temp.x + i && p.y == temp.y
                        || p.x == temp.x && p.y == temp.y + i
                        || p.x == temp.x + i && p.y == temp.y + i
                        || p.x == temp.x - i && p.y == temp.y
                        || p.x == temp.x && p.y == temp.y - i
                        || p.x == temp.x - i && p.y == temp.y - i
                        || p.x == temp.x - i && p.y == temp.y + i
                        || p.x == temp.x + i && p.y == temp.y - i) {
                    foundPoint = true;
                    return p;
                }
            }
            i++;
        }
        System.out.println("There are no unseen closest points, so we return back to the centre of the area");
        return areaCenter;

    }
}
