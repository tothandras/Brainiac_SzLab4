package com.brainiac.model;

public class BlockageCrystal {
    private EnemyType against;
    private int increment;

    /**
     * Varázskő létrehozása
     *
     * @param against   Ellenség típus
     * @param increment Lassítás mértéke
     */
    public BlockageCrystal(EnemyType against, int increment) {
        this.against = against;
        this.increment = increment;
    }

    /**
     * Varázskő hatásának lekérdezése
     * Ha a paraméterben megadott ellenség típussal megegyezik, akkor visszatér a lassítás mértékével, egyébként 0-val.
     *
     * @param enemyType Elleség típus
     * @return Lassítás mértéke
     */
    public int getIncrement(EnemyType enemyType) {
        if (enemyType == against) {
             return increment;
        }
        return 0;
    }

    public EnemyType getEnemyType(){
        return against;
    }
}
