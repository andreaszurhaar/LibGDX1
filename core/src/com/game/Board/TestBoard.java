package com.game.Board;

import java.util.ArrayList;

public class TestBoard {

	public static void main(String[] args) {

		ArrayList<Area> areas = new ArrayList();
		areas.add(new SentryTower(43,30,10,10));
		areas.add(new TargetArea(80,80,40,40));
		areas.add(new OuterWall(0,0,199,2));
		areas.add(new SentryTower(125,38,30,30));
		areas.add(new Structure(116,17,28,28));
		areas.add(new Structure(178,178,17,17));
		Board board = new Board();
		board.setUp(areas);
		ArrayList[][] result = board.getTrackerBoard();
		for(int i=0; i<result.length; i++) {
			for(int j=0; j<result[0].length; j++) {
				//System.out.print("{");
				for(int a=0; a<result[i][j].size(); a++) {
				//	System.out.print(result[i][j].get(a));
				}
				//System.out.print("} ");
				for(int l=result[i][j].size(); l<3; l++) {
				//	System.out.print(" ");
				}
			}
			//System.out.println();
		}
	}


}
