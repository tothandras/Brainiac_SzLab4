package com.brainiac.model;

import com.brainiac.controller.Skeleton;

/**
 * Project name: Brainiac_SzLab4
 * User: tothandras
 * Date: 2014.03.20.
 * Time: 12:12
 */
public class Dwarf extends Enemy {
    public Dwarf() {
        this.position = new Position(0,0);
        Skeleton.writeFunctionDetails("Dwarf()");
        Skeleton.writeReturnValue("");
    }

    @Override
    public void hurt(Damage damage) {
        Skeleton.writeFunctionDetails("Dwarf.hurt(Damage damage)");
        damage.getDamage(EnemyType.Dwarf);
        Skeleton.writeReturnValue("void");
    }
}
