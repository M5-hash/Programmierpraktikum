package src;

//import src.Images.Zielhilfe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;

import static src.config.*;

/**
 * Ruft die Methoden Tile und Schiffzeichner auf, verarbeitet jegliche Inputs welche auf dem Spielfeld passieren
 */
public class TilePainter extends JPanel implements MouseMotionListener {

    public static boolean horizontal = true;
    int AnzSchiffe = 0;
    public boolean Onfirstfield = false;
    private static int groesse = 0;
    private int PosX = 0;
    private int PosY = 0;
    private final Tile Ebene;
    boolean usable = false;
    MouseListener temp;
    MouseListener temp2;
    int counter;
    boolean hasshot = false;
    boolean hitKI = true;
    int[] groessen = {0, 0, size2, size3, size4, size5};
    boolean PlayerTurn;
    int field;
    boolean allowchange;
    boolean deleting;
    SpritePainter hier;
    SpritePainter Predicted;
    SpielWindow frame;
    PlayingField pf;
    ComPlayer Computer;
    Timer KItimer;
    int[] recentshot = new int[2];
    boolean placeable = false;
    boolean MovementHandler;

    /**
     * @param Feldgroesse gibt Groesse des Feldes vor
     * @param Feldvon     gibt an für wen des Feld ist
     *                    <p>
     *                    Konstruktor für TilePainter, welches das Felder an sich durch Tile aufruft
     *                    <p>
     *                    Übernimmt die Inputs des Spielers gibt diese wenn nötig an andere Methoden weiter
     */
    public TilePainter(int Feldgroesse, int Feldvon, SpielWindow frame, ComPlayer Com, PlayingField pf) {
        Ebene = new Tile(Feldgroesse, Feldvon);
        Computer = Com;
        this.frame = frame;
        field = Feldvon;
        this.pf = pf;
        hier = new SpritePainter(field, this, frame, pf);


        if (field == 0) {
            //Da es sich hier nicht um ein normales Feld handelt wird hier 4 == Vorhersage übergeben
            Predicted = new SpritePainter(4, this, frame, pf);
        }


        addMouseMotionListener(this);

        //TODO sichergehen, dass der der getroffen hat nochmal schießen darf und nicht zum anderen Spieler gewechselt wird
        addMouseListener(temp = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX();
                    int y = e.getY();


                    /*Die Position auf dem Feld wird durch diese Funktion berechnet, anstatt das nur die aktuelle Position in Pixeln zurückgegeben wird.
                     *
                     * Erzeugt dabei die Parameter yFeld und xFeld
                     *
                     * y-Feld: (y - top_gap) / TileSize.tile_height
                     * x-Feld: (x - sidegapl) / TileSize.tile_width
                     *
                     * */


                    setOnfirstfield(e);


                    if (Onfirstfield) {


                        int yFeld = ((y - TileSize.getSizeofBorder()) / TileSize.Tile_Size);
                        int xFeld = ((x - TileSize.getSizeofBorder()) / TileSize.Tile_Size);

                        if (!Tile.fightstart && field == 0) {

//                            System.out.println("Die Position auf der Y-Achse beträgt:" + yFeld + "\nDie Postion auf der X-Achse beträgt:" + xFeld);

                            if (!deleting && !Tile.isFightstart()) {
                                if (groessen[groesse] > 0 && pf.setShip(groesse, xFeld, yFeld, horizontal)) {
                                    change = true;
                                    AnzSchiffe++;

                                    switch (groesse) {
                                        case 2:
                                            if (groessen[groesse] > 0) {
                                                groessen[groesse]--;
                                                frame.btn_size2.setText("size 2: " + groessen[groesse]);
                                            }
                                            break;
                                        case 3:
                                            if (groessen[groesse] > 0) {
                                                groessen[groesse]--;
                                                frame.btn_size3.setText("size 3: " + groessen[groesse]);

                                            }
                                            break;
                                        case 4:
                                            if (groessen[groesse] > 0) {
                                                groessen[groesse]--;
                                                frame.btn_size4.setText("size 4: " + groessen[groesse]);

                                            }
                                            break;
                                        case 5:
                                            if (groessen[groesse] > 0) {
                                                groessen[groesse]--;
                                                frame.btn_size5.setText("size 5: " + groessen[groesse]);

                                            }
                                            break;
                                    }

                                } else {
                                    change = false;
                                }
                            }
                            if (!Tile.isFightstart() && deleting) {
                                try {
                                    int recycleship = pf.deleteShip(xFeld, yFeld);

                                    switch (recycleship) {
                                        case 2:
                                            groessen[recycleship]++;
                                            frame.btn_size2.setText("size 2: " + groessen[groesse]);
                                            AnzSchiffe--;
                                            break;
                                        case 3:
                                            groessen[recycleship]++;
                                            frame.btn_size3.setText("size 3: " + groessen[groesse]);
                                            AnzSchiffe--;
                                            break;
                                        case 4:
                                            groessen[recycleship]++;
                                            frame.btn_size4.setText("size 4: " + groessen[groesse]);
                                            AnzSchiffe--;
                                            break;
                                        case 5:
                                            groessen[recycleship]++;
                                            frame.btn_size5.setText("size 5: " + groessen[groesse]);
                                            AnzSchiffe--;
                                            break;
                                    }


                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }

                        if (Tile.isFightstart()) {

                            if (field == 1) {
                                if (SpielFeld2 == 1) {
                                    try {
                                        if (pf.getFieldEnemy()[yFeld][xFeld] == 0) {

                                            int TrefferSpieler = Computer.isShot(xFeld, yFeld);
                                            pf.didHit(TrefferSpieler, xFeld, yFeld);
                                            if (TrefferSpieler == 0) {
                                                hitKI = true;

                                                //Momentan noch funktionierend sollte aber eigentlich dafür sorgen, dass die KI nochmal Schießt, falls Sie gtroffen hat
                                                counter = 0;

                                                ActionListener taskPerformer = new ActionListener() {
                                                    public void actionPerformed(ActionEvent evt) {


                                                        int[] Feld = new int[2];
                                                        try {
                                                            Feld = Computer.doNextShot();
                                                            hasshot = true;
                                                            recentshot = Feld;
                                                        } catch (Exception exception) {
                                                            exception.printStackTrace();
                                                        }

                                                        try {
                                                            Computer.didHit(pf.isShot(Feld[0], Feld[1]));
                                                            allowchange = true;
                                                        } catch (Exception exception) {
                                                            exception.printStackTrace();
                                                        }
//                                                        System.out.println("I was called" + counter++ + "times");
                                                        hitKI = pf.getField()[Feld[1]][Feld[0]] == 1 || pf.getField()[Feld[1]][Feld[0]] == 2;
                                                        System.out.println(hitKI);
                                                        if (!hitKI) {
                                                            timerstopper();
                                                        }
                                                        if (hitKI) {
                                                            timerstarter();
                                                        }
                                                        System.out.println(counter++);
                                                    }

                                                };
                                                KItimer = new Timer(300, taskPerformer);
                                                KItimer.setRepeats(false);
                                                KItimer.start();

//                                                while(hitKI) {
//                                                Timer KiAct = new Timer() {
//                                                    @Override
//                                                    public void actionPerformed(ActionEvent KIevnt) {


                                            }


//                                                Timer KItimer = new Timer(110, KiAct);


                                            PlayerTurn = true;
//                                            }
//                                            System.out.println(Arrays.deepToString(pf.getField()).replace("]", "]\n"));
                                        }
                                        //mit getPlayingField().getGameOver kann ich rausfinden wer verloren hat für wen true --> hat verloren

                                        //SpielWindow.Com.doNextShot();

                                        System.out.println("Es wurde geschossen auf X: " + xFeld + " Y: " + yFeld);
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            }
                            if (field == 2 && !frame.Multclient && frame.server.myTurn) {


                                String xString = xFeld + " ";
                                String yString = "" + yFeld;

                                System.out.println(Arrays.deepToString(pf.getFieldEnemy()).replace("],", "],\n"));

                                System.out.println("shot " + xString + yString);

                                frame.server.setXY(xFeld, yFeld);
                                try {
                                    frame.server.Send("shot " + xString + yString);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                                try {
                                    do {
                                        frame.server.message_check();
                                        frame.tile.repaint();
                                        //tile.revalidate();

                                    } while (!frame.server.myTurn);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }

                            }

                            if (field == 2 && frame.Multclient && frame.client.myTurn) {
                                String xString = xFeld + " ";
                                String yString = "" + yFeld;

                                System.out.println(Arrays.deepToString(pf.getFieldEnemy()).replace("],", "],\n"));

                                System.out.println("shot " + xString + yString);

                                frame.client.setXY(xFeld, yFeld);
                                try {
                                    frame.client.Send("shot " + xString + yString);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                                try {
                                    do {
                                        frame.client.message_check();
                                        frame.tile.repaint();

                                    } while (!frame.client.myTurn);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }

                            }

                        }
                    }
                }

            }
        });

        /*
         * Erlaubt es dem Nutzer mit der rechten Maustaste zwischen einem vertikal und horizontal ausgerichteten Schiff zu wechseln
         * @param
         *
         * */
        addMouseListener(temp2 = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    horizontal = !horizontal;
                    MovementHandler = true;
//                    System.out.println("Es wurden " + AnzSchiffe + " platziert");
                }

            }
        });

    }

