package com.brainiac.model;

import java.awt.geom.Line2D;

public class Position {
    private int x;
    private int y;

    /**
     * Pozíció konstruktora
     *
     * @param x x koordinátája a pozíciónak
     * @param y y koordinátája a pozíciónak
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Másik pozíciótol vett távolsága
     *
     * @param position Másik pozíció
     * @return Távolság
     */
    public double distance(Position position) {
        return Math.sqrt(Math.pow(x - position.getX(), 2) + Math.pow(y - position.getY(), 2));
    }

    /**
     * Úttól vett távolság
     * 
     * @param road Út
     * @return Távolság
     */
    public double distanceFromRoad(Line2D road) {
        return road.ptSegDist(x, y);
    }

    /**
     * @return Pozíció x koordinátája
     */
    public int getX() {
        return x;
    }

    /**
     * @return Pozíció y koordinátája
     */
    public int getY() {
        return y;
    }

    /**
     * @param x Pozíció új x koordinátája
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y Pozíció új y koordinátája
     */
    public void setY(int y) {
        this.y = y;
    }
}
