package com.brainiac.view;

import com.brainiac.controller.GameEngine;
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
    private GameEngine gameEngine;
    private GameElements gameElements;
    private boolean running;
    private JPanel gamePanel;
    private Graphics2D graphics;
    private Image image;

    public final int WIDTH = 600;
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
        gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        gamePanel.setBackground(Color.BLACK);
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameEngine.handleMouseEvent(e);
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
                // update
                //gameEngine.update();

                if (image == null) {
                    image = createImage(WIDTH, HEIGHT);
                    graphics = (Graphics2D) image.getGraphics();
                }

                // draw roads
                for (Path path : gameElements.map.getPaths()) {
                    for (Line2D road : path.roads) {
                        graphics.draw(road);
                    }
                }

                // draw towers
                for (Tower tower : gameElements.towers) {
                    // TODO ezt jobban meg kell majd csinalni
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
