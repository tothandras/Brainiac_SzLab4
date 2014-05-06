package com.brainiac.model;

public class Hobbit extends Enemy {
    /**
     * Beállítjuk a megfellelő kezdő sebességet, életet és pozíciót
     *
     * @param position Kezdőpozíció
     */
    public Hobbit(Position position) {
        this.position = new Position(position.getX(), position.getY());
        life = 50;
        speed = 3;
    }

    /**
     * Hobbitot sebezzük
     *
     * @param damage: Sebzés
     */
    @Override
    public void hurt(Damage damage) {
        // TODO: hol hal meg?
        int d = damage.getDamage(EnemyType.Hobbit);
        life -= d;
    }

    /**
     * Mozgatjuk az törpöt a megfelelő irányba
     *
     * @param direction: Haladás iránya
     * @param blockage:  Ha az útjában akadály van, akkor megkapja paraméterben
     */
    @Override
    public void move(Direction direction, Blockage blockage) {
        // Pillanatnyi sebesség
        int currentSpeed = speed;
        if (blockage != null) {
            currentSpeed += blockage.block(EnemyType.Hobbit);
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
