package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Board;
import com.game.Board.Guard;
import com.game.Board.MapDivider;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GuardPatrolling {
    /**
     * @author Yvar Hulshof
     *
     */

    private MapDivider mp;
    private ArrayList<Point2D> centres;
    private Board board;
    private ArrayList<Agent> guards;
    private final int ALLOWED_DISTANCE_ERROR = 10;

    public GuardPatrolling(Board board){
        this.board = board;
        //board.setUpdateAgentMoveToPoint(true);
        guards = new ArrayList<Agent>();
        guards = findGuards();
        int nrCops = guards.size();
        mp = new MapDivider(nrCops);
        centres = mp.getCentres();
        if(nrCops > 0) initializeGuardLocations();
    }

    public void initializeGuardLocations(){
        for(int i = 0; i < guards.size(); i++){
            board.setUpdateAgentMoveToPoint(true);
            Point2D currentCentre = centres.get(i);
            Agent currentGuard = guards.get(i);
            currentGuard.setDestPoint(currentCentre);
            //Vector2 guardRequiredVector = new Vector2((float)100 - currentGuard.getX(), (float) 100 - currentGuard.getY());
            Vector2 guardRequiredVector = new Vector2((float)currentCentre.getX() - currentGuard.getX(), (float) currentCentre.getY() - currentGuard.getY());
            currentGuard.setAngle(guardRequiredVector.angle());
            System.out.println("centreX: " + currentCentre.getX() + " guardX: " + currentGuard.getX() + " centreY: " + currentCentre.getY() + " guardY: " + currentGuard.getY());
            System.out.println("guard: " + i + " angle: " + guardRequiredVector.angle());
            float guardX = currentGuard.getX();
            float guardY = currentGuard.getY();
            float centreX = (float) currentCentre.getX();
            float centreY = (float) currentCentre.getY();

           // if(Math.sqrt(((guardX - centreX) * (guardX - centreX)) + ((guardY - centreY) * (guardY - centreY))) > ALLOWED_DISTANCE_ERROR)
           //      currentGuard.rotation = (float) -Math.toRadians(Math.random()*currentGuard.turningCircle/2);
        }
    }

    public ArrayList<Agent> findGuards() {
        ArrayList<Agent> agents = board.getAgents();
        for (Agent a : agents) {
            if (a instanceof Guard)
                guards.add(a);
        }
        return guards;
    }





}
