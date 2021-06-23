package src;


import src.components.MenuButton;
import src.components.QuitButton;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static src.config.*;

public class SpielWindow extends JPanel {

    public static boolean change = false;
    public static int field_size = 10;
    public static PlayingField playingField = new PlayingField(field_size);
    //public static TilePainter tile2 = new TilePainter(field_size, "GegnerKI");
    public static TilePainter tile = new TilePainter(field_size, "Spieler");
    public static Zielhilfe Z = new Zielhilfe();

    public static int framewidth = 0;
    public static int frameheigth = 0;

    String Feldvon = "Spieler"; //"GegnerKI" "GegnerMensch"

    JLayeredPane LayeredPanel;
    JPanel Bg;
    JPanel gamePanel;
    GridLayout gameLayout;
    JButton buttonMenuStart;
    JButton buttonMenuOptions;
    JButton buttonQuitGame;
    Wahlstation wahlstation;
    JButton Fertig;
    JButton Delete;

    public SpielWindow(JFrame frame, JPanel menuMain) throws IOException, FontFormatException {

        System.out.println("ich bin das spielwindow");

        if(fullscreen){
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        }
        frame.setSize(GF_WIDTH, GF_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frameheigth = frame.getHeight();
        framewidth = frame.getWidth();

        LayeredPanel = new JLayeredPane();
        LayeredPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        Bg = new Background();
        Bg.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        wahlstation = new Wahlstation();
        wahlstation.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, frameheigth / 2 - 4 * TileSize.Tile_Size , 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2 + 2, 8 * TileSize.Tile_Size + 2); //Ohne das + 2 werden die netten Striche um die Wahlstation nicht gezeichnet
        wahlstation.setOpaque(false);

        //tile.setBounds(framewidth / 4, framewidth / 4, framewidth / 4, framewidth / 4);

        gameLayout = new GridLayout(0,1);
        gameLayout.setVgap(5);
        gamePanel = new JPanel();
        gamePanel.setBounds(framewidth* 45 / 100, frameheigth / 3, framewidth / 10, frameheigth / 3);
        gamePanel.setOpaque(false);
        gamePanel.setLayout(gameLayout);
        gamePanel.setVisible(false);

        buttonMenuStart = new MenuButton("Main Menu",ImageLoader.getImage(ImageLoader.MENU_BUTTON3));
        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            gamePanel.setVisible(false);
            frame.dispose();

            // Create MenuMain and display it
            try {
                new MenuStart();
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        gamePanel.add(buttonMenuStart);

        buttonMenuOptions = new MenuButton("Options",ImageLoader.getImage(ImageLoader.MENU_BUTTON3));
        gamePanel.add(buttonMenuOptions);

        buttonQuitGame = new QuitButton();
        gamePanel.add(buttonQuitGame);

        Z.setOpaque(false);
        Z.setBounds(15, 25,TileSize.Tile_Size * 3 , TileSize.Tile_Size * 2);

        Fertig = new MenuButton("Start", ImageLoader.getImage(ImageLoader.MENU_BUTTON2) ) ;
        Fertig.addActionListener(l -> {

            Tile.fightstart = !Tile.fightstart ;
            Fertig.setVisible(false);
            Delete.setVisible(false);
            gamePanel.setVisible(true);

        });
        Delete = new MenuButton("Delete", ImageLoader.getImage(ImageLoader.MENU_BUTTON2)) ;
        Delete.addActionListener(l -> {

        });

        //tile2.setBounds(1200, 15, 600, 1000);
        //tile2.setBounds(1200, 15, Borderwidth, Borderwidth);

        LayeredPanel.add(gamePanel, Integer.valueOf(1));
        LayeredPanel.add(Bg, Integer.valueOf(0));
        LayeredPanel.add(tile, Integer.valueOf(1));
        //LayeredPanel.add(tile2, Integer.valueOf(1));
        LayeredPanel.add(wahlstation, Integer.valueOf(1));
        LayeredPanel.add(Z, Integer.valueOf(1));
        LayeredPanel.add(Fertig, Integer.valueOf(1));
        LayeredPanel.add(Delete,Integer.valueOf(1));

        frame.add(LayeredPanel);


        Timer timer = new Timer(110, e -> {
            int Borderwidth = 2 * Math.max(18, TileSize.Tile_Size / 8) ;
            double dbframeheigth = frame.getHeight();
            double dbframewidth = frame.getWidth();
            int TileSizer = (int) (dbframewidth * 0.30) / field_size;

            //TODO rework put check in resizer as very small and very big fieldsizes mess everything up
            if (framewidth != frame.getWidth()) {
                framewidth = frame.getWidth();
                TileSize.setTile_Size(((framewidth - Borderwidth) / 4) / field_size);


            }
//            else if (frameheigth != frame.getHeight()) {
//                frameheigth = frame.getHeight();
//                TileSize.setTile_Size(((framewidth / 4) - Borderwidth) / field_size);
//            }

            LayeredPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            Bg.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            tile.setBounds(framewidth / 8, frameheigth / 4, TileSize.Tile_Size * field_size + Borderwidth, TileSize.Tile_Size * field_size + Borderwidth);
            Z.setBounds(15, 25,TileSize.Tile_Size * 3 , TileSize.Tile_Size * 2);
            //tile2.setBounds(1200, 15, Borderwidth, Borderwidth);
            wahlstation.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, frameheigth / 2 - 4 * TileSize.Tile_Size , 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2 + 2, 8 * TileSize.Tile_Size + 2); //Ohne das + 2 werden die netten Striche um die Wahlstation nicht gezeichnet
            Fertig.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, frameheigth / 2 + 5 * TileSize.Tile_Size , 120,50);
            gamePanel.setBounds(framewidth* 45 / 100, frameheigth / 3, framewidth / 10, frameheigth / 3 );

            boolean Rechnungpasst = (tile.getWidth() ==  (TileSize.Tile_Size * field_size + Borderwidth)) ;

            System.out.println("Die Werte stimmen überein : " + Rechnungpasst);


                    repaintAll();
            Bg.repaint();
            Bg.revalidate();

        });
        timer.start();
    }

    void repaintAll() {

//        gamePanel.repaint();
//        gamePanel.revalidate();
//
//        tile.repaint(); //Der beste Command, der von der Menschheit erfunden wurde
//        tile.revalidate();
//
//        Z.repaint();
//        Z.revalidate();
//
//        wahlstation.repaint();
//        wahlstation.revalidate();
      }
}
