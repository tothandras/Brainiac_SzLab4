package com.brainiac.model;

import java.awt.*;

public class Man extends Enemy {
    /**
     * Beállítjuk a megfellelő kezdő sebességet, életet és pozíciót
     *
     * @param position Kezdőpozíció
     */
    public Man(Position position) {
        this.position = new Position(position);
        life = 80;
        speed = 3;
        color = new Color(0.85f, 0.85f, 0.85f);
    }

    /**
     * Embert sebezzük
     *
     * @param damage: Sebzés
     */
    @Override
    public void hurt(Damage damage) {
        int d = damage.getDamage(EnemyType.Man);
        life -= d;
    }

    /**
     * Mozgatjuk az embert a megfelelő irányba
     *
     * @param direction: Haladás iránya
     */
    @Override
    public void move(Direction direction) {
        // Léptetés a megfelelő irányba
        switch (direction) {
            // Észak
            case NORTH:
                position.setY(position.getY() + 1);
                break;
            // Kelet
            case EAST:
                position.setX(position.getX() + 1);
                break;
            // Dél
            case SOUTH:
                position.setY(position.getY() - 1);
                break;
            // Nyugat
            case WEST:
                position.setX(position.getX() - 1);
                break;
            default:
                break;
        }
    }

    /**
     * Az ember sebességét lekérdező függvény. Az ember útjában lévő akadályt figyelembe véve ad választ.
     *
     * @param blockage az ember útjában lévő akadály
     * @return az ember esetlegesen módosított sebessége
     */
    @Override
    public int getSpeed(Blockage blockage) {
        if (blockage == null) {
            return speed;
        }
        return speed + blockage.block(EnemyType.Man);
    }

    /**
     * Ember szétvágása
     * Csinál egy másolatot magából, ugyanolyan tulajdonságokkal és fele annyi életerővel mint az eredeti egyed
     *
     * @return Szétvágott ember
     */
    @Override
    public Enemy cut() {
        // Felére csökkentjük a ember életerejét
        this.life = this.life / 2;
        // Létrehozunk egy embert megegyező tulajdonságokkal
        Man man = new Man(this.position);
        man.life = this.life;
        return man;
    }
}
