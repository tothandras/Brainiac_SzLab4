package com.brainiac.model;

import java.util.List;

public class Tower {
    private Position position;
    private List<TowerCrystal> crystals;
    private Damage damage;
    private int fireRate;
    private int range;

    public Tower(Position position) {
        this.position = position;
    }

    public void fire(Enemy enemy) {

    }

    public Position getPosition() {
        return position;
    }

    public int getRange() {
        return range;
    }

    public void upgrade(TowerCrystal crystal) {

    }
}
