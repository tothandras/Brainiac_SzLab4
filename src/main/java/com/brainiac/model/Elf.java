package com.brainiac.model;

import java.awt.*;

public class Elf extends Enemy {
    /**
     * Beállítjuk a megfellelő kezdő sebességet, életet és pozíciót
     *
     * @param position Kezdőpozíció
     */
    public Elf(Position position) {
        this.position = new Position(position);
        life = 100;
        speed = 2;
        color = new Color(0.0f, 1.0f, 0.0f);
    }

    /**
     * Tündét sebezzük
     *
     * @param damage: Sebzés
     */
    @Override
    public void hurt(Damage damage) {
        int d = damage.getDamage(EnemyType.Elf);
        life -= d;
    }

    /**
     * Mozgatjuk az tündét a megfelelő irányba
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
     * A tünde sebességét lekérdező függvény. A tünde útjában lévő akadályt figyelembe véve ad választ.
     *
     * @param blockage a tünde útjában lévő akadály
     * @return a tünde esetlegesen módosított sebessége
     */
    @Override
    public int getSpeed(Blockage blockage) {
        if (blockage == null) {
            return speed;
        }
        return speed + blockage.block(EnemyType.Elf);
    }

    /**
     * Tünde szétvágása
     * Csinál egy másolatot magából, ugyanolyan tulajdonságokkal és fele annyi életerővel mint az eredeti egyed
     *
     * @return Szétvágott tünde
     */
    @Override
    public Enemy cut() {
        // Felére csökkentjük a tünde életerejét
        this.life = this.life / 2;
        // Létrehozunk egy tündét megegyező tulajdonságokkal
        // TODO: kicsit lehet el kéne tolni?
        Elf elf = new Elf(this.position);
        elf.life = this.life;
        return elf;
    }
}
