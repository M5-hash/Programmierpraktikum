package src;

//import src.Images.Zielhilfe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import static src.config.*;

/**
 * Ruft die Methoden Tile und Schiffzeichner auf, verarbeitet jegliche Inputs welche auf dem Spielfeld passieren
 */
public class TilePainter extends JPanel implements MouseMotionListener {

    public static boolean horizontal = true;
    private static int groesse = 0;
    private final Tile Ebene;
    public boolean Onfirstfield = false;
    int AnzSchiffe = 0;
    boolean usable = false;
    MouseListener temp;
    MouseListener temp2;
    int counter;
    boolean hasshot = false;
    boolean hitKI = true;
    int[] groessen = {0, 0, size2, size3, size4, size5};
    boolean PlayerTurn = true;
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
    private int PosX = 0;
    private int PosY = 0;


    /**
     * @param Feldgroesse int --> Größe des zu erstellenden Spielfelds
     * @param Feldvon     int --> Für wen/was das Spielfeld ist (0 = Spieler ; 1 = GegnerKI ; 2 = GegnerOnline)
     * @param frame       SpielWindow --> Das erstellte Fenster
     * @param Com         ComPLayer --> ComputerGegner
     * @param pf          PlayingField --> PlayingField des Spielfeld1
     *                    <p>
     *                    Konstruktor für TilePainter, welches das Felder an sich durch Tile aufruft
     *                    <p>
     *                    Übernimmt die Inputs des Spielers gibt diese wenn nötig an andere Methoden weiter
     */
    public TilePainter(int Feldgroesse, int Feldvon, SpielWindow frame, ComPlayer Com, PlayingField pf) {
        Ebene = new Tile(Feldgroesse);
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

        //TODO Wenn man im Online Modus nochmal schießt, dann landet des einfach im Buffer
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
                                        case 2 -> {
                                            groessen[recycleship]++;
                                            frame.btn_size2.setText("size 2: " + groessen[recycleship]);
                                            AnzSchiffe--;
                                        }
                                        case 3 -> {
                                            groessen[recycleship]++;
                                            frame.btn_size3.setText("size 3: " + groessen[recycleship]);
                                            AnzSchiffe--;
                                        }
                                        case 4 -> {
                                            groessen[recycleship]++;
                                            frame.btn_size4.setText("size 4: " + groessen[recycleship]);
                                            AnzSchiffe--;
                                        }
                                        case 5 -> {
                                            groessen[recycleship]++;
                                            frame.btn_size5.setText("size 5: " + groessen[recycleship]);
                                            AnzSchiffe--;
                                        }
                                    }


                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }

                        if (Tile.isFightstart()) {

                            if (pf.getFieldEnemy()[yFeld][xFeld] == 0) {


                                if (field == 1) {
                                    if (SpielFeld2 == 1) {
                                        try {
                                            if (PlayerTurn) {

                                                int TrefferSpieler = Computer.isShot(xFeld, yFeld);
                                                pf.didHit(TrefferSpieler, xFeld, yFeld);
                                                if (TrefferSpieler == 0) {
                                                    hitKI = true;

                                                    ActionListener taskPerformer = evt -> {


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

                                                        hitKI = pf.getField()[Feld[1]][Feld[0]] == 1 || pf.getField()[Feld[1]][Feld[0]] == 2;
                                                        System.out.println(hitKI);
                                                        if (!hitKI) {
                                                            PlayerTurn = true;
                                                            timerstopper();
                                                        }
                                                        if (hitKI) {
                                                            PlayerTurn = false;
                                                            timerstarter();
                                                        }
                                                        System.out.println(counter++);
                                                    };
                                                    KItimer = new Timer(300, taskPerformer);
                                                    KItimer.setRepeats(false);
                                                    KItimer.start();

                                                }

                                            }

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
                                    } catch (Exception ioException) {
                                        ioException.printStackTrace();
                                    }
                                    try {
                                        do {
                                            frame.server.message_check();
                                            frame.tile.repaint();

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
                                    } catch (Exception ioException) {
                                        ioException.printStackTrace();
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
    public boolean setOnfirstfield(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();


        return Onfirstfield = x > TileSize.getSizeofBorder() && x < Tile.field_size * TileSize.Tile_Size + TileSize.getSizeofBorder() && y > TileSize.getSizeofBorder() && y < TileSize.getSizeofBorder() + fieldsize * TileSize.Tile_Size;
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
     *          Hier werden alle Methoden aufgerufen, welche etwas visuell darstellen.
     *          <p>
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setOpaque(false);

        //checkt ab, ob der Spieler bereits verloren hat
        if (Tile.fightstart && pf.gameover()) {
            //Gibt weiter, das nicht mehr das Spielfeld sondern der loss Screen Angezeigt werden soll
            frame.tile.Ebene.YouLost(g);
            //Schaltet die Zielhilfe aus (X und Y Anzeige über dem Spielfeld)
            frame.Z.stopdrawing();

            //checkt ab ob der Computer bereits verloren hat
        } else if (Tile.fightstart && frame.tile2.field == 1 && Computer.gameover()) {
            //Gibt weiter, das nicht mehr das Spielfeld sondern der win Screen Angezeigt werden soll
            frame.tile.Ebene.YouWin(g);
            //Schaltet die Zielhilfe aus (X und Y Anzeige über dem Spielfeld)
            frame.Z.stopdrawing();

            //Das else bedeutet, dass das Spiel noch nicht beendet ist, also wird entweder platziert oder
            //gekämpft
        } else {
            //Ruft das Spielfeld auf
            Ebene.DrawLayer(g);

            //Wenn das Spielfeld nicht vom Spieler ist, muss immer der Schiffzeichner aufgerufen werden egal welches Theme
            if (field == 1 || field == 2) {
                //Zeichnet die Sprites welche auf dem Spielfeld angezeigt werden
                hier.Schiffzeichner(g);
            } else {
                //Überprüfung auf gewähltes Theme
                if (selectedTheme.equals("Pokemon")) {
                    //Zeichnet die Sprites aber alle haben Pokemon Thematik
                    hier.Pokemonpicker(g);
                } else {
                    //Zeichnet die Sprites aber alles hat eine klassische Schiff Thematik
                    hier.Schiffzeichner(g);
                }
            }

            if (field == 0) {

                if (!Tile.isFightstart() && sumofships > AnzSchiffe) {
                    if (MovementHandler) {

                        Predicted.setPrediction(PosX, PosY);
                        placeable = pf.checkShip(groesse, PosX, PosY, horizontal);
                    }
                    Predicted.Schiffzeichner(g, placeable);
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

    /**
     * Stop den Timer, welcher es dem Menschen ermöglicht genau zu sehen was der Computer macht
     */
    private void timerstopper() {
        KItimer.stop();
    }

    /**
     * Startet den Timer, welcher es dem Menschen ermöglicht genau zu sehen was der Computer macht
     */
    private void timerstarter() {
        KItimer.start();
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * @param e gibt MouseEvent weiter
     *          <p>
     *          setzt die Pos Variablen auf das Tile auf dem sich die Maus momentan befindet und sagt der Vorhersage,
     *          ob diese sich neue Werte für die Anzeige holen muss
     *          <p>
     */
    @Override
    public void mouseMoved(MouseEvent e) {

        boolean changed = false;

        //checkt ab, ob die Maus sich auf dem Spielfeld befindet und dann ob es eine Änderung in der Maus Position gibt
        if (setOnfirstfield(e))
            changed = PosX != ((e.getX() - TileSize.getSizeofBorder()) / TileSize.Tile_Size) || PosY != (e.getY() - TileSize.getSizeofBorder()) / TileSize.Tile_Size;

        //Maus ist auf Spielfeld aber auf einen andren individuellen Feld als zuvor
        if (changed) {

            //Gibt an auf welchem Feld sich die Maus momentan befindet
            setPosX((e.getX() - TileSize.getSizeofBorder()) / TileSize.Tile_Size);
            setPosY((e.getY() - TileSize.getSizeofBorder()) / TileSize.Tile_Size);

            //Checkt ab ob man schon beim schießen ist wenn ja muss die Vorhersage, welche bim platzieren hilft
            //nicht mehr aufgerufen werden
            MovementHandler = !Tile.isFightstart();
            //Sagt der visuellen Vorhersage, dass die Maus sich auf einem anderen Feld befindet und diese ihre
            //Werte erneuern muss

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

