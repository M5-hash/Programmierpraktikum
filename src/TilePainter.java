package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TilePainter extends JPanel {

    public static int groesse = 3;
    public static boolean horizontal = true;

    private final Tile Ebene;
    SchiffPainter h = new SchiffPainter();

    public TilePainter(int x) {
        Ebene = new Tile(x);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX();
                    int y = e.getY();

                    int xRightEnd = Tile.side_gapl + SpielWindow.field_size * TileSize.Tile_Width;
                    int halfheightField = (SpielWindow.field_size * TileSize.Tile_Height) / 2;
                    int halfheightBox = 4 * TileSize.Tile_Height;
                    int fieldwidth = 3 * TileSize.Tile_Width + TileSize.Tile_Width / 2;
                    int FieldBox_gap = Math.max(60, 120 % TileSize.Tile_Width);

                    /*Die Position auf dem Feld wird durch diese Funktion berechnet, anstatt das nur die aktuelle Position in Pixeln zurückgegeben wird.
                     *
                     * Erzeugt dabei die Parameter yFeld und xFeld
                     *
                     * y-Feld: (y - top_gap) / TileSize.tile_height
                     * x-Feld: (x - sidegapl) / TileSize.tile_width
                     *
                     * */

                    if (x > Tile.side_gapl && x < Tile.field_size * TileSize.Tile_Width + Tile.side_gapl && y > Tile.top_gap && y < Tile.top_gap + x * TileSize.Tile_Height) {
                        int yFeld = ((y - Tile.top_gap) / TileSize.Tile_Height);
                        int xFeld = ((x - Tile.side_gapl) / TileSize.Tile_Width);

                        System.out.println("Die Position auf der Y-Achse beträgt:" + yFeld + "\nDie Postion auf der X-Achse beträgt:" + xFeld);

                        SpielWindow.change = SpielWindow.playingField.setShip(groesse, xFeld, yFeld, horizontal);                                                            //Lässt die Schiffzeichnen Methode wissen, on es zu einer Änderung gekommen ist
                    } else {

                        if (x >= xRightEnd + FieldBox_gap + TileSize.Tile_Width / 2                                                                 //Bereich in dem man klicken muss um sein Schiff auf die Groesse 5 zu setzen
                                && x <= xRightEnd + FieldBox_gap + TileSize.Tile_Width / 2 + TileSize.Tile_Width
                                && y >= Tile.top_gap + halfheightField - halfheightBox + TileSize.Tile_Height / 2
                                && y <= Tile.top_gap + halfheightField - halfheightBox + TileSize.Tile_Height / 2 + 5 * TileSize.Tile_Height) {
                            groesse = 5;
                            System.out.println("Die Größe wurde auf 5 gesetzt");
                        }

                        if (x >= xRightEnd + FieldBox_gap + TileSize.Tile_Width * 2                                                                 //Bereich in dem man klicken muss um sein Schiff auf die Groesse 4 zu setzen
                                && x <= xRightEnd + FieldBox_gap + TileSize.Tile_Width * 2 + TileSize.Tile_Width
                                && y >= Tile.top_gap + halfheightField - halfheightBox + TileSize.Tile_Height / 2
                                && y <= Tile.top_gap + halfheightField - halfheightBox + TileSize.Tile_Height / 2 + 4 * TileSize.Tile_Height) {
                            groesse = 4;
                            System.out.println("Die Größe wurde auf 4 gesetzt");
                        }

                        if(x >= xRightEnd + FieldBox_gap + TileSize.Tile_Width * 2                                                                 //Bereich in dem man klicken muss um sein Schiff auf die Groesse 3 zu setzen
                                && x <= xRightEnd + FieldBox_gap + TileSize.Tile_Width * 2 + TileSize.Tile_Width
                                && y >= Tile.top_gap + halfheightField + halfheightBox - (TileSize.Tile_Height * 7) / 2
                                && y <= Tile.top_gap + halfheightField + halfheightBox - (TileSize.Tile_Height * 7) / 2 + 3 * TileSize.Tile_Height){
                                groesse = 3 ;
                                System.out.println("Die Größe wurde auf 3 gesetzt");
                        }

                        if(x >= xRightEnd + FieldBox_gap + TileSize.Tile_Width / 2                                                                 //Bereich in dem man klicken muss um sein Schiff auf die Groesse 2 zu setzen
                                && x <= xRightEnd + FieldBox_gap + TileSize.Tile_Width / 2 + TileSize.Tile_Width
                                && y >= Tile.top_gap + halfheightField + halfheightBox - (TileSize.Tile_Height * 5) / 2
                                && y <= Tile.top_gap + halfheightField + halfheightBox - (TileSize.Tile_Height * 5) / 2 + 2 * TileSize.Tile_Height){
                                groesse = 2 ;
                                System.out.println("Die Größe wurde auf 2 gesetzt") ;
                        }
                    }

                    //xRightEnd + FieldBox_gap + TileSize.Tile_Width / 2

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
                }
            }
        });
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
            h.Schiffzeichner(g);
            h.Wahlstation(g);

        }


    }


}
