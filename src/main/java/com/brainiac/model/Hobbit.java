package com.brainiac.model;

import com.brainiac.controller.Skeleton;

public class Hobbit extends Enemy {
    public Hobbit() {
        this.position = new Position(0,0);
        Skeleton.writeFunctionDetails("Hobbit()");
        Skeleton.writeReturnValue("void");
    }

    @Override
    public void hurt(Damage damage) {
        Skeleton.writeFunctionDetails("Hobbit.hurt(Damage damage)");
        damage.getDamage(EnemyType.Hobbit);
        Skeleton.writeReturnValue("void");
    }

    @Override
    public void move(Direction direction, Blockage blockage) {

        Skeleton.writeFunctionDetails("Enemy.move(Direction direction, Blockage blockage)");
        if(blockage!=null){
        blockage.block(EnemyType.Hobbit);
        }
        Skeleton.writeReturnValue("void");
    }
}
