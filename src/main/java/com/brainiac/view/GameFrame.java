package com.brainiac.view;

import com.brainiac.controller.GameEngine;
import com.brainiac.controller.GameState;
import com.brainiac.model.Action;
import com.brainiac.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Random;

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
    // Az egyes ellenséges egységek eltolásának nagysága
    private HashMap<Enemy, Position> offset;

    // Ablak szélessége
    public final int WIDTH = 600;
    // Ablak magassága
    public final int HEIGHT = 600;

    boolean sync = true;

    public GameFrame(GameEngine gameEngine, GameElements gameElements) {
        this.gameEngine = gameEngine;
        this.gameElements = gameElements;
        this.action = Action.NONE;
        offset = new HashMap<Enemy, Position>();

        makeGUI();
        addWindowListener(this);
        setResizable(false);
        setLocationRelativeTo(null);
        setLocation(20, 40);
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
                sync = false; //Ne engedje rajzolni ekkor
                if (SwingUtilities.isRightMouseButton(e)) {
                    action = Action.NONE;
                } else {
                    // TODO kivihetnénk külön privát osztályba
                    int x = e.getX();
                    int y = e.getY();
                    Position position = new Position(x, y);
                    // Megvizsgáljuk, hogy gombra kattintottunk-e, a gombokat a képernyő alsó 50 pixelére rajzoljuk ki
                    if (y > HEIGHT - 50) {
                        // Ha igen, akkor Action-t váltunk
                        switch (action) {
                            case UPGRADE_TOWER:
                                if (x < WIDTH / 4) {
                                    // 1. Gomb
                                    action = Action.UPGRADE_DAMAGE;
                                } else if (x > WIDTH / 4 && x < 2 * WIDTH / 4) {
                                    // 2. Gomb
                                    action = Action.UPGRADE_FIRE_RATE;
                                } else if (x > WIDTH / 4 * 2 && x < 3 * WIDTH / 4) {
                                    // 3. Gomb
                                    action = Action.UPGRADE_RANGE;
                                }
                                break;

                            //direkt egymás után vannak a casek, mert mindegyiknél ugyanazt a 4 esetet kell elmenteni,
                            //a GameEnginebe mentem mit növel, sebzés, lövás gyorsaság, sugár
                            case UPGRADE_DAMAGE:
                                if (x < WIDTH / 4) {
                                    // 1. Gomb
                                    action = Action.UPGRADE_TOWER_ELF;
                                } else if (x > WIDTH / 4 && x < 2 * WIDTH / 4) {
                                    // 2. Gomb
                                    action = Action.UPGRADE_BLOCKAGE_DWARF;
                                } else if (x > WIDTH / 4 * 2 && x < 3 * WIDTH / 4) {
                                    // 3. Gomb
                                    action = Action.UPGRADE_TOWER_MAN;
                                } else if (x > WIDTH / 4 * 3 && x < WIDTH) {
                                    // 4. Gomb
                                    action = Action.UPGRADE_TOWER_HOBBIT;
                                }
                                break;

                            case UPGRADE_BLOCKAGE:
                                if (x < WIDTH / 4) {
                                    // 1. Gomb
                                    action = Action.UPGRADE_BLOCKAGE_ELF;
                                } else if (x > WIDTH / 4 && x < 2 * WIDTH / 4) {
                                    // 2. Gomb
                                    action = Action.UPGRADE_BLOCKAGE_DWARF;
                                } else if (x > WIDTH / 4 * 2 && x < 3 * WIDTH / 4) {
                                    // 3. Gomb
                                    action = Action.UPGRADE_BLOCKAGE_MAN;
                                } else if (x > WIDTH / 4 * 3 && x < WIDTH) {
                                    // 4. Gomb
                                    action = Action.UPGRADE_BLOCKAGE_HOBBIT;
                                }
                                break;


                            case NONE:
                                if (x < WIDTH / 4) {
                                    // 1. Gomb
                                    action = Action.BUILD_TOWER;
                                } else if (x > WIDTH / 4 && x < 2 * WIDTH / 4) {
                                    // 2. Gomb
                                    action = Action.BUILD_BLOCKAGE;
                                } else if (x > WIDTH / 4 * 2 && x < 3 * WIDTH / 4) {
                                    // 3. Gomb
                                    action = Action.UPGRADE_TOWER;
                                } else if (x > WIDTH / 4 * 3 && x < WIDTH) {
                                    // 4. Gomb
                                    action = Action.UPGRADE_BLOCKAGE;
                                }
                                break;
                        }
                    } else if (action != Action.NONE && action != Action.UPGRADE_BLOCKAGE && action != Action.UPGRADE_TOWER) {
                        // Ha nem, akkor a jelenlegi Action-nel meghívjuk a gameEngine eseménykezelő függvényét
                        if (gameEngine.handleEvent(position, action)) {
                            // Majd az action változót alapállapotba helyezzük, ha a parancs sikeresen végrehajtódott
                            action = Action.NONE;
                        }
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

                //ha valaki elérte a végzet hegyét vége a játéknak

                if (image == null) {
                    image = createImage(WIDTH, HEIGHT);
                    graphics = (Graphics2D) image.getGraphics();
                }

                // Előző kép törlése
                graphics.clearRect(0, 0, WIDTH, HEIGHT);
                Image im = new ImageIcon("src/map.png").getImage();
                graphics.drawImage(im, 0, 0, null);
                // draw roads
                for (Path path : gameElements.map.getPaths()) {
                    for (Line2D road : path.getRoads()) {

                        graphics.setColor(new Color(156, 158, 126));
                        int sizeOfRoad = path.sizeOfRoad;//itt állíthatjuk, be az utak szélleségét, kettővel oszthatónak kell lennie

                        //Végigmenve az utakon, amik vonalak, egy kicsit szélesebben rajzoljuk ki őket téglalapként
                        if (road.getX1() == road.getX2()) {
                            if (road.getY1() > road.getY2()) {
                                // egy kicsit odébb állítjuk az út kezdő koordinátáját és magasságát vagy szélességét,
                                // hogy az út kiszélesedjen
                                graphics.draw(new Rectangle((int) road.getX1() - sizeOfRoad, (int) road.getY2() - sizeOfRoad,
                                        (int) (road.getX2() - road.getX1()) + 2 * sizeOfRoad, (int) (road.getY1() - road.getY2()) + 2 * sizeOfRoad));
                                graphics.fill(new Rectangle((int) road.getX1() - sizeOfRoad, (int) road.getY2() - sizeOfRoad,
                                        (int) (road.getX2() - road.getX1()) + 2 * sizeOfRoad, (int) (road.getY1() - road.getY2()) + 2 * sizeOfRoad));
                            } else {
                                graphics.draw(new Rectangle((int) road.getX1() - sizeOfRoad, (int) road.getY1(),
                                        (int) (road.getX2() - road.getX1()) + 2 * sizeOfRoad, (int) (road.getY2() - road.getY1())));
                                graphics.fill(new Rectangle((int) road.getX1() - sizeOfRoad, (int) road.getY1(),
                                        (int) (road.getX2() - road.getX1()) + 2 * sizeOfRoad, (int) (road.getY2() - road.getY1())));
                            }
                        }


                        if (road.getY1() == road.getY2()) {
                            graphics.draw(new Rectangle((int) road.getX1() - sizeOfRoad, (int) road.getY1() - sizeOfRoad,
                                    (int) (road.getX2() - road.getX1()) + 2 * sizeOfRoad, (int) (road.getY2() - road.getY1()) + 2 * sizeOfRoad));
                            graphics.fill(new Rectangle((int) road.getX1() - sizeOfRoad, (int) road.getY1() - sizeOfRoad,
                                    (int) (road.getX2() - road.getX1()) + 2 * sizeOfRoad, (int) (road.getY2() - road.getY1()) + 2 * sizeOfRoad));
                        }
                    }
                }


                // draw towers
                for (Tower tower : gameElements.getTowers()) {
                    if (sync) { // hogy ne kapjunk hibát
                        Image img = new ImageIcon("src/tower.png").getImage();
                        //int towerRange = tower.getRange();
                        //graphics.setColor(Color.black);
                        if (gameElements.fog != null) {
                            if (gameElements.fog.getMiddle().distance(tower.getPosition()) < gameElements.fog.getRange()) {
                                //towerRange = towerRange / 2;
                                img = new ImageIcon("src/towerfog.png").getImage();
                            }
                        }
                        //graphics.draw(new Ellipse2D.Double(tower.getPosition().getX() - towerRange, tower.getPosition().getY() - towerRange, 2 * towerRange, 2 * towerRange));
                        graphics.drawImage(img, tower.getPosition().getX() - 16, tower.getPosition().getY() - 16, null);
                        //graphics.fill(new Ellipse2D.Double(tower.getPosition().getX() - WIDTH / 100, tower.getPosition().getY() - HEIGHT / 100, WIDTH / 50, HEIGHT / 50));
                    }
                }

                // draw blockages
                for (Blockage blockage : gameElements.getBlockages()) {
                    Image img = new ImageIcon("src/blockage2.png").getImage();
                    //if(blockage.upgraded){graphics.setColor(Color.green);}
                    //else{graphics.setColor(Color.BLUE);}
                    graphics.drawImage(img, blockage.getPosition().getX() - 16, blockage.getPosition().getY() - 16, null);
                    //graphics.fillOval(blockage.getPosition().getX() - 15, blockage.getPosition().getY() - 15, 30, 30);
                }

                Random rand = new Random();
                // draw enemies
                for (Enemy enemy : gameElements.getEnemies()) {
                    if (!offset.containsKey(enemy)) {
                        Position temp = new Position(rand.nextInt(6) - 3, rand.nextInt(6) - 3);
                        offset.put(enemy, temp);
                    }
                    graphics.setColor(enemy.getColor());
                    graphics.fillOval(enemy.getPosition().getX() - 3 + offset.get(enemy).getX(),
                            enemy.getPosition().getY() - 3 + offset.get(enemy).getY(), 6, 6);
                }

                for (Line2D shot : gameElements.shots) {
                    graphics.setColor(Color.green);
                    graphics.drawLine((int) shot.getX1(), (int) shot.getY1(), (int) shot.getX2(), (int) shot.getY2());
                }
                gameElements.shots.clear();

                // Gombok a képernyő alsó 50 pixelén jelennek vannak
                graphics.setColor(Color.BLACK);
                graphics.drawLine(0, HEIGHT - 50, WIDTH, HEIGHT - 50);
                for (int i = 0; i < 4; i++) {
                    if (((action == Action.BUILD_TOWER || action == Action.UPGRADE_TOWER_ELF || action == Action.UPGRADE_BLOCKAGE_ELF) && i == 0) ||
                            ((action == Action.BUILD_BLOCKAGE || action == Action.UPGRADE_TOWER_DWARF || action == Action.UPGRADE_BLOCKAGE_DWARF || action == Action.UPGRADE_FIRE_RATE) && i == 1) ||
                            ((action == Action.UPGRADE_TOWER_MAN || action == Action.UPGRADE_BLOCKAGE_MAN || action == Action.UPGRADE_RANGE) && i == 2) ||
                            ((action == Action.UPGRADE_TOWER_HOBBIT || action == Action.UPGRADE_BLOCKAGE_HOBBIT) && i == 3)) {
                        graphics.setColor(Color.GRAY);
                    } else {
                        graphics.setColor(Color.LIGHT_GRAY);
                    }
                    graphics.fillRect(i * WIDTH / 4, HEIGHT - 50, WIDTH / 4, 50);
                    graphics.setColor(Color.BLACK);
                    if (i != 0) {
                        graphics.drawLine(i * WIDTH / 4, HEIGHT, i * WIDTH / 4, HEIGHT - 50);
                    }
                }
                switch (action) {
                    case BUILD_TOWER:
                    case BUILD_BLOCKAGE:
                    case NONE:
                        graphics.setFont(new Font(Font.SERIF, Font.BOLD, 12));
                        graphics.drawString("Torony", WIDTH * 0.09f, HEIGHT - 25);
                        graphics.drawString("Akadály", WIDTH * 0.33f, HEIGHT - 25);
                        graphics.drawString("Torony fejlesztése", WIDTH * 0.53f, HEIGHT - 25);
                        graphics.drawString("Akadály fejlesztése", WIDTH * 0.77f, HEIGHT - 25);
                        break;
                    case UPGRADE_FIRE_RATE:
                    case UPGRADE_RANGE:
                    case UPGRADE_TOWER:
                        graphics.setFont(new Font(Font.SERIF, Font.BOLD, 12));
                        graphics.drawString("Sebzés", WIDTH * 0.09f, HEIGHT - 25);
                        graphics.drawString("Tüzelési sebesség", WIDTH * 0.30f, HEIGHT - 25);
                        graphics.drawString("Hatósugár", WIDTH * 0.58f, HEIGHT - 25);
                        break;

                    default:
                        graphics.setFont(new Font(Font.SERIF, Font.BOLD, 12));
                        graphics.drawString("ELF", WIDTH * 0.09f, HEIGHT - 25);
                        graphics.drawString("DWARF", WIDTH * 0.33f, HEIGHT - 25);
                        graphics.drawString("MAN", WIDTH * 0.53f, HEIGHT - 25);
                        graphics.drawString("HOBBIT", WIDTH * 0.77f, HEIGHT - 25);
                        break;
                }

                //varázserő kirajzolása
                Integer in = gameElements.saruman.getSpellPower();
                graphics.setFont(new Font(Font.SERIF, Font.BOLD, 18));
                graphics.drawString("Power: " + in.toString(), 10, 20);
                //szín beállítása majd a varázserő nagyságának megfelelő téglalap rajzolása
                graphics.setColor(Color.LIGHT_GRAY);
                graphics.fillRect(10, 30, in, 5);


                //kiírja, hogy Game Over
                if (gameEngine.gameState == GameState.End) {
                    Image img = new ImageIcon("src/gameover.png").getImage();
                    graphics.drawImage(img, WIDTH / 2 - 150, HEIGHT / 2 - 50, null);
                }

                if (gameEngine.gameState == GameState.Win) {
                    Image img = new ImageIcon("src/win.png").getImage();
                    graphics.drawImage(img, WIDTH / 2 - 100, HEIGHT / 2 - 130, null);
                }

                // paint screen
                Graphics g;
                g = this.getGraphics();
                if ((g != null) && (image != null))
                    g.drawImage(image, 3, 25, null);
                if (g != null) g.dispose();

                // Szál altatása 20ms-ig
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            sync = true; //torony kirajzolás engedélyező
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
