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
public class LowVisionArea extends Area {
	
	SpriteReader reader = new SpriteReader();

	public LowVisionArea(float startX, float startY, float width, float height) {
		super(startX, startY, width, height);
		try {
            this.texture = reader.getImage(104,225,15,33);
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

}
