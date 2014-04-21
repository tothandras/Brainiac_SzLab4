package com.brainiac;

import com.brainiac.model.*;

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
            } else if (cmd[0].equalsIgnoreCase("showGameStates")){
                showGameStates();
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
                    feldolgoz(line);
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
     * A beginWriteCommands utasítás kiadását kezeljük le ebben a függványben.
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
     * Az endWriteCommands utasítás kiadását kezeljük le ebben a függványben.
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
     * A startGame utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor elindul a játék.
     */
    private void startGame(){
        game.getGameEngine().startNewGame();
    }

    /**
     * A startBuild utasítás kiadását kezeljük le ebben a függványben.
     * Meghívása után elkezdhetünk építkezni.
     */
    private void startBuild(){
        if (canBuild){
            //TODO
            System.out.println("Butaság még egyszer engedélyezni az építést.");
        } else {
            canBuild = true;
        }
    }

    /**
     * A endBuild utasítás kiadását kezeljük le ebben a függványben.
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
     * Az addTower utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor lerakhatunk egy tornyot a megadott pozícióra.
     */
    private void addTower(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            //TODO: itt megnézzük, hogy az adott helyre szabad-e építkeznünk: nem út-e, van-e ott már torony
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az addBlockage utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor lerakhatunk egy tornyot a megadott pozícióra.
     */
    private void addBlockage(String[] cmd);

    /**
     * Az upgradeTowerAgainstDwarves utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő tornyot ez ellen az ellenség ellen.
     */
    private void upgradeTowerAgainstDwarves(String[] cmd){
        if (cmd.length > 2){
            int x = tryParseInt(cmd[1]);
            int y = tryParseInt(cmd[2]);
            boolean thereIsTower = false;
            for (Tower tower : game.getGameElements().towers) {
                if ((tower.getPosition().getX() == x) && (tower.getPosition().getY() == y)){
                    thereIsTower = true;
                    double damageIncrement = 1.3;
                    int costOfUpgrade = 5;
                    if (game.getGameElements().saruman.getSpellPower() < costOfUpgrade){
                        System.out.println("Torony fejlesztése sikertelen: nincs elég varázserő.");
                    } else {
                        tower.upgrade(new TowerCrystal(EnemyType.Dwarf, damageIncrement, 1.0, 1.0));
                        System.out.println("Torony fejlesztése sikeres.");
                    }
                }
            }
            if (!thereIsTower){
                System.out.println("Torony fejlesztése sikertelen: nem létezik a megadott helyen torony.");
            }
        } else {
            System.out.println("Nem megfelelő bemeneti paraméterek.");
        }
    }

    /**
     * Az upgradeTowerAgainstElves utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő tornyot ez ellen az ellenség ellen.
     */
    private void upgradeTowerAgainstElves(String[] cmd);

    /**
     * Az upgradeTowerAgainstHobbits utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő tornyot ez ellen az ellenség ellen.
     */
    private void upgradeTowerAgainstHobbits(String[] cmd);

    /**
     * Az upgradeTowerAgainstMen utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő tornyot ez ellen az ellenség ellen.
     */
    private void upgradeTowerAgainstMen(String[] cmd);

    /**
     * Az upgradeTowerAgainstSpeed utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő torony tüzelési gyakoriságát.
     */
    private void upgradeTowerShootingSpeed(String[] cmd);

    /**
     * Az upgradeBlockageAgainstDwarves utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő akadályt ez ellen az ellenség ellen.
     */
    private void upgradeBlockageAgainstDwarves(String[] cmd);

    /**
     * Az upgradeBlockageAgainstElves utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő akadályt ez ellen az ellenség ellen.
     */
    private void upgradeBlockageAgainstElves(String[] cmd);

    /**
     * Az upgradeBlockageAgainstHobbits utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő akadályt ez ellen az ellenség ellen.
     */
    private void upgradeBlockageAgainstHobbits(String[] cmd);

    /**
     * Az upgradeBlockageAgainstMen utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő akadályt ez ellen az ellenség ellen.
     */
    private void upgradeBlockageAgainstMen(String[] cmd);

    /**
     * A simulate utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor egy időegységnyit léptetünk a tornyokon és az ellenségeken.
     */
    private void simulate();

    /**
     * A help utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor kiírjuk a lehetséges parancsokat.
     */
    private void help()
    {
        System.out.println("Lehetseges parancsok: ");
        System.out.println("A kacsacsőrben levő paraméterek azok az adott függvény paraméterei, amit a parancs megadása után kell megadni");
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
        System.out.println("upgradeBlockageAgainstDwarves<x><y>");
        System.out.println("upgradeBlockageAgainstElves<x><y>");
        System.out.println("upgradeBlockageAgainstHobbits<x><y>");
        System.out.println("upgradeBlockageAgainstMen<x><y>");
        System.out.println("simulate");
        System.out.println("help");
        System.out.println("exit");
    }

    /**
     * ShowGameStates metódus. Kiírja az állapotokat.
     */

    private void showGameStates()
    {
        // A kiírásnál Sarumán varázserejét kellene kiírni. AZ 50 az csak ideiglenesen van.
        int power = 50;
        Map temp = new Map(50,50);
        //Ideiglenes pálya
        //Pálya kirajzolása
        for(int i = 0; i<temp.getWidth(); i++){
            for(int j = 0; i<temp.getHeight(); j++)
            {
                if(temp.)
            }
        }
        System.out.println("Szaruman varázsereje: " + power);
    }

    //TODO: komment megírása
    private void exit();

    /**
     * a loadCommandsból hívjuk meg. a loadCommands-fájl minden sorára meghívódik
     * @param a: a loadCommands-fájl következő sora
     */
    //TODO: átnevezhető, hirtelenjében ezt sikerült kitalálnom...
    private void feldolgoz(String a) {
    }
}
