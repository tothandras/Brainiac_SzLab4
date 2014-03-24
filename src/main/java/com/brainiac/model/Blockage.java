package com.brainiac.model;

import com.brainiac.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class Blockage {
    private Position position;
    private List<BlockageCrystal> crystals;

    public Blockage(Position position) {
        this.position = position;
        crystals=new ArrayList<BlockageCrystal>();
        Skeleton.writeFunctionDetails("Blockage(Position position)");
        Skeleton.writeReturnValue("");
    }

    public Position getPosition() {
        Skeleton.writeFunctionDetails("Blockage.getPosition()");
        Skeleton.writeReturnValue("Position: position");
        return position;
    }

    public void upgrade(BlockageCrystal crystal) {
        Skeleton.writeFunctionDetails("Blockage.upgrade()");
        this.crystals.add(crystal);
        Skeleton.writeReturnValue("void");
    }

    public int block(EnemyType enemyType) {
        Skeleton.writeFunctionDetails("Blockage.block(EnemyType enemyType)");
        Skeleton.writeReturnValue("int: 0");
        return 0;
    }
}
