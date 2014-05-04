package com.brainiac.model;

public class Damage {
    // Sebzés mértéke a különbözű ellenség típusokra
    private int toDwarf;
    private int toElf;
    private int toHobbit;
    private int toMan;

    /**
     * Damage osztály konstruktora
     * Minden ellenségre alapértelmezetten 10 a sebzése
     */
    public Damage() {
        toDwarf = 10;
        toElf = 10;
        toHobbit = 10;
        toMan = 10;
    }

    /**
     * @param enemyType Egy ellenség típus
     * @return Az ellenség típusra vonatkozó sebzés
     */
    public int getDamage(EnemyType enemyType) {
        switch (enemyType) {
            // Törp
            case Dwarf:
                return toDwarf;
            // Tünde
            case Elf:
                return toElf;
            // Hobbit
            case Hobbit:
                return toHobbit;
            // Ember
            case Man:
                return toMan;
            default:
                return 0;
        }

    }

    /**
     * @param damage    Új sebzés
     * @param enemyType Egy ellenség típus
     */
    public void setDamage(int damage, EnemyType enemyType) {
        switch (enemyType) {
            case Dwarf:
                toDwarf = damage;
                break;
            case Elf:
                toElf = damage;
                break;
            case Hobbit:
                toHobbit = damage;
                break;
            case Man:
                toMan = damage;
                break;
            default:
                break;
        }
    }
}
