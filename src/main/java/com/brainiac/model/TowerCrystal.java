package com.brainiac.model;

public class TowerCrystal {
    private EnemyType against;
    private int increment;
    private int speedIncrement;

    public TowerCrystal(EnemyType against, int increment) {
        this.against = against;
        this.increment = increment;
    }

    public TowerCrystal(int speedIncrement) {
        this.speedIncrement = speedIncrement;
    }

    public int getIncrement(EnemyType enemyType) {
        return increment;
    }

    public int getSpeedIncrement() {
        return speedIncrement;
    }
}
