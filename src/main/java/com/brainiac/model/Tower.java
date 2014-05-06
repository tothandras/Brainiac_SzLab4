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
    // Fejlesztve vagyunk?
    // TODO: biztos kell? Máshogy rajzoljuk ki a fejlesztettet mint a nem fejlesztettet?
    public boolean upgraded;
    // Az utolsó tüzelés óta eltelt óraütések száma
    private int timeSinceShoot;

    /**
     * Új torony létrehozása a pálya egy adott pozíciójára
     *
     * @param position A torony pozíciója
     */
    public Tower(Position position) {
        this.position = position;
        this.upgraded = false;
        this.damage = new Damage();
        this.fireRate = 10;
        this.range = 100;
        this.cutChance = 0.05;
        timeSinceShoot = 0;
    }

    /**
     * Torony lő egyet a paramáterében megkapott ellenségre
     *
     * @param enemy Az esetlegesen félbevágott ellenfél új darabja
     */
    public Enemy fire(Enemy enemy) {
        if (timeSinceShoot >= fireRate){
            timeSinceShoot = 0;
            enemy.hurt(damage);
            if (Math.random() < cutChance) {
                return enemy.cut();
            }
        }
        return null;
    }

    /**
     * Ezen függvény meghívásával jelezzük a toronynak, hogy telik az idő. Hatására esetleg lő a torony
     */
    public void tick() {
        timeSinceShoot += 1;
    }

    /**
     * Visszatér azzal az információval, hogy a torony lőhet-e már újra.
     * @return Igaz, ha már lőhet a torony.
     */
    public boolean canShoot(){
        return (timeSinceShoot >= fireRate);
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
     * A függvény segítségével beállíthatjuk a félbevágós lövés valószínűségét
     *
     * @param cutChance A félbevágós lövés új valószínűsége
     */
    public void setCutChance(double cutChance) {
        this.cutChance = cutChance;
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
        fireRate = fireRate + crystal.getFireRateIncrement();
        // Sebzés növelése a kristályban meghatározott ellenség ellen és mértékben
        damage.setDamage(damage.getDamage(crystal.getAgainst()) + crystal.getIncrement(), crystal.getAgainst());

        upgraded = true;
    }
}
