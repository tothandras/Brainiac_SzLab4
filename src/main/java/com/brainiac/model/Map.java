package com.brainiac.model;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Map {
    // Pálya szélessége
    private int width;
    // Pálya magassága
    private int height;
    // Útak
    private List<Path> paths;

    /**
     * Pálya konstruktora
     *
     * @param width  Szélesség
     * @param height Magasság
     */
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        paths = new ArrayList<Path>();

        // Előre beállított útvonalak
        // Fontos:  Minden útszakasz kezdőpontja az előző végpontja kell hogy legyen
        //          Minden végpontnak (width, height / 2.0)-ben kell lennie
        // 1.
        Path path1 = new Path();
        Line2D road1 = new Line2D.Double(0, height / 2.0, width / 3.0, height / 2.0);
        Line2D road2 = new Line2D.Double(width / 3.0, height / 2.0, width / 3.0, 2.0 * height / 3.0);
        Line2D road3 = new Line2D.Double(width / 3.0, 2.0 * height / 3.0, 2.0 * width / 3.0, 2.0 * height / 3.0);
        Line2D road4 = new Line2D.Double(2.0 * width / 3.0, 2.0 * height / 3.0, 2.0 * width / 3.0, height / 2.0);
        Line2D road5 = new Line2D.Double(2.0 * width / 3.0, height / 2.0, width, height / 2.0);
        path1.addRoad(road1);
        path1.addRoad(road2);
        path1.addRoad(road3);
        path1.addRoad(road4);
        path1.addRoad(road5);
        paths.add(path1);
        // 2.
        Path path2 = new Path();
        Line2D road6 = new Line2D.Double(0, height / 2.0, width / 3.0, height / 2.0);
        Line2D road7 = new Line2D.Double(width / 3.0, height / 2.0, width / 3.0, height / 3.0);
        Line2D road8 = new Line2D.Double(width / 3.0, height / 3.0, 2.5 * width / 3.0, height / 3.0);
        Line2D road9 = new Line2D.Double(2.5 * width / 3.0, height / 3.0, 2.5 * width / 3.0, height / 2.0);
        Line2D road10 = new Line2D.Double(2.5 * width / 3.0, height / 2.0, width, height / 2.0);
        path2.addRoad(road6);
        path2.addRoad(road7);
        path2.addRoad(road8);
        path2.addRoad(road9);
        path2.addRoad(road10);
        paths.add(path2);
    }

    /**
     * @return Pálya szélesség
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Pálya magassága
     */
    public int getHeight() {
        return height;
    }

    /**
     * Útvonalak lekérdezése
     *
     * @return Útvonalak listálya
     */
    public List<Path> getPaths() {
        return Collections.unmodifiableList(paths);
    }
}
