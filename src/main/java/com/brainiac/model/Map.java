package com.brainiac.model;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private int width;
    private int height;
    private List<Path> paths;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        paths = new ArrayList<Path>();
        Path path = new Path();

        /*Line2D road1 = new Line2D.Double(0, 2, 9, 2);
        Line2D road2 = new Line2D.Double(width / 3.0, height / 2.0, width / 3.0, 2.0 * height / 3.0);
        Line2D road3 = new Line2D.Double(width / 3.0, 2.0 * height / 3.0, 2.0 * width / 3.0, 2.0 * height / 3.0);
        Line2D road4 = new Line2D.Double(2.0 * width / 3.0, 2.0 * height / 3.0, 2.0 * width / 3.0, height / 2.0);
        Line2D road5 = new Line2D.Double(2.0 * width / 3.0, height / 2.0, width, height / 2.0);*/
        /*path.roads.add(road1);
        path.roads.add(road2);
        path.roads.add(road3);
        path.roads.add(road4);
        path.roads.add(road5);*/
        paths.add(path);
    }

    public void setPaths(Path path)
    {
        paths.add(path);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Path> getPaths() {
        return paths;
    }
}
