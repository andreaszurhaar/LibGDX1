/**
 * 
 */
package com.game.Board;

import java.io.IOException;

import com.game.Readers.SpriteReader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * @author Lukas Padolevicius
 * @author Andreas Zurhaar
 */
public class OuterWall extends Area{

	SpriteReader reader = new SpriteReader();

	public OuterWall(float startX, float startY, float width, float height) {
		super(startX, startY, width, height);
		name = "3";
        this.texture = new TextureRegion(new Texture("Water.png"),20,20,width,height);

	}
}
