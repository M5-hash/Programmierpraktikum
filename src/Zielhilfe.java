package src;

import javax.swing.*;
import java.awt.*;

public class Zielhilfe extends JPanel {

    Bildloader Bild = new Bildloader();
    String Zahldir = "Ich bin der String und ich bin ein Platzhalter";
    Image Zahl; //Nur ein Platzhalter, dass die IDE nicht weint
    SpielWindow spWin;
    int PosX = 5;
    int PosY = 5;
    boolean draw = true;
    JFrame frame ;
    boolean eitherfield ;
    boolean change;

    public Zielhilfe(SpielWindow Window, JFrame frame) {
        spWin = Window;
        this.frame = frame ;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (draw) {

            setOpaque(false);

            if (Tile.isFightstart()) {
                change = PosX != spWin.tile2.getPosX() || PosY != spWin.tile2.getPosY();
            } else {
                change = PosX != spWin.tile.getPosX() || PosY != spWin.tile.getPosY();
            }

            eitherfield = spWin.tile.getOnfirstfield() || spWin.tile2.getOnfirstfield() ;

            if (change && eitherfield) {
                BildRechner(g);
            }
        }

    }

    public void stopdrawing() {
        draw = false;
    }

    /**
     * @param g gibt Graphics Object weiter
     *          <p>
     *          Wandelt die int X & Y Position in ein Bilderkombination um
     */
    void BildRechner(Graphics g) {

        int NumberSize = frame.getHeight() / 20 ;

        if (Tile.isFightstart()) {
            PosX = spWin.tile2.getPosX() + 1;
            PosY = spWin.tile2.getPosY() + 1;
        } else {
            PosX = spWin.tile.getPosX() + 1;
            PosY = spWin.tile.getPosY() + 1;
        }

        int i = 0;
        int Bildercounter = 1;
        int Inbetweener = PosX;
        boolean notfinished = true;
        int displacement = 0;
        boolean secondrun = false;

        int laeufer;

        Zahl = Bild.BildLoader("src/Images/x.png");
        g.drawImage(Zahl, 0, 0, NumberSize, NumberSize, null);

        while (notfinished) {

            if (Inbetweener == 1) {
                Zahldir = "src/Images/1.png";

                Zahl = Bild.BildLoader(Zahldir);

                g.drawImage(Zahl, (NumberSize + displacement),
                        0,
                        NumberSize,
                        NumberSize, null);

                Bildercounter++ ;

            } else {

                while ((Inbetweener - Exponent(10, i)) > 0) {
                    i++;
                }

                if (secondrun) {
                    notfinished = false;
                }

                for (int j = i - 1; j >= 0; j--) {

                    laeufer = Inbetweener / (Exponent(10, j));
                    Inbetweener = Inbetweener - laeufer * Exponent(10, j);

                    if (laeufer < 10 && laeufer >= 0) {
                        Zahldir = "src/Images/" + laeufer + ".png";
                    } else if (laeufer == 10) {
                        Zahldir = "src/Images/1.png";
                        Inbetweener = 0;
                        i++;
                        j++;
                    }

                    Zahl = Bild.BildLoader(Zahldir);

                    displacement = Bildercounter++ * NumberSize;

                    g.drawImage(Zahl, displacement,
                            0,
                            NumberSize,
                            NumberSize, null);

                }
            }

            Bildercounter++;
            displacement = (Bildercounter++) * NumberSize;


            if(!secondrun){
                Zahl = Bild.BildLoader("src/Images/y.png");
                g.drawImage(Zahl, displacement, 0, NumberSize, NumberSize, null);
            }

            Inbetweener = PosY;
            secondrun = true;
            i = 0;



            if (PosY == 1) {

                Zahldir = "src/Images/1.png";

                Zahl = Bild.BildLoader(Zahldir);

                displacement = (Bildercounter) * NumberSize;

                g.drawImage(Zahl, displacement,
                        0,
                        NumberSize,
                        NumberSize, null);

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
