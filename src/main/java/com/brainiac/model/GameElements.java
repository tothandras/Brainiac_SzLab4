package com.brainiac.model;

import sun.org.mozilla.javascript.internal.ast.Block;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameElements {
    public List<Enemy> enemies;
    public List<Tower> towers;
    public List<Blockage> blockages;
    public Saruman saruman;
    public Map map;
    public Fog fog;
    public List<Line2D> shots;

    /**
     * Létrehozzuk a megfelelő tárolókat és játék elemeket
     */
    public GameElements() {
        enemies = new ArrayList<Enemy>();
        towers = new ArrayList<Tower>();
        blockages = new ArrayList<Blockage>();
        saruman = new Saruman();
        map = new Map(600, 600);
        shots = new ArrayList<Line2D>();
    }

    /**
     * A tornyok listáját visszaadó függvény. szálkezelési probléma elkerüléséhez kell
     * @return a tornyo egy új, módosíthatatlan listája
     */
    public List<Tower> getTowers(){
        return new ArrayList<Tower>(towers);
    }

    /**
     * Az akadályok listáját visszaadó függvény. szálkezelési probléma elkerüléséhez kell
     * @return az akadályok egy új, módosíthatatlan listája
     */
    public List<Blockage> getBlockages(){
        return new ArrayList<Blockage>(blockages);
    }

    /**
     * Az ellenségek listáját visszaadó függvény. szálkezelési probléma elkerüléséhez kell
     * @return az ellenségek egy új, módosíthatatlan listája
     */
    public List<Enemy> getEnemies(){
        return new ArrayList<Enemy>(enemies);
    }
}
