package com.brainiac.controller;

import com.brainiac.model.*;

import java.util.List;
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
        do {
            this.newRound();
            for (int i = 0; i < gameElements.towers.size(); ++i) {
                if (Skeleton.getBoolean((i+1) + " torony lő?")) {
                    gameElements.towers.get(i).fire(gameElements.enemies.get(0));
                }
            }
            this.checkGameState();
        } while (Skeleton.getBoolean("Új kör indítása?"));
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
        int nTowers = Skeleton.getInt("Hány torony legyen?");
        for (int i = 0; i < nTowers; ++i) {
            Skeleton.writeLine("Hova kerüljön a(z) " + (i + 1) + ". torony?");
            int x = Skeleton.getInt("X: ");
            int y = Skeleton.getInt("Y: ");
            Skeleton.writeFunctionDetails("gameElements.towers.add(Tower tower)");
            gameElements.towers.add(new Tower(new Position(x, y)));
            Skeleton.writeReturnValue("void");
            if(Skeleton.getBoolean("Fejlesztjük a tornyot?"))
                gameElements.towers.get(i).upgrade(new TowerCrystal(EnemyType.Dwarf,10));

        }
        int nBlockages = Skeleton.getInt("Hány akadály legyen?");
        for (int i = 0; i < nBlockages; ++i) {
            Skeleton.writeLine("Hova kerüljön a(z) " + (i + 1) + ". akadály?");
            int x = Skeleton.getInt("X: ");
            int y = Skeleton.getInt("Y: ");
            Skeleton.writeFunctionDetails("gameElements.blockages.add(Blockage blockage)");
            gameElements.blockages.add(new Blockage(new Position(x, y)));
            Skeleton.writeReturnValue("void");
            if(Skeleton.getBoolean("Fejlesztjük az akadályt?"))
                gameElements.blockages.get(i).upgrade(new BlockageCrystal(EnemyType.Dwarf,10));
        }
        Skeleton.writeReturnValue("void");
    }

    public void checkGameState() {
        boolean end = false;
        Skeleton.writeFunctionDetails("GameEngine.checkGameState()");
        for (Enemy enemy : gameElements.enemies) {
            Position currentPosition = enemy.getPosition();
            if (!end && Skeleton.getBoolean("Elérte ez az ellenség a Végzet hegyét?" + "( Ellenség pozíciója: (" + currentPosition.getX() + ", " + currentPosition.getY() + ") )")) {
                end = true;
                break;
            }
        }
        Skeleton.writeReturnValue(new Boolean(end).toString());
    }

    public Direction getDirection(Enemy enemy){
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
        Skeleton.writeFunctionDetails("GameEngine.getDirection()");
        Skeleton.writeReturnValue("Direction d");
        return direction;
    }

    public void isGameOver() {
        Skeleton.writeFunctionDetails("isGameOver()");
        Skeleton.writeReturnValue("void");
    }
}
