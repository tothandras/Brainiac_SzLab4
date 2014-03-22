package com.brainiac.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Skeleton {
    /**
     * a tab változó mutatja meg, hogy mekkora behúzást kell alkalmazni egy-egy függvénynél
     */
    static String tab;

    /**
     * ezt a függvény hívjuk meg a main függvényben. itt dől el, hogy melyik tesztesetet futtatjuk.
     */
    static void whichCase(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            String thisCase = br.readLine();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

