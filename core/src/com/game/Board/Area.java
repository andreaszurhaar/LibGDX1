package com.game.Board;



import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author Lukas Padolevicius
 *
 */

public class Area extends AssetManager {

	public Rectangle area;
	public float xPos;
	public float yPos;
	public String name;
    public TextureRegion texture;

	
    public Area(float startX, float startY, float width, float height) {
        area = new Rectangle(startX,startY, width, height);
        xPos = startX;
        yPos = startY;
    }

    public float getMinX() {
        return area.getX();
    }
    
    public float getMaxX() {
        return area.getX()+area.getWidth();
    }

    public float getMinY() {
        return area.getY();
    }

    public float getMaxY() {
        return area.getY()+area.getHeight();
    }

    public boolean contains(float x, float y) {
        return area.contains(x,y);
    }
    
    public void setX(int xPos){
        this.xPos += xPos;
        area.setX((float) this.xPos);
    }
    public void setY(int yPos){
        this.yPos += yPos;
        area.setY((float) this.yPos);
    }
    public void setName(String string){this.name  =string;}
    
    public void drawTexture(SpriteBatch sb) {
    	sb.draw(texture, xPos, yPos, 
    			(float) area.getWidth(), (float) area.getHeight());}


}
