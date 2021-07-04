package src;

//import src.Images.Zielhilfe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static src.config.*;

/**
 * Ruft die Methoden Tile und Schiffzeichner auf, verarbeitet jegliche Inputs welche auf dem Spielfeld passieren
 */
public class TilePainter extends JPanel implements MouseMotionListener {

    /**
     * Boolean, gibt an ob das zu setzende Schiff horizontal ausgerichtet ist
     */
    public static boolean horizontal = true;
    /**
     * Die Größe des momentan ausgewählten Schiffs, wenn 0 ist kein Schiff ausgewählt
     */
    private static int groesse = 0;
    /**
     * Variable für den Hintergrund
     */
    private final Tile Ebene;
    /**
     * Array für die Anzahl der Größen des Schiffes
     */
    private final int[] groessen = {0, 0, size2, size3, size4, size5};
    /**
     * Falls true --> Maus befindet sich auf dem Spielfeld; false --> Maus befindet sich neben dem Feld
     */
    public boolean Onfirstfield = false;
    /**
     * Anzahl der bis jetzt gesetzten Schiffe
     */
    int AnzSchiffe = 0;
    /**
     * Gibt an ob bereits geschossen wurde
     */
    boolean hasshot = false;
    /**
     * Gibt an, der letzte Schuss der KI ein Treffer war
     */
    boolean hitKI = true;
    /**
     * Gibt an ob der Spieler am Zug ist
     */
    boolean PlayerTurn = true;
    /**
     * int --> Für wen/was das Spielfeld ist (0 = Spieler ; 1 = GegnerKI ; 2 = GegnerOnline)
     */
    int field;
    /**
     * Gibt an, ob es Änderungen gibt, welche von der GUI dargestellt werden müssen
     */
    boolean allowchange = true;
    /**
     * Gibt an, ob der momentan Klick dem löschen dient
     */
    boolean deleting;
    /**
     * SpritePainter, welcher die Schiffe diesen Feldes zeichnet
     */
    SpritePainter hier;
    /**
     * SpritePainter, welcher die Vorhersage ob ein Schiff platziert werden kann zeichnet
     */
    SpritePainter Predicted;
    /**
     * Frame, von dem diese Klasse aufgerufen wurde
     */
    SpielWindow frame;
    /**
     * Spielfeld, mit welche diese Klasse interagiert und darstellt
     */
    PlayingField pf;
    /**
     * ComputerGegner, falls im Singleplayer gegen die Ki angetreten wird
     */
    ComPlayer Computer;
    /**
     * Timer, vom dem die KI aufgerufen wird, sodass die einzelnen Angriffe derer erkennbar sind
     */
    Timer KItimer;
    /**
     * Letzter Schuss der KI
     */
    int[] recentshot = new int[2];
    /**
     * Gibt an, ob dass momentan gewählte Schiff an der Mausposition platziert werden kann
     */
    boolean placeable = false;
    /**
     * Gibt an, ob es zu einer Änderung kam, welche von der Vorhersage dargestellt werden muss
     */
    boolean MovementHandler;
    /**
     * X-Position des Feldes auf dem sich die Maus befindet
     */
    private int PosX = 0;
    /**
     * Y-Position des Feldes auf dem sich die Maus befindet
     */
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
     *                    (platzieren, löschen, schießen), falls die Position der Klicks dies erlaubt
     */
    public TilePainter(int Feldgroesse, int Feldvon, SpielWindow frame, ComPlayer Com, PlayingField pf) {
        Ebene = new Tile(Feldgroesse);
        System.out.println(AnzSchiffe);
        Computer = Com;
        this.frame = frame;
        field = Feldvon;

        PickSmallestAvailableSize();
        this.pf = pf;
        hier = new SpritePainter(field, frame, pf);

        if (!onlineCom) {
            if (field == 0) {
                //Da es sich hier nicht um ein normales Feld handelt wird hier 4 == Vorhersage übergeben
                Predicted = new SpritePainter(4, frame, pf);
            }

            addMouseMotionListener(this);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {

                        //Wenn der Klick sich auf dem Spielfeld befunden hat
                        if (Onfirstfield) {

                            //Wenn man noch beim platzieren ist
                            if (!Tile.fightstart && field == 0) {

                                //Wenn man nicht am löschen ist
                                if (!deleting) {

                                    placingMausklick();
                                }

                                //Wenn man gerade am Löschen ist
                                if (deleting) {

                                    deleteMausklick();
                                }
                            }

                            //Wenn man am schießen/ nicht mehr am platzieren ist
                            if (Tile.isFightstart()) {

                                //Wenn auf diese Stelle noch nicht geschossen wurde
                                if (pf.getFieldEnemy()[PosY][PosX] == 0) {

                                    //Wenn wir auf dem Spielfeld des Gegners sind
                                    if (field == 1) {
                                        //Wenn wir einen Computergegner haben
                                        if (SpielFeld2 == 1) {

                                            KISchussMausklick();
                                        }

                                    } else {
                                        if (field == 2 && frame.Online.getMyTurn()) {

                                            OnlineMausklick();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });

            //Erlaubt es dem Nutzer mit der rechten Maustaste zwischen horizontal und vertikal in der Schiffplatzierung zu wechseln
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        horizontal = !horizontal;
                        MovementHandler = true;
                    }

                }
            });
        }
    }

