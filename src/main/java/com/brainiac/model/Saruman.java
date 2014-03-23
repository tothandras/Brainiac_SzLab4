package com.brainiac.model;

public class Saruman {
    private int spellPower;

    public Saruman() {
        Skeleton.writeFunctionDetails("Saruman()");
        Skeleton.writeReturnValue("void");
    }

    // atneveztem
    public int getSpellPower() {
        return spellPower;
    }

    // atneveztem
    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }
}
