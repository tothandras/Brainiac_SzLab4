package com.brainiac.model;

import com.brainiac.controller.Skeleton;

import java.util.List;

/**
 * Project name: Brainiac_SzLab4
 * User: tothandras
 * Date: 2014.03.20.
 * Time: 12:09
 */
public class Blockage {
    private Position position;
    private List<BlockageCrystal> crystals;

    public Blockage(Position position) {
        this.position = position;
    }

    public Position getPosition() {

        return position;
    }


    void upgrade(BlockageCrystal crystal) {

    }

    int block(EnemyType enemyType) {

        Skeleton.writeFunctionDetails("Blockage.block(EnemyType enemyType)");
        Skeleton.writeReturnValue("0");

        return 0;
    }
}
