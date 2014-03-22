package com.brainiac.model;

import com.brainiac.controller.Skeleton;

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
