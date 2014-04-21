package com.brainiac.model;

import com.brainiac.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class Blockage {
    private Position position;
    private List<BlockageCrystal> crystals;
    /**
     * mivel már nincs lista a kristályokról, így tudunk a legegyszerűbben
     * információt kapni arról, hogy fejlesztett-e a torony
     */
    public boolean upgraded;

    /**
     * A Blockage osztály konstruktora.
     *
     * @param position Az új akadály pozíciója.
     */
    public Blockage(Position position) {
        this.position = position;
        crystals = new ArrayList<BlockageCrystal>();
        upgraded = false;
    }

    /**
     * Visszaadja az akadály pozícióját a pályán.
     *
     * @return Az akadály pozíciója.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Az akadály fejlesztése egy adott kristállyal.
     *
     * @param crystal A hozzáadott kristály.
     */
    public void upgrade(BlockageCrystal crystal) {
        this.crystals.add(crystal);
        upgraded = true;
    }

    /**
     * Megmondja a lassulás mértéket, amikor egy ellenség az akadályon megpróbál áthaladni.
     *
     * @param enemyType Milyen fajta ellenség ellen vagyunk kíváncsiak a lassulás mértékére.
     * @return Visszaadja hányadára csökkenjen az egység sebessége.
     */
    public int block(EnemyType enemyType) {
        int retValue = 0;
        for (BlockageCrystal crystal : crystals) {
            retValue += crystal.getIncrement(enemyType);
        }
        return retValue;
    }
}
