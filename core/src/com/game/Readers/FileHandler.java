package com.game.Readers;

import com.game.Board.Agent;
import com.game.Board.Area;
import com.game.Board.Structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
                    fileWriter.write(" ");
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
                        fileWriter.write(" ");
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
                    Integer width = (int) structures.get(i).area.width;
                    Integer height = (int) walls.get(i).area.height;

                    if (walls.get(i).doorsAndWindows.size() == 0) {

                        fileWriter.write(Integer.toString(xPos));
                        fileWriter.write(":");
                        fileWriter.write(Integer.toString(yPos));
                        fileWriter.write(":");
                        fileWriter.write(Integer.toString(width));
                        fileWriter.write(":");
                        fileWriter.write(Integer.toString(height));
                        fileWriter.write(" ");
                    }
                    else{

                        for(int j = 0; j < walls.get(i).doorsAndWindows.size(); j++) {
                            Integer x = (int) walls.get(i).doorsAndWindows.get(j).xPos;
                            Integer y = (int) walls.get(i).doorsAndWindows.get(j).yPos;
                            Integer w = (int) walls.get(i).doorsAndWindows.get(j).area.width;
                            Integer h = (int) walls.get(i).doorsAndWindows.get(j).area.height;

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
                            fileWriter.write(" ");

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
        /*
        File file=new File("Levels.txt");
        Scanner sc= null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNextLine()){
            while(sc.next() != ":") {
                System.out.println(sc.next(":"));
            }
        }
        */

         ArrayList<ArrayList> master = new ArrayList<ArrayList>();
         master.add(agents);
         master.add(structures);
         master.add(walls);
         return master;
    }

}
