package com.brainiac.controller;

import com.brainiac.model.*;

import java.util.Random;

public class GameEngine {
    private GameElements gameElements;

    public GameEngine(GameElements gameElements) {
        this.gameElements = gameElements;
        Skeleton.writeFunctionDetails("GameEngine(GameElements gameElements)");
        Skeleton.writeReturnValue("void");
    }

    public void startNewGame(){
        Skeleton.writeFunctionDetails("GameEngine.startNewGame()");
        Skeleton.writeReturnValue("void");
        this.newRound();
        this.checkGameState();
    }

    public void newRound(){
        Skeleton.writeFunctionDetails("GameEngine.newRound()");
        Skeleton.writeReturnValue("void");
        Random rn = new Random();
        int nEnemies = Skeleton.getInt("Hány ellenség jön az új körben?");
        for (int i = 0; i < nEnemies; ++i){
            System.out.println(i);
            int j = Math.abs(rn.nextInt()) % 4;
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
        int nTowers= Skeleton.getInt("Hány torony legyen?");
        int x=5,y=5;
        for(int i=0;i<nTowers;++i){
            Skeleton.writeFunctionDetails("gameElements.towers.add(Tower tower)");
            gameElements.towers.add(new Tower(new Position(++x,++y)));
            Skeleton.writeReturnValue("void");
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
        Skeleton.writeReturnValue("void");
    }

    public void isGameOver(){
        Skeleton.writeFunctionDetails("isGameOver()");
        Skeleton.writeReturnValue("void");

    }
}
