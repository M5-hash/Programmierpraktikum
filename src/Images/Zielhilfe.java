package src.Images;

import src.Bildloader;
import src.SpielWindow;
import src.TileSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Zielhilfe extends JPanel {

    Bildloader Bild = new Bildloader();
    String Zahldir = "Ich bin der String und ich bin ein Platzhalter";
    Image Zahl; //Nur ein Platzhalter, dass die IDE nicht weint

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SpielWindow.tile.getOnfirstfield()) {

                    System.out.println("Die paintComponent wurde aufgerufen");

                    int PosX = SpielWindow.tile.getPosX();
                    int PosY = SpielWindow.tile.getPosY();

                    System.out.println(PosX + PosY);

                    int i = 0;
                    int Inbetweener = PosX;
                    boolean notfinished = true;
                    int displacement = 0;
                    boolean secondrun = false;

                    int laeufer = 0;

                    while (notfinished) {

                        //System.out.println(Inbetweener);

                        if (Inbetweener == 1) {
                            Zahldir = "src/Image/1.jpg";
                        } else {

                            while ((Inbetweener - Exponent(10, i)) > 0) {
                                i++;
                            }

                            if (secondrun) {
                                notfinished = false;
                                displacement = i * TileSize.Tile_Size;
                            }

                            for (int j = i - 1; j >= 0; j--) {

                                laeufer = Inbetweener / (Exponent(10, j));
                                Inbetweener = Inbetweener - laeufer * Exponent(10, j);

                                switch (laeufer) {

                                    case 0:
                                        Zahldir = "src/Image/0.jpg";
                                        break;

                                    case 1:
                                        Zahldir = "src/Image/0.jpg";
                                        break;

                                    case 2:
                                        Zahldir = "src/Image/2.jpg";
                                        break;

                                    case 3:
                                        Zahldir = "src/Image/3.jpg";
                                        break;

                                    case 4:
                                        Zahldir = "src/Image/4.jpg";
                                        break;

                                    case 5:
                                        Zahldir = "src/Image/5.jpg";
                                        break;

                                    case 6:
                                        Zahldir = "src/Image/6.jpg";
                                        break;

                                    case 7:
                                        Zahldir = "src/Image/7.jpg";
                                        break;

                                    case 8:
                                        Zahldir = "src/Image/8.jpg";
                                        break;

                                    case 9:
                                        Zahldir = "src/Image/9.jpg";
                                        break;

                                    default:

                                        //Zahldir = "src/Image/Stop";
                                        break;


                                }
                            }


                            Zahl = Bild.BildLoader(Zahldir);

                            g.drawImage(Zahl, (TileSize.Tile_Size + displacement),
                                    0,
                                    TileSize.Tile_Size,
                                    TileSize.Tile_Size, null);

                        }

                        if (!secondrun) {
                            System.out.println("\n Und jetzt kommt das Y: \n \n \n ");
                        }

                        Inbetweener = PosY;
                        secondrun = true;


                        i = 0;
                        laeufer = 0;
                    }

                }


            }
        });

        timer.start();
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
