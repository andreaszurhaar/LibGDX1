package com.game.Board;

import java.util.ArrayList;

public class SoundOccurence {

    private long time;
    ArrayList<Integer> position;
    int cell;

    public SoundOccurence(long time, ArrayList<Integer> positionTracker, int cell) {
        this.time = time;
        this.position = position;
        this.cell = cell;
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
