/**
 * 
 */
package com.game.Board;

import java.io.IOException;

import com.game.Readers.SpriteReader;

/**
 * @author Lukas Padolevicius
 *
 */
public class OuterWall extends Area{

	SpriteReader reader = new SpriteReader();

	public OuterWall(float startX, float startY, float width, float height) {
		super(startX, startY, width, height);
		try {
            this.texture = reader.getImage(32,292,26,28);
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
}
