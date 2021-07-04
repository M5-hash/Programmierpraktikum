package src;

import javax.swing.*;
import java.awt.*;

/**
 * Zeigt die aktuelle X- und Y-Position der Maus dem Nutzer an
 */
public class Zielhilfe extends JPanel {

    /**
     * Bildloader Object, welches es ermöglicht Bilder zu laden
     */
    Bildloader Bild = new Bildloader();
    /**
     * String, welcher die Queldatei der Bilder/Zahlen angibt
     */
    String Zahldir;
    /**
     * Bilddatei, in welchen die Zahlen gespeichert werden
     */
    Image Zahl;
    /**
     *
     */
    SpielWindow spWin;
    /**
     * X-Position des Feldes auf dem sich die Maus befindet
     */
    int PosX = 5;
    /**
     * Y-Position des Feldes auf dem sich die Maus befindet
     */
    int PosY = 5;
    /**
     * Fenster auf welche sich die Zielhilfe befindet
     */
    JFrame frame;
    /**
     * Gibt an ob sich die Maus auf einem der Beiden Spielfelder befindet
     */
    boolean eitherfield;
    /**
     * Gibt an, ob es zu einer Änderung kam, welche dargestellt werden muss
     */
    boolean change;

    /**
     * @param Window SpielWindow Object
     * @param frame  JFrame Object
     *               <p>
     *               Zielhilfe stellt die aktuelle Position der Maus dar
     */
    public Zielhilfe(SpielWindow Window, JFrame frame) {
        spWin = Window;
        this.frame = frame;
    }


    /**
     * @param g Graphics Object
     *          <p>
     *          Wenn das Spiel bereits vorbei ist, dann wird die Zeilhilfe nicht gebraucht/gezeichnet.
     *          Überprüft ob es zu einer Änderung in der Maus Position kam und die, wenn ja wird die Zielhilfe neu gezeichnet.
     *          Die Position wird von dem momentan verwendeten Feld bezogen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        setOpaque(false);

        if (Tile.isFightstart()) {
            change = PosX != spWin.tile2.getPosX() || PosY != spWin.tile2.getPosY();
            eitherfield = spWin.tile2.getOnfirstfield();
        } else {
            change = PosX != spWin.tile.getPosX() || PosY != spWin.tile.getPosY();
            eitherfield = spWin.tile.getOnfirstfield();
        }

        if (change && eitherfield) {
            BildRechner(g);
        }


    }


    /**
     * @param g gibt Graphics Object weiter
     *          <p>
     *          Wandelt die int X und Y Position in eine Bilderkombination um, welche dann leserlich von der GUI dargestellt werden kann
     */
    void BildRechner(Graphics g) {

        int NumberSize = frame.getHeight() / 20;

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

                Bildercounter++;

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


            if (!secondrun) {
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


    /**
     * @param x int --> Basis der Potenz, welche berechnet werden soll
     * @param n int --> Exponent der Potenz, welche berechnet werden soll
     * @return int berechnete Potenz
     */
    public int Exponent(int x, int n) {

        int dummy = x;
        x = 1;
        for (int i = 0; i < n; i++) {
            x = x * dummy;
        }
        return x;
    }
}
