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
public class TargetArea extends Area {

    SpriteReader reader = new SpriteReader();

	public TargetArea(float startX, float startY, float width, float height) {
		super(startX, startY, width, height);
		try {
            this.texture = reader.getImage(36,228,20,20);
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

}
