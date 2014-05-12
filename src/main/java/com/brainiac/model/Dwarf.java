package com.brainiac.model;

import java.awt.*;

public class Dwarf extends Enemy {
    /**
     * Beállítjuk a megfellelő kezdő sebességet, életet és pozíciót
     *
     * @param position Kezdőpozíció
     */
    public Dwarf(Position position) {
        this.position = new Position(position);
        life = 120;
        speed = 4;
        color = new Color(1.0f, 0.8398f, 0.0f);
    }

    /**
     * Törpöt sebezzük
     *
     * @param damage: Sebzés
     */
    @Override
    public void hurt(Damage damage) {
        int d = damage.getDamage(EnemyType.Dwarf);
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
     * A törp sebességét lekérdező függvény. A törp útjában lévő akadályt figyelembe véve ad választ.
     *
     * @param blockage a törp útjában lévő akadály
     * @return a törp esetlegesen módosított sebessége
     */
    @Override
    public int getSpeed(Blockage blockage) {
        if (blockage == null) {
            return speed;
        }
        return speed + blockage.block(EnemyType.Dwarf);
    }

    /**
     * Törp szétvágása
     * Csinál egy másolatot magából, ugyanolyan tulajdonságokkal és fele annyi életerővel mint az eredeti egyed
     *
     * @return Szétvágott törp
     */
    @Override
    public Enemy cut() {
        // Felére csökkentjük a törp életerejét
        this.life = this.life / 2;
        // Létrehozunk egy törpöt megegyező tulajdonságokkal
        Dwarf dwarf = new Dwarf(this.position);
        dwarf.life = this.life;
        return dwarf;
    }
}
