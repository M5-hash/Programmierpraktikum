package src;

import javax.swing.*;
import java.awt.*;

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

        int[][] SchiffWahl;


        //int[] Usable =
        Graphics2D g2 = (Graphics2D) g;

        int xRightEnd = Tile.side_gapl + SpielWindow.field_size * TileSize.Tile_Size;
        int halfheightField = (SpielWindow.field_size * TileSize.Tile_Size) / 2;
        int Boxheight = 8 * TileSize.Tile_Size;
        int fieldwidth = 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2;
        int FieldBox_gap = Math.max(60, 120 % TileSize.Tile_Size);

        /*int x1 = TileSize.xRightEnd + TileSize.FieldBox_gap;
        int y1 = Tile.top_gap + TileSize.halfheightField + TileSize.halfBoxheight;
        int x2 = x1 + TileSize.fieldwidth;
        int y2 = Tile.top_gap + TileSize.halfheightField - TileSize.halfBoxheight;

        g2.drawLine(x1, y1, x2, y1);
        g2.drawLine(x1, y2, x2, y2);
        g2.drawLine(x1, y1, x1, y2);
        g2.drawLine(x2, y2, x2, y1);*/


//        g2.drawLine(xRightEnd + FieldBox_gap, Tile.top_gap + halfheightField + halfBoxheight, xRightEnd + FieldBox_gap + fieldwidth, Tile.top_gap + halfheightField + halfBoxheight);
//        g2.drawLine(xRightEnd + FieldBox_gap, Tile.top_gap + halfheightField - halfBoxheight, xRightEnd + FieldBox_gap + fieldwidth, Tile.top_gap + halfheightField - halfBoxheight);
//        g2.drawLine(xRightEnd + FieldBox_gap, Tile.top_gap + halfheightField + halfBoxheight, xRightEnd + FieldBox_gap, Tile.top_gap + halfheightField - halfBoxheight);
//        g2.drawLine(xRightEnd + FieldBox_gap + fieldwidth, Tile.top_gap + halfheightField - halfBoxheight, xRightEnd + FieldBox_gap + fieldwidth, Tile.top_gap + halfheightField + halfBoxheight);

        g2.drawLine(0, 0, fieldwidth, 0); //Linie oben
        g2.drawLine(0, Boxheight, fieldwidth, Boxheight); //Linie unten
        g2.drawLine(0, 0, 0, Boxheight); //Linie links
        g2.drawLine(fieldwidth, 0, fieldwidth, Boxheight); //Linie rechts

        Image Schiff = Bild.BildLoader("src/Images/Vorne32false.png");

        //switch[] (Usable)

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

    }
}
