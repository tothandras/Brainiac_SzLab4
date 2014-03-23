package com.brainiac.model;

import com.brainiac.controller.Skeleton;

public class Saruman {
    private int spellPower;

    public Saruman() {
        Skeleton.writeFunctionDetails("Saruman()");
        Skeleton.writeReturnValue("");
    }

    // atneveztem
    public int getSpellPower() {

        Skeleton.writeFunctionDetails("Saruman.getSpellPower()");
        Skeleton.writeReturnValue("int: "+spellPower);
        return spellPower;
    }

    // atneveztem
    public void setSpellPower(int spellPower) {
        Skeleton.writeFunctionDetails("Saruman.setSpellPower()");
        this.spellPower = spellPower;
        Skeleton.writeReturnValue("void");
    }
}