    /**
     * @return int gibt die Groesse des zu platzierenden Schiffes an
     */
    public static int getGroesse() {
        return groesse;
    }

    /**
     * @param groesse int gibt die Groesse des zu platzierenden Schiffs zurück
     */
    public static void setGroesse(int groesse) {
        TilePainter.groesse = groesse;
    }

    /**
     * Überprüft, ob der Spieler am Zug ist und übergibt den Schuss dann der KI, sowie was getroffen wurde an das eigene PlayingField.
     * Bei einem Treffer wird auf den nächsten menschlichen Input gewartet, ansonsten ist die KI am Zug, welche solange
     * weiter schießt, bis diese verfehlt, zwischen jedem Schuss der Ki ist ein delay eingebaut, der es dem Menschen
     * erlaubt die einzelnen Schüsse nachzuverfolgen.
     */
    private void KISchussMausklick() {
        try {
            //Falls der Spieler gerade am Zug ist
            if (PlayerTurn) {

                // gibt zurück, was auf dem feld auf welches man geschossen hat war :0: Kein Treffer, 1: Treffer, 2: Treffer und versenkt
                int TrefferSpieler = Computer.isShot(PosX, PosY);

                //gibt die jeweilige Information an das eigene PlayingField zurück
                pf.didHit(TrefferSpieler, PosX, PosY);

                //Wenn der Spieler verfehlt hat, dann ist der Computer am Zug
                if (TrefferSpieler == 0) {


                    hitKI = true;
                    frame.Turn.switchTurn(PlayerTurn);

                    //Timer mit delay wird verwendet, sodas der Spieler die Schüsse der KI nachvollziehen/sehen kann
                    ActionListener taskPerformer = evt -> {


                        int[] Feld = new int[2];
                        try {
                            // Wohin der Computer schießt
                            Feld = Computer.doNextShot();
                            hasshot = true;
                            recentshot = Feld;
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        try {
                            //Gibt an den Computer weiter ob er getroffen hat oder nicht
                            Computer.didHit(pf.isShot(Feld[0], Feld[1]));
                            //Egal ob getroffen oder nicht, gibt es eine Veränderung welche von der GUI gezeichnet werden muss
                            allowchange = true;
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        //true falls letzter Schuss ein Treffer war
                        hitKI = pf.getField()[Feld[1]][Feld[0]] == 1 || pf.getField()[Feld[1]][Feld[0]] == 2;
                        System.out.println(hitKI);
                        //Falls nicht getroffen --> Spieler am Zug & und Timer wird beendet
                        if (!hitKI) {
                            PlayerTurn = true;
                            frame.Turn.switchTurn(PlayerTurn);
                            timerstopper();
                        }
                        if (hitKI) {
                            PlayerTurn = false;
                            frame.Turn.switchTurn(PlayerTurn);
                            timerstarter();
                        }
                    };
                    //300ms sind genug um die einzelnen Schüsse der Ki zu sehen
                    KItimer = new Timer(300, taskPerformer);
                    KItimer.setRepeats(false);
                    KItimer.start();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Übergibt den Schuss an die Kommunikation im passenden String Format.
     */
    private void OnlineMausklick() {
        String xString = PosX + " ";
        String yString = "" + PosY;

        System.out.println("shot " + xString + yString);

        frame.Online.setLastXY(PosX, PosY);
        try {
            frame.Online.Send("shot " + xString + yString);
            frame.dummybutton.doClick();

        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Übergibt, welches Feld geklickt wurde an das PlayingField, welche das jeweilige Schiff dann entfernt.
     * Die Menge aller Schiffe sowie die der jeweiligen Schiffsart, welche gesetzt werden kann, wird daraufhin um Eins erhöht.
     */
    private void deleteMausklick() {
        try {

            //Die Groesse des gelöschten Schiffes wird zurückgegeben
            int recycleship = pf.deleteShip(PosX, PosY);

            //Je nach momentaner Schiffgröße...
            switch (recycleship) {
                case 2 -> {
                    //... wird die noch platzierbare Anzahl des jeweiligen Schiffes um eins erhöht
                    groessen[recycleship]++;

                    //... und der Text mit der neuen Anzahl upgedated
                    frame.btn_size2.setText("size 2: " + groessen[recycleship]);
                }
                case 3 -> {
                    groessen[recycleship]++;
                    frame.btn_size3.setText("size 3: " + groessen[recycleship]);
                }
                case 4 -> {
                    groessen[recycleship]++;
                    frame.btn_size4.setText("size 4: " + groessen[recycleship]);
                }
                case 5 -> {
                    groessen[recycleship]++;
                    frame.btn_size5.setText("size 5: " + groessen[recycleship]);
                }
            }


        } catch (Exception exception) {
            exception.printStackTrace();
        }
        AnzSchiffe--;
    }

    /**
     * Übergibt, welches Feld geklickt wurde an das PlayingField, welche das jeweilige Schiff dann setzt.
     * Die Menge der jeweiligen Schiffsart, welche gesetzt werden kann, wird daraufhin um Eins verringert.
     */
    private void placingMausklick() {

        //Wenn es ein Schiff dieser Größe zum setzen gibt und es auch an dieser Stelle auch platzierbar ist
        if (groessen[groesse] > 0 && pf.setShip(groesse, PosX, PosY, horizontal)) {

            //Es wird die Anzahl der Schiffe um eins erhöht
            AnzSchiffe++;

            //Je nach momentaner Schiffgröße...
            switch (groesse) {
                case 2 -> {
                    //... wird die noch platzierbare Anzahl des jeweiligen Schiffes um eins verringert
                    groessen[groesse]--;

                    //... und der Text mit der neuen Anzahl upgedated
                    frame.btn_size2.setText("size 2: " + groessen[groesse]);
                }
                case 3 -> {
                    groessen[groesse]--;
                    frame.btn_size3.setText("size 3: " + groessen[groesse]);
                }
                case 4 -> {
                    groessen[groesse]--;
                    frame.btn_size4.setText("size 4: " + groessen[groesse]);
                }
                case 5 -> {
                    groessen[groesse]--;
                    frame.btn_size5.setText("size 5: " + groessen[groesse]);
                }
            }

        }
        if (groessen[groesse] == 0) {
            PickSmallestAvailableSize();
        }
    }

    /**
     * @return boolean gibt an, ob sich die Maus auf dem Spielfeld befindet
     */
    public boolean getOnfirstfield() {
        return Onfirstfield;
    }

    /**
     * Überprüft ob die Maus sich momentan auf dem Spielfeld befindet
     *
     * @param e gibt MouseEvent weiter
     * @return True: Wenn sich die Maus auf dem Spielfeld befindet
     */
    public boolean setOnfirstfield(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();


        return Onfirstfield = x > TileSize.getSizeofBorder() && x < Tile.field_size * TileSize.Tile_Size + TileSize.getSizeofBorder() && y > TileSize.getSizeofBorder() && y < TileSize.getSizeofBorder() + fieldsize * TileSize.Tile_Size;
    }

    /**
     * @return gibt zurück, auf dem wievielten Feld sich die Maus auf der X-Achse befindet
     */
    public int getPosX() {
        return PosX;
    }

    /**
     * @param posX Auf dem wievielten Feld sich die Maus auf der X-Achse befindet
     */
    public void setPosX(int posX) {
        PosX = posX;
    }

    /**
     * @return gibt zurück, auf dem wievielten Feld sich die Maus auf der Y-Achse befindet
     */
    public int getPosY() {
        return PosY;
    }

    /**
     * @param posY Auf dem wievielten Feld sich die Maus auf der Y-Achse befindet
     */
    public void setPosY(int posY) {
        PosY = posY;
    }


    /**
     * @return int[]
     * <p>
     * gibt die X- und Y-Pos des letzten Schusses der KI zurück
     */
    public int[] getRecentshot() {
        return recentshot;
    }

    /**
     * @param g Graphics Object
     *          Hier werden alle Methoden aufgerufen, welche etwas visuell darstellen.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setOpaque(false);

        //checkt ab, ob der Spieler bereits verloren hat
        if (Tile.fightstart && pf.gameover()) {
            //Gibt weiter, das nicht mehr das Spielfeld sondern der loss Screen Angezeigt werden soll
            frame.tile.Ebene.YouLost(g);
            //Schaltet die Zielhilfe aus (X und Y Anzeige über dem Spielfeld)
            //frame.Z.stopdrawing();
            frame.menuPanel.remove(frame.Z);
            frame.menuPanel.remove(frame.Turn);

            //checkt ab ob der Computer bereits verloren hat
        } else if (Tile.fightstart && pf.getEnemyShipsDestroyed() >= sumofships) {
            //Gibt weiter, das nicht mehr das Spielfeld sondern der win Screen Angezeigt werden soll
            frame.tile.Ebene.YouWin(g);
            //Schaltet die Zielhilfe aus (X und Y Anzeige über dem Spielfeld)
            frame.menuPanel.remove(frame.Z);
            frame.menuPanel.remove(frame.Turn);

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
                    hier.PokemonZeichner(g);
                } else {
                    //Zeichnet die Sprites aber alles hat eine klassische Schiff Thematik
                    hier.Schiffzeichner(g);
                }
            }

            //Feld des Spielers
            if (field == 0) {

                //Der Kampf hat noch nicht begonnen und es sind noch nicht alle Schiffe gesetz, welche gesetzt werden dürfen
                if (!Tile.isFightstart() && groessen[groesse] > 0) {

                    //Es kam zu einer Änderung
                    if (MovementHandler) {
                        //Und deswegen wird die Schiffposition neuberechnet
                        Predicted.setPrediction(PosX, PosY);
                        placeable = pf.checkShip(groesse, PosX, PosY, horizontal);

                        //Alle Änderungen wurden abgearbeitet
                        MovementHandler = false;
                    }
                    //Die Vorhersage an sich werden gezeichnet
                    Predicted.Schiffzeichner(g, placeable);
                }

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
     * setzt die Pos Variablen auf das Tile auf dem sich die Maus momentan befindet und sagt der Vorhersage,
     * ob diese sich neue Werte für die Anzeige holen muss
     *
     * @param e gibt MouseEvent weiter
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

    /**
     * Setzt das ausgewählte Schiff auf das momentan kleinstmögliche
     */
    private void PickSmallestAvailableSize() {
        for (int i = 2; i <= 5; i++) {
            if (groessen[i] != 0) {
                groesse = i;
                break;
            }
        }
    }
}

