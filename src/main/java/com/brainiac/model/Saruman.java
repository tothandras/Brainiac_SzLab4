package com.brainiac.model;

public class Saruman {
    private int spellPower;

    /**
     * Szarumán konstruktora. Szarumánnak a játék kezdetén 100 varázsereje lesz.
     */
    public Saruman() {
        spellPower = 100;
    }

    /**
     * Szarumán varázserejét lehet ezzel a függvénnyel lekérdezni.
     *
     * @return Szarumán varázsereje.
     */
    public int getSpellPower() {
        return spellPower;
    }

    /**
     * Szarumán varázserejét lehet ezzel a függvénnyel beállítani.
     *
     * @param spellPower Szarumán varázsereje ennyi lesz. Ez NEM a változás mértéke!
     */
    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }
}
