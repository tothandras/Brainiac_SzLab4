package com.brainiac.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Skeleton {

    /**
     * Ha a System.in-t bezárjuk a close()-al, akkor utána nem lehet használni
     */
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    /**
     * a tab változó mutatja meg, hogy mekkora behúzást kell alkalmazni egy-egy függvénynél
     */
    public static String tab="";

    /**
     * ezt a függvény hívjuk meg a main függvényben. itt dől el, hogy melyik tesztesetet futtatjuk.
     */
    public static void whichCase() {
        try {
            String thisCase = bufferedReader.readLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * elnevezésekben rossz vagyok, kitalálhattok valami értelmesebbet
     *
     * @param s a meghívott függvény neve, paraméterei
     */
    public static void writeFunctionDetails(String s) {
        System.out.println(tab + "-->" + s);
        tab = tab + '\t';
    }

    public static void writeLine(String s){
        System.out.println(s);

    }

    /**
     * akkor használjuk, ha a tesztelőtől egy boolean értéket szeretnénk kérni.
     * megfelelő válaszlehetőségek: true/false
     *
     * @param s a feltett kérdés
     * @return az adott válasz
     */
    public static Boolean getBoolean(String s) {
        System.out.println("[?]" + s + "[true/false]");
        try {
            String value = bufferedReader.readLine();
            return value.equalsIgnoreCase("true");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * akkor használjuk, ha a tesztelőtől egy int értéket szeretnénk kérni.
     *
     * @param s a feltett kérdés
     * @return az adott válasz
     */
    public static int getInt(String s) {
        System.out.println("[?]" + s + "[1..]");
        try {
            String value = bufferedReader.readLine();
            return Integer.parseInt(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1;
    }

    /**
     * azért csináltam külön egy ilyet, mert itt csökkenteni kell a behúzás mértékét,
     * az előbbinél meg növelni, meg a nyíl is másik irányban van.
     *
     * @param s a visszatérési érték típusa, értéke
     */
    public static void writeReturnValue(String s) {
        tab = tab.substring(0, tab.length() - 1);
        System.out.println(tab + "<--" + s);
    }

}

