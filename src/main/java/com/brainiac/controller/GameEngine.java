package com.brainiac.controller;

import com.brainiac.model.*;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class GameEngine implements Runnable {
    private GameElements gameElements;
    private GameState gameState;

    public GameEngine(GameElements gameElements) {
        this.gameElements = gameElements;
    }

    public void startNewGame() {
        gameElements.saruman.setSpellPower(100);
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Új kör indítása. Eltávolítja a pályáról az akadályokat.
     *
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
            enemy.getPosition().setX(0);
            enemy.getPosition().setY((int) gameElements.map.getPaths().get(0).roads.get(0).getY1());
            gameElements.enemies.add(enemy);
        }
    }

    /**
     * Játék futtatása.
     */
    @Override
    public void run() {
        if (checkGameState()) {
            System.out.println("Game Over");
        } else {
            if (gameState == GameState.Step) {
                step();
                fire();
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void step() {
        for (Enemy enemy : gameElements.enemies) {
            Blockage blockage = null;
            for (Blockage block : gameElements.blockages) {
                if (Math.sqrt(Math.pow(enemy.getPosition().getX() - block.getPosition().getX(), 2) + Math.pow(enemy.getPosition().getY() - block.getPosition().getY(), 2)) < 2) {
                    blockage = block;
                }
            }
            for (Path path : gameElements.map.getPaths()) {
                for (Line2D road : path.roads) {
                    if (road.contains(enemy.getPosition().getX(), enemy.getPosition().getY())) {
                        if (road.getX2() == road.getX1() && road.getY2() > road.getY1()) {
                            // eszak
                            enemy.move(Direction.NORTH, blockage);
                        } else if (road.getX2() == road.getX1() && road.getY2() < road.getY1()) {
                            // del
                            enemy.move(Direction.SOUTH, blockage);
                        } else if (road.getX2() > road.getX1() && road.getY2() == road.getY1()) {
                            // kelet
                            enemy.move(Direction.EAST, blockage);
                        } else if (road.getX2() < road.getX1() && road.getY2() == road.getY1()) {
                            // nyugat
                            enemy.move(Direction.WEST, blockage);
                        }
                    }
                }
            }
        }
    }

    private void fire() {
        for (Tower tower : gameElements.towers) {
            for (Enemy enemy : gameElements.enemies) {
                if (Math.sqrt(Math.pow(tower.getPosition().getX() - enemy.getPosition().getX(), 2) + Math.pow(tower.getPosition().getY() - enemy.getPosition().getY(), 2)) < tower.getRange()) {
                    tower.fire(enemy);
                    break;
                }
            }
        }
    }

    /**
     * Ellenőrzi az egységek helyzetét.
     *
     * @return true, ha bármely ellenség elérte a végzet hegyét; false, különben
     */
    private boolean checkGameState() {
        boolean end = false;
        Point2D baradDur = new Point2D.Double(gameElements.map.getWidth() / 2, gameElements.map.getHeight() / 2);

        for (Enemy enemy : gameElements.enemies) {
            int x = enemy.getPosition().getX();
            int y = enemy.getPosition().getY();

            if (Math.sqrt(Math.pow(x - baradDur.getX(), 2) + Math.pow(y - baradDur.getY(), 2)) < 3) {
                end = true;
                break;
            }
        }

        return end;
    }
}
