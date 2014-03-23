package com.brainiac.model;

import com.brainiac.controller.Skeleton;

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
        crystals=new ArrayList<TowerCrystal>();
        Skeleton.writeFunctionDetails("Tower(Range: (" + range + "))");
        Skeleton.writeFunctionDetails("Tower(Position: (" + position.getX() + "," + position.getY() + "))");
        Skeleton.writeReturnValue("void");
    }

    public void fire(Enemy enemy) {
        Skeleton.writeFunctionDetails("Tower.fire(Enemy enemy)");
        enemy.hurt(damage);
        Skeleton.writeReturnValue("");
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
