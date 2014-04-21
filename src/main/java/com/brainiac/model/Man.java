package com.brainiac.model;

import com.brainiac.Skeleton;

public class Man extends Enemy {
    /**
     * Beállítjuk a megfellelő kezdő sebességet, életet és pozíciót.
     */
    public Man() {
        life = 70;
        speed = 2;
        this.position = new Position(0, 0);
    }

    /**
     * @param damage: a sebzés
     */
    @Override
    public void hurt(Damage damage) {
        int d = damage.getDamage(EnemyType.Man);
        life -= d;
    }

    /**
     * mozgatjuk az ellenefelet a megfelelő irányba és sebeséggel
     *
     * @param direction: milyen irányba mozogjon
     * @param blockage:  kap e blokkolót
     */
    @Override
    public void move(Direction direction, Blockage blockage) {
        int speed_actual = speed - blockage.block(EnemyType.Man);

        switch (direction) {
            case EAST:
                position.setX(position.getX() + speed_actual);
                break;
            case NORTH:
                position.setY(position.getY() + speed_actual);
                break;
            case SOUTH:
                position.setY(position.getY() - speed_actual);
                break;
            case WEST:
                position.setX(position.getX() - speed_actual);
                break;
            default:
                break;

        }
    }

    @Override
    public Enemy cut() {
        this.life = this.life / 2;
        Man man = new Man();
        man.life = this.life;
        man.position = this.position;
        return man;
    }

}
