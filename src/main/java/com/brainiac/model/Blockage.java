package com.brainiac.model;

import java.util.ArrayList;
import java.util.List;

public class Blockage {
    private Position position;
    private List<BlockageCrystal> crystals;

    public boolean upgraded;

    /**
     * Új akadály létrehozása a pálya egy adott pozíciójára
     *
     * @param position Az akadály pozíciója
     */
    public Blockage(Position position) {
        this.position = position;
        crystals = new ArrayList<BlockageCrystal>();
        upgraded = false;
    }

    /**
     * Visszaadja az akadály pozícióját a pályán
     *
     * @return Az akadály pozíciója
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Az akadály fejlesztése egy adott kristállyal
     *
     * @param crystal A hozzáadott kristály
     */
    public void upgrade(BlockageCrystal crystal) {
        this.crystals.add(crystal);
        upgraded = true;
    }

    /**
     * Lekérdezhető a lassulás mértéke egy ellenség típusra
     *
     * @param enemyType Ellenség típus
     * @return Akadályozás mértéke
     */
    public int block(EnemyType enemyType) {
        int retValue = 0;
        for (BlockageCrystal crystal : crystals) {
            retValue += crystal.getIncrement(enemyType);
        }
        return retValue;
    }
}
