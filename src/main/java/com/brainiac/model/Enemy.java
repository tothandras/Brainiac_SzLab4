package com.brainiac.model;

public abstract class Enemy {
    // Ellenség életereje
    protected int life;
    // Ellenség sebessége
    protected int speed;
    // Ellenség pozíciója
    protected Position position;
    //
    protected int timeSinceMove;

    public Enemy() {
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
     */
    public abstract void move(Direction direction);

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
     * @param blockage az ellenség útjában lévő akadály
     * @return Ellenség sebessége
     */
    public abstract int getSpeed(Blockage blockage);


    /**
     * Csinál egy másolatot magából, ugyanolyan tulajdonságokkal és fele annyi életerővel mint az eredeti egyed.
     */
    public abstract Enemy cut();
}
