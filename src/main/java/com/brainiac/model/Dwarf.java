package com.brainiac.model;

public class Dwarf extends Enemy {
    /**
     * Beállítjuk a megfellelő kezdő sebességet, életet és pozíciót
     *
     * @param position Kezdőpozíció
     */
    public Dwarf(Position position) {
        this.position = position;
        life = 100;
        speed = 1;
    }

    /**
     * Törpöt sebezzük
     *
     * @param damage: Sebzés
     */
    @Override
    public void hurt(Damage damage) {
        // TODO: hol hal meg?
        int d = damage.getDamage(EnemyType.Dwarf);
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
            int block = blockage.block(EnemyType.Dwarf);
            currentSpeed -= block > currentSpeed ? currentSpeed : block;
        }

        // Léptetés a megfelelő irányba
        switch (direction) {
            // Észak
            case NORTH:
                position.setY(position.getY() + currentSpeed);
                break;
            // Kelet
            case EAST:
                position.setX(position.getX() + currentSpeed);
                break;
            // Dél
            case SOUTH:
                position.setY(position.getY() - currentSpeed);
                break;
            // Nyugat
            case WEST:
                position.setX(position.getX() - currentSpeed);
                break;
            default:
                break;

        }
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
        // TODO: kicsit lehet el kéne tolni?
        Dwarf dwarf = new Dwarf(this.position);
        dwarf.life = this.life;
        return dwarf;
    }
}
