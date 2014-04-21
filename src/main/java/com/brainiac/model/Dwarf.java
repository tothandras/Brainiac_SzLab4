package com.brainiac.model;

import com.brainiac.Skeleton;

/**
 * Project name: Brainiac_SzLab4
 * User: tothandras
 * Date: 2014.03.20.
 * Time: 12:12
 */
public class Dwarf extends Enemy {
    /**
     * Beállítjuk a megfellelő kezdő sebességet, életet és pozíciót.
     */
    public Dwarf() {
        life = 100;
        speed = 2;
        this.position = new Position(0, 0);
    }


    /**
     * @param damage: a sebzés
     */
    @Override
    public void hurt(Damage damage) {
        int d = damage.getDamage(EnemyType.Dwarf);
        life -= d;
    }

    /**
     * mozgatjuk az ellenefelet a megfelelő irányba és sebeséggel
     * @param direction: milyen irányba mozogjon
     * @param blockage: kap e blokkolót
     */
    @Override
    public void move(Direction direction, Blockage blockage) {
        //aktuális sebesség
        int speed_actual = speed - blockage.block(EnemyType.Dwarf);

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
        Dwarf dwarf = new Dwarf();
        dwarf.life = this.life;
        dwarf.position = this.position;
        return dwarf;
    }

}
