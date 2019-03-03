package com.game.Board;



import com.badlogic.gdx.math.Rectangle;

/**
 * @author Lukas Padolevicius
 *
 */

public class Area{

	private Rectangle area;
	
    public Area(float startX, float startY, float width, float height) {
        area = new Rectangle(startX,startY, width, height);
    }

    public float getMinX() {
        return area.getX();
    }
    
    public float getMaxX() {
        return area.getX()+area.getWidth();
    }

    public float getMinY() {
        return area.getY();
    }

    public float getMaxY() {
        return area.getY()+area.getHeight();
    }

    public boolean contains(float x, float y) {
        return area.contains(x,y);
    }

}
