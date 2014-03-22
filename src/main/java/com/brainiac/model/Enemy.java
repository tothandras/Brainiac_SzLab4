package com.brainiac.model;

import com.brainiac.controller.Skeleton;

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



    public void hurt(Damage damage) {

    }

    public void move(Direction direction, Blockage blockage) {

        Skeleton.writeFunctionDetails("Enemy.move(Direction direction, Blockage blockage)");
        blockage.block();



    }

    public Position getPosition() {
        Skeleton.writeFunctionDetails("Enemy.getPosition()");
        Skeleton.writeReturnValue("Position.x: " + position.getX() + " Position.y: " + position.getY());
        return position;
    }
}
