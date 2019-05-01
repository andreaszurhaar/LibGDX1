package com.game.AI;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;

public class Tracking {

    private Point2D.Float currentPosition;
    private float angle;

    public Tracking(Point2D.Float currentP)
    {
        currentPosition = currentP;
        Vector2 point = new Vector2(currentPosition.x, currentPosition.y);
        //setAngle(point.angle());
        angle = trackIntruder();
    }

    public float trackIntruder()
    {
        //call a-star for fastest route to intruder?
        //OR
        //just keep updating the angle and speed to the general direction of the intruder

        return angle;
    }


}
