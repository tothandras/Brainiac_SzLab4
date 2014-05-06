package com.brainiac.view;

import com.brainiac.controller.GameEngine;
import com.brainiac.model.Action;
import com.brainiac.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class GameFrame extends JFrame implements WindowListener, Runnable {
    // Játék motorja
    private GameEngine gameEngine;
    // Játék modelljei
    private GameElements gameElements;
    // Futó állapotban vagyunk?
    private boolean running;
    // Action beállítása gombra kattintásnál
    private Action action;
    // Játék felülete
    private JPanel gamePanel;
    private Graphics2D graphics;
    private Image image;

    // Ablak szélessége
    public final int WIDTH = 600;
    // Ablak magassága
    public final int HEIGHT = 600;

    public GameFrame(GameEngine gameEngine, GameElements gameElements) {
        this.gameEngine = gameEngine;
        this.gameElements = gameElements;
        this.action = Action.NONE;

        makeGUI();
        addWindowListener(this);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Felhasználói felület létrehozása.
     */
    private void makeGUI() {
        gamePanel = new JPanel(new BorderLayout());
        // -10 magasság, hogy MAC-en jól nézzek ki
        gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT - 10));
        gamePanel.setBackground(Color.BLACK);
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO kivihetnénk külön privát osztályba
                int x = e.getX();
                int y = e.getY();
                Position position = new Position(x, y);
                // Megvizsgáljuk, hogy gombra kattintottunk-e, a gombokat a képernyő alsó 50 pixelére rajzoljuk ki
                if (y > HEIGHT - 50) {
                    // Ha igen, akkor Action-t váltunk
                    if (x < WIDTH / 4) {
                        // 1. Gomb
                        action = Action.BUILD_TOWER;
                    } else if (x > WIDTH / 4 && x < 2 * WIDTH / 4) {
                        // 2. Gomb
                        action = Action.BUILD_BLOCKAGE;
                    } else if (x > WIDTH / 4 * 2 && x < 3 * WIDTH / 4) {
                        // TODO itt feloszthatnánk több kisebb gombra, hogy mit szeretnénk fejleszteni
                        // 3. Gomb
                        action = Action.UPGRADE_TOWER;
                    } else if (x > WIDTH / 4 * 3 && x < WIDTH) {
                        // TODO itt feloszthatnánk több kisebb gombra, hogy mit szeretnénk fejleszteni
                        // 4. Gomb
                        action = Action.UPGRADE_BLOCKAGE;
                    }
                } else if (action != Action.NONE) {
                    // Ha nem, akkor a jelenlegi Action-nel meghívjuk a gameEngine eseménykezelő függvényét
                    if (gameEngine.handleEvent(position, action)) {
                        // Majd az action változót alapállapotba helyezzük, ha a parancs sikeresen végrehajtódott
                        action = Action.NONE;
                    }
                }
            }
        });

        add(gamePanel, BorderLayout.CENTER);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
    }

    public void run() {
        running = true;

        while (running) {
            try {
                // Játék állapotának frissítése
                gameEngine.update();

                if (image == null) {
                    image = createImage(WIDTH, HEIGHT);
                    graphics = (Graphics2D) image.getGraphics();
                }

                // Előző kép törlése
                graphics.clearRect(0, 0, WIDTH, HEIGHT);

                // draw roads
                for (Path path : gameElements.map.getPaths()) {
                    for (Line2D road : path.getRoads()) {
                        graphics.draw(road);
                    }
                }

                // draw towers
                for (Tower tower : gameElements.towers) {
                    // TODO ezt jobban meg kell majd csinalni
                    // TODO + ha kirajzol es meghivjuk a handleMouseEventet, hogy tornyot adjunk hozza, akkor  java.util.ConcurrentModificationException-t kapunk (próbálgassátok ti is)
                    graphics.draw(new Ellipse2D.Double(tower.getPosition().getX() - tower.getRange(), tower.getPosition().getY() - tower.getRange(), 2 * tower.getRange(), 2 * tower.getRange()));
                    graphics.fill(new Ellipse2D.Double(tower.getPosition().getX() - WIDTH / 100, tower.getPosition().getY() - HEIGHT / 100, WIDTH / 50, HEIGHT / 50));
                }

                // draw blockages
                for (Blockage blockage : gameElements.blockages) {
                    // TODO ide meg az akadályoknak
                    graphics.setColor(Color.BLUE);
                    graphics.fillOval(blockage.getPosition().getX() - 15, blockage.getPosition().getY() - 15, 30, 30);
                }

                // draw enemies
                for (Enemy enemy : gameElements.enemies) {
                    // TODO ide kellenek majd a képek az egyégeknek
                    graphics.setColor(Color.RED);
                    graphics.fillOval(enemy.getOffset().getX() - 3, enemy.getOffset().getY() - 3, 6, 6);
                }

                for (Line2D shot : gameElements.shots){
                    graphics.setColor(Color.green);
                    graphics.drawLine((int)shot.getX1(), (int)shot.getY1(), (int)shot.getX2(), (int)shot.getY2());
                }
                gameElements.shots.clear();

                // Gombok a képernyő alsó 50 pixelén jelennek vannak
                // TODO ide majd szép képekből álló gombok kellenek, ha kell megrajzolom Illustratorban és átküldöm
                graphics.setColor(Color.BLACK);
                graphics.drawLine(0, HEIGHT - 50, WIDTH, HEIGHT - 50);
                for (int i = 0; i < 4; i++) {
                    if (action == Action.BUILD_TOWER && i == 0 || action == Action.BUILD_BLOCKAGE && i == 1 || action == Action.UPGRADE_TOWER && i == 2 || action == Action.UPGRADE_BLOCKAGE && i == 3) {
                        graphics.setColor(Color.GRAY);
                    } else {
                        graphics.setColor(Color.LIGHT_GRAY);
                    }
                    graphics.fillRect(i * WIDTH / 4, HEIGHT - 50, (i + 1) * WIDTH / 4, 50);
                    graphics.setColor(Color.BLACK);
                    if (i != 0) {
                        graphics.drawLine(i * WIDTH / 4, HEIGHT, i * WIDTH / 4, HEIGHT - 50);
                    }
                }
                graphics.drawString("Torony", WIDTH * 0.09f, HEIGHT - 25);
                graphics.drawString("Akadály", WIDTH * 0.33f, HEIGHT - 25);
                graphics.drawString("Torony fejlesztése", WIDTH * 0.53f, HEIGHT - 25);
                graphics.drawString("Akadály fejlesztése", WIDTH * 0.77f, HEIGHT - 25);

                // paint screen
                Graphics g;
                g = this.getGraphics();
                if ((g != null) && (image != null))
                    g.drawImage(image, 0, 16, null);
                if (g != null) g.dispose();

                // Szál altatása 20ms-ig
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        running = false;
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
