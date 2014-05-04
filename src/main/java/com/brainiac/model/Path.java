package com.brainiac.model;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    // Útvonal több egyenes útszakaszból áll
    private List<Line2D> roads;

    public Path() {
        roads = new ArrayList<Line2D>();
    }

    public List<Line2D> getRoads() {
        return Collections.unmodifiableList(roads);
    }

    public void addRoad(Line2D road) {
        roads.add(road);
    }

    public static Direction getDirection(Line2D road) {
        if (Math.abs(road.getX2() - road.getX1()) < 5 && road.getY2() > road.getY1()) {
            // Észak
            return Direction.NORTH;
        } else if (road.getX2() == road.getX1() && road.getY2() < road.getY1()) {
            // Dél
            return Direction.SOUTH;
        } else if (road.getX2() > road.getX1() && road.getY2() == road.getY1()) {
            // Kelet
            return Direction.EAST;
        } else {
            // Nyugat
            return Direction.WEST;
        }
    }
}
