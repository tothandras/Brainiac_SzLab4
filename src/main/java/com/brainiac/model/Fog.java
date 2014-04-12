package com.brainiac.model;

public class Fog {
    private Position middle;
    private int range;

    /**
     * A Fog osztály konstruktora.
     *
     * @param middle A létrehozott új köd középpontja.
     * @param range  A létrehozott új köd sugara.
     */
    public Fog(Position middle, int range) {
        this.middle = middle;
        this.range = range;
    }

    /**
     * A köd osztály hatósugarát lehet ezzel a függvénnyel lekérdezni.
     *
     * @return Visszatér a köd sugarával.
     */
    public int getRange() {
        return range;
    }

    /**
     * A köd osztály középpontját lehet ezzel a függvénnyel lekérdezni.
     *
     * @return Visszatér a köd középpontjával.
     */
    public Position getMiddle() {
        return middle;
    }
}
