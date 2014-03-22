package com.brainiac.model;

import com.brainiac.controller.Skeleton;

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
        Skeleton.writeFunctionDetails("Tower.fire(Enemy enemy)");
        enemy.hurt(damage);
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
