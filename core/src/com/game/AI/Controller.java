package com.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.game.Board.Agent;
import com.game.Board.Board;
import com.game.Board.Guard;
import com.game.Board.Intruder;
import com.game.Board.MapDivider;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Controller {

    private ArrayList<Guard> guards;
    private ArrayList<Intruder> intruders;
    private Board board;
    private ArrayList<Point2D.Float> centres;


    public Controller(Board board){
        this.board = board;
        guards = new ArrayList<Guard>();
        intruders = new ArrayList<Intruder>();
        findAgents();
        //take input from menu or something

        //start patrolling
        patrolling();
    }


       public void findAgents() {
        ArrayList<Agent> agents = board.getAgents();
        for (Agent a : agents) {
            if (a instanceof Guard) {
                guards.add((Guard) a);
            }
            if(a instanceof Intruder){
                intruders.add((Intruder) a);
            }
        }
    }

    public void patrolling()
    {
        //call mapdivider to calculate the number of independent areas to patrol
        MapDivider mapDivider = new MapDivider(guards.size());
        centres = mapDivider.getCentres();
        if(guards.size() > 0) {
            initializeGuardLocations();
        }
        for (Guard g : guards)
        {
            g.patrolling();
        }
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
}
