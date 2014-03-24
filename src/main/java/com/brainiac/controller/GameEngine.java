package com.brainiac.controller;

import com.brainiac.Skeleton;
import com.brainiac.model.*;

import java.util.Iterator;
import java.util.Random;

public class GameEngine {
    private GameElements gameElements;

    public GameEngine(GameElements gameElements) {
        this.gameElements = gameElements;
        Skeleton.writeFunctionDetails("GameEngine(GameElements gameElements)");
        Skeleton.writeReturnValue("");
    }

    public void startNewGame() {
        Skeleton.writeFunctionDetails("GameEngine.startNewGame()");
        this.newRound();
        Skeleton.writeReturnValue("void");
    }

    public void newRound() {
        Skeleton.writeFunctionDetails("GameEngine.newRound()");
        Random rn = new Random();
        int nEnemies = Skeleton.getInt("Hány ellenség jön az új körben?");
        for (int i = 0; i < nEnemies; ++i) {
            int j = Math.abs(rn.nextInt()) % 4;
            switch (j) {
                case 0:
                    gameElements.enemies.add(new Dwarf());
                    break;
                case 1:
                    gameElements.enemies.add(new Elf());
                    break;
                case 2:
                    gameElements.enemies.add(new Hobbit());
                    break;
                case 3:
                    gameElements.enemies.add(new Man());
                    break;
            }
        }
        int nTowers = Skeleton.getInt("Hány új torony legyen?");
        for (int i = 0; i < nTowers; ++i) {
            Skeleton.writeLine("Hova kerüljön a(z) " + (i + 1) + ". torony?");
            int x = Skeleton.getInt("X: ");
            int y = Skeleton.getInt("Y: ");
            Skeleton.writeFunctionDetails("gameElements.towers.add(Tower tower)");
            gameElements.towers.add(new Tower(new Position(x, y)));
            Skeleton.writeReturnValue("void");
            if (Skeleton.getBoolean("Fejlesztjük a tornyot?"))
                gameElements.towers.get(i).upgrade(new TowerCrystal(EnemyType.Dwarf, 10));

        }
        int nBlockages = Skeleton.getInt("Hány akadály legyen?");
        for (int i = 0; i < nBlockages; ++i) {
            Skeleton.writeLine("Hova kerüljön a(z) " + (i + 1) + ". akadály?");
            int x = Skeleton.getInt("X: ");
            int y = Skeleton.getInt("Y: ");
            Skeleton.writeFunctionDetails("gameElements.blockages.add(Blockage blockage)");
            gameElements.blockages.add(new Blockage(new Position(x, y)));
            Skeleton.writeReturnValue("void");
            if (Skeleton.getBoolean("Fejlesztjük az akadályt?"))
                gameElements.blockages.get(i).upgrade(new BlockageCrystal(EnemyType.Dwarf, 10));
        }

        Iterator<Enemy> iterator = gameElements.enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            gameElements.map.getPaths();
            int n = Skeleton.getInt("Melyik irányba haladjon az ellenség? (alapértelmezett: észak, 1: kelet, 2: dél, 3: nyugat)");
            Direction direction;
            switch (n) {
                case 1:
                    direction = Direction.WEST;
                    break;
                case 2:
                    direction = Direction.SOUTH;
                    break;
                case 3:
                    direction = Direction.EAST;
                    break;
                default:
                    direction = Direction.NORTH;
                    break;
            }
            for (Blockage blockage : gameElements.blockages) {
                blockage.getPosition();
            }
            enemy.getPosition();
            enemy.move(direction, nBlockages == 0 ? null : gameElements.blockages.get(0));

            for (int i = 0; i < gameElements.towers.size(); ++i) {
                if (Skeleton.getBoolean((i + 1) + " torony lő?")) {
                    Enemy firstEnemy = gameElements.enemies.get(0);
                    gameElements.towers.get(i).getPosition();
                    gameElements.towers.get(i).getRange();
                    firstEnemy.getPosition();
                    if (Skeleton.getBoolean("Lövi a torony az elleséget?")) {
                        gameElements.towers.get(i).fire(firstEnemy);
                        if (Skeleton.getBoolean("Meghalt az ellenség?")) {
                            iterator.remove();
                            Saruman saruman = gameElements.saruman;
                            saruman.setSpellPower(saruman.getSpellPower() + Skeleton.getInt("Mennyivel növeljük Saruman varázserejét?"));
                        }
                    }
                }
            }
        }
        if (!this.checkGameState()) {
            newRound();
        } else {
            Skeleton.writeReturnValue("void");
        }
    }

    public boolean checkGameState() {
        boolean end = false;
        Skeleton.writeFunctionDetails("GameEngine.checkGameState()");
        for (Enemy enemy : gameElements.enemies) {
            Position currentPosition = enemy.getPosition();
            end = Skeleton.getBoolean("Elérte ez az ellenség a Végzet hegyét?" + "( Ellenség pozíciója: (" + currentPosition.getX() + ", " + currentPosition.getY() + ") )");
            if (end) {
                break;
            }
        }
        Skeleton.writeReturnValue(Boolean.toString(end));
        return end;
    }
}
