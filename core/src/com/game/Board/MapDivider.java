package com.game.Board;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Famke Nouwens
 */
public class MapDivider {
    private int boardWidth, boardHeight;
    private int nrCops;
    private float width, height;
    private ArrayList<Point2D.Float> centres;


    public MapDivider(int nrCops)
    {
        this.nrCops = nrCops;
        boardHeight = Board.BOARD_HEIGHT;
        boardWidth = Board.BOARD_WIDTH;
        findEqualAreas();
    }

    public void findEqualAreas()
    {
        int nrVertical=1; //number of boxes/rectangles
        int nrHorizontal=1;
        if (nrCops%2 == 0)
        {
            nrVertical = nrCops/2;
            if (nrCops %4 == 0){
                //TODO doesn't work with 8 guards yet
                nrHorizontal = nrCops/4 +1;
            }
            else{
                nrHorizontal = nrCops/nrVertical;
                System.out.println("nrHorizontal=" + nrHorizontal);
            }

        }
        else {
            nrVertical = nrCops;
            nrHorizontal = 1;
            System.out.println("nrHorizontal=" + nrHorizontal);
        }
        System.out.println("nrVertical=" + nrVertical);
        centres = findCenters(nrVertical,nrHorizontal);
    }


    public ArrayList<Point2D.Float> findCenters(int nrVertical, int nrHorizontal){
        ArrayList<Point2D.Float> centres = new ArrayList<Point2D.Float>();

        //if(nrVertical == 0 ) nrVertical = 1;
        //if(nrHorizontal == 0 ) nrHorizontal = 1;

        width = boardWidth/nrVertical;
        height = boardHeight/nrHorizontal;


        float tempX = 0.0f;
        float tempY = 0.0f;
        boolean start = true;
        boolean startY = true;


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
                if (startY)
                {
                    tempY = height/2;
                }
                else{
                    tempY = tempY + height;
                }
                centres.add(new Point2D.Float(tempX*5.0f,tempY*5.0f));
                start = false;
                startY = false;
            }
            tempY = height/2;
            startY = true;
        }
        return centres;
    }

    public ArrayList<Point2D.Float> getCentres() {
        return centres;
    }

    public Float getBorderWidth()    {    return width;     }

    public Float getBorderHeight()   {    return height;    }
}


