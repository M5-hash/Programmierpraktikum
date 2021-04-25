package Feld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SpielWindow extends JPanel {

    boolean vertikal = true ;


    public static void main(String[] args) {                     //Startet das Programm und erstellt das Window

        final int[] frame_height = new int[1];
        final int[] frame_width = new int[1];
        int field_height = 10;
        int field_width = 10;
        int bottom_gap = 80;

        JFrame frame = new JFrame("Schiffe versenken");
        TilePainter tile = new TilePainter(field_height, field_width);

        frame.setSize(new Dimension(1000, 1000));
        frame.setLocation(new Point(1400, 20));
        frame.getContentPane().add(tile);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame_height[0] = frame.getHeight();
        frame_width[0] = frame.getWidth();

        Timer timer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (frame_height[0] != frame.getHeight() || frame_width[0] != frame.getWidth()) {
                    if (frame.getHeight() < 1000 && frame.getWidth() > frame.getHeight()) {
                        frame_height[0] = frame.getHeight() - 30;
                        frame_width[0] = frame.getHeight() - 100;
                        frame.setSize(frame.getHeight(), frame.getHeight() + bottom_gap);

                    } else if (frame.getWidth() < 1600 && frame.getWidth() < frame.getHeight()) {
                        frame_height[0] = frame.getWidth() - 30;
                        frame_width[0] = frame.getWidth() - 100;
                        frame.setSize(frame.getWidth(), frame.getWidth());
                    }

                    TileSize.setTile_Height(Math.min(frame_height[0], frame_width[0]) / field_height);
                    TileSize.setTile_Width(Math.min(frame_height[0], frame_width[0]) / field_width);


                }
                tile.repaint();                                                                             //Der beste Command, der von der Menschheit erfunden wurde
            }
        });
        timer.start();
        frame.getContentPane().add(new SpielWindow());
    }

    public SpielWindow() {



        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX();
                    int y = e.getY();
                    /*Der Sender an dem Julian seine Logik
                     *
                     * y-Feld: (y - top_gap) / TileSize.tile_height
                     * x-Feld: (x - sidegapl) / TileSize.tile_width
                     *
                     * */
                    int xFeld = ((y - Tile.top_gap) / TileSize.Tile_Height) + 1;
                    int yFeld = ((x - Tile.side_gapl) / TileSize.Tile_Width) + 1;

                    System.out.println("Die Position auf der Y-Achse beträgt:" + yFeld + "\nDie Postion auf der X-Achse beträgt:" + xFeld);

                    int [][] Gamer = {{0,0,0,0},{0,0,0,0},{0,0,0,0}} ;

                    SchiffPainter Hans = new SchiffPainter(Gamer);
                    Hans.Schiffplatzieren(xFeld,yFeld,2,vertikal) ;
                }

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               if(e.getButton() == MouseEvent.BUTTON3) {
                   vertikal = !vertikal;
               }
            }
        });


    }
}
