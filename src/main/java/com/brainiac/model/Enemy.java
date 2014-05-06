package com.brainiac.model;

import java.util.Random;

public abstract class Enemy {
    // Ellenség életereje
    protected int life;
    // Ellenség sebessége
    protected int speed;
    // Ellenség pozíciója
    protected Position position;
    // Az ellenség eltolásának pozíciója az úton
    protected Position offset;
    //
    protected int timeSinceMove;

    public Enemy() {
        Random rand = new Random();
        offset = new Position(rand.nextInt(6) - 3, rand.nextInt(6) - 3);
        timeSinceMove = 0;
    }

    /**
     * Ellenséget sebezzük
     *
     * @param damage: Sebzés
     */
    public abstract void hurt(Damage damage);


    /**
     * Mozgatjuk az ellenséget a megfelelő irányba
     *
     * @param direction: Haladás iránya
     * @param blockage:  Ha az útjában akadály van, akkor megkapja paraméterben
     */
    public abstract void move(Direction direction, Blockage blockage);

    /**
     * Visszatér az ellenség pozíciójával
     *
     * @return Ellenség pozíciója
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Visszatér az ellenség életerejével
     *
     * @return Ellenség életereje
     */
    public int getLife() {
        return life;
    }

    /**
     * Visszatér az ellenség sebességével
     *
     * @return Ellenség sebessége
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Visszatér az ellenség eltolt pozíciójával
     *
     * @return Az eltolási vektor és a pozíció összege
     */
    public Position getOffset() {
        return new Position(position.getX() + offset.getX(), position.getY() + offset.getY());
    }

    /**
     * Csinál egy másolatot magából, ugyanolyan tulajdonságokkal és fele annyi életerővel mint az eredeti egyed.
     */
    public abstract Enemy cut();
}
