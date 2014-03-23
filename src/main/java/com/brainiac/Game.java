package com.brainiac;

import com.brainiac.controller.GameEngine;
import com.brainiac.controller.Skeleton;
import com.brainiac.model.GameElements;
import com.brainiac.view.GameFrame;

public class Game {
    private GameEngine gameEngine;
    private GameElements gameElements;
    private GameFrame gameFrame;

    // ujdonsag: konstruktor
    public Game() {
        gameElements = new GameElements();
        gameEngine = new GameEngine(gameElements);
        gameFrame = new GameFrame(gameEngine, gameElements);
    }

    public static void main(String[] args) {
        Skeleton.writeLine("Main: A játékhoz szükséges elemek létrehozása megkezdődött!");
        Game game = new Game();
        game.gameEngine.startNewGame();
    }
}
