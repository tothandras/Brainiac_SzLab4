package com.brainiac.controller;

import com.brainiac.model.*;

import java.util.Random;

public class GameEngine {
    private GameElements gameElements;

    public GameEngine(GameElements gameElements) {
        this.gameElements = gameElements;
    }

    public void newRound(){
        Random rn = new Random();
        int nEnemies = Skeleton.getInt("Hány ellenség jön az új körben?");
        for (int i = 0; i < nEnemies; ++i){
            int j = rn.nextInt() % 4;
            switch (j){
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
    }

    public void checkGameState(){
        Skeleton.writeFunctionDetails("GameEngine.checkGameState()");
        for (Enemy enemy: gameElements.enemies) {
            Position currentPosition = enemy.getPosition();
            if (Skeleton.getBoolean("Elérte az ellenség a Végzet hegyét?")){
                isGameOver();
            }
        }
    }

    public void isGameOver(){

    }
}
