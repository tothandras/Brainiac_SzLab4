package com.brainiac.model;

import com.brainiac.Skeleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Torony osztály
 */
public class Tower {
    private Position position;
    private Damage damage;
    private int fireRate;
    private int range;

    /**
     * Új torony létrehozása a pálya egy adott pozíciójára
     * @param position A torony pozíciója
     */
    public Tower(Position position) {
        this.position = position;
        this.damage = new Damage();
        this.fireRate = 1;
        this.range = 100;
    }

    /**
     * Torony lő egyet a paramáterében megkapott ellenfélre.
     * @param enemy Az ellenfél
     */
    public void fire(Enemy enemy) {
        enemy.hurt(damage);
    }

    /**
     * Visszatér a torony pozíciójával
     * @return position A pozíció
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Torony hatótávjának lekérdezése
     * @return range A hatótáv
     */
    public int getRange() {
        return range;
    }

    /**
     * Torony fejlesztése kristállyal
     * @param crystal a kristály
     */
    public void upgrade(TowerCrystal crystal) {
        range *=  (int)crystal.getRangeIncrement();
        fireRate *= (int)crystal.getIncrement();
        damage.setDamage((int)crystal.getIncrement(), crystal.getAgainst());
    }
}
