package com.brainiac.model;

import com.brainiac.Skeleton;

public class TowerCrystal {
    private EnemyType against;
    private double increment;
    private double speedIncrement;

    /**
     * Új varázskő a sebzés növelésére
     * @param against
     * @param increment
     */
    public TowerCrystal(EnemyType against, double increment) {
        this.against = against;
        this.increment = increment;
    }

    /**
     *  Új varázskő a lövési gyakoriság növelésére
     * @param speedIncrement
     */
    public TowerCrystal(double speedIncrement) {
        this.speedIncrement = speedIncrement;
    }

    /**
     * Varázskő sebzésének lekérdezése
     * Ha a paraméterben megadott enemyvel megegyezik akkor visszatér a növelés mértékével
     * egyébként 1-el.
     * @param enemyType
     * @return increment
     */
    public double getIncrement(EnemyType enemyType) {
        if(enemyType==against){
            return increment;
        }
        return 1;
    }

    /**
     * Verázskő lővési gyakoriságra gyakorolt hatása
     * @return
     */
    public double getSpeedIncrement() {
        return speedIncrement;
    }
}
