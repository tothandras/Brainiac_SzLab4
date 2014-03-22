package com.brainiac.model;

import com.brainiac.controller.Skeleton;

public class Hobbit extends Enemy {
    public Hobbit() {
        Skeleton.writeFunctionDetails("Hobbit()");
        Skeleton.writeReturnValue("void");
    }

    @Override
    public void hurt(Damage damage) {
        Skeleton.writeFunctionDetails("Hobbit.hurt(Damage damage)");
        damage.getDamage(EnemyType.Hobbit);
        Skeleton.writeReturnValue("void");
    }
}
