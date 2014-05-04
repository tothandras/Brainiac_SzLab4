package com.brainiac.model;

public class TowerCrystal {
    private EnemyType against;
    private int increment;
    private int fireRateIncrement;
    private int rangeIncrement;

    /**
     * Új varázskő a sebzés, hatótávolság, vagy tüzelési sebesség növelésére
     *
     * @param against Ellenség típus ellen
     * @param increment Sebzés növelése
     * @param speedIncrement Tüzelési sebesség növelése
     * @param rangeIncrement Tüzelési hatótávolság növelése
     */
    public TowerCrystal(EnemyType against, int increment, int speedIncrement, int rangeIncrement) {
        this.against = against;
        this.increment = increment;
        this.fireRateIncrement = speedIncrement;
        this.rangeIncrement = rangeIncrement;
    }

    /**
     * Varázskő sebzésének lekérdezése
     *
     * @return Sebzés növelésének mértéke
     */
    public int getIncrement() {
        return increment;
    }

    /**
     * Varázskő sebzésnövelése melyik elleség típus ellen hatásos
     *
     * @return Ellenség típus
     */
    public EnemyType getAgainst() {
        return against;
    }

    /**
     * Varázskő lővési gyakoriságra gyakorolt hatása
     *
     * @return Tüzelési sebesség növelésének mértéke
     */
    public int getFireRateIncrement() {
        return fireRateIncrement;
    }

    /**
     * Varázskő hatótáv növelésének lekérdezése
     *
     * @return Tüzelési hatótávolság növelésének mértéke
     */
    public int getRangeIncrement() {
        return rangeIncrement;
    }
}
