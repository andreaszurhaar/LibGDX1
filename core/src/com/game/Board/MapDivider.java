package com.game.Board;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MapDivider {
    private int boardWidth, boardHeight;
    private int nrCops;


    public MapDivider(int nrCops)
    {
        this.nrCops = nrCops;
        boardHeight = Board.BOARD_HEIGHT;
        boardWidth = Board.BOARD_WIDTH;
        findEqualAreas();
    }

    public void findEqualAreas()
    {
        int nrVertical; //number of boxes/rectangles
        int nrHorizontal;
        if (nrCops%2 == 0)
        {
            nrVertical = nrCops/2;
            if (nrCops %4 == 0){
                nrHorizontal = nrCops/4 ;
            }
            else{
                nrHorizontal = nrCops/nrVertical;
            }

        }
        else {
            nrVertical = nrCops;
            nrHorizontal = 1;
        }
        findCenters(nrVertical,nrHorizontal);
    }

    public void findCenters(int nrVertical, int nrHorizontal){
        ArrayList<Point2D> centre = new ArrayList<Point2D>();

        double width = boardWidth/nrVertical;
        double height = boardHeight/nrHorizontal;

        double tempX = 0.0;
        double tempY = 0.0;
        boolean start = true;


        for (int i = 0; i<nrVertical; i++)
        {
            if (start)
            {
                tempX = width/2;
            }
            else{
                tempX = tempX + width;
            }

            for (int j = 0; j<nrHorizontal; j++)
            {
                if (start)
                {
                    tempY = height/2;
                }
                else{
                    tempY = tempY + height;
                }
                centre.add(new Point2D.Double(tempX,tempY));
                start = false;
            }
            tempY = height/2;
        }

    }
}
