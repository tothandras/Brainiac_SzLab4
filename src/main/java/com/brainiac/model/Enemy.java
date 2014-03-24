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



    public void hurt(Damage damage) {
        Skeleton.writeFunctionDetails("Enemy.hurt()");
        Skeleton.writeReturnValue("void");
    }

    public void move(Direction direction, Blockage blockage) {
        Skeleton.writeFunctionDetails("Enemy.move(Direction direction, Blockage blockage)");
        boolean blocked = false;
        if (blockage != null) {
            blocked = Skeleton.getBoolean("Lépés blokkolása?");
        }
        if (blocked) {
            blockage.block(EnemyType.Dwarf);
        }
        switch (direction) {
            case NORTH:
                Skeleton.writeLine("Ellenség léptetése északi irányba blokkolás" + (blocked ? "sal." : " nélkül."));
                break;
            case WEST:
                Skeleton.writeLine("Ellenség léptetése keleti irányba blokkolás" + (blocked ? "sal." : " nélkül."));
                break;
            case SOUTH:
                Skeleton.writeLine("Ellenség léptetése déli irányba blokkolás" + (blocked ? "sal." : " nélkül."));
                break;
            case EAST:
                Skeleton.writeLine("Ellenség léptetése nyugati irányba blokkolás" + (blocked ? "sal." : " nélkül."));
                break;
        }
        Skeleton.writeReturnValue("void");
    }

    public Position getPosition() {
        Skeleton.writeFunctionDetails("Enemy.getPosition()");
        Skeleton.writeReturnValue("Position: position");
        return position;
    }
}
