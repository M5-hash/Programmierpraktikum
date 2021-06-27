package src;


import src.components.MenuButton;
import src.components.QuitButton;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;

import static src.config.*;

public class SpielWindow extends JPanel {

    public static boolean change = false;
    public static int framewidth = 0;
    public static int frameheigth = 0;
    private static PlayingField playingField = new PlayingField(fieldsize, calculateships(), true);
    private static ComPlayer Com;
    String Feldvon = "Spieler"; //"GegnerKI" "GegnerMensch"

    {
        try {
            Com = new ComPlayerNormal(new PlayingField(fieldsize, calculateships(), false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public SpielWindow(JFrame frame, JPanel menuMain, boolean KI) throws IOException, FontFormatException {


        TilePainter tile2 = new TilePainter(fieldsize, "GegnerKI");
        TilePainter tile = new TilePainter(fieldsize, "Spieler");
        Zielhilfe Z = new Zielhilfe();


        JLayeredPane LayeredPanel;
        JPanel Bg;
        JPanel gamePanel;
        GridLayout gameLayout;
        JButton buttonMenuStart;
        JButton buttonRestart;
        JButton buttonSaveGame;
        JButton buttonLoadGame;
        JButton buttonMenuOptions;
        JButton buttonQuitGame;
        Wahlstation wahlstation;
        JButton buttonReady;
        JButton buttonDelete;

        System.out.println(size2 + "Das hier ist ein Schiff der größe 2");
        System.out.println(size3 + "Das hier ist ein Schiff der größe 3");
        System.out.println(size4 + "Das hier ist ein Schiff der größe 4");
        System.out.println(size5 + "Das hier ist ein Schiff der größe 5");
        System.out.println(Arrays.toString(calculateships()) + "Hier sollten die Schiffe stehen, welche man verwenden darf");

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

        LayeredPanel      = new JLayeredPane();
        Bg                = new Background();
        wahlstation       = new Wahlstation();
        gameLayout        = new GridLayout(0, 1);
        buttonDelete      = new MenuButton("DELETE",       ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonReady       = new MenuButton("START GAME",   ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuStart   = new MenuButton("MAIN MENU",    ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonRestart     = new MenuButton("RESTART GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonSaveGame    = new MenuButton("SAVE GAME",    ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonLoadGame    = new MenuButton("LOAD GAME",    ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuOptions = new MenuButton("OPTIONS",      ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonQuitGame    = new QuitButton();
        gamePanel         = new JPanel();

//        wahlstation.setOpaque(false);

        gameLayout.setVgap(5);
        gamePanel.setBounds(framewidth * 45 / 100, frameheigth / 3, framewidth / 20, frameheigth / 3);
        gamePanel.setOpaque(false);
        gamePanel.setLayout(gameLayout);
        gamePanel.setVisible(false);

        buttonReady.addActionListener(l -> {

            Tile.fightstart = !Tile.fightstart;
            buttonReady.setVisible(false);
            buttonDelete.setVisible(false);
            gamePanel.setVisible(true);
        });
        buttonDelete.addActionListener(e -> {

            System.out.println("Du hast delete gedrückt");
            System.out.println(tile.deleting);
            tile.switchDeleting();
            System.out.println(Arrays.deepToString(Com.pf.getField()).replace("]", "]\n"));
            System.out.println(Arrays.deepToString(SpielWindow.playingField.getFieldEnemy()).replace("]", "]\n"));
            System.out.println(tile.deleting);
        });
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
        buttonRestart.addActionListener(e -> {
            buttonReady.setVisible(true);
            buttonDelete.setVisible(true);
            gamePanel.setVisible(false);
        });
        buttonSaveGame.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Choose a directory to save your file: ");
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setDialogTitle("Sava a File");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
            jfc.setFileFilter(filter);

            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (jfc.getSelectedFile().isDirectory()) {
                    System.out.println("You selected the directory: " + jfc.getSelectedFile());
                }
            }
        });
        buttonLoadGame.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath());
            }
        });

        gamePanel.add(buttonMenuStart);
        gamePanel.add(buttonRestart);
        gamePanel.add(buttonSaveGame);
        gamePanel.add(buttonLoadGame);
        gamePanel.add(buttonMenuOptions);
        gamePanel.add(buttonQuitGame);

        LayeredPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        Bg.          setBounds(0, 0, frame.getWidth(), frame.getHeight());
        tile.        setBounds(framewidth * 13 / 100, frameheigth * 25 / 100, TileSize.Tile_Size * fieldsize + 2 * Math.max(18, TileSize.Tile_Size / 8), TileSize.Tile_Size * fieldsize + 2 * Math.max(18, TileSize.Tile_Size / 8));
        tile2.       setBounds(framewidth * 63 / 100, frameheigth * 25 / 100, TileSize.Tile_Size * fieldsize + 2 * Math.max(18, TileSize.Tile_Size / 8), TileSize.Tile_Size * fieldsize + 2 * Math.max(18, TileSize.Tile_Size / 8));
        wahlstation. setBounds(framewidth * 46 / 100, frameheigth * 25 / 100, 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2 + 2, 8 * TileSize.Tile_Size + 2); //Ohne das + 2 werden die netten Striche um die Wahlstation nicht gezeichnet
        buttonReady. setBounds(framewidth * 46 / 100, frameheigth * 59 / 100, framewidth * 8 / 100, frameheigth * 5  / 100);
        buttonDelete.setBounds(framewidth * 46 / 100, frameheigth * 65 / 100, framewidth * 8 / 100, frameheigth * 5  / 100);
        gamePanel.   setBounds(framewidth * 46 / 100, frameheigth * 33 / 100, framewidth * 8 / 100, frameheigth * 33 / 100);

        LayeredPanel.add(gamePanel, Integer.valueOf(1));
        LayeredPanel.add(Bg, Integer.valueOf(0));
        LayeredPanel.add(tile, Integer.valueOf(1));
        LayeredPanel.add(tile2, Integer.valueOf(1));
        LayeredPanel.add(wahlstation, Integer.valueOf(1));
        LayeredPanel.add(Z, Integer.valueOf(1));
        LayeredPanel.add(buttonReady, Integer.valueOf(1));
        LayeredPanel.add(buttonDelete, Integer.valueOf(1));

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
            Bg.          setBounds(0, 0, frame.getWidth(), frame.getHeight());
            tile.        setBounds(framewidth * 13 / 100, frameheigth * 25 / 100, TileSize.Tile_Size * fieldsize + Borderwidth, TileSize.Tile_Size * fieldsize + Borderwidth);
            tile2.       setBounds(framewidth * 63 / 100, frameheigth * 25 / 100, TileSize.Tile_Size * fieldsize + Borderwidth, TileSize.Tile_Size * fieldsize + Borderwidth);
            Z.           setBounds(framewidth * 13 / 100 + Borderwidth / 2, frameheigth * 17 / 100, TileSize.Tile_Size * 3, frameheigth * 8 / 100);
            wahlstation. setBounds(framewidth * 46 / 100, frameheigth / 4, 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2 + 2, 8 * TileSize.Tile_Size + 2); //Ohne das + 2 werden die netten Striche um die Wahlstation nicht gezeichnet
            buttonReady. setBounds(framewidth * 46 / 100, frameheigth * 59 / 100, framewidth * 8 / 100, frameheigth * 5  / 100);
            buttonDelete.setBounds(framewidth * 46 / 100, frameheigth * 65 / 100, framewidth * 8 / 100, frameheigth * 5  / 100);
            gamePanel.   setBounds(framewidth * 46 / 100, frameheigth * 33 / 100, framewidth * 8 / 100, frameheigth * 33 / 100);

            Bg.repaint();
            Bg.revalidate();
        });
        timer.start();
    }

    public static ComPlayer getCom() {
        return Com;
    }

    public static PlayingField getPlayingField() {
        return playingField;
    }

    static int[] calculateships() {

        int[] sizes = {size2,size3,size4,size5};

        int[] compiledArray = new int[size5 + size4 + size3 + size2];
        int counter = 0;

        for(int i = 0; i < 4 ; i++){
            for(int j = 0; j < sizes[i]; sizes[i]-- ){
                compiledArray[counter] = i + 2 ;
                counter++ ;
            }
        }

        return compiledArray;
    }
}
