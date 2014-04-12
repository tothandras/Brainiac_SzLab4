package com.brainiac.model;

import com.brainiac.Skeleton;

/**
 * Project name: Brainiac_SzLab4
 * User: tothandras
 * Date: 2014.03.20.
 * Time: 12:11
 */
public class BlockageCrystal {
    private EnemyType against;
    private int increment;

    /**
     * Varázskő létrehozása
     * @param against
     * @param increment
     */
    public BlockageCrystal(EnemyType against, int increment) {
        this.against = against;
        this.increment = increment;

    }

    /**
     * Varázskő hatásának lekérdezése
     * Ha a paraméterben megadott enemyvel megegyezik ,akkor visszatér a növelés mértékével
     * egyébként 1-el.
     * @return increment
     * @param enemyType
     */
    public int getIncrement(EnemyType enemyType) {
        if(enemyType==against){
            return increment;
        }
        return 0;
    }
}
