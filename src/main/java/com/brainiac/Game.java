package com.brainiac;

import com.brainiac.controller.GameEngine;
import com.brainiac.model.GameElements;
import com.brainiac.view.GameFrame;

public class Game {
    private GameEngine gameEngine;
    private GameElements gameElements;
    private GameFrame gameFrame;

    /**
     * A konstruktorban létrehozzuk a tagváltozókat
     */
    public Game() {
        gameElements = new GameElements();
        gameEngine = new GameEngine(gameElements);
        gameFrame = new GameFrame(gameEngine, gameElements);
    }

    /**
     * Program belépési pontja
      * @param args Argumentumok
     */
    public static void main(String[] args) {
        // Példányosítás
        Game game = new Game();
        // Új játék indítása
        game.gameEngine.startNewGame();
        // Nézet szál indítása
        new Thread(game.gameFrame).start();
    }
}
