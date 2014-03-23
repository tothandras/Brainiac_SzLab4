package com.brainiac.model;

import com.brainiac.controller.Skeleton;

public class Man extends Enemy {
    public Man() {
        this.position = new Position(0,0);
        Skeleton.writeFunctionDetails("Man()");
        Skeleton.writeReturnValue("void");
    }

    @Override
    public void hurt(Damage damage) {
        Skeleton.writeFunctionDetails("Man.hurt(Damage damage)");
        damage.getDamage(EnemyType.Man);
        Skeleton.writeReturnValue("void");
    }
}
