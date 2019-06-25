/**
 * Window Graphic and logic object
 */
package com.game.Board;

import java.io.IOException;

import com.game.Readers.SpriteReader;

/**
 * @author Lukas Padolevicius
 * @author Andreas Zurhaar
 */
public class Window extends Area {
	
	SpriteReader reader = new SpriteReader();
	public boolean horizontal;

	public Window(float startX, float startY, float width, float height, boolean horizontal) {
		super(startX, startY, width, height);
		this.horizontal = horizontal;
		name = "10";
		try {
			if(horizontal) {
        		this.texture = reader.getImage(32,292,26,28);
        	} else {
                this.texture = reader.getImage(32,292,26,28);
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }

	}


}
