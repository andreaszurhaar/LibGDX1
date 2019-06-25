/**
 * 
 */
package com.game.Board;

import java.io.IOException;
import java.util.ArrayList;

import com.game.Readers.SpriteReader;
import com.game.States.MapState;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Lukas Padolevicius
 * @author Andreas Zurhaar
 */
public class Structure extends Area {

    SpriteReader reader = new SpriteReader();
    public ArrayList<Area> doorsAndWindows;
    public float noiseRange;
    public boolean horizontal;

	public Structure(float startX, float startY, float width, float height, boolean horizontal) {
		super(startX, startY, width, height);
	    doorsAndWindows = new ArrayList<Area>();
		this.horizontal = horizontal;
		viewDistance = 10;
		name = "7";
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
			doorsAndWindows.add(new Door(xPos+xDist-3/MapState.X_REDUC,yPos,30/MapState.X_REDUC,20/MapState.Y_REDUC,horizontal));
		}
		if(!horizontal) {
			float yDist = y-yPos;
			doorsAndWindows.add(new Door(xPos,yPos+yDist-6/MapState.Y_REDUC,20/MapState.X_REDUC,30/MapState.Y_REDUC,horizontal));
		}

		name = "5";
	}
		
	public void placeWindow(float x, float y) {
		if(horizontal) {
			float xDist = x-xPos;
			doorsAndWindows.add(new Window(xPos+xDist-3/MapState.X_REDUC,yPos,30/MapState.X_REDUC,20/MapState.Y_REDUC,horizontal));
		}
		if(!horizontal) {
			float yDist = y-yPos;
			doorsAndWindows.add(new Window(xPos,yPos+yDist-6/MapState.Y_REDUC,20/MapState.X_REDUC,20/MapState.Y_REDUC,horizontal));
		}
		name = "6";

	}
	
	@Override
    public void drawTexture(SpriteBatch sb, float xReduc, float yReduc) {
		sb.draw(texture, xPos*xReduc, yPos*yReduc,(float) area.getWidth()*xReduc,(float) area.getHeight()*yReduc);
		for(int i=0; i<doorsAndWindows.size(); i++) {
			doorsAndWindows.get(i).drawTexture(sb,xReduc,yReduc);
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
