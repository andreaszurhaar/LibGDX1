/**
 * 
 */
package com.game.States;

import java.util.ArrayList;
import com.badlogic.gdx.math.Rectangle;

import com.game.CopsAndRobbers;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Intruder;
import com.game.Board.OuterWall;
import com.game.Board.Structure;
import com.game.Board.TargetArea;

/**
 * @author Lukas Padolevicius
 *
 */
public class ObstacleGenerator {

    public static final float X_REDUC = 2.5f;
    public static final float Y_REDUC = 2.5f;
    public ArrayList<ArrayList<Area>> structures;
    public ArrayList<Area> str;

	public ObstacleGenerator() {
		structures = new ArrayList<ArrayList<Area>>();
	}
	
	public void generate(Rectangle area, int wallnum, float horizontalRatio, int iterations) {
		
		for(int i=0; i<iterations; i++) {
			makeBase();
			
			for(int j=0; j<wallnum; j++) {
				float ran = (float) Math.random();
				if(ran < horizontalRatio) {
					for(int c=0; c<100; c++) {
						float xpos = area.x+(float) (Math.random())*(area.width-40);
						float ypos = area.y+(float) (Math.random())*(area.height-8);
	                    Structure curr = new Structure(xpos, ypos, 100 / X_REDUC, 20 / Y_REDUC, true);
	                    if(!(checkCollision(curr.area))) {
	                    str.add(curr);
	                    	break;
	                    }
					}
                 
				} else {
					for(int c=0; c<100; c++) {
						float xpos = area.x+(float) (Math.random())*(area.width-8);
						float ypos = area.y+(float) (Math.random())*(area.height-40);
	                    Structure curr = new Structure(xpos, ypos, 20 / X_REDUC, 100 / Y_REDUC, true);
	                    if(!(checkCollision(curr.area))) {
	                    str.add(curr);
	                    	break;
	                    }
					}
                 					
				}
				
				
				
			}
			
			
			
			
			
			
			
			structures.add(str);
		}
	}
	
	public void makeBase() {
		str = new ArrayList<Area>();
		str.add(new OuterWall(20/X_REDUC,0,(CopsAndRobbers.WIDTH-40)/X_REDUC,20/Y_REDUC));
       	str.add(new OuterWall(20/X_REDUC,(CopsAndRobbers.HEIGHT-150)/Y_REDUC,(CopsAndRobbers.WIDTH-40)/X_REDUC,20/Y_REDUC));
       	str.add(new OuterWall(0,0,20/X_REDUC,(CopsAndRobbers.HEIGHT-130)/Y_REDUC));
       	str.add(new OuterWall((CopsAndRobbers.WIDTH-20)/X_REDUC,0,20/X_REDUC,(CopsAndRobbers.HEIGHT-130)/Y_REDUC));
       	str.add(new TargetArea(340,90,50/X_REDUC,50/Y_REDUC));

       	/*
       	structures.add(new OuterWall(247/X_REDUC,36/Y_REDUC,6/X_REDUC,60));
       	structures.add(new OuterWall(36/X_REDUC,36/Y_REDUC,428,6/Y_REDUC));
       	structures.add(new OuterWall(36/X_REDUC,58/Y_REDUC,146,6/Y_REDUC));
       	structures.add(new OuterWall(253/X_REDUC,58/Y_REDUC,146,6/Y_REDUC));
       	structures.add(new OuterWall(36/X_REDUC,80/Y_REDUC,428,6/Y_REDUC));
       	*/
	}
	
	public ArrayList<ArrayList<Area>> getAreas() {
		return structures;
	}
	
	public boolean checkCollision(Rectangle rec) {
		boolean res = false;
		for(int i=0; i<str.size(); i++) {
			boolean sameX = true;
			boolean sameY = true;
			if((rec.x < str.get(i).area.x && rec.x+rec.width < str.get(i).area.x) || (rec.x > str.get(i).area.x+str.get(i).area.width && rec.x+rec.width > str.get(i).area.x+str.get(i).area.width)) {
				sameX = false;
			}
			if((rec.y < str.get(i).area.y && rec.y+rec.height < str.get(i).area.y) || (rec.y > str.get(i).area.y+str.get(i).area.height && rec.y+rec.height > str.get(i).area.y+str.get(i).area.height)) {
				sameY = false;
			}
			if(sameX && sameY) {return true;}
		}
		return res;
	}
}
