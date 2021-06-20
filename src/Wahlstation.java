package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Wahlstation extends JPanel {

    Bildloader Bild = new Bildloader();

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (SchiffPainter.ready) {
            if (!Tile.fightstart) {
                Wahlstationpainter(g);
            }
        }
    }

    public void Wahlstationpainter(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        int Boxheight = 8 * TileSize.Tile_Size;
        int fieldwidth = 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2;

        Image Bckgrnd = Bild.BildLoader("src/Images/BorderVert.png") ;

        g.drawImage(Bckgrnd,0,0,fieldwidth ,Boxheight ,null ) ;

        g2.drawLine(0, 0, fieldwidth, 0); //Linie oben
        g2.drawLine(0, Boxheight, fieldwidth, Boxheight); //Linie unten
        g2.drawLine(0, 0, 0, Boxheight); //Linie links
        g2.drawLine(fieldwidth, 0, fieldwidth, Boxheight); //Linie rechts

        Image Schiff = Bild.BildLoader("src/Images/Vorne32false.png");


        g.drawImage(Schiff, TileSize.Tile_Size / 2,
                TileSize.Tile_Size / 2,
                TileSize.Tile_Size,
                TileSize.Tile_Size, null);

        g.drawImage(Schiff, TileSize.Tile_Size * 2,
                TileSize.Tile_Size / 2,
                TileSize.Tile_Size,
                TileSize.Tile_Size, null);

        g.drawImage(Schiff, TileSize.Tile_Size / 2,
                Boxheight - (TileSize.Tile_Size * 5) / 2,
                TileSize.Tile_Size,
                TileSize.Tile_Size, null);

        g.drawImage(Schiff, TileSize.Tile_Size * 2,
                Boxheight - (TileSize.Tile_Size * 7) / 2,
                TileSize.Tile_Size,
                TileSize.Tile_Size, null);

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

                        if (x >= TileSize.Tile_Size / 2                                                                 //Bereich in dem man klicken muss um sein Schiff auf die Groesse 5 zu setzen
                                && x <= TileSize.Tile_Size / 2 + TileSize.Tile_Size
                                && y >= TileSize.Tile_Size / 2
                                && y <= TileSize.Tile_Size / 2 + 5 * TileSize.Tile_Size) {
                            TilePainter.groesse = 5;
                            System.out.println("Die Größe wurde auf 5 gesetzt");
                        }

                        if (x >= TileSize.Tile_Size * 2                                                                 //Bereich in dem man klicken muss um sein Schiff auf die Groesse 4 zu setzen
                                && x <= TileSize.Tile_Size * 2 + TileSize.Tile_Size
                                && y >= TileSize.Tile_Size / 2
                                && y <= TileSize.Tile_Size / 2 + 4 * TileSize.Tile_Size) {
                            TilePainter.groesse = 4;
                            System.out.println("Die Größe wurde auf 4 gesetzt");
                        }

                        if (x >= TileSize.Tile_Size * 2                                                                 //Bereich in dem man klicken muss um sein Schiff auf die Groesse 3 zu setzen
                                && x <= TileSize.Tile_Size * 2 + TileSize.Tile_Size
                                && y >= Boxheight - (TileSize.Tile_Size * 7) / 2
                                && y <= Boxheight - (TileSize.Tile_Size * 7) / 2 + 3 * TileSize.Tile_Size) {
                            TilePainter.groesse = 3;
                            System.out.println("Die Größe wurde auf 3 gesetzt");
                        }

                        if (x >= TileSize.Tile_Size / 2                                                                 //Bereich in dem man klicken muss um sein Schiff auf die Groesse 2 zu setzen
                                && x <= TileSize.Tile_Size / 2 + TileSize.Tile_Size
                                && y >= Boxheight - (TileSize.Tile_Size * 5) / 2
                                && y <= Boxheight - (TileSize.Tile_Size * 5) / 2 + 2 * TileSize.Tile_Size) {
                            TilePainter.groesse = 2;
                            System.out.println("Die Größe wurde auf 2 gesetzt");
                        }

                    }
                }
            }

        });

    }

}
