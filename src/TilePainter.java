package src;

//import src.Images.Zielhilfe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

import static src.config.*;

public class TilePainter extends JPanel implements MouseMotionListener {

    private static int groesse = 3;
    private static int PosX = 0;
    private static int PosY = 0;
    public static boolean horizontal = true;
    public static int AnzSchiffe = 0;
    int[] groessen = {0, 0, size2, size3, size4, size5};
    /**
     * @param Feldgroesse gibt Groesse des Feldes vor
     * @param Feldvon     gibt an für wen des Feld ist
     *                    <p>
     *                    Konstruktor für TilePainter, welches das Felder an sich durch Tile aufruft
     *                    <p>
     *                    Übernimmt die Inputs des Spielers gibt diese wenn nötig an andere Methoden weiter
     */
    public TilePainter(int Feldgroesse, String Feldvon) {
        Ebene = new Tile(Feldgroesse, Feldvon);
        field = Feldvon;
        hier = new SpritePainter(Feldvon);


        if (Feldvon.equals("Spieler")) {
            Predicted = new SpritePainter("Vorhersage");
        }


        if (Feldvon.equals("Spieler")) addMouseMotionListener(this);


        //TODO sichergehen, dass der der getroffen hat nochmal schießen darf und nicht zum anderen Spieler gewechselt wird, ANzeig der treffer auf Feld Spieler komisch / falsch
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

                        if (!Tile.fightstart && Feldvon.equals("Spieler")) {

                            System.out.println("Die Position auf der Y-Achse beträgt:" + yFeld + "\nDie Postion auf der X-Achse beträgt:" + xFeld);

                            if (!deleting && !Tile.isFightstart()) {
                                if (groessen[groesse] > 0 && SpielWindow.getPlayingField().setShip(groesse, xFeld, yFeld, horizontal)) {
                                    SpielWindow.change = true;
                                    AnzSchiffe++;

                                    switch (groesse) {
                                        case 2 -> size2--;
                                        case 3 -> size3--;
                                        case 4 -> size4--;
                                        case 5 -> size5--;
                                    }

                                } else {
                                    SpielWindow.change = false;
                                }


                                groessen[groesse]--;
                                System.out.println(groessen[groesse]);


                            }
                            if (!Tile.isFightstart() && deleting) {
                                try {
                                    SpielWindow.getPlayingField().deleteShip(xFeld, yFeld);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }

                        if (Tile.isFightstart()) {

                            if (Feldvon.equals("GegnerKI")) {

                                try {
                                    if (SpielWindow.getPlayingField().getFieldEnemy()[yFeld][xFeld] == 0) {

                                        int TrefferSpieler = SpielWindow.getCom().isShot(xFeld, yFeld);
                                        SpielWindow.getPlayingField().didHit(TrefferSpieler, xFeld, yFeld);
                                        if (TrefferSpieler == 0) {
                                            int[] Feld = SpielWindow.getCom().doNextShot();
                                            SpielWindow.getCom().didHit(SpielWindow.getPlayingField().isShot(Feld[0], Feld[1]));

                                        }

                                        System.out.println(Arrays.deepToString(SpielWindow.getPlayingField().getField()).replace("]", "]\n"));

//                                    System.out.println(Arrays.deepToString(SpielWindow.Com.pf.getFieldEnemy()).replace("]", "]\n"));
//                                    System.out.println(Arrays.deepToString(SpielWindow.getPlayingField().getFieldEnemy()).replace("]", "]\n"));
                                    }
                                    //mit getPlayingField().getGameOver kann ich rausfinden wer verloren hat für wen true --> hat verloren

                                    //SpielWindow.Com.doNextShot();

                                    System.out.println("Es wurde geschossen auf X: " + xFeld + " Y: " + yFeld);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                            //if(Feldvon.equals("GegnerMensch") ){ //&& Com_base.getmyturn()


                                String xString = xFeld + " " ;
                                String yString = "" + yFeld ;

                                System.out.println("shot " + xString + yString );

                                //Com_base.send("shot " + xString + yString ) ;
                                //Com_base.setTurn(false) ;


                            //}

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
    public static boolean Onfirstfield = false;
    private final Tile Ebene;

    public static int getGroesse() {
        return groesse;
    }
    String field;
    boolean deleting;
    SpritePainter hier;
    SpritePainter Predicted;
    boolean placeable = false;
    boolean MovementHandler;

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

    public static void setPosX(int posX) {
        PosX = posX;
    }

    public static int getPosY() {
        return PosY;
    }

    public static void setPosY(int posY) {
        PosY = posY;
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
        hier.Schiffzeichner(g);
        if (SpritePainter.ready && field.equals("Spieler")) {

            if (MovementHandler) {
                Predicted.setPrediction(PosX, PosY);
                placeable = SpielWindow.getPlayingField().checkShip(groesse, PosX, PosY, horizontal);
                Predicted.Schiffzeichner(g, placeable);
            } else {
                Predicted.Schiffzeichner(g, placeable);
            }
            hier.Pokemonpicker(g);
            MovementHandler = false;

            //}


        }

    }

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


//            if(deleting){
//                int[][] fieldcheck = SpielWindow.getPlayingField().getField();
//
//                if(fieldcheck[PosX][PosY] != 0){
//                    Predicted.changetored(PosX, PosY);
//                }
//            }

            MovementHandler = true;
        }
    }

}

