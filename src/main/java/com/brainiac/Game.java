package com.brainiac;

import com.brainiac.controller.GameEngine;
import com.brainiac.model.GameElements;
import com.brainiac.model.Position;
import com.brainiac.model.Tower;
import com.brainiac.view.GameFrame;

public class Game {
    private GameEngine gameEngine;
    private GameElements gameElements;
    private GameFrame gameFrame;
    //TODO
    ///Ezt nézzétek majd át, hogy így jó-e.
    private Proto proto;

    /**
     * A konstruktorban létrehozzuk a tagváltozókat
     */
    public Game() {
        gameElements = new GameElements();
        gameEngine = new GameEngine(gameElements);
        gameFrame = new GameFrame(gameEngine, gameElements);
        proto = new Proto(this);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gameEngine.startNewGame();
        new Thread(game.gameFrame).start();
    }

    /**
     * A proto ezen keresztül tudja elérni a gameEngine változót
     * @return a Game osztály gameEngine változója
     */
    public GameEngine getGameEngine(){
        return gameEngine;
    }
    /**
     * A proto ezen keresztül tudja elérni a gameElements változót
     * @return a Game osztály gameElemets változója
     */
    public GameElements getGameElements(){
        return gameElements;
    }
    /**
     * A proto ezen keresztül tudja elérni a gameFrame változót
     * @return a Game osztály gameFrame változója
     */
    public GameFrame getGameFrame(){
        return gameFrame;
    }
}
