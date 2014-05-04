package com.brainiac.controller;

import com.brainiac.model.*;

import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    private GameElements gameElements;
    // Játék állapota
    public GameState gameState;

    /**
     * Konstuktor
     * A játékmotor működéséhez szűkség van a játék elemeire
     *
     * @param gameElements Játék elemek
     */
    public GameEngine(GameElements gameElements) {
        this.gameElements = gameElements;
    }

    /**
     * Új játék indítása
     */
    public void startNewGame() {
        // Szarumán varázserejének beállítása
        gameElements.saruman.setSpellPower(100);
        // Először építés fázisban vagyunk
        gameState = GameState.Step;
        newRound(1);
    }

    /**
     * Új kör indítása. Eltávolítja a pályáról az akadályokat.
     *
     * @param numberOfEnemies Ellenségek száma.
     */
    public void newRound(int numberOfEnemies) {

        // Ellenségek felhelyezése a pályára

        // Ellenőrizzük, hogy van-e útvonalunk
        if (!gameElements.map.getPaths().isEmpty()) {
            List<Position> startingPositions = new ArrayList<Position>();

            for (Path path : gameElements.map.getPaths()) {
                // Ha az útvonal valóban utakból áll
                if (!path.getRoads().isEmpty()) {
                    // 0. indexű mindig a kezdőpont
                    startingPositions.add(new Position((int) path.getRoads().get(0).getX1(), (int) path.getRoads().get(0).getY1()));
                }
            }

            Random random = new Random();
            // Véletlenszerű típusú, numberOfEnemies számú ellenséget elhelyezünk az út elejére
            for (int i = 0; i < numberOfEnemies; ++i) {
                Enemy enemy;
                // Véletlenszerűen választunk egyet az útvonalak közül
                // Lehet különböző kezdőpontjuk
                Position startingPosition = new Position(startingPositions.get(Math.abs(random.nextInt()) % startingPositions.size()));

                // Véletlenszerűen választunk az ellenség típusok közül
                switch (Math.abs(random.nextInt()) % 4) {
                    case 0:
                        enemy = new Dwarf(startingPosition);
                        break;
                    case 1:
                        enemy = new Elf(startingPosition);
                        break;
                    case 2:
                        enemy = new Hobbit(startingPosition);
                        break;
                    default:
                        enemy = new Man(startingPosition);
                        break;
                }
                gameElements.enemies.add(enemy);
            }
        }
    }

    /**
     * Játék állapotának frissítése.
     */
    public void update() {
        // Csak akkor léptetünk, ha nem építési szakaszban vagyunk
        if (gameState == GameState.Step) {
            // Ha minden ellenség halott
            if (gameElements.enemies.isEmpty()) {
                // Levesszük az akadályokat a pályáról
                gameElements.blockages.clear();
                // Vége a körnek, építési szakasz
                gameState = GameState.Build;
            }
            // Ha valamelyik ellenség elérte a végzet hegyét
            else if (checkGameState()) {
                // Vége a játéknak, veszítettünk
                gameState = GameState.End;
            }
            // Egyébként léptetjük az ellenségeket és tüzelünk a tornyokkal
            else {
                step();
                //fire();
            }
        }
    }

    private void step() {
        Random random = new Random();

        // Akadályok ellenőrzése
        for (Enemy enemy : gameElements.enemies) {
            Blockage blockage = null;
            for (Blockage block : gameElements.blockages) {
                if (enemy.getPosition().distance(block.getPosition()) < 2) {
                    blockage = block;
                }
            }

            // Megnézzük melyik úton van rajta
            List<Line2D> roads = new ArrayList<Line2D>();
            for (Path path : gameElements.map.getPaths()) {
                for (Line2D road : path.getRoads()) {
                    // Ha rajta van az úton
                    if (enemy.getPosition().distanceFromRoad(road) == 0) {
                        roads.add(road);
                    }

                }
            }

            // Véletlenszerűen választunk egy útat, ami mentén mozgatjuk
            if (!roads.isEmpty()) {
                Line2D road = roads.get(Math.abs(random.nextInt() % roads.size()));
                enemy.move(Path.getDirection(road), blockage);
            }
        }
    }

    private void fire() {
        for (Tower tower : gameElements.towers) {
            int towerRange = tower.getRange();
            if (gameElements.fog != null) {
                if (gameElements.fog.getMiddle().distance(tower.getPosition()) < gameElements.fog.getRange()) {
                    towerRange = towerRange / 2;
                }
            }
            for (Enemy enemy : gameElements.enemies) {
                if (tower.getPosition().distance(enemy.getPosition()) < towerRange) {
                    System.out.println("Torony (" + tower.getPosition().getX() + ", " + tower.getPosition().getY() + "): Lő");

                    Enemy temp = tower.fire(enemy);
                    if (enemy.getLife() <= 0) {
                        gameElements.enemies.remove(enemy);
                        System.out.println("Ellenség (" + enemy.getPosition().getX() + ", " + enemy.getPosition().getY() + "): Meghal");

                        gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() + 1);
                    } else if (temp != null) {
                        gameElements.enemies.add(temp);
                        System.out.println("Ellenség (" + enemy.getPosition().getX() + ", " + enemy.getPosition().getY() + "): félbevágódik");

                    }
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
        // A végzet hegye (width, height / 2.0)-ben van
        Position baradDur = new Position(gameElements.map.getWidth() / 2, gameElements.map.getHeight() / 2);

        // Minden ellenségre megvizsgáljuk elérte-e
        for (Enemy enemy : gameElements.enemies) {
            // Ha igen, akkor vége van
            if (baradDur.distance(enemy.getPosition()) < 3) {
                return true;
            }
        }

        return false;
    }

    public void handleMouseEvent(MouseEvent event, Action action) {
        // Csak akkor tudunk építeni, ha építési állapotban vagyunk
        if (gameState == GameState.Build) {
            int x = event.getX();
            int y = event.getY();
            Position p = new Position(x, y);

            switch (action) {
                // Akadály építése
                case BUILD_BLOCKAGE:
                    // Akadály ára
                    int costOfBlockageBuild = 10;
                    // Utra próbáljuk-e letenni
                    boolean isOnRoad = false;

                    for (Path path : gameElements.map.getPaths()) {
                        for (Line2D road : path.getRoads()) {
                            double normalLength = Math.sqrt(Math.pow(road.getX2() - road.getX1(), 2) + Math.pow(road.getY2() - road.getY1(), 2));
                            double distance = Math.abs((p.getX() - road.getX1()) * (road.getY2() - road.getY1()) - (p.getY() - road.getY1()) * (road.getX2() - road.getX1())) / normalLength;
                            if (distance < 2) {
                                isOnRoad = true;
                                System.out.println("ON ROAD");
                                break;
                            }
                        }
                    }
                    if (isOnRoad) {
                        boolean theSamePosition = false;
                        for (Blockage blockage : gameElements.blockages) {
                            if (blockage.getPosition().distance(p) < 5) {
                                theSamePosition = true;

                                System.out.println("Akadály építése sikertelen: Van m'r");
                            }
                        }
                        if (!theSamePosition) {
                            if (gameElements.saruman.getSpellPower() < costOfBlockageBuild) {
                                System.out.println("Akadály építése sikertelen: nincs elég varázserő.");

                            } else {
                                gameElements.blockages.add(new Blockage(new Position(x, y)));
                                gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfBlockageBuild);
                                System.out.println("Akadály építése sikeres.");

                            }
                        }
                    } else {
                        System.out.println("Akadály építése sikertelen: az akadályt csak útra szabad építeni.");

                    }
                    break;
                case BUILD_TOWER:
                    break;
                case UPGRADE_BLOCKAGE:
                    break;
                case UPGRADE_TOWER:
                    break;
            }
        }

    }

    /*
    public void handleEvent(Event event) {
        switch (event.action) {
            case BUILD_TOWER: {
                int costOfTowerBuild = 10;
                boolean isOnRoad = false;
                for (Path path : gameElements.map.getPaths()) {
                    for (Line2D road : path.getRoads()) {
                        if (road.ptLineDist((double) event.x, (double) event.y) == 0) {
                            isOnRoad = true;
                        }
                    }
                }
                if (!isOnRoad) {
                    boolean theSamePosition = false;
                    for (Tower tower : gameElements.towers) {
                        if ((tower.getPosition().getX() == event.x) && (tower.getPosition().getY() == event.y)) {
                            theSamePosition = true;
                            System.out.println("Torony építése sikertelen: a tornyot csak üres helyre lehet építeni");

                        }
                    }
                    if (!theSamePosition) {
                        if (gameElements.saruman.getSpellPower() < costOfTowerBuild) {
                            System.out.println("Torony építése sikertelen: nincs elég varázserő.");

                        } else {
                            gameElements.towers.add(new Tower(new Position(event.x, event.y)));
                            gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfTowerBuild);
                            System.out.println("Torony építése sikeres.");

                        }
                    }
                } else {
                    System.out.println("Torony építése sikertelen: a tornyot csak üres helyre lehet építeni");

                }
            }
            break;
            case BUILD_BLOCKAGE: {
                int costOfBlockageBuild = 10;
                boolean isOnRoad = false;
                for (Path path : gameElements.map.getPaths()) {
                    for (Line2D road : path.getRoads()) {
                        if (road.ptLineDist((double) event.x, (double) event.y) == 0) {
                            isOnRoad = true;
                        }
                    }
                }
                if (isOnRoad) {
                    boolean theSamePosition = false;
                    for (Blockage blockage : gameElements.blockages) {
                        if ((blockage.getPosition().getX() == event.x) && (blockage.getPosition().getY() == event.y)) {
                            theSamePosition = true;
                            System.out.println("Akadály építése sikertelen: az akadályt csak útra szabad építeni.");

                        }
                    }
                    if (!theSamePosition) {
                        if (gameElements.saruman.getSpellPower() < costOfBlockageBuild) {
                            System.out.println("Akadály építése sikertelen: nincs elég varázserő.");

                        } else {
                            gameElements.blockages.add(new Blockage(new Position(event.x, event.y)));
                            gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfBlockageBuild);
                            System.out.println("Akadály építése sikeres.");

                        }
                    }
                } else {
                    System.out.println("Akadály építése sikertelen: az akadályt csak útra szabad építeni.");

                }
            }
            break;
            case UPGRADE_TOWER: {
                boolean thereIsTower = false;
                int costOfTowerUpgrade = 5;
                for (Tower tower : gameElements.towers) {
                    if ((tower.getPosition().getX() == event.x) && (tower.getPosition().getY() == event.y)) {
                        thereIsTower = true;
                        if (gameElements.saruman.getSpellPower() < costOfTowerUpgrade) {
                            System.out.println("Torony fejlesztése sikertelen: nincs elég varázserő.");

                        } else {
                            tower.upgrade(new TowerCrystal(event.against, event.damageIncrement,
                                    event.fireRateIncrement, event.rangeIncrement));
                            gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfTowerUpgrade);
                            System.out.println("Torony fejlesztése sikeres.");

                        }
                    }
                }
                if (!thereIsTower) {
                    System.out.println("Torony fejlesztése sikertelen: nem létezik a megadott helyen torony.");

                }
            }
            break;
            case UPGRADE_BLOCKAGE: {
                boolean thereIsBlockage = false;
                int costOfBlockageUpgrade = 5;
                for (Blockage blockage : gameElements.blockages) {
                    if ((blockage.getPosition().getX() == event.x) && (blockage.getPosition().getY() == event.y)) {
                        thereIsBlockage = true;
                        if (gameElements.saruman.getSpellPower() < costOfBlockageUpgrade) {
                            System.out.println("Akadály fejlesztése sikertelen: nincs elég varázserő.");

                        } else {
                            blockage.upgrade(new BlockageCrystal(event.against, event.slowIncrement));
                            gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfBlockageUpgrade);
                            System.out.println("Akadály fejlesztése sikeres.");

                        }
                    }
                }
                if (!thereIsBlockage) {
                    System.out.println("Akadály fejlesztése sikertelen: nem létezik a megadott helyen akadály.");

                }
            }
            break;
            default:
                break;
        }
    }
    */
}
