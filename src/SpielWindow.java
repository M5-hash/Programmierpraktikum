package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SpielWindow extends JPanel {

    public static PlayingField playingField = new PlayingField(10) ;

    public static boolean horizontal = true;
    public static int field_height = 10;
    public static int field_width = 10;
    public static int[][] Spielfeld = new int[field_height][field_width];
    public static int groesse = 3;                                                 //Standardmäßig ist ein Schiff mit der größe 3 ausgewählt


    public static void main(String[] args) {                     //Startet das Programm und erstellt das Window

        final int[] frame_height = new int[1];
        final int[] frame_width = new int[1];
        int bottom_gap = 80;

        JFrame frame = new JFrame("Schiffe versenken");
        TilePainter tile = new TilePainter(field_height, field_width);
        frame.setSize(new Dimension(1600, 1200));
        frame.setLocation(new Point(1400, 20));
        frame.getContentPane().add(tile);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame_height[0] = frame.getHeight();
        frame_width[0] = frame.getWidth();

        Timer timer = new Timer(55, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (frame_height[0] != frame.getHeight() || frame_width[0] != frame.getWidth()) {
                    if (frame.getWidth() > frame.getHeight()) {
                        frame_height[0] = frame.getHeight() - 30;
                        frame_width[0] = (frame.getHeight() - 100);
                        //frame.setSize(frame.getHeight(), frame.getHeight() + bottom_gap);

                    } else if (frame.getWidth() < frame.getHeight()) {
                        frame_height[0] = frame.getWidth() - 30;
                        frame_width[0] = frame.getWidth() - 100;
                        //frame.setSize(frame.getWidth(), frame.getWidth());
                    }

                    TileSize.setTile_Height(Math.min(frame_height[0], frame_width[0]) / field_height);
                    TileSize.setTile_Width(Math.min(frame_height[0], frame_width[0]) / field_width);


                }
                tile.repaint();                                                                             //Der beste Command, der von der Menschheit erfunden wurde
            }
        });
        timer.start();
        //frame.setSize(TileSize.Tile_Height * field_height + 120 , TileSize.Tile_Height * field_height + 120 + 20/10);
        frame.getContentPane().add(new SpielWindow());
    }

    public SpielWindow() {

        /*
         * Berechnet des Feld, welches vom Nutzer geklickt wurde, dieses wird dann mit der Groesse und Ausrichtung des aktuellen Schiffs an die Logik weitergeleitet
         *
         * Die Weiterleitung geschieht mit (groesse, X-Wert, Y-Wert, horizontal)
         *
         * */

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

                    if(x > Tile.side_gapl && x < field_width * TileSize.Tile_Width + Tile.top_gap && y > Tile.top_gap && y < Tile.top_gap + field_height * TileSize.Tile_Height){
                        int yFeld = ((y - Tile.top_gap) / TileSize.Tile_Height);
                        int xFeld = ((x - Tile.side_gapl) / TileSize.Tile_Width);

                        System.out.println("Die Position auf der Y-Achse beträgt:" + yFeld + "\nDie Postion auf der X-Achse beträgt:" + xFeld);

                        System.out.println(playingField.setShip(groesse,xFeld, yFeld, horizontal));
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
                }
            }
        });


    }
}
