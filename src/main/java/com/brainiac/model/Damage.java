package com.brainiac.model;

import com.brainiac.Skeleton;

/**
 * Project name: Brainiac_SzLab4
 * User: tothandras
 * Date: 2014.03.20.
 * Time: 12:03
 */
public class Damage {
    private int toDwarf;
    private int toElf;
    private int toHobbit;
    private int toMan;

    public Damage() {
        Skeleton.writeFunctionDetails("Damage()");
        Skeleton.writeReturnValue("");
    }

    public int getDamage(EnemyType enemyType) {
        Skeleton.writeFunctionDetails("Damage.getDamage(EnemyType enemyType)");
        switch (enemyType){
            case Dwarf:
                Skeleton.writeReturnValue("int: " + toDwarf);
                return toDwarf;
            case Elf:
                Skeleton.writeReturnValue("int: " + toElf);
                return toElf;
            case Hobbit:
                Skeleton.writeReturnValue("int: " + toHobbit);
                return toHobbit;
            case Man:
                Skeleton.writeReturnValue("int: " + toMan);
                return toMan;
        }
        return 0;
    }

    void setDamage(int damage, EnemyType enemyType) {

    }
}
