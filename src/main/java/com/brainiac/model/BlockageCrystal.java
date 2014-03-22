package com.brainiac.model;

/**
 * Project name: Brainiac_SzLab4
 * User: tothandras
 * Date: 2014.03.20.
 * Time: 12:11
 */
public class BlockageCrystal {
    private EnemyType against;
    private int increment;

    public BlockageCrystal(EnemyType against, int increment) {
        this.against = against;
        this.increment = increment;
    }

    public int getIncrement() {
        return increment;
    }
}
