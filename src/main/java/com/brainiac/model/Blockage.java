package com.brainiac.model;

import com.brainiac.controller.Skeleton;

import java.util.List;

public class Blockage {
    private Position position;
    private List<BlockageCrystal> crystals;

    public Blockage(Position position) {
        this.position = position;
        Skeleton.writeFunctionDetails("Blockage(Position: (" + position.getX() + "," + position.getY() + "))");
        Skeleton.writeReturnValue("void");
    }

    public Position getPosition() {
        return position;
    }

    void upgrade(BlockageCrystal crystal) {
        Skeleton.writeFunctionDetails("Blockage.upgrade()");
        this.crystals.add(crystal);
        Skeleton.writeReturnValue("void");
    }

    int block(EnemyType enemyType) {
        return 0;
    }
}
