package com.brainiac.view;

import com.brainiac.controller.GameEngine;
import com.brainiac.model.*;
import com.brainiac.model.Action;
import javafx.geometry.Pos;

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
                // TODO kivihetnénk külön függvénybe
                int x = e.getX();
                int y = e.getY();
                Position p = new Position(x, y);
                // Megvizsgáljuk, hogy gombra kattintottunk-e, a gombokat a képernyő alsó 200 pixelére rajzoljuk ki
                // TODO
                // ...
                // TODO Ha igen, akkor Action-t váltunk
                // ...
                // TODO Ha nem, akkor a jelenlegi Action-nel meghívjuk a gameEngine egérkattintást lekezelő függvényét, kell majd Action változó a Framebe
                // ...
                Action a = Action.BUILD_BLOCKAGE;
                gameEngine.handleMouseEvent(e, a);
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
                    // TODO + ha kirajzol es meghivjuk a handleMouseEventet, hogy tornyot adjunk hozza, akkor  java.util.ConcurrentModificationException-t kapunk (lehet kene egy valtozo amivel lockolunk)
                    graphics.draw(new Ellipse2D.Double(tower.getPosition().getX() - tower.getRange(), tower.getPosition().getY() - tower.getRange(), 2 * tower.getRange(), 2 * tower.getRange()));
                    graphics.fill(new Ellipse2D.Double(tower.getPosition().getX() - WIDTH / 100, tower.getPosition().getY() - HEIGHT / 100, WIDTH / 50, HEIGHT / 50));
                }

                // draw enemies
                for (Enemy enemy : gameElements.enemies) {
                    graphics.setColor(Color.RED);
                    graphics.drawOval(enemy.getPosition().getX() - 2, enemy.getPosition().getY() - 2, 4, 4);
                }

                // draw blockages
                for (Blockage blockage : gameElements.blockages) {
                    graphics.setColor(Color.BLUE);
                    graphics.drawOval(blockage.getPosition().getX() - 2, blockage.getPosition().getY() - 2, 4, 4);
                }

                // paint screen
                Graphics g;
                g = this.getGraphics();
                if ((g != null) && (image != null))
                    g.drawImage(image, 0, 16, null);
                if (g != null) g.dispose();

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
