package src;

//import src.Images.Zielhilfe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static src.TileSize.xRightEnd;

public class TilePainter extends JPanel implements MouseMotionListener {

    public static int groesse = 3;
    public static boolean horizontal = true;
    public static int AnzSchiffe = 0;
    public static int PosX = 0 ;
    public static int PosY = 0 ;
    public static boolean Onfirstfield = false ;

    public static boolean getOnfirstfield() {
        return Onfirstfield;
    }

    public static int getPosX() {
        return PosX;
    }

    public static int getPosY() {
        return PosY;
    }

    public static void setPosX(int posX) {
        PosX = posX;
    }

    public static void setPosY(int posY) {
        PosY = posY;
    }


    private final Tile Ebene;
    SchiffPainter hier ;


    public TilePainter(int Feldgroesse, String Feldvon) {
        Ebene = new Tile(Feldgroesse, Feldvon);
        hier = new SchiffPainter(Feldvon);

        if(Feldvon.equals("Spieler")) {

            addMouseMotionListener(this);

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

                        if (!Tile.fightstart) {

                            setOnfirstfield(e);

                            if (Onfirstfield) {
                                int yFeld = (y / TileSize.Tile_Size);
                                int xFeld = (x / TileSize.Tile_Size);

                                System.out.println("Die Position auf der Y-Achse beträgt:" + yFeld + "\nDie Postion auf der X-Achse beträgt:" + xFeld);

                                if (SpielWindow.change = SpielWindow.playingField.setShip(groesse, xFeld, yFeld, horizontal)) {
                                    AnzSchiffe++;

                                }
                                //Lässt die Schiffzeichnen Methode wissen, on es zu einer Änderung gekommen ist
                            }
                        } else {
                            System.out.println("Ich bin in die else gekommen, sonst passiert hier aber noch wenig");
                            System.out.println(TileSize.getDisplacement());
                            if (x > TileSize.getDisplacement() + Tile.side_gapl && x < Tile.field_size * TileSize.Tile_Size + Tile.side_gapl + TileSize.getDisplacement() && y > Tile.top_gap && y < Tile.top_gap + x * TileSize.Tile_Size) {
                                try {
                                    SpielWindow.playingField.isShot(x, y);
                                    System.out.println("Es wurde geschossen auf X: " + x + " Y: " + y);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }

                        //xRightEnd + TileSize.getFieldBox_gap() + TileSize.Tile_Width / 2

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
    }

    /**
     * @param g Man brauch für nahezu alles ein Object des Typs Graphics, deswegen gibt es hier eins.
     *          <p>
     *          Hier werden alle Methoden aufgerufen, welche etwas Zeichnen.
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Ebene.DrawLayer(g);
        if (SchiffPainter.ready) {
            hier.Schiffzeichner(g);
            //Zielhilfe Z = new Zielhilfe(g) ;
            if (!Tile.fightstart) {

            }


        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    public void setOnfirstfield(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();


        Onfirstfield = x > 0 && x < Tile.field_size * TileSize.Tile_Size && y > 0 && y < SpielWindow.field_size * TileSize.Tile_Size;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

//        setOnfirstfield(e);
//        if(Onfirstfield){
//            setPosX(e.getX() / TileSize.Tile_Size) ;
//            setPosY(e.getY() / TileSize.Tile_Size) ;
//
//            System.out.println("mouseMoved wurde aufgerufen");
//        }
//            if (Onfirstfield) {
//                //Hellseher(xPos, yPos);
//
//            }
//
//
        }
}

