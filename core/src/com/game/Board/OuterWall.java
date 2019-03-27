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
public class OuterWall extends Area{

	SpriteReader reader = new SpriteReader();

	public OuterWall(float startX, float startY, float width, float height) {
		super(startX, startY, width, height);
		name = "3";
		try {
            this.texture = reader.getImage(225,191,60,32);
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
}
