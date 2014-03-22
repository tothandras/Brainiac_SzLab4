package com.brainiac.model;

import com.brainiac.controller.Skeleton;

public class Man extends Enemy {
    public Man() {
    }

    @Override
    public void hurt(Damage damage) {
        Skeleton.writeFunctionDetails("Man.hurt(Damage damage)");
        damage.getDamage(EnemyType.Man);
    }
}
