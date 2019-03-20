package com.game.Board;

import java.util.ArrayList;

public class SoundOccurence {

    final int SOUND_RANGE = 10;
    private long time;
    ArrayList<Integer> position;
    int cell;
    double xpos;
    double ypos;

    public SoundOccurence(long time, ArrayList<Integer> positionTracker, int cell) {
        this.time = time;
        this.position = position;
        this.cell = cell;
    }

    public SoundOccurence(long time, double xpos, double ypos) {
        this.time = time;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public long getTime() {
        return time;
    }

    public ArrayList<Integer> getPosition(){
        return position;
    }

    public int getCell() {
        return cell;
    }
}
