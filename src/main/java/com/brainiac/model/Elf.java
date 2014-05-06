package com.brainiac.model;

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
    }

    /**
     * Tündét sebezzük
     *
     * @param damage: Sebzés
     */
    @Override
    public void hurt(Damage damage) {
        // TODO: hol hal meg?
        int d = damage.getDamage(EnemyType.Elf);
        life -= d;
    }

    /**
     * Mozgatjuk az tündét a megfelelő irányba
     *
     * @param direction: Haladás iránya
     * @param blockage:  Ha az útjában akadály van, akkor megkapja paraméterben
     */
    @Override
    public void move(Direction direction, Blockage blockage) {
        // Pillanatnyi sebesség
        int currentSpeed = speed;
        if (blockage != null) {
            currentSpeed += blockage.block(EnemyType.Elf);
        }

        timeSinceMove += 1;
        // Ha már kell lépni
        if (timeSinceMove >= currentSpeed){
            timeSinceMove = 0;
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
