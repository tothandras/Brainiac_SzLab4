package com.brainiac;

import com.brainiac.model.*;

import java.awt.geom.Line2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Proto {
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    public static PrintWriter fileOut = null;
    Game game;
    boolean canBuild;

    public Proto(Game game){
        this.game = game;
        canBuild = false;
    }

    /**
     * A game osztályból is meghívható függvény. Ezzel kezdjük el a tesztelést.
     */
    public void doCommands(){
        try{
            while (true){
                String line = bufferedReader.readLine();
                execute(line);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     * Biztonságosan csinál egész számot egy stringből
     * @param value a kapott string
     * @return a stringből képzett egész szám. ha nem értelmes szövéget adtunk meg, 0-val tér vissza
     */
    int tryParseInt(String value) {
        try {
            int x = Integer.parseInt(value);
            return x;
        } catch(NumberFormatException nfe) {
            System.out.println("Nem megfelelő formátumú bemenet. 0-val számolunk tovább helyette.");
            return 0;
        }
    }

    /**
     * Ez a függvény fogja értelmezni a tesztelés során megadott parancsokat.
     */
    private void execute(String line) {
        try {
            String[] cmd = line.split(" ");
            if (cmd[0].equalsIgnoreCase("loadCommands")) {
                loadCommands(cmd);
            } else if (cmd[0].equalsIgnoreCase("beginWriteCommands")) {
                beginWriteCommands(cmd);
            } else if (cmd[0].equalsIgnoreCase("endWriteCommands")){
                endWriteCommands();
            } else if (cmd[0].equalsIgnoreCase("startGame")){
                startGame();
            } else if (cmd[0].equalsIgnoreCase("startBuild")){
                startBuild();
            } else if (cmd[0].equalsIgnoreCase("endBuild")){
                endBuild();
            } else if (cmd[0].equalsIgnoreCase("addTower")){
                addTower(cmd);
            } else if (cmd[0].equalsIgnoreCase("addBlockage")){
                addBlockage(cmd);
            } else if (cmd[0].equalsIgnoreCase("upgradeTowerAgainstDwarves")){
                upgradeTowerAgainstDwarves(cmd);
            } else if (cmd[0].equalsIgnoreCase("upgradeTowerAgainstElves")){
                upgradeTowerAgainstElves(cmd);
            } else if (cmd[0].equalsIgnoreCase("upgradeTowerAgainstHobbits")){
                upgradeTowerAgainstHobbits(cmd);
            } else if (cmd[0].equalsIgnoreCase("upgradeTowerAgainstMen")){
                upgradeTowerAgainstMen(cmd);
            } else if (cmd[0].equalsIgnoreCase("upgradeTowerShootingSpeed")){
                upgradeTowerShootingSpeed(cmd);
            } else if (cmd[0].equalsIgnoreCase("upgradeTowerRange")){
                upgradeTowerRange(cmd);
            } else if (cmd[0].equalsIgnoreCase("upgradeBlockageAgainstDwarves")){
                upgradeBlockageAgainstDwarves(cmd);
            } else if (cmd[0].equalsIgnoreCase("upgradeBlockageAgainstElves")){
                upgradeBlockageAgainstElves(cmd);
            } else if (cmd[0].equalsIgnoreCase("upgradeBlockageAgainstHobbits")){
                upgradeBlockageAgainstHobbits(cmd);
            } else if (cmd[0].equalsIgnoreCase("upgradeBlockageAgainstMen")){
                upgradeBlockageAgainstMen(cmd);
            } else if (cmd[0].equalsIgnoreCase("simulate")){
                simulate();
            } else if (cmd[0].equalsIgnoreCase("help")){
                help();
            } else if (cmd[0].equalsIgnoreCase("exit")){
                exit();
            } else if (cmd[0].equalsIgnoreCase("showGameState")){
                showGameState();
            } else if (cmd[0].equalsIgnoreCase("listEnemies")){
                listEnemies();
            } else if (cmd[0].equalsIgnoreCase("listTowers")){
                listTowers();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * A loadCommands utasítás kiadását kezeljük le ebben a függvényben.
     * @param cmd a beírt utasítás szavakra tördelve
     */
    private void loadCommands(String[] cmd) {
        if (cmd.length < 2) {
            System.out.println("Nincs megadva fájlnév.");
        } else {

            BufferedReader br = null;
            try {

                //Szükség volt írni egy setPaths, illetve egy setPosition függvényt írni az enemyhez

                /*Alapértelmezett beállítások:
                    A pálya 5 magas, 10 hosszú
                    Van egy egyenes út a (0,2) (9,2) koordináták között
                    Egy ellenség indul az úton a (0,2)-es koordinátából
                    Szarumán varázsereje 50

                */
                game.getGameElements().towers.clear();
                game.getGameElements().blockages.clear();
                game.getGameElements().enemies.clear();
                game.getGameElements().saruman.setSpellPower(50);
                Path path = new Path();
                Line2D road = new Line2D.Double(0, 2, 9, 2);
                path.roads.add(road);
                game.getGameElements().map = new Map(10, 5);
                game.getGameElements().map.setPaths(path);
                Enemy enemy = new Elf();
                enemy.setPosition(new Position(0, 2));
                game.getGameElements().enemies.add(enemy);

                //gyakran kell, de nem default: Blockage blockage = new Blockage(new Position(2, 2));
                //game.getGameElements().blockages.add(blockage);

                if (cmd[1].equalsIgnoreCase("teszt1.txt")) {

                    //Egy tornyot rakunk a pályára a (2,3) koordinátákra
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);

                } else if (cmd[1].equalsIgnoreCase("teszt2.txt")) {

                    //Egy tornyot rakunk a pályára a (2,3) koordinátákra
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);

                } else if (cmd[1].equalsIgnoreCase("teszt3.txt")) {

                    //Szarumánnak nincs elég varázsereje
                    //Egy tornyot rakunk a pályára a (2,3) koordinátákra
                    game.getGameElements().saruman.setSpellPower(5);
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);

                } else if (cmd[1].equalsIgnoreCase("teszt4.txt")) {

                    //Szarumán akadályt fejleszt
                    //Egy tornyot rakunk a pályára a (2,3) koordinátákra
                    //Meglévő akadály a (2,2) helyen
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);
                    Blockage blockage = new Blockage(new Position(2,2));
                    game.getGameElements().blockages.add(blockage);

                } else if (cmd[1].equalsIgnoreCase("teszt5.txt")) {

                    //Akadály fejlesztés sikertelen, mert nincs a megadott helyen akadály
                    //Egy tornyot rakunk a pályára a (2,3) koordinátákra
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);

                } else if (cmd[1].equalsIgnoreCase("teszt6.txt")) {

                    //Egy tornyot rakunk a pályára a (2,3) koordinátákra
                    //Van akadály, de nincs varázserő
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);
                    game.getGameElements().saruman.setSpellPower(4);
                    Blockage blockage = new Blockage(new Position(2,2));
                    game.getGameElements().blockages.add(blockage);

                } else if (cmd[1].equalsIgnoreCase("teszt7.txt")) {

                    //Egy tornyot rakunk a pályára a (2,3) koordinátákra
                    //Azt teszteljük, hogy eltűnik e az akadály
                    //Akadály van a pályán, ellenség viszont nincs
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);
                    game.getGameElements().enemies.clear();
                    Blockage blockage = new Blockage(new Position(2,2));
                    game.getGameElements().blockages.add(blockage);

                } else if (cmd[1].equalsIgnoreCase("teszt8.txt")) {

                    //Egy tünde van a pályán a 0,r-es pozícióban 5 életerővel 1-es sebességgel
                    //Utolsó lövésre meg kell hallnia
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);
                    Damage damage = new Damage();
                    damage.setDamage(9.5, EnemyType.Elf); //Levon 95 életet
                    enemy.hurt(damage);
                    enemy.setSpeed(1);
                    damage.setDamage((10/95), EnemyType.Elf); //majd visszaállítjuk, hogy csak 10-et vonjon le

                } else if (cmd[1].equalsIgnoreCase("teszt9.txt")) {

                    // Tünde 50 élettel
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);
                    Damage damage = new Damage();
                    damage.setDamage(5, EnemyType.Elf); //Levon 50 életet
                    enemy.hurt(damage);
                    enemy.setSpeed(1);
                    damage.setDamage((10/50), EnemyType.Elf); //Majd visszaállítja, hogy csak 10-et vonjon le

                } else if (cmd[1].equalsIgnoreCase("teszt10.txt")) {

                    //Torony lerakása egyenlőre üres pályára


                } else if (cmd[1].equalsIgnoreCase("teszt11.txt")) {

                    //Torony lerakása foglalt helyre
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().enemies.clear();
                    game.getGameElements().towers.add(tower);

                } else if (cmd[1].equalsIgnoreCase("teszt12.txt")) {

                    //Torony lerakása viszont kevés varázserő van
                    game.getGameElements().saruman.setSpellPower(5);

                } else if (cmd[1].equalsIgnoreCase("teszt13.txt")) {

                    //Torony fejlesztése sikeresen
                    //Van a pályán egy torony, illetve egy akadály
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);
                    Blockage blockage = new Blockage(new Position(2,2));
                    game.getGameElements().blockages.add(blockage);

                } else if (cmd[1].equalsIgnoreCase("teszt14.txt")) {

                    //Torony fejlesztése azon a helyen ahol nincs torony
                    //alapértelmezett beállítások

                } else if (cmd[1].equalsIgnoreCase("teszt15.txt")) {

                    //Torony fejlesztése kevés varázserővel
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().saruman.setSpellPower(4);
                    game.getGameElements().towers.add(tower);

                } else if (cmd[1].equalsIgnoreCase("teszt16.txt")) {

                    //Ellenfél kettőbevágása
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(1.0);
                    enemy.setSpeed(1);
                    game.getGameElements().towers.add(tower);

                } else if (cmd[1].equalsIgnoreCase("teszt17.txt")) {

                    //Torony lövés tesztelése. Van egy torony a pályán, illetve egy ellenfél a (2,2) positionnál
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);
                    Enemy enemy_2 = new Elf();
                    enemy_2.setPosition(new Position(2, 2));

                } else if (cmd[1].equalsIgnoreCase("teszt18.txt")) {

                    //A pályán van egy torony, amire köd ereszkedik
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);

                } else if (cmd[1].equalsIgnoreCase("teszt19.txt")) {

                    //Nincs torony a pályán. Egy ellenfél az út utolsó blokkjában van
                    enemy.setPosition(new Position(9, 2));

                } else if (cmd[1].equalsIgnoreCase("teszt20.txt")) {

                    //Betöltünk egy pályát

                } else if (cmd[1].equalsIgnoreCase("teszt21.txt")) {

                    //Az ellenfél mozog előre
                    //Alapértelmezett beállítások

                } else if (cmd[1].equalsIgnoreCase("teszt22.txt")) {

                    //Egy ellenfél a megadott helyen és előtte az (1,2) pozícióban egy akadály
                    Blockage blockage = new Blockage(new Position(1,2));
                    game.getGameElements().blockages.add(blockage);

                } else if (cmd[1].equalsIgnoreCase("teszt23.txt")) {

                    //Van egy torony a pályán, illetve egy ellenfél 5 életerővel
                    Tower tower = new Tower(new Position(2, 3));
                    tower.setCutChance(0.0);
                    game.getGameElements().towers.add(tower);
                    Damage damage = new Damage();
                    damage.setDamage(9.5, EnemyType.Elf); //Levon 95 életet
                    enemy.hurt(damage);
                    damage.setDamage((10/95), EnemyType.Elf); //majd visszaállítjuk, hogy csak 10-et vonjon le


                }
                String line;
                br = new BufferedReader(new FileReader(cmd[1]));
                while ((line = br.readLine()) != null) {
                    execute(line);
                }
                System.out.println(cmd[1] + " betöltése sikeres.");
            } catch (IOException e) {
                System.out.println(cmd[1] + " betöltési hiba!");
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * A beginWriteCommands utasítás kiadását kezeljük le ebben a függvényben.
     * @param cmd a beírt utasítás szavakra tördelve
     */
    private void beginWriteCommands(String[] cmd) {
        if (fileOut != null) {
            System.out.println("Meg meg van nyitva egy kimeneti fajl, addig nem lehet ujat kezdeni.");
        } else if (cmd.length < 2){
            System.out.println("Nincs megadva a kimeneti fajl neve.");
        } else {
            try {
                fileOut = new PrintWriter(cmd[1]);
                System.out.println("Fájlba írás kezdete>");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Az endWriteCommands utasítás kiadását kezeljük le ebben a függvényben.
     * Meghíváskor lezárjuk a fájlt, ha még nem lett lezárva.
     */
    private void endWriteCommands(){
        if (fileOut != null){
            fileOut.close();
            System.out.println("<Fájlba írás vége.");
            fileOut = null;
        }
    }

    /**
     * A startGame utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor elindul a játék.
     */
    private void startGame(){
        game.getGameEngine().startNewGame();
    }

    /**
     * A startBuild utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívása után elkezdhetünk építkezni.
     */
    private void startBuild(){
        if (canBuild){
            System.out.println("Butaság még egyszer engedélyezni az építést.");
        } else {
            canBuild = true;
            System.out.println("Pályaelemek építésének kezdete>");
            if (fileOut != null){
                fileOut.println("Pályaelemek építésének kezdete>");
            }
        }
    }

    /**
     * A endBuild utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívása után már nem tudunk építkezni.
     */
    private void endBuild(){
        if (!canBuild){
            System.out.println("Hogyan fejezzük be az építést, ha el sem kezdtük?");
        } else {
            canBuild = false;
            System.out.println("<Pályaelemek építésének vége");
            if (fileOut != null){
                fileOut.println("<Pályaelemek építésének vége");
            }
        }
    }

    /**
     * Az addTower utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor lerakhatunk egy tornyot a megadott pozícióra.
     */
    private void addTower(String[] cmd){
        if (canBuild){
            if (cmd.length > 2){
                int x = tryParseInt(cmd[1]);
                int y = tryParseInt(cmd[2]);
                game.getGameEngine().handleEvent(new Event(x, y, Action.BUILD_TOWER));
            } else {
                System.out.println("Nem megfelelő bemeneti paraméterek.");
            }
        } else {
            System.out.println("startBuild parancs nélkül nem lehet építeni!");
        }
    }

    /**
     * Az addBlockage utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor lerakhatunk egy tornyot a megadott pozícióra.
     */
    private void addBlockage(String[] cmd){
        if (canBuild){
            if (cmd.length > 2){
                int x = tryParseInt(cmd[1]);
                int y = tryParseInt(cmd[2]);
                game.getGameEngine().handleEvent(new Event(x, y, Action.BUILD_BLOCKAGE));
            } else {
                System.out.println("Nem megfelelő bemeneti paraméterek.");
            }
        } else {
            System.out.println("startbuild parancs nélkül nem lehet építeni!");
        }
    }

    /**
     * Az upgradeTowerAgainstDwarves utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő tornyot ez ellen az ellenség ellen.
     */
    private void upgradeTowerAgainstDwarves(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            Event event = new Event(x, y, Action.UPGRADE_TOWER);
            event.against = EnemyType.Dwarf;
            event.damageIncrement = 1.3;
            game.getGameEngine().handleEvent(event);
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az upgradeTowerAgainstElves utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő tornyot ez ellen az ellenség ellen.
     */
    private void upgradeTowerAgainstElves(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            Event event = new Event(x, y, Action.UPGRADE_TOWER);
            event.against = EnemyType.Elf;
            event.damageIncrement = 1.3;
            game.getGameEngine().handleEvent(event);
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az upgradeTowerAgainstHobbits utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő tornyot ez ellen az ellenség ellen.
     */
    private void upgradeTowerAgainstHobbits(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            Event event = new Event(x, y, Action.UPGRADE_TOWER);
            event.against = EnemyType.Hobbit;
            event.damageIncrement = 1.3;
            game.getGameEngine().handleEvent(event);
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az upgradeTowerAgainstMen utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő tornyot ez ellen az ellenség ellen.
     */
    private void upgradeTowerAgainstMen(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            Event event = new Event(x, y, Action.UPGRADE_TOWER);
            event.against = EnemyType.Man;
            event.damageIncrement = 1.3;
            game.getGameEngine().handleEvent(event);
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az upgradeTowerShootingSpeed utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő torony tüzelési gyakoriságát.
     */
    private void upgradeTowerShootingSpeed(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            Event event = new Event(x, y, Action.UPGRADE_TOWER);
            event.fireRateIncrement = 1.3;
            game.getGameEngine().handleEvent(event);
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az upgradeTowerRange utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő torony hatótávolságát.
     */
    private void upgradeTowerRange(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            Event event = new Event(x, y, Action.UPGRADE_TOWER);
            event.rangeIncrement = 1.3;
            game.getGameEngine().handleEvent(event);
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az upgradeBlockageAgainstDwarves utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő akadályt ez ellen az ellenség ellen.
     */
    private void upgradeBlockageAgainstDwarves(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            Event event = new Event(x, y, Action.UPGRADE_BLOCKAGE);
            event.against = EnemyType.Dwarf;
            event.slowIncrement = 1;
            game.getGameEngine().handleEvent(event);
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az upgradeBlockageAgainstElves utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő akadályt ez ellen az ellenség ellen.
     */
    private void upgradeBlockageAgainstElves(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            Event event = new Event(x, y, Action.UPGRADE_BLOCKAGE);
            event.against = EnemyType.Elf;
            event.slowIncrement = 1;
            game.getGameEngine().handleEvent(event);
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az upgradeBlockageAgainstHobbits utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő akadályt ez ellen az ellenség ellen.
     */
    private void upgradeBlockageAgainstHobbits(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            Event event = new Event(x, y, Action.UPGRADE_BLOCKAGE);
            event.against = EnemyType.Hobbit;
            event.slowIncrement = 1;
            game.getGameEngine().handleEvent(event);
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az upgradeBlockageAgainstMen utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő akadályt ez ellen az ellenség ellen.
     */
    private void upgradeBlockageAgainstMen(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            Event event = new Event(x, y, Action.UPGRADE_BLOCKAGE);
            event.against = EnemyType.Man;
            event.slowIncrement = 1;
            game.getGameEngine().handleEvent(event);
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * A simulate utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor egy időegységnyit léptetünk a tornyokon és az ellenségeken.
     */

    private void simulate(){
        if (canBuild){
            System.out.println("Amíg építés fázisban vagy, nem tudod léptetni a játékot");
        } else {
            game.getGameEngine().update();
        }
    }

    /**
     * A help utasítás kiadását kezeljük le ebben a függvényben.
     * Meghívásakor kiírjuk a lehetséges parancsokat.
     */
    private void help()
    {
        System.out.println("A kacsacsőrben levő paraméterek azok az adott függvény paraméterei, amit a parancs megadása után kell megadni");
        System.out.println("Lehetseges parancsok: ");
        System.out.println("beginWriteCommands<fileName>");
        System.out.println("endWriteCommands");
        System.out.println("startGame");
        System.out.println("startBuild");
        System.out.println("endBuild");
        System.out.println("addTower<x><y>");
        System.out.println("addBlockage<x><y>");
        System.out.println("upgradeTowerAgainstDwarves<x><y>");
        System.out.println("upgradeTowerAgainstElves<x><y>");
        System.out.println("upgradeTowerAgainstHobbits<x><y>");
        System.out.println("upgradeTowerAgainstMen<x><y>");
        System.out.println("upgradeTowerShootingSpeed<x><y>");
        System.out.println("upgradeTowerRange<x><y>");
        System.out.println("upgradeBlockageAgainstDwarves<x><y>");
        System.out.println("upgradeBlockageAgainstElves<x><y>");
        System.out.println("upgradeBlockageAgainstHobbits<x><y>");
        System.out.println("upgradeBlockageAgainstMen<x><y>");
        System.out.println("simulate");
        System.out.println("showGameState");
        System.out.println("help");
        System.out.println("exit");
    }

    /**
     * ShowGameStates metódus. Kiírja az állapotokat.
     */

    private void showGameState()
    {
        Map theMap = game.getGameElements().map;
        char[][] out = new char[theMap.getWidth()][theMap.getHeight()];
        for (int i = 0; i < theMap.getHeight(); i++) {
            for (int j = 0; j < theMap.getWidth(); j++) {
                out[j][i] = '-';
            }
        }
        for (Tower tower : game.getGameElements().towers) {
            out[tower.getPosition().getX()][tower.getPosition().getY()] =
                    tower.upgraded ? 'Ó' : 'O';
        }
        for (Path path : theMap.getPaths()) {
            for (Line2D road : path.roads) {
                Position p1 = new Position((int)road.getP1().getX(), (int)road.getP1().getY());
                Position p2 = new Position((int)road.getP2().getX(), (int)road.getP2().getY());
                //ha ez az út egy függőleges út
                if (p1.getX() == p2.getX()){
                    for (int i = Math.min(p1.getY(), p2.getY()); i <= Math.max(p1.getY(), p2.getY()); i++) {
                        out[p1.getX()][i] = ' ';
                    }
                } else { //ha ez az út egy vízszintes út
                    for (int i = Math.min(p1.getX(), p2.getX()); i <= Math.max(p1.getX(), p2.getX()); i++) {
                        out[i][p1.getY()] = ' ';
                    }
                }
            }
        }
        for (Blockage blockage : game.getGameElements().blockages) {
            out[blockage.getPosition().getX()][blockage.getPosition().getY()] =
                    blockage.upgraded ? '#' : '+';
        }
        for (Enemy enemy : game.getGameElements().enemies) {
            if (enemy.getPosition().getX() < theMap.getWidth() && enemy.getPosition().getY() < theMap.getHeight()){
                out[enemy.getPosition().getX()][enemy.getPosition().getY()] = 'X';
            }
        }
        for (int i = theMap.getHeight() - 1; i >= 0; i--) {
            for (int j = 0; j < theMap.getWidth(); j++) {
                System.out.print(out[j][i]);
                if (fileOut != null){
                    fileOut.print(out[j][i]);
                }
            }
            System.out.println("");
            if (fileOut != null){
                fileOut.println("");
            }

        }
        System.out.println("Szarumán varázsereje: " + game.getGameElements().saruman.getSpellPower());
        if (fileOut != null){
            fileOut.println("Szarumán varázsereje: " + game.getGameElements().saruman.getSpellPower());
        }
    }

    /**
     * Az ellenségeket listázza ki a függvény a következő sablon szerint:
     * Ellenség<azonosító>: pozíció: <x>, <y>; életerő: <életerő>; sebesség: <sebesség>
     */
    private void listEnemies(){
        int db = 0;
        System.out.println("Pályán lévő egységek felsorolásának kezdete>");
        if (fileOut != null){
            fileOut.println("Pályán lévő egységek felsorolásának kezdete>");
        }
        for (Enemy enemy : game.getGameElements().enemies) {
            System.out.println("Ellenség" + (db++) + ": pozíció: " + enemy.getPosition().getX() + ", " +
                                enemy.getPosition().getY() + "; életerő: " + enemy.getLife() + "; sebesség: " + enemy.getSpeed());
            if (fileOut != null){
                fileOut.println("Ellenség" + db + ": pozíció: " + enemy.getPosition().getX() + ", " +
                        enemy.getPosition().getY() + "; életerő: " + enemy.getLife() + "; sebesség: " + enemy.getSpeed());
            }
        }
        System.out.println("<Pályán lévő egységek felsorolásának vége");
        if (fileOut != null){
            fileOut.println("<Pályán lévő egységek felsorolásának vége");
        }
    }

    /**
     * A tornyokat listázza ki a függvény a következő sablon szerint:
     * Torony<azonosító>: pozíció: <x>, <y>; hatósugár:<hatósugár>; Tüzelési gyakoriság: <fireRate>
     */
    private void listTowers(){
        int db = 0;
        for (Tower tower : game.getGameElements().towers) {
            int towerRange = tower.getRange();
            if (game.getGameElements().fog != null){
                if (game.getGameElements().fog.getMiddle().distance(tower.getPosition()) < game.getGameElements().fog.getRange()){
                    towerRange = towerRange / 2;
                }
            }
            System.out.println("Torony" + (db++) + ": pozíció: " + tower.getPosition().getX() + ", " + tower.getPosition().getY() +
                    "; hatósugár:" + towerRange + "; Tüzelési gyakoriság: " + tower.getSpeed());
            if (fileOut != null){
                fileOut.println("Torony" + db + ": pozíció: " + tower.getPosition().getX() + ", " + tower.getPosition().getY() +
                        "; hatósugár:" + towerRange + "; Tüzelési gyakoriság: " + tower.getSpeed());
            }
        }
    }

    /**
     * Meghívásának hatására bezáródik a program.
     */
    private void exit(){
        System.exit(0);
    }
}

