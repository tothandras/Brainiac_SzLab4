package com.brainiac.model;

import com.brainiac.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class Tower {
    private Position position;
    private List<TowerCrystal> crystals;
    private Damage damage;
    private int fireRate;
    private int range;

    public Tower(Position position) {
        this.position = position;
        this.damage = new Damage();
        this.fireRate = 1;
        this.range = 10;
        crystals = new ArrayList<TowerCrystal>();
        Skeleton.writeFunctionDetails("Tower(Position position))");
        Skeleton.writeReturnValue("");
    }

    public void fire(Enemy enemy) {
        Skeleton.writeFunctionDetails("Tower.fire(Enemy enemy)");
        enemy.hurt(damage);
        Skeleton.writeReturnValue("void");
    }

    public Position getPosition() {
        return position;
    }

    public int getRange() {
        return range;
    }

    public void upgrade(TowerCrystal crystal) {
        Skeleton.writeFunctionDetails("Tower.upgrade()");
        this.crystals.add(crystal);
        Skeleton.writeReturnValue("void");
    }
}
