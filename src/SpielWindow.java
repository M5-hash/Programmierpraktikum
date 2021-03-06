package src;


import src.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static src.config.*;

/**
 * Erstellt das Fenster, auf welchem sich die Benutzeroberfläche befindet.
 * Die Positionen und Größen der einzelnen GUI Elemente werden eigenständig ermittelt.
 * <p>
 * Ruft im Singleplayer auch das eigene Spielfeld, sowie den ComputerGegner auf
 */
public class SpielWindow extends JPanel {
    /**
     * True - Multiplayer und Client<br>
     * False - Sonst
     */
    boolean Multclient;
    /**
     * Zeigt an, wessen Runde es ist
     */
    DisplayTurn Turn;
    /**
     * Rechtes Spielfeld
     */
    TilePainter tile2;
    /**
     * Linkes Spielfeld
     */
    TilePainter tile;
    /**
     * Zeigt die X und Y Koordinaten des Spielfelds an
     */
    Zielhilfe Z;
    /**
     * Layout der Buttons
     */
    GridLayout gameLayout;
    /**
     * Panel auf dem die Components hinzugefügt werden
     */
    JPanel menuPanel;
    /**
     * Panel der Buttons bevor das Spiel startet
     */
    JPanel gamePanel1;
    /**
     * Panel der Buttons nachdem das Spiel gestartet wurde
     */
    JPanel gamePanel2;
    /**
     * Öffnet das Sizemenü und stellt die Schwierigkeit der KI auf Easy
     */
    JButton buttonMenuStart;
    /**
     * Speichert aktuellen Spielstand
     */
    JButton buttonSaveGame;
    /**
     * Button um das Spiel zu schließen
     */
    JButton buttonQuitGame;
    /**
     * Startet das Spiel
     */
    JButton buttonReady;
    /**
     * Ermöglicht das Entfernen der gesetzten Schiffe
     */
    JButton buttonDelete;
    /**
     * Buttongruppe der ToggleButton
     */
    ButtonGroup buttonGroup;
    /**
     * Ermöglicht das Setzen der Size 2 Schiffe
     */
    ToggleButton btn_size2;
    /**
     * Ermöglicht das Setzen der Size 3 Schiffe
     */
    ToggleButton btn_size3;
    /**
     * Ermöglicht das Setzen der Size 4 Schiffe
     */
    ToggleButton btn_size4;
    /**
     * Ermöglicht das Setzen der Size 5 Schiffe
     */
    ToggleButton btn_size5;
    /**
     *
     */
    MenuButton dummybutton = new MenuButton("DUMMY BUTTON", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
    /**
     * Referenz zur Com_base
     */
    Com_base Online;
    /**
     * Referenz zum ComPlayer
     */
    private ComPlayer Com;
    /**
     * Referenz zum playingField
     */
    private PlayingField playingField;

    /**
     * Konstruktor - Singleplayer
     *
     * @param frame Frame des SpielWindow
     * @throws IOException         Falls nicht alle Bilder geladen werden konnten
     * @throws FontFormatException Falls Font nicht geladen werden konnte
     */
    public SpielWindow(JFrame frame) throws IOException, FontFormatException {
        playingField = new PlayingField(fieldsize, calculateships(), true);
        makeComponents(frame);
    }

    /**
     * Konstruktor - Wird benutzt wenn das Spiel zuvor geladen wurde
     *
     * @param frame Frame des SpielWindow
     * @param pf    Geladenes Playingfield
     * @param Com   Geladener Computer
     */
    public SpielWindow(JFrame frame, PlayingField pf, ComPlayer Com) {
        this.playingField = pf;
        this.Com = Com;
        makeComponents(frame, false);
        adjustState(pf.getStatus() == 1);
        gamestart();
    }

    /**
     * Konstruktor - Multiplayer Client
     *
     * @param frame  Frame des SpielWindow
     * @param Client Netzwerkverbindung des Client
     */
    public SpielWindow(JFrame frame, Client Client) {
        Online = Client;
        playingField = Client.getPf();
        Multclient = true;
        makeComponents(frame, onlineCom);
        if (onlineCom) {
            tile.AnzSchiffe = sumofships;
            gamestart();
        }

        if (Online.getLoaded()) {
            adjustState(playingField.getStatus() == 1);
            gamestart();
        }
    }

    /**
     * Konstruktor - Multiplayer Server
     *
     * @param frame  Frame des SpielWindow
     * @param Server Netzwerkverbindung des Servers
     */
    public SpielWindow(JFrame frame, Server Server) {
        System.out.println("Wir sind in das SpielWindow gekommen");
        Online = Server;
        playingField = Server.getPf();
        Multclient = false;
        makeComponents(frame, onlineCom);
        if (onlineCom) {
            tile.AnzSchiffe = sumofships;
            gamestart();
        }

        if (Online.getLoaded()) {
            adjustState(playingField.getStatus() == 1);
            gamestart();
        }
    }

    /**
     * Zustandsanpassung des Spielstands nach dem Laden
     *
     * @param pfStatus Status des Spieles <br>
     *                 0 = Schiffe setzen <br>
     *                 1 = Spieler darf schießen <br>
     *                 2 = Gegner darf schießen <br>
     */
    private void adjustState(boolean pfStatus) {
        tile.AnzSchiffe = sumofships;
        tile2.PlayerTurn = pfStatus;
        if (selectedTheme.equals("Pokemon")) {
            //Daten reinladen
            tile.hier.updatePokemon();
            //Daten updaten
            tile.hier.updatePokemon();
        }
    }

    /**
     * Wrappermethode von makeComponents <br>
     * com Angabe ist immer true
     *
     * @param frame Frame des SpielWindow
     */
    private void makeComponents(JFrame frame) {
        makeComponents(frame, true);
    }

    /**
     * Erzeugung aller Components
     *
     * @param frame  Frame des SpielWindow
     * @param setCom True - Computer spielt mit
     */
    private void makeComponents(JFrame frame, boolean setCom) {

        System.out.println("Wir sind in der makecomponents");

        sumofships = size2 + size3 + size4 + size5;
        System.out.println("sumofships" + sumofships);
        frameheigth = frame.getHeight();
        framewidth = frame.getWidth();

        if (setCom && (SpielFeld1 == 1 || SpielFeld2 == 1)) {
            if (KIisEasy) {
                try {
                    Com = new ComPlayerEasy(new PlayingField(fieldsize, calculateships(), false));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Com = new ComPlayerNormal(new PlayingField(fieldsize, calculateships(), false));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (fullscreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        }
        frame.setSize(GF_WIDTH, GF_HEIGHT);
        frame.setMinimumSize(new Dimension(854, 480));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        frameheigth = frame.getHeight();
        framewidth = frame.getWidth();

        Turn = new DisplayTurn();
        tile = new TilePainter(fieldsize, SpielFeld1, this, Com, playingField);
        tile2 = new TilePainter(fieldsize, SpielFeld2, this, Com, playingField);
        Z = new Zielhilfe(this, frame);

        gameLayout = new GridLayout(0, 1);
        buttonDelete = new DeleteButton();
        buttonReady = new MenuButton("START GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON), "Das Spiel kann erst gestartet werden, wenn alle Schiffe gesetzt sind");
        buttonMenuStart = new MenuButton("MAIN MENU", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonSaveGame = new SaveGameButton("SAVE GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON), this);
        buttonQuitGame = new QuitButton();

        if (selectedTheme.equals("Pokemon")) {
            menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.GAME_BACKGROUND));
            btn_size2 = new ToggleButton("size 2: " + size2, ImageLoader.getImage(ImageLoader.RED), ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL1));
            btn_size3 = new ToggleButton("size 3: " + size3, ImageLoader.getImage(ImageLoader.RED), ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL2));
            btn_size4 = new ToggleButton("size 4: " + size4, ImageLoader.getImage(ImageLoader.RED), ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL3));
            btn_size5 = new ToggleButton("size 5: " + size5, ImageLoader.getImage(ImageLoader.RED), ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL4));
        } else {
            Bildloader Bild = new Bildloader();
            menuPanel = new CustomPanel(Bild.BildLoader("src/Images/NavalBackgroundShort.jpg"));
            btn_size2 = new ToggleButton("size 2: " + size2, Bild.BildLoader("src/Images/RedNavalButtonShortFullRed.png"), Bild.BildLoader("src/Images/NavalButtonFullRoyal.png"), Bild.BildLoader("src/Images/2Stern.png"));
            btn_size3 = new ToggleButton("size 3: " + size3, Bild.BildLoader("src/Images/RedNavalButtonShortFullRed.png"), Bild.BildLoader("src/Images/NavalButtonFullRoyal.png"), Bild.BildLoader("src/Images/3Stern.png"));
            btn_size4 = new ToggleButton("size 4: " + size4, Bild.BildLoader("src/Images/RedNavalButtonShortFullRed.png"), Bild.BildLoader("src/Images/NavalButtonFullRoyal.png"), Bild.BildLoader("src/Images/4Stern.png"));
            btn_size5 = new ToggleButton("size 5: " + size5, Bild.BildLoader("src/Images/RedNavalButtonShortFullRed.png"), Bild.BildLoader("src/Images/NavalButtonFullRoyal.png"), Bild.BildLoader("src/Images/5Stern.png"));
        }
        gamePanel1 = new JPanel();
        gamePanel2 = new JPanel();
        buttonGroup = new ButtonGroup();

        dummybutton.setBounds(-100, -100, 1, 1);

        dummybutton.addActionListener(e -> {

            try {
                Online.NetworkProtocol();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (!Online.getMyTurn() && !Online.getPf().gameover() && Online.SocketActive) {
                Thread t1 = new Thread(() -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                        dummybutton.doClick();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                });
                t1.start();
            } else if (onlineCom && Online.getMyTurn() && !Online.getPf().gameover() && Online.SocketActive) {
                int[] hold = new int[0];
                try {
                    hold = Online.getComPl().doNextShot();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                tile2.setPosX(hold[0]);
                tile2.setPosY(hold[1]);
                Online.setLastXY(hold[0], hold[1]);
                Online.Send("shot " + hold[0] + " " + hold[1]);

                try {
                    dummybutton.doClick();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        menuPanel.setLayout(null);

        tile2.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        tile.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        gameLayout.setVgap(5);

        gamePanel1.setOpaque(false);
        gamePanel1.setLayout(gameLayout);
        gamePanel1.setVisible(false);

        gamePanel2.setOpaque(false);
        gamePanel2.setLayout(gameLayout);

        btn_size2.addActionListener(e -> TilePainter.setGroesse(2));
        buttonGroup.add(btn_size2);
        btn_size3.addActionListener(e -> TilePainter.setGroesse(3));
        buttonGroup.add(btn_size3);
        btn_size4.addActionListener(e -> TilePainter.setGroesse(4));
        buttonGroup.add(btn_size4);
        btn_size5.addActionListener(e -> TilePainter.setGroesse(5));
        buttonGroup.add(btn_size5);
        buttonReady.addActionListener(e -> {

            if (tile.AnzSchiffe == sumofships) {
                Tile.fightstart = true;
                tile.AnzSchiffe = 0;
                if (Tile.isFightstart()) {
                    Z.setBounds(framewidth * 62 / 100, frameheigth * 17 / 100, framewidth * 25 / 100, frameheigth * 10 / 100);
                }
                gamePanel1.setVisible(true);
                gamePanel2.setVisible(false);
                if (SpielFeld2 == 2 && Multclient) {
                    Thread t1 = new Thread(() -> {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                            dummybutton.doClick();
                        } catch (InterruptedException e12) {
                            e12.printStackTrace();
                        }

                    });
                    t1.start();
                }
            } else {
                setToolTipText("Es wurden noch nicht alle Schiffe platziert");
            }

        });
        buttonDelete.addActionListener(e -> {

            ((DeleteButton) buttonDelete).switchDeleting();
            tile.switchDeleting();

        });
        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            gamePanel1.setVisible(false);
            frame.dispose();
            Tile.fightstart = false;
            fullscreen = false;
            fieldsize = 10;
            size2 = 1;
            size3 = 1;
            size4 = 1;
            size5 = 1;
            sumofships = 4;
            if(this.Online != null){
                this.Online.KillSocket();
            }
            // Create MenuMain and display it
            try {
                new MenuStart();
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        if(this.Online == null) {
            gamePanel1.add(buttonMenuStart);
        }
        if (!Multclient) {
            gamePanel1.add(buttonSaveGame);
        }
        gamePanel1.add(buttonQuitGame);

        gamePanel2.add(btn_size2);
        gamePanel2.add(btn_size3);
        gamePanel2.add(btn_size4);
        gamePanel2.add(btn_size5);
        gamePanel2.add(buttonReady);
        gamePanel2.add(buttonDelete);

        TileSize.setTile_Size(((framewidth / 4) - 2 * Math.max(18, TileSize.Tile_Size / 8)) / fieldsize);
        menuPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        tile.setBounds(framewidth * 13 / 100, frameheigth * 25 / 100, framewidth * 25 / 100, framewidth * 25 / 100);
        tile2.setBounds(framewidth * 62 / 100, frameheigth * 25 / 100, framewidth * 25 / 100, framewidth * 25 / 100);
        Z.setBounds(framewidth * 13 / 100, frameheigth * 10 / 100, framewidth * 25 / 100, frameheigth * 10 / 100);
        Turn.setBounds(framewidth * 40 / 100, frameheigth * 10 / 100, framewidth * 10 / 100, frameheigth * 10 / 100);
        gamePanel1.setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, framewidth * 14 / 100);
        gamePanel2.setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, framewidth * 25 / 100);

        menuPanel.add(gamePanel1);
        menuPanel.add(gamePanel2);
        menuPanel.add(tile);
        menuPanel.add(tile2);
        menuPanel.add(Z);
        menuPanel.add(Turn);

        frame.add(menuPanel);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int isfightstart = Tile.isFightstart() ? 62 : 13;
                int W = 16;
                int H = 9;

                Rectangle b = frame.getBounds();
                int Borderwidth = 2 * Math.max(18, TileSize.Tile_Size / 8);

                if (framewidth != frame.getWidth()) {
                    framewidth = frame.getWidth();
                    frameheigth = frame.getHeight();
                    frame.setBounds(b.x, b.y, b.width, b.width * H / W);
                    TileSize.setTile_Size(((framewidth / 4) - Borderwidth) / fieldsize);

                } else if (frameheigth != frame.getHeight()) {
                    frameheigth = frame.getHeight();
                    framewidth = frame.getWidth();
                    frame.setBounds(b.x, b.y, b.height * W / H, b.height);
                    TileSize.setTile_Size(((framewidth / 4) - Borderwidth) / fieldsize);
                }

                menuPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                tile.setBounds(framewidth * 13 / 100, frameheigth * 25 / 100, framewidth * 25 / 100, framewidth * 25 / 100);
                tile2.setBounds(framewidth * 62 / 100, frameheigth * 25 / 100, framewidth * 25 / 100, framewidth * 25 / 100);
                gamePanel1.setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, framewidth * 14 / 100);
                gamePanel2.setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, framewidth * 25 / 100);
                Z.setBounds(framewidth * isfightstart / 100, frameheigth * 17 / 100, framewidth * 25 / 100, frameheigth * 10 / 100);
                Turn.setBounds(framewidth * 41 / 100, frameheigth * 10 / 100, framewidth * 18 / 100, frameheigth * 10 / 100);

                menuPanel.revalidate();
                menuPanel.repaint();
            }
        });

