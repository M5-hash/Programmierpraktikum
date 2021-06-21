package src;


import src.components.MenuButton;
import src.components.MenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static src.config.*;

public class SpielWindow extends JPanel {


    Wahlstation wahlstation;

    public static boolean change = false;
    public static int field_size = 5;
    public static PlayingField playingField = new PlayingField(field_size);
    //public static TilePainter tile2 = new TilePainter(field_size, "GegnerKI");
    public static TilePainter tile = new TilePainter(field_size, "Spieler");
    public static Zielhilfe Z = new Zielhilfe();

    public static int getFramewidth() {
        return framewidth;
    }

    public static int getFrameheigth() {
        return frameheigth;
    }

    public static int framewidth = 0;
    public static int frameheigth = 0;
    String Feldvon = "Spieler"; //"GegnerKI" "GegnerMensch"


//    public static void main(String[] args) {                     //Startet das Programm und erstellt das Window wir hier nur wegen dem testen benötigt
//
//        SpielWindow Hallo = new SpielWindow();
//
//    }

    public SpielWindow() throws IOException, FontFormatException {

        int bottom_gap = 80;
        JButton Fertig = new MenuButton("Start", ImageLoader.getImage(ImageLoader.MENU_BUTTON2) ) ;
        JButton Delete = new MenuButton("Delete", ImageLoader.getImage(ImageLoader.MENU_BUTTON2)) ;


        JFrame frame = new MenuFrame("Schiffe versenken");
        JLayeredPane LayeredPanel = new JLayeredPane();

        frame.setSize(new Dimension(GF_WIDTH, GF_HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        TileSize.setTile_Size(frame.getWidth() / 25);

        frameheigth = frame.getHeight();
        framewidth = frame.getWidth();

        JPanel Bg = new Background();


        wahlstation = new Wahlstation();
        wahlstation.setBackground(new Color(0, 0, 0, 0));

        //tile.setBounds(1000, 100, 20, 20);
        //tile2.setBounds(1200, 15, 600, 1000);
        Bg.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        int Gamer = (int) (frame.getWidth() * 0.30) / field_size;
        TileSize.setTile_Size(Gamer);

        //tile2.setBounds(1200, 15, Borderwidth, Borderwidth);
        Z.setBounds(15, 25,TileSize.Tile_Size * 3 , TileSize.Tile_Size * 2);
        LayeredPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        wahlstation.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, frameheigth / 2 - 4 * TileSize.Tile_Size , 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2 + 2, 8 * TileSize.Tile_Size + 2); //Ohne das + 2 werden die netten Striche um die Wahlstation nicht gezeichnet
        Fertig.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, frameheigth / 2 + 5 * TileSize.Tile_Size , 120,50);
        frame.setSize(framewidth,frameheigth);

        Z.setBackground(new Color(0,0,0,0));


        LayeredPanel.add(Bg, Integer.valueOf(0));
        LayeredPanel.add(tile, Integer.valueOf(1));
        //LayeredPanel.add(tile2, Integer.valueOf(1));
        LayeredPanel.add(wahlstation, Integer.valueOf(1));
        LayeredPanel.add(Z, Integer.valueOf(2));
        LayeredPanel.add(Fertig, Integer.valueOf(2));
        LayeredPanel.add(Delete,Integer.valueOf(2));


        LayeredPanel.setBackground(Color.darkGray);
        LayeredPanel.setVisible(true);
        frame.add(LayeredPanel);

        Fertig.addActionListener(l -> {

            Tile.fightstart = !Tile.fightstart ;
            Fertig.setVisible(false);
            Delete.setVisible(false);

        });

        Delete.addActionListener(l -> {

        });


        LayeredPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        TileSize.setTile_Size(frame.getHeight() / 14);

        Timer timer = new Timer(110, e -> {

            int Borderwidth = field_size * TileSize.Tile_Size + 2 * Math.max(18, TileSize.Tile_Size / 8) ;


            double dbframeheigth = frame.getHeight();
            double dbframewidth = frame.getWidth();
            int TileSizer = (int) (dbframewidth * 0.30) / field_size;


            //TODO rework put check in resizer as very small and very big fieldsizes mess everything up
            if (framewidth != frame.getWidth()) {
                framewidth = frame.getWidth();
                TileSize.setTile_Size(TileSizer);

            } else if (frameheigth != frame.getHeight()) {
                frameheigth = frame.getHeight();
                TileSize.setTile_Size(TileSizer);
            }


            int yposFeld1 = (int) (dbframeheigth * 0.23);
            int xposFeld1 = (int) (dbframewidth * 0.095);


            Bg.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            tile.setBounds(xposFeld1, yposFeld1, Borderwidth, Borderwidth);
            //tile2.setBounds(1200, 15, Borderwidth, Borderwidth);
            Z.setBounds(15, 25,TileSize.Tile_Size * 3 , TileSize.Tile_Size * 2);
            LayeredPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            wahlstation.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, frameheigth / 2 - 4 * TileSize.Tile_Size , 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2 + 2, 8 * TileSize.Tile_Size + 2); //Ohne das + 2 werden die netten Striche um die Wahlstation nicht gezeichnet
            Fertig.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, frameheigth / 2 + 5 * TileSize.Tile_Size , 120,50);

            //tile.setBounds(300, 120, TileSize.Tile_Size * SpielWindow.field_size, TileSize.Tile_Size * SpielWindow.field_size);

            repaintAll();
            Bg.repaint();
            Bg.revalidate();

        });
        timer.start();


    }

    void repaintAll() {

        tile.repaint(); //Der beste Command, der von der Menschheit erfunden wurde
        tile.revalidate();

        Z.repaint();
        Z.revalidate();

        wahlstation.repaint();
        wahlstation.revalidate();

    }
}
