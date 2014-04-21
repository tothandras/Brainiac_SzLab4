package com.brainiac;

import com.brainiac.model.*;

import java.awt.geom.Line2D;
import java.io.*;

public class Proto {
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private PrintWriter fileOut = null;
    Game game;
    boolean canBuild;

    public Proto(Game game){
        this.game = game;
        canBuild = false;
    }

    int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch(NumberFormatException nfe) {
            System.out.println("Nem megfelelő formátumú bemenet. 0-val számolunk tovább helyette.");
            return 0;
        }
    }

    /**
     * Ez a függvény fogja értelmezni a tesztelés során megadott parancsokat.
     * Egyelőre csak 1 sort olvas!
     */
    //TODO: szabad neki szebb nevet adni :)
    public void sdfghjkl() {
        try {
            String line = bufferedReader.readLine();
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
            System.out.println("Nincs megadva fajlnev.");
        } else {

            BufferedReader br = null;
            try {
                String line;
                br = new BufferedReader(new FileReader(cmd[1]));
                while ((line = br.readLine()) != null) {
                    execute(line);
                }
                System.out.println(cmd[1] + " betoltese sikeres.");
            } catch (IOException e) {
                System.out.println(cmd[1] + " betoltesi hiba!");
                //e.printStackTrace();
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
                System.out.println("Fajlba iras kezdete>");
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
            System.out.println("<Fajlba iras vege.");
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
            out[tower.getPosition().getY()][tower.getPosition().getX()] =
                    tower.upgraded ? 'Ó' : 'O';
        }
        for (Path path : theMap.getPaths()) {
            for (Line2D road : path.roads) {
                Position p1 = new Position((int)road.getP1().getX(), (int)road.getP1().getY());
                Position p2 = new Position((int)road.getP2().getX(), (int)road.getP2().getY());
                //ha ez az út egy függőleges út
                if (p1.getX() == p2.getX()){
                    for (int i = Math.min(p1.getY(), p2.getY()); i < Math.max(p1.getY(), p2.getY()); i++) {
                        out[p1.getX()][i] = ' ';
                    }
                } else { //ha ez az út egy vízszintes út
                    for (int i = Math.min(p1.getX(), p2.getX()); i < Math.max(p1.getX(), p2.getX()); i++) {
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
            out[enemy.getPosition().getX()][enemy.getPosition().getY()] = 'X';
        }
        for (int i = 0; i < theMap.getWidth(); i++) {
            for (int j = 0; j < theMap.getHeight(); j++) {
                System.out.print(out[i][j]);
            }
            System.out.println("");

        }
        System.out.println("Szaruman varázsereje: " + game.getGameElements().saruman.getSpellPower());
    }

    //TODO: komment megírása
    private void exit();

    /**
     * a loadCommandsból hívjuk meg. a loadCommands-fájl minden sorára meghívódik
     * @param a: a loadCommands-fájl következő sora
     */
    //TODO: átnevezhető, hirtelenjében ezt sikerült kitalálnom...
    private void execute(String a) {
    }
}
