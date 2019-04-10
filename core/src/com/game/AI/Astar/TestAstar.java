package com.game.AI.Astar;

import java.util.ArrayList;
import com.game.Board.*;

public class TestAstar {

    public static void main(String[] args)
    {

        //IMPORTANT!! IT ENTERS A INFINITE LOOP AFTER A CERTAIN MAP SIZE

        ArrayList<Area> areas = new ArrayList<Area>();
        //areas.add(new Structure(1,1,1,1));
        System.out.println("Running a-star");
        double start = System.currentTimeMillis();

        Astar test = new Astar(areas);
        double end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end-start)/1000 + " s" );

    }
}
