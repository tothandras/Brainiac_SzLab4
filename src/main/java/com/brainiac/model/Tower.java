package com.brainiac.model;

public class Tower {
    private Position position;
    // Sebzés osztály, külön-külön el van tárolva benne a különböző ellenségtípusokra vonatkozó sebzés mértéke
    private Damage damage;
    // Tüzelési sebesség
    private int fireRate;
    // Tüzelési hatótávolság
    private int range;
    // Szévágás valószínűsége (0-1)
    private double cutChance;

    /**
     * Új torony létrehozása a pálya egy adott pozíciójára
     *
     * @param position A torony pozíciója
     */
    public Tower(Position position) {
        this.position = position;
        this.damage = new Damage();
        this.fireRate = 20;
        this.range = 100;
        this.cutChance = 0.05;
    }

    /**
     * Torony lő egyet a paramáterében megkapott ellenségre
     *
     * @param enemy Az esetlegesen félbevágott ellenfél új darabja
     */
    public Enemy fire(Enemy enemy) {
        enemy.hurt(damage);
        if (Math.random() < cutChance) {
            return enemy.cut();
        }
        return null;
    }

    /**
     * Visszatér a torony pozíciójával
     *
     * @return position A pozíció
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Torony hatótávjának lekérdezése
     *
     * @return range A hatótáv
     */
    public int getRange() {
        return range;
    }

    /**
     * Torony tüzelési gyakoriságának lekérdezése
     *
     * @return a torony tüzelési gyakorisága
     */
    public int getSpeed() {
        return fireRate;
    }

    /**
     * Torony fejlesztése kristállyal
     *
     * @param crystal a kristály
     */
    public void upgrade(TowerCrystal crystal) {
        // Tüzelési hatótávolság növelése a kristályban meghatározott mértékben
        range = range + crystal.getRangeIncrement();
        // Tüzelési sebesség növelése a kristályban meghatározott mértékben
        //5 esetén nem csökkentünk tovább
        if(fireRate>5)
            fireRate = fireRate - crystal.getFireRateIncrement();
        // Sebzés növelése a kristályban meghatározott ellenség ellen és mértékben
        damage.setDamage(damage.getDamage(crystal.getAgainst()) + crystal.getIncrement(), crystal.getAgainst());
    }
}
