package com.brainiac.model;

import com.brainiac.controller.Skeleton;

import java.util.List;

public class Blockage {
    private Position position;
    private List<BlockageCrystal> crystals;

    public Blockage(Position position) {
        this.position = position;
        Skeleton.writeFunctionDetails("Blockage(Position position)");
        Skeleton.writeReturnValue("");
    }

    public Position getPosition() {
        Skeleton.writeFunctionDetails("Blockage.getPosition()");
        Skeleton.writeReturnValue("Position: position");
        return position;
    }

    void upgrade(BlockageCrystal crystal) {
        Skeleton.writeFunctionDetails("Blockage.upgrade()");
        this.crystals.add(crystal);
        Skeleton.writeReturnValue("void");
    }

    int block(EnemyType enemyType) {
        Skeleton.writeFunctionDetails("Blockage.block(EnemyType enemyType)");
        Skeleton.writeReturnValue("int: 0");
        return 0;
    }
}
