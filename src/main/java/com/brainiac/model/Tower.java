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
    private double cutChance;
    /**
     * mivel már nincs lista a kristályokról, így tudunk a legegyszerűbben
     * információt kapni arról, hogy fejlesztett-e a torony
     */
    public boolean upgraded;

    /**
     * Új torony létrehozása a pálya egy adott pozíciójára
     *
     * @param position A torony pozíciója
     */
    public Tower(Position position) {
        this.upgraded = false;
        this.position = position;
        this.damage = new Damage();
        this.fireRate = 1;
        this.range = 100;
        this.cutChance = 0.05;
    }

    /**
     * Torony lő egyet a paramáterében megkapott ellenfélre.
     *
     * @param enemy Az ellenfél
     */
    public Enemy fire(Enemy enemy) {
        enemy.hurt(damage);
        if (Math.random() < cutChance){
            return enemy.cut();
        }
        return null;
    }

    /**
     * Visszatér a torony pozíciójával
     *
     * @return position A pozíció
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Torony hatótávjának lekérdezése
     *
     * @return range A hatótáv
     */
    public int getRange() {
        return range;
    }

    /**
     * Torony tüzelési gyakoriságának lekérdezése
     * @return a torony tüzelési gyakorisága
     */
    public int getSpeed(){
        return fireRate;
    }

    /**
     * A félbevágós lövedék esélyének lekérdezése
     * @return a félbevágós lövedék esélye
     */
    public double getCutChance(){
        return cutChance;
    }

    /**
     * A függvény segítségével beállíthatjuk a félbevágós lövés esélyét.
     * @param cutChance A félbevágós lövés új esélye.
     */
    public void setCutChance(double cutChance){
        this.cutChance = cutChance;
    }

    /**
     * Torony fejlesztése kristállyal
     *
     * @param crystal a kristály
     */
    public void upgrade(TowerCrystal crystal) {
        range = (int) (range * crystal.getRangeIncrement());
        fireRate = (int) (fireRate * crystal.getIncrement());
        damage.setDamage(crystal.getIncrement(), crystal.getAgainst());
        upgraded = true;
    }
}
