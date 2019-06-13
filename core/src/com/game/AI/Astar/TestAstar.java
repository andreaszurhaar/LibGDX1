package com.game.AI.Astar;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import com.game.Board.*;

/**
 * @author Famke Nouwens
 */
public class TestAstar {

    public static void main(String[] args)
    {
//
//        ArrayList<Area> areas = new ArrayList<Area>();
////        areas.add(new Structure(1,1,1,1,true));
//        System.out.println("Running a-star");
//        double start = System.currentTimeMillis();
//
//       // Astar test = new Astar(areas);
//        double end = System.currentTimeMillis();
//        System.out.println("Time taken: " + (end-start)/1000 + " s" );

       /* Point2D point1 = new Point2D.Float(1,1);
        Point2D point2 = new Point2D.Float(2,3);
        Line2D.Float line2D = new Line2D.Float(point1, point2);

        Rectangle2D.Float rect = new Rectangle2D.Float(0,2,2,2);

        boolean test = line2D.intersects(rect);
        System.out.println("Line intersects rectangle?: " + test);*/

        Rectangle2D.Float rect1 = new Rectangle2D.Float(0,2,2,1);
        Rectangle2D.Float rect2 = new Rectangle2D.Float(3,8,3,1);
        Rectangle2D.Float rect3 = new Rectangle2D.Float(6,4,1,2);
        Rectangle2D.Float rect4 = new Rectangle2D.Float(1,5,2,2);
        Rectangle2D.Float rect5 = new Rectangle2D.Float(6,1,3,1);

        ArrayList<Rectangle2D.Float> rectangles = new ArrayList<Rectangle2D.Float>();
        rectangles.add(rect1);
        rectangles.add(rect2);
        rectangles.add(rect3);
        rectangles.add(rect4);
        rectangles.add(rect5);

        AStarNew astar = new AStarNew(rectangles, 1f,1f,2f,10f);
    }
}
