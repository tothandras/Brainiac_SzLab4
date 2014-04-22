package com.brainiac.model;

public class Event {
    public int x, y;
    public Action action;
    public EnemyType against;
    public double damageIncrement, fireRateIncrement, rangeIncrement;
    public int slowIncrement;

    public Event(int x, int y, Action action) {
        this.x = x;
        this.y = y;
        this.action = action;
        damageIncrement = 1.0;
        fireRateIncrement = 1.0;
        rangeIncrement = 1.0;
        slowIncrement = 0;
        against = EnemyType.Dwarf;

    }
}
