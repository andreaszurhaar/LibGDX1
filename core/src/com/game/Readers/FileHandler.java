package com.game.Readers;
import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Guard;
import com.game.Board.Intruder;
import com.game.Board.LowVisionArea;
import com.game.Board.OuterWall;
import com.game.Board.SentryTower;
import com.game.Board.Structure;
import com.game.Board.TargetArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

    public FileHandler(){}

    public void fileWriter(ArrayList<Agent> agents, ArrayList<Area> structures, ArrayList<Structure> walls) throws FileNotFoundException {

        String outputFile = "Levels.txt";

        try {
            FileWriter fileWriter = new FileWriter(outputFile,true);

            fileWriter.append("\n");
            fileWriter.append("\n");
            if(agents.size() > 0) {
                for (int i = 0; i < agents.size(); i++) {
                    String string = agents.get(i).name;
                    fileWriter.write(string);
                    fileWriter.write(":");
                    Integer xPos = (int) agents.get(i).xPos;
                    Integer yPos = (int) agents.get(i).yPos;
                    Integer width = (int) agents.get(i).area.width;
                    Integer height = (int) agents.get(i).area.height;
                    fileWriter.write(Integer.toString(xPos));
                    fileWriter.write(":");
                    fileWriter.write(Integer.toString(yPos));
                    fileWriter.write(":");
                    fileWriter.write(Integer.toString(width));
                    fileWriter.write(":");
                    fileWriter.write(Integer.toString(height));
                    fileWriter.write("/");
                }
            }
            fileWriter.append("\n");

                if(structures.size() > 0) {
                    for (int i = 0; i < structures.size(); i++) {
                        String string = structures.get(i).name;
                        fileWriter.write(string);
                        fileWriter.write(":");
                        Integer xPos = (int) structures.get(i).xPos;
                        Integer yPos = (int) structures.get(i).yPos;
                        Integer width = (int) structures.get(i).area.width;
                        Integer height = (int) structures.get(i).area.height;
                        fileWriter.write(Integer.toString(xPos));
                        fileWriter.write(":");
                        fileWriter.write(Integer.toString(yPos));
                        fileWriter.write(":");
                        fileWriter.write(Integer.toString(width));
                        fileWriter.write(":");
                        fileWriter.write(Integer.toString(height));
                        fileWriter.write("/");
                    }

                }

            fileWriter.append("\n");
            if(walls.size() > 0) {
                for (int i = 0; i < walls.size(); i++) {
                    if (walls.get(i).horizontal) {
                        fileWriter.write("1");
                    } else {
                        fileWriter.write("2");
                    }
                    fileWriter.write(":");
                    Integer xPos = (int) walls.get(i).xPos;
                    Integer yPos = (int) walls.get(i).yPos;
                    Integer width = (int) walls.get(i).area.width;
                    Integer height = (int) walls.get(i).area.height;
                    String name = walls.get(i).name;

                    if (walls.get(i).doorsAndWindows.size() == 0) {
                        fileWriter.write(name);
                        fileWriter.write(":");
                        fileWriter.write(Integer.toString(xPos));
                        fileWriter.write(":");
                        fileWriter.write(Integer.toString(yPos));
                        fileWriter.write(":");
                        fileWriter.write(Integer.toString(width));
                        fileWriter.write(":");
                        fileWriter.write(Integer.toString(height));
                        fileWriter.write("/");
                    }
                    else{

                        for(int j = 0; j < walls.get(i).doorsAndWindows.size(); j++) {
                            Integer x = (int) walls.get(i).doorsAndWindows.get(j).xPos;
                            Integer y = (int) walls.get(i).doorsAndWindows.get(j).yPos;
                            Integer w = (int) walls.get(i).doorsAndWindows.get(j).area.width;
                            Integer h = (int) walls.get(i).doorsAndWindows.get(j).area.height;

                            fileWriter.write(name);
                            fileWriter.write(":");
                            fileWriter.write(Integer.toString(xPos));
                            fileWriter.write(":");
                            fileWriter.write(Integer.toString(yPos));
                            fileWriter.write(":");
                            fileWriter.write(Integer.toString(width));
                            fileWriter.write(":");
                            fileWriter.write(Integer.toString(height));
                            fileWriter.write(":");
                            fileWriter.write(Integer.toString(xPos));
                            fileWriter.write(":");
                            fileWriter.write(Integer.toString(yPos));
                            fileWriter.write(":");
                            fileWriter.write(Integer.toString(width));
                            fileWriter.write(":");
                            fileWriter.write(Integer.toString(height));
                            fileWriter.write("/");

                        }

                    }
                }

            }


            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<ArrayList> fileReader(int level){
         ArrayList<Agent> agents = new ArrayList<Agent>();
         ArrayList<Area> structures = new ArrayList<Area>();
         ArrayList<Structure> walls = new ArrayList<Structure>();

        File file=new File("Levels.txt");
        Scanner sc= null;
        /*
        sets the starting line to the 0 level
        */

        try {
            sc = new Scanner(file);
            for(int i = 0; i < 4; i++) {
                sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*
        Sets the scanner the appropriate level
         */
        for(int i = level*4; i > 0; i--){
            sc.nextLine();
        }

        String line = sc.nextLine();
        String[] agentObjects = line.split("/");
        ArrayList<String[]> data = new ArrayList<String[]>();
        String[] string;

        /*reads from the first line aka the
            agents objects
         */

        for(int i = 0; i < agentObjects.length; i++) {

            string =  agentObjects[i].split(":");
            data.add(string);
        }

        for(int i = 0; i < data.size(); i++) {

               //Checks first index to see what kind of agent to make
                if(Integer.parseInt(data.get(i)[0]) == 1){

                    agents.add(new Intruder((Integer.parseInt(data.get(i)[1])),Integer.parseInt(data.get(i)[2]),Integer.parseInt(data.get(i)[3]),Integer.parseInt(data.get(i)[4])));

                }
                else{

                    agents.add(new Guard((Integer.parseInt(data.get(i)[1])),Integer.parseInt(data.get(i)[2]),Integer.parseInt(data.get(i)[3]),Integer.parseInt(data.get(i)[4])));
                }
        }

         /*reads from the SECOND line aka the
            structures objects
         */

        String line2 = sc.nextLine();
        String[] agentObjects2 = line2.split("/");
        ArrayList<String[]> data2 = new ArrayList<String[]>();
        String[] string2;



        for(int i = 0; i < agentObjects2.length; i++) {

            string2 =  agentObjects2[i].split(":");
            data2.add(string2);
        }

        for(int i = 0; i < data2.size(); i++) {

            //Checks first index to see what kind of agent to make
            if(Integer.parseInt(data2.get(i)[0]) == 3){

                structures.add(new OuterWall((Integer.parseInt(data2.get(i)[1])),Integer.parseInt(data2.get(i)[2]),Integer.parseInt(data2.get(i)[3]),Integer.parseInt(data2.get(i)[4])));

            }

            if(Integer.parseInt(data2.get(i)[0]) == 4){

                structures.add(new LowVisionArea((Integer.parseInt(data2.get(i)[1])),Integer.parseInt(data2.get(i)[2]),Integer.parseInt(data2.get(i)[3]),Integer.parseInt(data2.get(i)[4])));

            }

            if(Integer.parseInt(data2.get(i)[0]) == 8){

                structures.add(new TargetArea((Integer.parseInt(data2.get(i)[1])),Integer.parseInt(data2.get(i)[2]),Integer.parseInt(data2.get(i)[3]),Integer.parseInt(data2.get(i)[4])));

            }

            if(Integer.parseInt(data2.get(i)[0]) == 9){

                structures.add(new SentryTower((Integer.parseInt(data2.get(i)[1])),Integer.parseInt(data2.get(i)[2]),Integer.parseInt(data2.get(i)[3]),Integer.parseInt(data2.get(i)[4])));

            }

        }

         /*reads from the THIRD line aka the
            wall objects with door/windows
         */


        String line3 = sc.nextLine();
        String[] agentObjects3 = line3.split("/");
        ArrayList<String[]> data3 = new ArrayList<String[]>();
        String[] string3;



        for(int i = 0; i < agentObjects3.length; i++) {

            string3 =  agentObjects3[i].split(":");
            data3.add(string3);
        }

        for(int i = 0; i < data3.size(); i++) {

            //Checks first index to see if it should be a vertical or horizontal wall
            if(Integer.parseInt(data3.get(i)[0]) == 1) {
                if (data3.get(i).length > 6 ) {


                    if (Integer.parseInt(data3.get(i)[1]) == 5) {
                        System.out.println("made it to 5");
                        Structure structure = new Structure((Integer.parseInt(data3.get(i)[2])), Integer.parseInt(data3.get(i)[3]), Integer.parseInt(data3.get(i)[4]), Integer.parseInt(data3.get(i)[5]), true);
                        structure.placeDoor((Integer.parseInt(data3.get(i)[6])+40), Integer.parseInt(data3.get(i)[7]));
                        walls.add(structure);
                    }
                    if (Integer.parseInt(data3.get(i)[1]) == 6) {
                        System.out.println("made it to 6");
                        Structure structure = new Structure((Integer.parseInt(data3.get(i)[2])), Integer.parseInt(data3.get(i)[3]), Integer.parseInt(data3.get(i)[4]), Integer.parseInt(data3.get(i)[5]), true);
                        structure.placeWindow((Integer.parseInt(data3.get(i)[6])+40), Integer.parseInt(data3.get(i)[7]));
                        walls.add(structure);
                    }
                }
                else {
                    walls.add(new Structure((Integer.parseInt(data3.get(i)[2])), Integer.parseInt(data3.get(i)[3]), Integer.parseInt(data3.get(i)[4]), Integer.parseInt(data3.get(i)[5]), true));
                }
            }

            if(Integer.parseInt(data3.get(i)[0]) == 2) {
                if (data3.get(i).length > 6 ) {


                    if (Integer.parseInt(data3.get(i)[1]) == 5) {
                        Structure structure = new Structure((Integer.parseInt(data3.get(i)[2])), Integer.parseInt(data3.get(i)[3]), Integer.parseInt(data3.get(i)[4]), Integer.parseInt(data3.get(i)[5]), false);
                        structure.placeDoor((Integer.parseInt(data3.get(i)[6])), Integer.parseInt(data3.get(i)[7])+40);
                        walls.add(structure);
                    }
                    if (Integer.parseInt(data3.get(i)[1]) == 6) {
                        Structure structure = new Structure((Integer.parseInt(data3.get(i)[2])), Integer.parseInt(data3.get(i)[3]), Integer.parseInt(data3.get(i)[4]), Integer.parseInt(data3.get(i)[5]), false);
                        structure.placeWindow((Integer.parseInt(data3.get(i)[6])), Integer.parseInt(data3.get(i)[7])+40);
                        walls.add(structure);
                    }
                }

                else {
                    System.out.println("made it to else");
                    walls.add(new Structure((Integer.parseInt(data3.get(i)[2])), Integer.parseInt(data3.get(i)[3]), Integer.parseInt(data3.get(i)[4]), Integer.parseInt(data3.get(i)[5]), false));
                }
            }

        }

         ArrayList<ArrayList> master = new ArrayList<ArrayList>();
         master.add(agents);
         master.add(structures);
         master.add(walls);
        System.out.print("Agents" + "" + agents.size());
        System.out.print("struc" + "" + structures.size());
        System.out.println("walls" + "" + walls.size());

         return master;
    }

}