    public static int getGroesse() {
        return groesse;
    }

    public static void setGroesse(int groesse) {
        TilePainter.groesse = groesse;
    }

    public boolean getOnfirstfield() {
        return Onfirstfield;
    }

    /**
     * @param e gibt MoueseEvent weiter
     *          <p>
     *          Überprüft ob die Maus sich momentan auf dem Spielfeld befindet
     */
    public void setOnfirstfield(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();


        Onfirstfield = x > TileSize.getSizeofBorder() && x < Tile.field_size * TileSize.Tile_Size + TileSize.getSizeofBorder() && y > TileSize.getSizeofBorder() && y < TileSize.getSizeofBorder() + fieldsize * TileSize.Tile_Size;
    }

    public int getPosX() {
        return PosX;
    }

    /**
     * @param posX Auf dem wievielten Feld sich die Maus auf der X-Achse befindet
     */
    public void setPosX(int posX) {
        PosX = posX;
    }

    public int getPosY() {
        return PosY;
    }

    /**
     * @param posY Auf dem wievielten Feld sich die Maus auf der Y-Achse befindet
     */
    public void setPosY(int posY) {
        PosY = posY;
    }

    public int[] getGroessen() {
        return groessen;
    }

    public void switchUsable() {
        usable = !usable;
        if (usable) {
            this.removeMouseListener(temp);
            this.removeMouseListener(temp2);
        } else {
            this.addMouseListener(temp);
            this.addMouseListener(temp2);
        }
    }

