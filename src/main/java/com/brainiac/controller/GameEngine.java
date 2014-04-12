package com.brainiac.controller;

import com.brainiac.model.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class GameEngine implements Runnable {
    private GameElements gameElements;

    public GameEngine(GameElements gameElements) {
        this.gameElements = gameElements;
    }

    public void startNewGame() {
        gameElements.saruman.setSpellPower(100);
        newRound(1);
    }

    /**
     * Új kör indítása. Eltávolítja a pályáról az akadályokat.
     * @param numberOfEnemies Ellenségek száma.
     */
    public void newRound(int numberOfEnemies) {
        gameElements.blockages.clear();
        gameElements.enemies.clear();
        Random random = new Random();
        Enemy enemy;
        for (int i = 0; i < numberOfEnemies; ++i) {
            switch (random.nextInt() % 4) {
                case 0:
                    enemy = new Dwarf();
                    break;
                case 1:
                    enemy = new Elf();
                    break;
                case 2:
                    enemy = new Hobbit();
                    break;
                default:
                    enemy = new Man();
                    break;
            }
            gameElements.enemies.add(enemy);
        }
    }

    /**
     *
     */
    @Override
    public void run() {

    }

    /**
     * Ellenőrzi az egységek helyzetét.
     * @return true, ha bármely ellenség elérte a végzet hegyét; false, különben
     */
    public boolean checkGameState() {
        boolean end = false;
        Point2D baradDur = new Point2D.Double(gameElements.map.getWidth() / 2, gameElements.map.getHeight() / 2);

        for (Enemy enemy : gameElements.enemies) {
            int x = enemy.getPosition().getX();
            int y = enemy.getPosition().getY();
            if (x >= baradDur.getX() - 3 && x <= baradDur.getX() + 3 && y >= baradDur.getY() - 3 && y <= baradDur.getY() + 3) {
                end = true;
                break;
            }
        }

        return end;
    }
}
