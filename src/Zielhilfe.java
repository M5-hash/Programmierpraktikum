package src;

import javax.swing.*;
import java.awt.*;

public class Zielhilfe extends JPanel {

    Bildloader Bild = new Bildloader();
    String Zahldir = "Ich bin der String und ich bin ein Platzhalter";
    Image Zahl; //Nur ein Platzhalter, dass die IDE nicht weint
    static int PosX  = 5;
    static int PosY = 5;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setOpaque(false);

        boolean change = PosX != TilePainter.getPosX() ||PosY != TilePainter.getPosY() ;

        if(change && TilePainter.getOnfirstfield()){
            BildRechner(g);
        }

    }

    /**
     * @param g     gibt Graphics Object weiter
     *
     *              Wandelt die int X & Y Position in ein Bilderkombination um
     */
    void BildRechner( Graphics g) {

        PosX = TilePainter.getPosX() + 1;
        PosY = TilePainter.getPosY() + 1;

        int i = 0;
        int Bildercounter = 1 ;
        int Inbetweener = PosX;
        boolean notfinished = true;
        int displacement = 0;
        boolean secondrun = false;

        int laeufer ;

        Zahl = Bild.BildLoader("src/Images/PokX.png");
        g.drawImage(Zahl,0,0,TileSize.Tile_Size, TileSize.Tile_Size, null);

        Zahl = Bild.BildLoader("src/Images/PokY.png");
        g.drawImage(Zahl,0,TileSize.Tile_Size,TileSize.Tile_Size, TileSize.Tile_Size, null);

        while (notfinished) {

            if (Inbetweener == 1) {
                Zahldir = "src/Images/1.png";

                Zahl = Bild.BildLoader(Zahldir);

                g.drawImage(Zahl, (TileSize.Tile_Size + displacement),
                        0,
                        TileSize.Tile_Size,
                        TileSize.Tile_Size, null);

            } else {

                while ((Inbetweener - Exponent(10, i)) > 0) {
                    i++;
                }

                int h = i ;

                if (secondrun) {
                    notfinished = false;
                }

                for (int j = i - 1; j >= 0; j--) {

                    laeufer = Inbetweener / (Exponent(10, j));
                    Inbetweener = Inbetweener - laeufer * Exponent(10, j);

                    switch (laeufer) {

                        case 0:
                            Zahldir = "src/Images/0.png";
                            break;

                        case 1:
                            Zahldir = "src/Images/1.png";
                            break;

                        case 2:
                            Zahldir = "src/Images/2.png";
                            break;

                        case 3:
                            Zahldir = "src/Images/3.png";
                            break;

                        case 4:
                            Zahldir = "src/Images/4.png";
                            break;

                        case 5:
                            Zahldir = "src/Images/5.png";
                            break;

                        case 6:
                            Zahldir = "src/Images/6.png";
                            break;

                        case 7:
                            Zahldir = "src/Images/7.png";
                            break;

                        case 8:
                            Zahldir = "src/Images/8.png";
                            break;

                        case 9:
                            Zahldir = "src/Images/9.png";
                            break;

                        case 10:
                            Zahldir = "src/Images/1.png" ;
                            Inbetweener = 0 ;
                            i++;
                            j++;
                            break ;

                        default:
                            break;

                    }

                    Zahl = Bild.BildLoader(Zahldir);

                    displacement = Bildercounter++ * TileSize.Tile_Size ;

                    int scndrow = secondrun ? 1 : 0 ;

                    g.drawImage(Zahl, displacement,
                            scndrow * TileSize.Tile_Size,
                            TileSize.Tile_Size,
                            TileSize.Tile_Size, null);

                }
            }

            Inbetweener = PosY;
            secondrun = true;
            Bildercounter = 1 ;
            i = 0;

            if(PosY == 1){

                Zahldir = "src/Images/1.png";

                Zahl = Bild.BildLoader(Zahldir);

                g.drawImage(Zahl, TileSize.Tile_Size,
                        TileSize.Tile_Size,
                        TileSize.Tile_Size,
                        TileSize.Tile_Size, null);

                break;
            }
        }
    }


    public int Exponent(int x, int n) {

        int dummy = x;
        x = 1;
        for (int i = 0; i < n; i++) {
            x = x * dummy;
        }
        return x;
    }
}
