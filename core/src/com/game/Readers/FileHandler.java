package com.game.Readers;

import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Structure;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHandler {

    public FileHandler(){}

    public void fileWriter(String string) throws FileNotFoundException {
        if (string.length() < 1) {

            String outputFile = "Levels.txt";

            try {
                ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(outputFile));
                objectOutput.writeObject(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<ArrayList> fileReader(int level){
         ArrayList<Agent> agents = new ArrayList<Agent>();
         ArrayList<Area> structures = new ArrayList<Area>();
         ArrayList<Structure> walls = new ArrayList<Structure>();
         /* Need to fill this out

          */

         ArrayList<ArrayList> master = new ArrayList<ArrayList>();
         master.add(agents);
         master.add(structures);
         master.add(walls);
         return master;
    }

}
