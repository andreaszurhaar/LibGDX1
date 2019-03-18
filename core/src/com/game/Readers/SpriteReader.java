package com.game.Readers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteReader extends AssetManager {

    public SpriteReader(){}

    public TextureRegion getImage(int rows, int cols, int width, int height) throws IOException {
        Texture wholeImage = new Texture(Gdx.files.internal("Dungeon_Tileset_at.png"));
        TextureRegion firstRegion = new TextureRegion(wholeImage,rows,cols,width,height); // gets the region from the 0,0 point of the whole image and is 50 x 50p
        return firstRegion;
    }
}
