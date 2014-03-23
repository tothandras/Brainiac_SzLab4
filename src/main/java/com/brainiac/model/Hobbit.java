package com.brainiac.model;

import com.brainiac.controller.Skeleton;

public class Hobbit extends Enemy {
    public Hobbit() {
        this.position = new Position(0,0);
        Skeleton.writeFunctionDetails("Hobbit()");
        Skeleton.writeReturnValue("");
    }

    @Override
    public void hurt(Damage damage) {
        Skeleton.writeFunctionDetails("Hobbit.hurt(Damage damage)");
        damage.getDamage(EnemyType.Hobbit);
        Skeleton.writeReturnValue("void");
    }
}
