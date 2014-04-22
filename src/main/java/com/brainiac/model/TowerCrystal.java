package com.brainiac.model;

public class TowerCrystal {
    private EnemyType against;
    private double increment;
    private double speedIncrement;
    private double rangeIncrement;

    /**
     * Új varázskő a sebzés növelésére
     *
     * @param against
     * @param increment
     */
    public TowerCrystal(EnemyType against, double increment, double speedIncrement, double rangeIncrement) {
        this.against = against;
        this.increment = increment;
        this.speedIncrement = speedIncrement;
        this.rangeIncrement = rangeIncrement;
    }

    /**
     * Varázskő sebzésének lekérdezése
     * Visszatér a növelés mértékével
     *
     * @return increment
     */
    public double getIncrement() {
        return increment;

    }

    /**
     * Milyen ellenfélre hat
     * Visszatér egy ellenfél típussal
     *
     * @return against az EnemyType
     */
    public EnemyType getAgainst() {
        return against;
    }

    /**
     * Varázskő lővési gyakoriságra gyakorolt hatása
     *
     * @return
     */
    public double getSpeedIncrement() {
        return speedIncrement;
    }

    /**
     * Varázskő hatótáv növelésének lekérdezése
     *
     * @return hatótáv
     */
    public double getRangeIncrement() {
        return rangeIncrement;
    }
}
