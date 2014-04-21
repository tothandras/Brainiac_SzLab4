package com.brainiac.model;

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

    /**
     * Damage osztály konstruktora. Minden ellenfélnek alapértelmezetten 10 a sebzése
     */

    public Damage() {
        toDwarf = 10;
        toElf = 10;
        toHobbit = 10;
        toMan = 10;
    }

    /**
     *
     * @param enemyType
     * paraméterül kap egy ellenfél típust, és visszatér annak a sebzésével
     * @return
     */

    public int getDamage(EnemyType enemyType) {

        switch (enemyType){
            case Dwarf:

                return toDwarf;
            case Elf:

                return toElf;
            case Hobbit:

                return toHobbit;
            default:

                return toMan;
        }

    }

    /**
     *
     * @param damage
     * a paraméterül átvett damaget hozzáadja a másik paraméterül kapott ellenség típushoz
     * @param enemyType
     */

    void setDamage(double damage, EnemyType enemyType) {
        switch (enemyType){
            case Dwarf:

                toDwarf = (int)(toDwarf * damage);
                break;
            case Elf:

                toElf = (int)(toElf * damage);
                break;
            case Hobbit:

                toHobbit = (int)(toHobbit * damage);
                break;
            case Man:

                toMan = (int)(toMan * damage);
                break;
            default:
                break;
        }
    }
}
