package com.brainiac.view;

import com.brainiac.controller.GameEngine;
import com.brainiac.model.GameElements;

public class GameFrame {
    private GameEngine gameEngine;
    private GameElements gameElements;

    public GameFrame(GameEngine gameEngine, GameElements gameElements) {
        this.gameEngine = gameEngine;
        this.gameElements = gameElements;
    }
}
