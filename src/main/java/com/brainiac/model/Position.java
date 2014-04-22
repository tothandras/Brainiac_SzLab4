package com.brainiac.model;

public class Position {
    private int x;
    private int y;

    /**
     * @param x: x koordinátája a pozíciónak
     * @param y: y koordinátája a pozíciónak
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Position position) {
        return Math.sqrt(Math.pow(x - position.getX(), 2) + Math.pow(y - position.getY(), 2));
    }

    /**
     * @return: visszatér az x koordinátával
     */
    public int getX() {
        return x;
    }

    /**
     * @return: visszatér az í koordinátával
     */
    public int getY() {
        return y;
    }

    // kulon setter mindegyikre, elteres az eredetitol

    /**
     * @param x: az érték amire átállítjuk az x koordinátát
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y: az érték amire átállítjuk az y koordinátát
     */
    public void setY(int y) {
        this.y = y;
    }
}
