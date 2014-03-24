package com.brainiac.model;

import com.brainiac.Skeleton;

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
        Skeleton.writeFunctionDetails("BlockageCrystal(EnemyType against, int increment)");
        Skeleton.writeReturnValue("");
        this.against = against;
        this.increment = increment;
    }

    public int getIncrement() {
        Skeleton.writeFunctionDetails("BlockageCrystal.getIncrement()");
        Skeleton.writeReturnValue("int: "+ increment);
        return increment;
    }
}
