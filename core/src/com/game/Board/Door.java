/**
 * 
 */
package com.game.Board;

import java.io.IOException;

import com.game.Readers.SpriteReader;

/**
 * @author Lukas Padolevicius
 * @author Andreas Zurhaar
 */
public class Door extends Area{

	SpriteReader reader = new SpriteReader();
	public String name = "7";

	public Door(float startX, float startY, float width, float height, boolean horizontal) {
		super(startX, startY, width, height);
		try {
			if(horizontal) {
        		this.texture = reader.getImage(32,417,30,30);
        	} else {
                this.texture = reader.getImage(106,450,12,30);
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

}
