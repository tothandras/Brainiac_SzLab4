package com.brainiac.model;

public class Man extends Enemy {
    /**
     * Beállítjuk a megfellelő kezdő sebességet, életet és pozíciót
     *
     * @param position Kezdőpozíció
     */
    public Man(Position position) {
        this.position = new Position(position);
        life = 100;
        speed = 3;
    }

    /**
     * Embert sebezzük
     *
     * @param damage: Sebzés
     */
    @Override
    public void hurt(Damage damage) {
        // TODO: hol hal meg?
        int d = damage.getDamage(EnemyType.Man);
        life -= d;
    }

    /**
     * Mozgatjuk az embert a megfelelő irányba
     *
     * @param direction: Haladás iránya
     * @param blockage:  Ha az útjában akadály van, akkor megkapja paraméterben
     */
    @Override
    public void move(Direction direction, Blockage blockage) {
        // Pillanatnyi sebesség
        int currentSpeed = speed;
        if (blockage != null) {
            currentSpeed += blockage.block(EnemyType.Man);
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
        // TODO: kicsit lehet el kéne tolni?
        Man man = new Man(this.position);
        man.life = this.life;
        return man;
    }
}
