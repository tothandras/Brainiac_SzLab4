package com.brainiac;

import java.io.*;

public class Proto {
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private PrintWriter fileOut = null;

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
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * A loadCommands utasítás kiadását kezeljük le ebben a függványben.
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
    private void startGame();

    /**
     * A startBuild utasítás kiadását kezeljük le ebben a függványben.
     * Meghívása után elkezdhetünk építkezni.
     */
    private void startBuild();

    /**
     * A endBuild utasítás kiadását kezeljük le ebben a függványben.
     * Meghívása után már nem tudunk építkezni.
     */
    private void endBuild();

    /**
     * Az addTower utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor lerakhatunk egy tornyot a megadott pozícióra.
     */
    private void addTower(String[] cmd);

    /**
     * Az addBlockage utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor lerakhatunk egy tornyot a megadott pozícióra.
     */
    private void addBlockage(String[] cmd);

    /**
     * Az upgradeTowerAgainstDwarves utasítás kiadását kezeljük le ebben a függványben.
     * Meghívásakor fejlesztjük a megadott korrdinátán lévő tornyot ez ellen az ellenség ellen.
     */
    private void upgradeTowerAgainstDwarves(String[] cmd);

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
    private void help();

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