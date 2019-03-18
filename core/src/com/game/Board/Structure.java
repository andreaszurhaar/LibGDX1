/**
 * 
 */
package com.game.Board;

import java.io.IOException;
import java.util.ArrayList;

import com.game.Readers.SpriteReader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Lukas Padolevicius
 *
 */
public class Structure extends Area {

    SpriteReader reader = new SpriteReader();
    public ArrayList<Area> doorsAndWindows;
    public float noiseRange;
    boolean horizontal;

	public Structure(float startX, float startY, float width, float height, boolean horizontal) {
		super(startX, startY, width, height);
	    doorsAndWindows = new ArrayList<Area>();
		this.horizontal = horizontal;
        SpriteReader reader = new SpriteReader();
        try {
        	if(horizontal) {
        		this.texture = reader.getImage(225,191,60,32);
        	} else {
                this.texture = reader.getImage(178,191,15,33);
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
		
	public void placeDoor(float x, float y) {
		if(horizontal) {
			float xDist = x-xPos;
			doorsAndWindows.add(new Door(xPos+xDist-15,yPos,30,20,horizontal));
		}
		if(!horizontal) {
			float yDist = y-yPos;
			doorsAndWindows.add(new Door(xPos,yPos+yDist-15,20,30,horizontal));
		}
	}
		
	public void placeWindow(float x, float y) {
		if(horizontal) {
			float xDist = x-xPos;
			doorsAndWindows.add(new Window(xPos+xDist-15,yPos,area.getWidth(),area.getHeight(),horizontal));
		}
		if(!horizontal) {
			float yDist = y-yPos;
			doorsAndWindows.add(new Window(xPos,yPos+yDist-15,area.getWidth(),area.getHeight(),horizontal));
		}

	}
	
	@Override
    public void drawTexture(SpriteBatch sb) {
		sb.draw(texture, xPos, yPos,(float) area.getWidth(),(float) area.getHeight());
		for(int i=0; i<doorsAndWindows.size(); i++) {
			doorsAndWindows.get(i).drawTexture(sb);
		}
    }

	/*
	@Override
	public void dispose() {
		for(int i=0; i<doorsAndWindows.size(); i++) {
			doorsAndWindows.get(i).dispose();
		}
		this.dispose();
	}
	*/
	


}
