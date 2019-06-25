package com.game.Board;



import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;
import com.game.States.MapState;

/**
 * @author Lukas Padolevicius
 * @author Andreas Zurhaar
 */

public class Area extends AssetManager {

	public Rectangle area;
	public float xPos;
	public float yPos;
	public float viewDistance;
	public String name;
    public TextureRegion texture;

	
    public Area(float startX, float startY, float width, float height) {
        area = new Rectangle(startX,startY, width, height);
        xPos = startX;
        yPos = startY;
        viewDistance = 0;
    }

    public float getMinX() {
        return xPos;
    }
    
    public float getMaxX() {
        return xPos+area.getWidth();
    }

    public float getMinY() {
        return yPos;
    }

    public float getMaxY() {
        return yPos+area.getHeight();
    }

    public boolean contains(float x, float y) {
        return area.contains(x,y);
    }
    
    public boolean intersects(Rectangle rect) {
		return Intersector.overlaps(rect,this.area);
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
    
    public void drawTexture(SpriteBatch sb, float xReduc, float yReduc) {
    	sb.draw(texture, xPos*xReduc, yPos*yReduc, 
    			(float) area.getWidth()*xReduc, (float) area.getHeight()*yReduc);}


}