    public int[] getRecentshot() {
        return recentshot;
    }

    /**
     * @param g Man brauch für nahezu alles ein Object des Typs Graphics, deswegen gibt es hier eins.
     *          <p>
     *          Hier werden alle Methoden aufgerufen, welche etwas Zeichnen.
     *          <p>
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        setOpaque(false);

        if (Tile.fightstart && pf.gameover()) {
            frame.tile.Ebene.YouLost(g);
            frame.Z.stopdrawing();
            System.out.println("Der Computer gewann");
        } else if (Tile.fightstart && Computer.gameover()) {
            frame.tile.Ebene.YouWin(g);
            frame.Z.stopdrawing();
            System.out.println("Der Mensch gewann");
        } else {
            Ebene.DrawLayer(g);
            if (field == 1) {
                hier.Schiffzeichner(g);
            }
            if (selectedTheme.equals("Pokemon")) {
                hier.Pokemonpicker(g);
            } else {
                hier.Schiffzeichner(g);
            }

            if (SpritePainter.ready && field == 0) {

                if (MovementHandler) System.out.println(sumofships + AnzSchiffe);

                if (!Tile.isFightstart() && sumofships > AnzSchiffe) {
                    if (MovementHandler) {
                        Predicted.setPrediction(PosX, PosY);
                        placeable = pf.checkShip(groesse, PosX, PosY, horizontal);
                        Predicted.Schiffzeichner(g, placeable);
                    } else {
                        Predicted.Schiffzeichner(g, placeable);
                    }
                }
                MovementHandler = false;
            }
        }

    }

    /**
     * Setzt die Variable deleting.
     * <p>
     * Falls deleting = true, werden keine Schiffe gesetzt sondern gelöscht
     */
    public void switchDeleting() {
        this.deleting = !this.deleting;
    }

    private void timerstopper() {
        KItimer.stop();
    }

    private void timerstarter() {
        KItimer.start();
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * @param e gibt MouseEvent weiter
     *          <p>
     *          setzt die Pos Variablen auf das Tile auf dem sich die Maus momentan befindet
     *          <p>
     *          TODO Wenn man Pech hat bleibt existiert der Predictor nach Spielstart immernoch
     */
    @Override
    public void mouseMoved(MouseEvent e) {

        setOnfirstfield(e);
        boolean changed = PosX != ((e.getX() - TileSize.getSizeofBorder()) / TileSize.Tile_Size) || PosY != (e.getY() - TileSize.getSizeofBorder()) / TileSize.Tile_Size;

        if (Onfirstfield && changed && !Tile.isFightstart()) {

            setPosX((e.getX() - TileSize.getSizeofBorder()) / TileSize.Tile_Size);
            setPosY((e.getY() - TileSize.getSizeofBorder()) / TileSize.Tile_Size);

            MovementHandler = true;
        } else if(Onfirstfield && changed && Tile.isFightstart()) {

            setPosX((e.getX() - TileSize.getSizeofBorder()) / TileSize.Tile_Size);
            setPosY((e.getY() - TileSize.getSizeofBorder()) / TileSize.Tile_Size);
        }
    }

    public void clearAll(PlayingField pf) {

        this.pf = pf;

        hier.pf = pf;
        if (field == 0) {
            Predicted.pf = pf;
        }
    }
}

