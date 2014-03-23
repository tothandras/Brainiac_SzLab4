package com.brainiac.model;

import java.util.ArrayList;
import java.util.List;

public class GameElements {
    public List<Enemy> enemies;
    public List<Tower> towers;
    public List<Blockage> blockages;
    public Saruman saruman;
    public Map map;

    public GameElements() {
        enemies = new ArrayList<Enemy>();
        towers = new ArrayList<Tower>();
        blockages = new ArrayList<Blockage>();
        saruman = new Saruman();
        map = new Map(100, 100);
    }
}
