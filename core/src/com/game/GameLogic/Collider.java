package com.game.GameLogic;

import com.game.Objects.GameObject;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Collider {

    public Collider(){

    }

    public ArrayList<GameObject> copVsRobber(ArrayList<GameObject> robbers, ArrayList<GameObject> cops){
       /* Checks if  any of the agents or*/
       if(cops.size() > 0 && robbers.size() > 0) {
           for (int i = 0; i < cops.size(); i++) {
               for (int j = 0; j < robbers.size(); j++) {
                   if (cops.get(i).bounds.overlaps(robbers.get(j).bounds) == true) {
                       System.out.println("made it");
                       robbers.remove(j);
                   }
               }
           }
       }
        return robbers;
    }

}
