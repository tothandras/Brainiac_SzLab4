package com.brainiac.model;

import com.brainiac.Skeleton;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    // kulon setter mindegyikre, elteres az eredetitol
    public void setX(int x) {
        Skeleton.writeFunctionDetails("Position.setX()");
        Skeleton.writeReturnValue("void");
        this.x = x;
    }

    public void setY(int y) {
        Skeleton.writeFunctionDetails("Position.seYX()");
        Skeleton.writeReturnValue("void");
        this.y = y;
    }
}
