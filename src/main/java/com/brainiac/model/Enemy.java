package com.brainiac.model;

import com.brainiac.Skeleton;

/**
 * Project name: Brainiac_SzLab4
 * User: tothandras
 * Date: 2014.03.20.
 * Time: 12:02
 */
public abstract class Enemy {
    protected int life;
    protected int speed;
    protected Position position;


    /**
     * @param damage: a sebzés
     */
    public abstract void hurt(Damage damage);


    /**
     * mozgatjuk az ellenefelet a megfelelő irányba és sebeséggel
     * @param direction: milyen irányba mozogjon
     * @param blockage: kap e blokkolót
     */
    public abstract void move(Direction direction, Blockage blockage);


    /*
    Visszatér az ellenség pozícióval
     */
    public Position getPosition() {
        return position;
    }
}
