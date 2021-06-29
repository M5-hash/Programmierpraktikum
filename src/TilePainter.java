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
    public static int AnzSchiffe = 0;
    public static boolean Onfirstfield = false;
    private static int groesse = 3;
    private static int PosX = 0;
    private static int PosY = 0;
    private final Tile Ebene;
    int counter;
    boolean hasshot = false;
    boolean hitKI = true;
    int[] groessen = {0, 0, size2, size3, size4, size5};
    boolean PlayerTurn;
    int field;
    boolean deleting;
    SpritePainter hier;
    SpritePainter Predicted;
    SpielWindow frame;
    PlayingField pf;
    ComPlayer Computer;
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
        System.out.println(field);
        hier = new SpritePainter(field, this, frame, pf);


        if (field == 0) {
            //Da es sich hier nicht um ein normales Feld handelt wird hier 4 == Vorhersage übergeben
            Predicted = new SpritePainter(4, this, frame, pf);
        }


        if (field == 0) addMouseMotionListener(this);


        //TODO sichergehen, dass der der getroffen hat nochmal schießen darf und nicht zum anderen Spieler gewechselt wird
        addMouseListener(new MouseAdapter() {
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

                            System.out.println("Die Position auf der Y-Achse beträgt:" + yFeld + "\nDie Postion auf der X-Achse beträgt:" + xFeld);

                            if (!deleting && !Tile.isFightstart()) {
                                if (groessen[groesse] > 0 && pf.setShip(groesse, xFeld, yFeld, horizontal)) {
                                    change = true;
                                    AnzSchiffe++;

                                    switch (groesse) {
                                        case 2:
                                            if (size2 > 0) {
                                                size2--;
                                                frame.btn_size2.setText("size 2: " + size2);
                                                groessen[groesse]--;
                                            }
                                            break;
                                        case 3:
                                            if (size3 > 0) {
                                                size3--;
                                                frame.btn_size3.setText("size 3: " + size3);
                                                groessen[groesse]--;
                                            }
                                            break;
                                        case 4:
                                            if (size4 > 0) {
                                                size4--;
                                                frame.btn_size4.setText("size 4: " + size4);
                                                groessen[groesse]--;
                                            }
                                            break;
                                        case 5:
                                            if (size5 > 0) {
                                                size5--;
                                                frame.btn_size5.setText("size 5: " + size5);
                                                groessen[groesse]--;
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
                                            size2++;
                                            groessen[recycleship]++;
                                            frame.btn_size2.setText("size 2: " + size2);
                                            AnzSchiffe--;
                                            break;
                                        case 3:
                                            size3++;
                                            groessen[recycleship]++;
                                            frame.btn_size3.setText("size 3: " + size3);
                                            AnzSchiffe--;
                                            break;
                                        case 4:
                                            size4++;
                                            groessen[recycleship]++;
                                            frame.btn_size4.setText("size 4: " + size4);
                                            AnzSchiffe--;
                                            break;
                                        case 5:
                                            size5++;
                                            groessen[recycleship]++;
                                            frame.btn_size5.setText("size 5: " + size5);
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

                                                ActionListener KiAct = new ActionListener() {
                                                    @Override
                                                    public void actionPerformed(ActionEvent KIevnt) {

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
                                                        } catch (Exception exception) {
                                                            exception.printStackTrace();
                                                        }
                                                        System.out.println("I was called" + counter++ + "times");
                                                        hitKI = pf.getField()[Feld[1]][Feld[0]] == 1 || pf.getField()[Feld[1]][Feld[0]] == 2;


                                                    }
                                                };

                                                Timer KItimer = new Timer(110, KiAct);

                                                if (hitKI) {
                                                    KItimer.start();
                                                }


                                                pf.gameover();
                                                Computer.gameover();
                                                PlayerTurn = true;
                                            }
                                            System.out.println(Arrays.deepToString(pf.getField()).replace("]", "]\n"));
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

                                    }while(!frame.server.myTurn);
                                    } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                                ;


                            }

                            if (field == 2 && frame.Multclient && frame.client.myTurn) {
                                String xString = xFeld + " ";
                                String yString = "" + yFeld;

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
                                    do{
                                    frame.client.message_check();
                                    frame.tile.repaint();
                                        //tile.revalidate();


                                    }while(!frame.client.myTurn);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                                ;


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
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    horizontal = !horizontal;
                    MovementHandler = true;
                    System.out.println("Es wurden " + AnzSchiffe + " platziert");
                }

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2) {
                    Tile.fightstart = !Tile.fightstart;
                    TileSize.setFighting(Tile.fightstart ? 1 : 0);
                    System.out.println("Der Kampf hat begonnen");
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

    public static boolean getOnfirstfield() {
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

    public static int getPosX() {
        return PosX;
    }

    /**
     * @param posX Auf dem wievielten Feld sich die Maus auf der X-Achse befindet
     */
    public static void setPosX(int posX) {
        PosX = posX;
    }

    public static int getPosY() {
        return PosY;
    }

    /**
     * @param posY Auf dem wievielten Feld sich die Maus auf der Y-Achse befindet
     */
    public static void setPosY(int posY) {
        PosY = posY;
    }

    public int[] getRecentshot() {
        return recentshot;
    }

    /**
     * @param g Man brauch für nahezu alles ein Object des Typs Graphics, deswegen gibt es hier eins.
     *          <p>
     *          Hier werden alle Methoden aufgerufen, welche etwas Zeichnen.
     *          <p>
     *          TODO Theme check mit einarbeiten, den gibt es bis jetzt leider noch nicht
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        setOpaque(false);
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

    /**
     * Setzt die Variable deleting.
     * <p>
     * Falls deleting = true, werden keine Schiffe gesetzt sondern gelöscht
     */
    public void switchDeleting() {
        this.deleting = !this.deleting;
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
        }
    }

}

