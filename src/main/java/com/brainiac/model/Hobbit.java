package com.brainiac.model;

import java.awt.*;

public class Hobbit extends Enemy {
    /**
     * Beállítjuk a megfellelő kezdő sebességet, életet és pozíciót
     *
     * @param position Kezdőpozíció
     */
    public Hobbit(Position position) {
        this.position = new Position(position);
        life = 50;
        speed = 3;
        color = new Color(1.0f, 0.0f, 0.0f);
    }

    /**
     * Hobbitot sebezzük
     *
     * @param damage: Sebzés
     */
    @Override
    public void hurt(Damage damage) {
        int d = damage.getDamage(EnemyType.Hobbit);
        life -= d;
    }

    /**
     * Mozgatjuk az törpöt a megfelelő irányba
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
     * A hobbit sebességét lekérdező függvény. A hobbit útjában lévő akadályt figyelembe véve ad választ.
     *
     * @param blockage a hobbit útjában lévő akadály
     * @return a hobbit esetlegesen módosított sebessége
     */
    @Override
    public int getSpeed(Blockage blockage) {
        if (blockage == null) {
            return speed;
        }
        return speed + blockage.block(EnemyType.Hobbit);
    }

    /**
     * Hobbit szétvágása
     * Csinál egy másolatot magából, ugyanolyan tulajdonságokkal és fele annyi életerővel mint az eredeti egyed
     *
     * @return Szétvágott hobbit
     */
    @Override
    public Enemy cut() {
        // Felére csökkentjük a törp életerejét
        this.life = this.life / 2;
        // Létrehozunk egy törpöt megegyező tulajdonságokkal
        // TODO: kicsit lehet el kéne tolni?
        Hobbit hobbit = new Hobbit(this.position);
        hobbit.life = this.life;
        return hobbit;
    }

}
