package com.brainiac.model;

import com.brainiac.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class Blockage {
    private Position position;
    private List<BlockageCrystal> crystals;

    /**
     * A Blockage osztály konstruktora.
     *
     * @param position Az új akadály pozíciója.
     */
    public Blockage(Position position) {
        this.position = position;
        crystals = new ArrayList<BlockageCrystal>();
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
    }

    /**
     * Megmondja a lassulás mértéket, amikor egy ellenség az akadályon megpróbál áthaladni.
     *
     * @param enemyType Milyen fajta ellenség ellen vagyunk kíváncsiak a lassulás mértékére.
     * @return Visszaadja hányadára csökkenjen az egység sebessége.
     */
    public double block(EnemyType enemyType) {
        double retValue = 1.0;
        for (BlockageCrystal crystal : crystals) {
            retValue *= crystal.getIncrement(enemyType);
        }
        return 0;
    }
}