        Timer timer = new Timer(110, e -> {
            menuPanel.repaint();
            menuPanel.revalidate();
        });
        timer.start();

    }

    /**
     * Übergang zwischen der Schiffsplatzierung und dem Beschuss des Gegners
     */
    private void gamestart() {
        if (tile.AnzSchiffe == sumofships) {
            Tile.fightstart = true;
            tile.AnzSchiffe = 0;
            if (Tile.isFightstart()) {
                Z.setBounds(framewidth * 62 / 100, frameheigth * 17 / 100, framewidth * 25 / 100, frameheigth * 10 / 100);
            }
            gamePanel1.setVisible(true);
            gamePanel2.setVisible(false);
            if ((Online.loaded || SpielFeld2 == 2) && Multclient && !onlineCom) {
                try {
                    Thread t1 = new Thread(() -> {
                        try {
                            Turn.switchTurn(false);
                            TimeUnit.MILLISECONDS.sleep(100);
                            dummybutton.doClick();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    });
                    t1.start();

                } catch (Exception f) {
                    f.printStackTrace();
                }
            } else if (SpielFeld2 == 2 && Multclient && onlineCom) {
                try {
                    Thread t1 = new Thread(() -> {
                        try {
                            Turn.switchTurn(false);
                            TimeUnit.MILLISECONDS.sleep(100);
                            dummybutton.doClick();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    });
                    t1.start();

                } catch (Exception f) {
                    f.printStackTrace();
                }
            } else if (SpielFeld2 == 2 && !Multclient && onlineCom) {
                int[] hold = new int[0];
                try {
                    hold = Online.getComPl().doNextShot();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tile2.setPosX(hold[0]);
                tile2.setPosY(hold[1]);
                Online.setLastXY(hold[0], hold[1]);
                Online.Send("shot " + hold[0] + " " + hold[1]);
                try {

                    Thread t1 = new Thread(() -> {
                        try {
                            Turn.switchTurn(false);

                            TimeUnit.MILLISECONDS.sleep(100);
                            dummybutton.doClick();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    });
                    t1.start();

                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        } else {
            System.out.println("Nicht alle Schiffe gesetzt");
        }
    }

    /**
     * Umwandlung einzelner Int Werte in ein Int Array
     *
     * @return Int Array mit Anzahl zu setzender Schiffe
     */
    static int[] calculateships() {

        int[] sizes = {size2, size3, size4, size5};

        int[] compiledArray = new int[size5 + size4 + size3 + size2];
        int counter = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < sizes[i]; sizes[i]--) {
                compiledArray[counter] = i + 2;
                counter++;
            }
        }

        return compiledArray;
    }

    /**
     * Com - Getter
     *
     * @return this.Com
     */
    public ComPlayer getCom() {
        return this.Com;
    }

    /**
     * PlayingField - Getter
     *
     * @return this.playingField
     */
    public PlayingField getPlayingField() {
        return this.playingField;
    }

    /**
     * Server - Getter
     *
     * @return this.Online
     */
    public Com_base getServer() {
        return this.Online;
    }

}
