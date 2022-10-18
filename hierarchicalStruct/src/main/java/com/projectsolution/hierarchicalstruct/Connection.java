package com.projectsolution.hierarchicalstruct;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.io.Serializable;

/**
 * Connection class
 */
public class Connection extends Group implements Serializable {

    /**
     * top element
     */
    Task top;
    /**
     * bottom element
     */
    Task bottom;

    /**
     * @param top element
     * @param bottom element
     */
    public Connection(Task top, Task bottom)
    {
        super(new Line(getPointsOfConnection(top, bottom).item1.getX(),
                getPointsOfConnection(top, bottom).item1.getY(),
                getPointsOfConnection(top, bottom).item1.getX(),
                getPointsOfConnection(top, bottom).item2.getY()),
                new Line(getPointsOfConnection(top, bottom).item1.getX(),
                getPointsOfConnection(top, bottom).item2.getY(),
                getPointsOfConnection(top, bottom).item2.getX(),
                getPointsOfConnection(top, bottom).item2.getY()));
        this.top=top;
        this.bottom=bottom;
    }

    /**
     * @param parent element
     * @param child element
     * @return list of points
     */
    public static ConnectPoint<Point2D, Point2D> getPointsOfConnection(Task parent, Task child) {
        var fromPoints = parent.getArrayOfMinMaxPointsTop();
        var toPoints = child.getArrayOfMinMaxPointsBottom();
        Point2D pointFromFinal = Point2D.ZERO;
        Point2D pointToFinal = Point2D.ZERO;
        double lowestDistance = Double.POSITIVE_INFINITY;
        for (Point2D fromPoint : fromPoints) {
            for (Point2D toPoint : toPoints) {
                var newDistance = fromPoint.distance(toPoint);
                if (newDistance < lowestDistance) {
                    pointFromFinal = fromPoint;
                    pointToFinal = toPoint;
                    lowestDistance = newDistance;
                }
            }
        }
        return new ConnectPoint<>(pointFromFinal, pointToFinal);
    }
}
