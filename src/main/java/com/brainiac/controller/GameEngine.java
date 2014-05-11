package com.brainiac.controller;

import com.brainiac.model.*;
import com.brainiac.view.GameFrame;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    private GameElements gameElements;
    // a játék kezdete óta eltelt update-k száma
    private int ticks;
    // Játék állapota
    public GameState gameState;
    private Fog fog;

    /**
     * Konstuktor
     * A játékmotor működéséhez szűkség van a játék elemeire
     *
     * @param gameElements Játék elemek
     */
    public GameEngine(GameElements gameElements) {
        this.gameElements = gameElements;
        ticks = 0;
    }

    /**
     * Új játék indítása
     */
    public void startNewGame() {
        // Szarumán varázserejének beállítása
        gameElements.saruman.setSpellPower(30);
        // Először építés fázisban vagyunk
        gameState = GameState.Step;
        newRound(10);
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
                Position startingPosition = startingPositions.get(Math.abs(random.nextInt()) % startingPositions.size());

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
        fog = new Fog(new Position(300,300),150);
    }

    /**
     * Játék állapotának frissítése.
     */
    public void update() {
        // növeljük az eltelt lépések számát
        ticks = ticks + 1;

        // Csak akkor léptetünk, ha nem építési szakaszban vagyunk
        if (gameState == GameState.Step) {
            // Ha minden ellenség halott
            if (gameElements.enemies.isEmpty()) {
                // Levesszük az akadályokat a pályáról
                gameElements.blockages.clear();
                // Vége a körnek, építési szakasz
                //gameState = GameState.Build;
                newRound(10);

            }
            // Ha valamelyik ellenség elérte a végzet hegyét
            else if (checkGameState()) {
                // Vége a játéknak, veszítettünk
                gameState = GameState.End;
            }
            // Egyébként léptetjük az ellenségeket és tüzelünk a tornyokkal
            else {
                   //TODO Ezt lehet nem itt kéne megvalósítani, és nem ilyen módon
                if((ticks%800)==1){
                    if(gameElements.fog == null){
                        gameElements.fog = fog;
                    }else{
                        gameElements.fog = null;
                    }
                }
                step();
                fire();
            }
        }
    }

    /**
     * Egységek léptetése
     */
    private void step() {
        Random random = new Random();

        // Akadályok ellenőrzése
        for (Enemy enemy : gameElements.enemies) {
            Blockage blockage = null;
            for (Blockage block : gameElements.blockages) {
                if (enemy.getPosition().distance(block.getPosition()) < block.getRange()) {
                    blockage = block;
                }
            }

            // Kell-e lépnie az ellenséges egységnek ebben a körben
            if ((ticks % enemy.getSpeed(blockage) == 0)){
                // Megnézzük melyik úton van rajta
                List<Line2D> roads = new ArrayList<Line2D>();
                for (Path path : gameElements.map.getPaths()) {
                    // Visszafelé iterálunk, hogy az előbbre lévő utat találjuk meg az utak csatlakozásánál
                    for (int i = path.getRoads().size(); i > 0; i--) {
                        Line2D road = path.getRoads().get(i - 1);
                        if (enemy.getPosition().distanceFromRoad(road) == 0) {
                            roads.add(road);
                            // Találtunk már utat az útvonalon
                            break;
                        }
                    }
                }

                // Véletlenszerűen választunk egy útat, ami mentén mozgatjuk
                if (!roads.isEmpty()) {
                    Line2D road = roads.get(Math.abs(random.nextInt() % roads.size()));
                    enemy.move(Path.getDirection(road));
                }
            }
        }
    }

    /**
     * Tornyok tüzelése
     */
    private void fire() {
        for (Tower tower : gameElements.towers) {
            if ((ticks % tower.getSpeed()) == 0) {
                int towerRange = tower.getRange();
                // Ha köd ereszkedik le
                if (gameElements.fog != null) {
                    if (gameElements.fog.getMiddle().distance(tower.getPosition()) < gameElements.fog.getRange()) {
                        towerRange = towerRange / 2;
                    }
                }
                for (Enemy enemy : gameElements.enemies) {
                    if (tower.getPosition().distance(enemy.getPosition()) < towerRange) {
                        System.out.println("Torony (" + tower.getPosition().getX() + ", " + tower.getPosition().getY() + "): Lő");

                        Enemy temp = tower.fire(enemy);
                        gameElements.shots.add(new Line2D.Double(tower.getPosition().getX(), tower.getPosition().getY(),
                                enemy.getPosition().getX(), enemy.getPosition().getY()));
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
    }

    /**
     * Ellenőrzi az egységek helyzetét.
     *
     * @return true, ha bármely ellenség elérte a végzet hegyét; false, különben
     */
    private boolean checkGameState() {
        // A végzet hegye (width, height / 2.0)-ben van
        Position baradDur = new Position(gameElements.map.getWidth(), gameElements.map.getHeight() / 2);
        // Minden ellenségre megvizsgáljuk elérte-e
        for (Enemy enemy : gameElements.enemies) {
            // Ha igen, akkor vége van
            if (baradDur.distance(enemy.getPosition()) < 3) {
                return true;
            }
        }

        return false;
    }

    /**
     * Események kezelése
     *
     * @param position Parancs pozíciója
     * @param action   Parancs
     * @return Igaz, ha sikerült
     */
    public boolean handleEvent(Position position, Action action) {
        // Csak akkor tudunk építeni, ha építési állapotban vagyunk
        if (gameState == GameState.Step) {
            int x = position.getX();
            int y = position.getY();
            int costOfTowerUpgrade=10;
            int costOfBlockageUpgrade=10;
            int costOfBlockageBuild = 10;
            switch (action) {
                // Akadály építése
                case BUILD_BLOCKAGE:
                    // Akadály ára

                    // útra próbáljuk-e letenni
                    boolean isOnRoad = false;

                    for (Path path : gameElements.map.getPaths()) {
                        for (Line2D road : path.getRoads()) {
                            if (position.distanceFromRoad(road) < path.sizeOfRoad) {
                                isOnRoad = true;
                                break;
                            }
                        }
                    }
                    if (isOnRoad) {
                        boolean theSamePosition = false;
                        for (Blockage blockage : gameElements.blockages) {
                            // Két akadály közelségének ellenőrzése
                            if (blockage.getPosition().distance(position) < blockage.getRange() * 2) {
                                theSamePosition = true;
                                System.out.println("Akadály építése sikertelen: Van már");
                            }
                        }
                        if (!theSamePosition && gameElements.saruman.getSpellPower() >= costOfBlockageBuild) {
                            // Nincs ugyanott akadály és van elég varázserő
                            gameElements.blockages.add(new Blockage(new Position(x, y)));
                            gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfBlockageBuild);
                            return true;
                        } else {
                            // Van már adakály a közelben, vagy nincs elég varázserő
                            return false;
                        }
                    } else {
                        // Akadályt csak útra szabad építeni
                        return false;
                    }
                case BUILD_TOWER:
                    int costOfTowerBuild = 10;
                    isOnRoad = false;
                    for (Path path : gameElements.map.getPaths()) {
                        for (Line2D road : path.getRoads()) {
                            //megvizsgálom, hogy elég távol van e az uttól a lerakandó torony
                            if (position.distanceFromRoad(road) < path.sizeOfRoad+5) {
                                isOnRoad = true;
                            }
                        }
                    }
                    if (!isOnRoad) {
                        boolean theSamePosition = false;
                        for (Tower tower : gameElements.towers) {
                            if ((tower.getPosition().getX() == x) && (tower.getPosition().getY() == y)) {
                                // Torony építése sikertelen: a tornyot csak üres helyre lehet építeni
                                theSamePosition = true;
                            }
                        }
                        if (!theSamePosition && gameElements.saruman.getSpellPower() >= costOfTowerBuild) {
                            // Torony építése sikeres
                            gameElements.towers.add(new Tower(new Position(x, y)));
                            gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfTowerBuild);
                            return true;
                        } else {
                            // Torony építése sikerertelen
                            return false;
                        }
                    } else {
                        // A tornyot csak üres helyre lehet építeni
                        return false;
                    }
                case UPGRADE_BLOCKAGE:
                case UPGRADE_TOWER:
                    // NE CSINÁLJON SEMMIT, DIREKT
                    break;
                case UPGRADE_TOWER_ELF:
                    for (Tower tower : gameElements.towers) {
                        if (Math.abs(tower.getPosition().getX()-x) < 5 && Math.abs(tower.getPosition().getY()-y) < 5) {
                            // a kiválasztott torony
                            if (gameElements.saruman.getSpellPower() >= costOfTowerUpgrade) {
                                // Van-e elegendő varázserő?
                                tower.upgrade(new TowerCrystal(EnemyType.Elf,10,0,0)); //10-zel többet sebez
                                gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfTowerUpgrade);
                                return true;
                            } else {
                                // Torony építése sikerertelen
                                return false;
                            }
                        }
                    }
                    return true;
                case UPGRADE_TOWER_DWARF:
                    for (Tower tower : gameElements.towers) {
                        if (Math.abs(tower.getPosition().getX()-x) < 5 && Math.abs(tower.getPosition().getY()-y) < 5) {
                            // a kiválasztott torony
                            if (gameElements.saruman.getSpellPower() >= costOfTowerUpgrade) {
                                // Van-e elegendő varázserő?
                                tower.upgrade(new TowerCrystal(EnemyType.Dwarf,10,0,0)); //10-zel többet sebez
                                gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfTowerUpgrade);
                                return true;
                            } else {
                                // Torony építése sikerertelen
                                return false;
                            }
                        }
                    }
                    return true;
                case UPGRADE_TOWER_MAN:
                    // TODO
                    System.out.println("MAN");
                    return true;
                case UPGRADE_TOWER_HOBBIT:
                    // TODO
                    System.out.println("HOBBIT");
                    return true;
                case UPGRADE_BLOCKAGE_ELF:
                    for (Blockage blockage : gameElements.blockages) {
                        if (Math.abs(blockage.getPosition().getX()-x) < 15 && Math.abs(blockage.getPosition().getY() - y) < 15) {
                            // a kiválasztott torony
                            if (gameElements.saruman.getSpellPower() >= costOfBlockageUpgrade) {
                                // Van-e elegendő varázserő?
                                blockage.upgrade(new BlockageCrystal(EnemyType.Elf,2));//2-vel lassít
                                gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfBlockageUpgrade);
                                return true;
                            } else {
                                // Blockage építése sikerertelen
                                return false;
                            }
                        }
                    }
                    return true;
                case UPGRADE_BLOCKAGE_DWARF:
                    for (Blockage blockage : gameElements.blockages) {
                        if (Math.abs(blockage.getPosition().getX()-x) < 15 && Math.abs(blockage.getPosition().getY() - y) < 15) {
                            // a kiválasztott torony
                            if (gameElements.saruman.getSpellPower() >= costOfBlockageUpgrade) {
                                // Van-e elegendő varázserő?
                                blockage.upgrade(new BlockageCrystal(EnemyType.Dwarf,5));//2-vel lassít
                                gameElements.saruman.setSpellPower(gameElements.saruman.getSpellPower() - costOfBlockageUpgrade);
                                return true;
                            } else {
                                // Blockage építése sikerertelen
                                return false;
                            }
                        }
                    }
                    return true;
                case UPGRADE_BLOCKAGE_MAN:
                    // TODO

                    System.out.println("BLO_MAN");
                    return true;
                case UPGRADE_BLOCKAGE_HOBBIT:
                    // TODO
                    System.out.println("BLO_HOBBIT");
                    return true;

            }
        }
        return false;
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
