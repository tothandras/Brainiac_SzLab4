package com.brainiac.model;

import com.brainiac.controller.Skeleton;

public class TowerCrystal {
    private EnemyType against;
    private int increment;
    private int speedIncrement;

    public TowerCrystal(EnemyType against, int increment) {
        Skeleton.writeFunctionDetails("TowerCrystal(EnemyType against, int increment)");
        this.against = against;
        this.increment = increment;
        Skeleton.writeReturnValue("");
    }

    public TowerCrystal(int speedIncrement) {
        Skeleton.writeFunctionDetails("TowerCrystal(int speedIncrement)");
        this.speedIncrement = speedIncrement;
        Skeleton.writeReturnValue("");
    }

    public int getIncrement(EnemyType enemyType) {
        Skeleton.writeFunctionDetails("TowerCrystal.getIncrement(EnemyType enemyType)");
        Skeleton.writeReturnValue("int: "+increment);
        return increment;
    }

    public int getSpeedIncrement() {
        Skeleton.writeFunctionDetails("TowerCrystal.getIncrement(EnemyType enemyType)");
        Skeleton.writeReturnValue("int: "+increment);
        return speedIncrement;
    }
}
