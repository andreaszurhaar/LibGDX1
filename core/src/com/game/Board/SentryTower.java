/**
 * Sentry Tower Graphic and logic object
 */

package com.game.Board;

import java.io.IOException;

import com.game.Readers.SpriteReader;

/**
 * @author Lukas Padolevicius
 * @author Andreas Zurhaar
 */

public class SentryTower extends Area {

	SpriteReader reader = new SpriteReader();


	public SentryTower(float startX, float startY, float width, float height) {
		super(startX, startY, width, height);
		name = "9";
		viewDistance = 18;
		try {
            this.texture = reader.getImage(65,417,30,30);
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
}
