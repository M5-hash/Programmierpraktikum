package src;


import src.components.MenuButton;
import src.components.QuitButton;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static src.config.*;

public class SpielWindow extends JPanel {

    public static boolean change = false;
    public static PlayingField playingField = new PlayingField(fieldsize);
    public static TilePainter tile2 = new TilePainter(fieldsize, "GegnerKI");
    public static TilePainter tile = new TilePainter(fieldsize, "Spieler");
    public static Zielhilfe Z = new Zielhilfe();
    public static ComPlayer Com;

    static {
        try {
            Com = new ComPlayerNormal(new PlayingField(fieldsize), new int[]{3, 3, 3, 4});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int framewidth = 0;
    public static int frameheigth = 0;

    String Feldvon = "Spieler"; //"GegnerKI" "GegnerMensch"

    public SpielWindow(JFrame frame, JPanel menuMain, boolean KI) throws IOException, FontFormatException {

        JLayeredPane LayeredPanel;
        JPanel Bg;
        JPanel gamePanel;
        GridLayout gameLayout;
        JButton buttonMenuStart;
        JButton buttonMenuOptions;
        JButton buttonSaveGame;
        JButton buttonLoadGame;
        JButton buttonQuitGame;
        Wahlstation wahlstation;
        JButton Fertig;
        JButton Delete;

        if (fullscreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        }
        frame.setSize(GF_WIDTH, GF_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        frameheigth = frame.getHeight();
        framewidth = frame.getWidth();

        LayeredPanel = new JLayeredPane();
        LayeredPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        Bg = new Background();
        Bg.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        wahlstation = new Wahlstation();
        wahlstation.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, frameheigth / 2 - 4 * TileSize.Tile_Size, 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2 + 2, 8 * TileSize.Tile_Size + 2); //Ohne das + 2 werden die netten Striche um die Wahlstation nicht gezeichnet
        wahlstation.setOpaque(false);

        //tile.setBounds(framewidth / 4, framewidth / 4, framewidth / 4, framewidth / 4);

        gameLayout = new GridLayout(0, 1);
        gameLayout.setVgap(5);
        gamePanel = new JPanel();
        gamePanel.setBounds(framewidth * 45 / 100, frameheigth / 3, framewidth / 20, frameheigth / 3);
        gamePanel.setOpaque(false);
        gamePanel.setLayout(gameLayout);
        gamePanel.setVisible(false);

        buttonMenuStart = new MenuButton("MAIN MENU", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
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

        buttonSaveGame = new MenuButton("SAVE GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonSaveGame.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Choose a directory to save your file: ");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (jfc.getSelectedFile().isDirectory()) {
                    System.out.println("You selected the directory: " + jfc.getSelectedFile());
                }
            }
        });
        gamePanel.add(buttonSaveGame);

        buttonLoadGame = new MenuButton("LOAD GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonLoadGame.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) ;
            {
                File selectedFile = jfc.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath());
            }
        });
        gamePanel.add(buttonLoadGame);

        buttonMenuOptions = new MenuButton("OPTIONS", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        gamePanel.add(buttonMenuOptions);

        buttonQuitGame = new QuitButton();
        gamePanel.add(buttonQuitGame);

        Z.setOpaque(false);
        Z.setBounds(15, 25, TileSize.Tile_Size * 3, TileSize.Tile_Size * 2);

        Delete = new MenuButton("DELETE", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        Delete.addActionListener(l -> {
            System.out.println("Du hast delete gedrückt");
            System.out.println(tile.deleting);
            tile.switchDeleting();
            System.out.println(Arrays.deepToString(Com.pf.getField()).replace("]", "]\n"));
            System.out.println(Arrays.deepToString(SchiffPainter.getEnemyPlacement).replace("]", "]\n"));
            System.out.println(tile.deleting);

        });

        Fertig = new MenuButton("START GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON2));
        Fertig.addActionListener(l -> {

            Tile.fightstart = !Tile.fightstart;
            Fertig.setVisible(false);
            Delete.setVisible(false);
            gamePanel.setVisible(true);

        });

        tile2.setBounds(1200, 15, 600, 1000);

        LayeredPanel.add(gamePanel, Integer.valueOf(1));
        LayeredPanel.add(Bg, Integer.valueOf(0));
        LayeredPanel.add(tile, Integer.valueOf(1));
        LayeredPanel.add(tile2, Integer.valueOf(1));
        LayeredPanel.add(wahlstation, Integer.valueOf(1));
        LayeredPanel.add(Z, Integer.valueOf(1));
        LayeredPanel.add(Fertig, Integer.valueOf(1));
        LayeredPanel.add(Delete, Integer.valueOf(1));

        frame.add(LayeredPanel);


        Timer timer = new Timer(110, e -> {
            int Borderwidth = 2 * Math.max(18, TileSize.Tile_Size / 8);
            int dbframeheigth = frame.getHeight();
            int dbframewidth = frame.getWidth();
            int TileSizer = (int) (dbframewidth * 0.30) / fieldsize;

            //TODO rework put check in resizer as very small and very big fieldsizes mess everything up
            if (framewidth != frame.getWidth()) {
                framewidth = frame.getWidth();
                TileSize.setTile_Size(((framewidth - Borderwidth) / 4) / fieldsize);

            } else if (frameheigth != frame.getHeight()) {
                frameheigth = frame.getHeight();
                TileSize.setTile_Size(((framewidth / 4) - Borderwidth) / fieldsize);
            }

            LayeredPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            Bg.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            tile.setBounds(framewidth / 8, frameheigth / 4, TileSize.Tile_Size * fieldsize + Borderwidth, TileSize.Tile_Size * fieldsize + Borderwidth);
            tile2.setBounds(framewidth * 5 / 8, frameheigth / 4, TileSize.Tile_Size * fieldsize + Borderwidth, TileSize.Tile_Size * fieldsize + Borderwidth);
            Z.setBounds(15, 25, TileSize.Tile_Size * 3, TileSize.Tile_Size * 2);
            //tile2.setBounds(1200, 15, Borderwidth, Borderwidth);
            wahlstation.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, frameheigth / 2 - 4 * TileSize.Tile_Size, 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2 + 2, 8 * TileSize.Tile_Size + 2); //Ohne das + 2 werden die netten Striche um die Wahlstation nicht gezeichnet
            Fertig.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, frameheigth / 2 + 5 * TileSize.Tile_Size, 120, 50);
            Delete.setBounds((framewidth / 2) - (TileSize.Tile_Size * 3 + TileSize.Tile_Size / 2) / 2, 120, 120, 50);
            gamePanel.setBounds(framewidth * 46 / 100, frameheigth / 3, framewidth * 8 / 100, frameheigth / 3);
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
