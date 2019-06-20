package com.game.Readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TestWriter {

    String outputFile;

    public TestWriter(String level, float time, String win, float guard_distance, float intruder_distance  ){

        outputFile = level;
        try {
            FileWriter fileWriter = new FileWriter(outputFile,true);

            fileWriter.append("\n");
            fileWriter.append("\n");

            fileWriter.write("time :" + time);
            fileWriter.write(" / ");
            fileWriter.write(win);
            fileWriter.write(" / ");
            fileWriter.write("guard distance : " + guard_distance);
            fileWriter.write(" / ");
            fileWriter.write("intruder distance : " + intruder_distance);

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
