package com.brainiac.model;

import com.brainiac.Proto;

public class Hobbit extends Enemy {
    /**
     * Beállítjuk a megfellelő kezdő sebességet, életet és pozíciót.
     */
    public Hobbit() {
        life = 50;
        speed = 2;
        this.position = new Position(0, 0);
    }

    /**
     * @param damage: a sebzés
     */
    @Override
    public void hurt(Damage damage) {
        int d = damage.getDamage(EnemyType.Hobbit);
        life -= d;
        System.out.println("Hobbit (" + position.getX() + ", " + position.getY() + "): Sérül ");
        if (Proto.fileOut != null) {
            Proto.fileOut.println("Hobbit (" + position.getX() + ", " + position.getY() + "): Sérül ");
        }
    }

    /**
     * mozgatjuk az ellenefelet a megfelelő irányba és sebeséggel
     *
     * @param direction: milyen irányba mozogjon
     * @param blockage:  kap e blokkolót
     */
    @Override
    public void move(Direction direction, Blockage blockage) {
        int speed_actual = speed - blockage.block(EnemyType.Hobbit);

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
        System.out.println("Hobbit (" + position.getX() + ", " + position.getY() + "): Lép ");
        if (Proto.fileOut != null) {
            Proto.fileOut.println("Hobbit (" + position.getX() + ", " + position.getY() + "): Lép ");
        }
    }

    @Override
    public Enemy cut() {
        this.life = this.life / 2;
        Hobbit hobbit = new Hobbit();
        hobbit.life = this.life;
        hobbit.position = this.position;
        return hobbit;
    }

    @Override
    public void setPosition(Position pos) {
        this.position = pos;
    }

}
