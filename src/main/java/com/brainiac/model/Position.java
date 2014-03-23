package com.brainiac.model;

import com.brainiac.controller.Skeleton;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        Skeleton.writeFunctionDetails("Position(int:"+x+","+"int: "+ y+")");
        Skeleton.writeReturnValue("void");
    }

    public int getX() {
        Skeleton.writeFunctionDetails("Position.getX()");
        Skeleton.writeReturnValue("int: "+x);
        return x;
    }

    public int getY() {
        Skeleton.writeFunctionDetails("Position.getY()");
        Skeleton.writeReturnValue("int: "+y);
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
