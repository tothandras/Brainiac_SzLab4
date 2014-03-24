package com.brainiac.model;

import com.brainiac.controller.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class GameElements {
    public List<Enemy> enemies;
    public List<Tower> towers;
    public List<Blockage> blockages;
    public Saruman saruman;
    public Map map;

    public GameElements() {
        Skeleton.writeFunctionDetails("GameElements()");
        Skeleton.writeFunctionDetails("ArrayList<Enemy>()");
        enemies = new ArrayList<Enemy>();
        Skeleton.writeReturnValue("");
        Skeleton.writeFunctionDetails("ArrayList<Tower>()");
        towers = new ArrayList<Tower>();
        Skeleton.writeReturnValue("");
        Skeleton.writeFunctionDetails("ArrayList<Blockage>()");
        blockages = new ArrayList<Blockage>();
        Skeleton.writeReturnValue("");
        saruman = new Saruman();
        map = new Map(100, 100);
        Skeleton.writeReturnValue("");
    }
}
