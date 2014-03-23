package com.brainiac.model;

import com.brainiac.controller.Skeleton;

/**
 * Project name: Brainiac_SzLab4
 * User: tothandras
 * Date: 2014.03.20.
 * Time: 12:12
 */
public class Elf extends Enemy {
    public Elf() {
        this.position = new Position(0,0);
        Skeleton.writeFunctionDetails("Elf()");
        Skeleton.writeReturnValue("void");
    }

    @Override
    public void hurt(Damage damage) {
        Skeleton.writeFunctionDetails("Elf.hurt(Damage damage)");
        damage.getDamage(EnemyType.Elf);
        Skeleton.writeReturnValue("void");
    }

    @Override
    public void move(Direction direction, Blockage blockage) {

        Skeleton.writeFunctionDetails("Enemy.move(Direction direction, Blockage blockage)");
        if(blockage!=null){
        blockage.block(EnemyType.Elf);
        }
        Skeleton.writeReturnValue("void");
    }
}
