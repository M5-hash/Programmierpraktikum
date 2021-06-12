package src;

import src.Images.Zielhilfe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpielWindow extends JPanel {


    Wahlstation wahlstation = new Wahlstation();

    public static boolean change = false;
    public static int field_size = 10;
    public static PlayingField playingField = new PlayingField(field_size);
    public static TilePainter tile = new TilePainter(field_size);
    public static Zielhilfe Z = new Zielhilfe() ;
    String Feldvon = "Spieler"; //"GegnerKI" "GegnerMensch"


    public static void main(String[] args) {                     //Startet das Programm und erstellt das Window wir hier nur wegen dem testen benötigt

        SpielWindow Hallo = new SpielWindow();

    }

    public SpielWindow() {

        final int[] frame_height = new int[1];
        final int[] frame_width = new int[1];
        int bottom_gap = 80;

        JLayeredPane LayeredPanel = new JLayeredPane();
        JFrame frame = new JFrame("Schiffe versenken");

        frame.setSize(new Dimension(1920, 1080));
        frame.setLocation(new Point(200, 20));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        TileSize.setTile_Size(frame.getWidth() / 25);

        System.out.println("Du hast den schönen SinglePlayer Knopf berührt");


        //tile.setBounds(1000, 100, 20, 20);
        tile.setBounds(15, 15, 600, 1000);
        wahlstation.setBounds(800, 25, 700, 700);
        Z.setBounds(1600, 25, 700, 700);

        LayeredPanel.add(tile, Integer.valueOf(1));
        LayeredPanel.add(wahlstation, Integer.valueOf(1));
        LayeredPanel.add(Z,Integer.valueOf(1)) ;

        LayeredPanel.setBackground(Color.darkGray);
        LayeredPanel.setVisible(true);
        frame.add(LayeredPanel);

        frame_height[0] = frame.getHeight();
        frame_width[0] = frame.getWidth();


        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel Background = new JPanel();


                if (frame_width[0] != frame.getWidth()) {
                    TileSize.setTile_Size(frame.getWidth() / 25);

                } else if (frame_height[0] != frame.getHeight()) {
                    TileSize.setTile_Size(frame.getHeight() / 14);
                }

                LayeredPanel.setBounds(0, 0, TileSize.Tile_Size * SpielWindow.field_size, TileSize.Tile_Size * SpielWindow.field_size);
                //tile.setBounds(300, 120, TileSize.Tile_Size * SpielWindow.field_size, TileSize.Tile_Size * SpielWindow.field_size);

                repaintAll();

            }
        });
        timer.start();


    }

    void repaintAll() {

        tile.repaint(); //Der beste Command, der von der Menschheit erfunden wurde
        tile.revalidate();


//        wahlstation.repaint();
//        wahlstation.revalidate();

    }
}
