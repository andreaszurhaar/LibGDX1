package com.game.AI;

import java.lang.Math;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Board;
import com.game.Board.Guard;
import com.game.Board.MapDivider;
import com.game.States.MainState;

import java.awt.Point;

/**
 * When calling the constructor of the class all the x-y location of the centers of the areas and the staring location of the guards are put in arraylist.
 * The algorithm returns an arraylist of Point2D.Float where for each element of the array (that represents a cop, in the order with which it got them) contains the location of the nearest untaken area-center
 *
 */

public class CopsCenters {

    private MapDivider mp;
    private MainState ms;
    private ArrayList<Point2D.Float> centres;
    private ArrayList<Point2D.Float> guardsLoc;
    private ArrayList<Point2D.Float> guardCenter;
    private ArrayList<Agent> guards;
    private ArrayList<ArrayList<Double>> distances;
    private ArrayList<Double> dist;
    private ArrayList<Double> standDev;
    private ArrayList<Integer> ordered;
    private ArrayList<Integer> coupled;
    private Board board;
    private Guard guard;
    private int i,j,pos;
    private double maxSD,minDist;

    public CopsCenters () {
        centres = mp.getCentres();
        guards = ms.getGuards();

        for (i=0;i<centres.size();i++) {
            guardsLoc.get(i).setLocation(guards.get(i).getX(),guards.get(i).getY());
        }
    }

    // This method computes the distance between each guard and all the possible area-centers. It also computes the standard deviation for the distances of each cop
    public void setDistances () {
        for (i=0;i<centres.size();i++) {
            distances.set(i,computeEuclideanDist(i));
        }
        for (i=0;i<centres.size();i++) {
            standDev.set(i,calculateSD(distances.get(i)));
        }
    }

    // Compute euclidean distance between a guard and all the area-centers
    public ArrayList<Double> computeEuclideanDist(int i) {
        for (j=0;j<centres.size();j++) {
        dist.set(j,Math.sqrt((Math.pow(centres.get(j).getX()-guardsLoc.get(i).getX(),2))+(Math.pow(centres.get(j).getY()-guardsLoc.get(i).getY(),2))));
        }
        return dist;
    }

    // computes Standard Deviation for each guard
    public double calculateSD(ArrayList<Double> span)
    {
        double sum = 0.0;
        double standardDeviation = 0.0;
        int length = span.size();
        int i;

        for (i=0;i<length;i++) {
            sum += span.get(i);
        }

        double mean = sum/length;
        for (i=0;i<length;i++) {
            standardDeviation += Math.pow(span.get(i) - mean, 2);
        }
        return Math.sqrt(standardDeviation/length);
    }

    // The method orders the guards by highest standard deviation w.r.t. distances. The guard with highest standard deviation will be the first to receive the nearest center
    public void order () {
        for (j=0;j<centres.size();j++) {
            maxSD=0;
            pos=0;
            for (i=0;i<centres.size();i++)  {
                if (maxSD<standDev.get(i)) {
                        maxSD=standDev.get(i);
                        pos=i;
                }
            }
            standDev.set(pos,-standDev.get(pos));
            ordered.add(pos);
        }
    }

    // this is the method to call to get the total output of the algorithm
    public ArrayList<Point2D.Float> getCenters () {
        setDistances();
        order();
        for (i=0;i<centres.size();i++) {
            coupled.add(ordered.get(i),findMin(distances.get(i)));
        }

        for (i=0;i<centres.size();i++) {
            guardCenter.set(coupled.get(i),centres.get(i));
        }
        return guardCenter;
    }

    //return position in arraylist of nearest untaken center
    public int findMin (ArrayList<Double> dists) {
        minDist=1000000;
        for (i=0;i<centres.size();i++)  {
            if(!coupled.contains(i)) {
                if (minDist>dists.get(i)) {
                    minDist=dists.get(0);
                    pos=i;
                }
            }
        }
        return pos;
    }
}
