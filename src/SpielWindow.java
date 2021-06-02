package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpielWindow extends JPanel {

    private static JButton Singleplayer = new JButton("SinglePlayer");
    private JPanel panel1;
    private static JButton Multiplayer = new JButton("MultiPlayer");

    public static PlayingField playingField = new PlayingField(10);
    public static boolean change = false;
    public static int field_size = 10;
    //Standardmäßig ist ein Schiff mit der größe 3 ausgewählt


    public static void main(String[] args) {                     //Startet das Programm und erstellt das Window

        /*final int[] frame_height = new int[1];
        final int[] frame_width = new int[1];
        int bottom_gap = 80;


        JFrame frame = new JFrame("Schiffe versenken");

        frame.setSize(new Dimension(1600, 1200));
        frame.setLocation(new Point(1400, 20));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(Singleplayer);


        Singleplayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Du hast den schönen SinglePlayer Knopf berührt");
                TilePainter tile = new TilePainter(field_size);
                frame.remove(Singleplayer);
                frame.getContentPane().add(tile);
                frame_height[0] = frame.getHeight();
                frame_width[0] = frame.getWidth();


                Timer timer = new Timer(55, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        //System.out.println("Der Timer wurde gestartet");

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

                            TileSize.setTile_Height(Math.min(frame_height[0], frame_width[0]) / field_size);
                            TileSize.setTile_Width(Math.min(frame_height[0], frame_width[0]) / field_size);


                        }
                        tile.repaint(); //Der beste Command, der von der Menschheit erfunden wurde
                        tile.revalidate();
                    }
                });
                //frame.getContentPane().add(new SpielWindow());
                timer.start();
                //frame.setSize(TileSize.Tile_Height * field_size + 120 , TileSize.Tile_Height * field_size + 120 + 20/10);

            }
        });*/


        SpielWindow Hallo = new SpielWindow() ;

    }

    public SpielWindow() {

        final int[] frame_height = new int[1];
        final int[] frame_width = new int[1];
        int bottom_gap = 80;


        JFrame frame = new JFrame("Schiffe versenken");

        frame.setSize(new Dimension(1600, 1200));
        frame.setLocation(new Point(1400, 20));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(Singleplayer);


        Singleplayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Du hast den schönen SinglePlayer Knopf berührt");
                TilePainter tile = new TilePainter(field_size);
                frame.remove(Singleplayer);
                frame.getContentPane().add(tile);
                frame_height[0] = frame.getHeight();
                frame_width[0] = frame.getWidth();


                Timer timer = new Timer(55, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        //System.out.println("Der Timer wurde gestartet");

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

                            TileSize.setTile_Height(Math.min(frame_height[0], frame_width[0]) / field_size);
                            TileSize.setTile_Width(Math.min(frame_height[0], frame_width[0]) / field_size);


                        }
                        tile.repaint(); //Der beste Command, der von der Menschheit erfunden wurde
                        tile.revalidate();
                    }
                });
                //frame.getContentPane().add(new SpielWindow());
                timer.start();
                //frame.setSize(TileSize.Tile_Height * field_size + 120 , TileSize.Tile_Height * field_size + 120 + 20/10);

            }
        });


    }
}
