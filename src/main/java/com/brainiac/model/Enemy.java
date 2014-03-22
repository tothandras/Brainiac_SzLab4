package com.brainiac.model;

/**
 * Project name: Brainiac_SzLab4
 * User: tothandras
 * Date: 2014.03.20.
 * Time: 12:02
 */
public abstract class Enemy {
    protected int life;
    protected int speed;

    public void hurt(Damage damage) {

    }

    public void move(Direction direction, Blockage blockage) {

    }

    public Position getPosition() {
        return new Position(0, 0);
    }
}
