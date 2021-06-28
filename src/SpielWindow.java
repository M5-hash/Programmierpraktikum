package src;


import src.components.*;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static src.config.*;

public class SpielWindow extends JPanel {

    public static boolean change = false;
    public static int framewidth = 0;
    public static int frameheigth = 0;

    private static PlayingField playingField ;
    TilePainter tile2;
    TilePainter tile;
    Client client ;
    Server server ;
    boolean Multclient ;

    private static Object Multiplayer ;

    public SpielWindow(JFrame frame, boolean KI,PlayingField pf, Client Client ){
        client = Client ;
        playingField = pf ;
        makeComponents(frame);
        Multclient = true ;
    }
    private static ComPlayer Com;
    String Feldvon = "Spieler"; //"GegnerKI" "GegnerMensch"



    {
        try {
            Com = new ComPlayerNormal(new PlayingField(fieldsize, calculateships(), false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getMultiplayer() {
        return Multiplayer;
    }

    public SpielWindow(JFrame frame, boolean KI) throws IOException, FontFormatException {
        playingField = new PlayingField(fieldsize, calculateships(), true);
        makeComponents(frame);
    }

//    public SpielWindow(JFrame frame, boolean KI,PlayingField pf, Client Client ){
//        Multiplayer = Client ;
//        playingField = pf ;
//        makeComponents(frame);
//    }

    public SpielWindow(JFrame frame, boolean KI ,PlayingField pf, Server Server){
        Multiplayer = Server ;
        playingField = pf ;
        makeComponents(frame);
        Multclient = false ;
    }

    public SpielWindow(JFrame frame, JPanel menuPanel, boolean ki, Object client) throws IOException, FontFormatException{ //Eigentlich obsolete
        playingField = new PlayingField(fieldsize, calculateships(), true);
        makeComponents(frame);
    }

    private void makeComponents(JFrame frame) {

        Zielhilfe Z;

        JPanel      menuPanel;
        JPanel      gamePanel1;
        JPanel      gamePanel2;
        GridLayout  gameLayout;
        JButton     buttonMenuStart;
        JButton     buttonRestart;
        JButton     buttonSaveGame;
        JButton     buttonLoadGame;
        JButton     buttonMenuOptions;
        JButton     buttonQuitGame;
        JButton     buttonReady;
        JButton     buttonDelete;
        JButton     btn_size2;
        JButton     btn_size3;
        JButton     btn_size4;
        JButton     btn_size5;
//        Wahlstation wahlstation;

        tile2 = new TilePainter(fieldsize, "GegnerKI", this);
        tile = new TilePainter(fieldsize, "Spieler", this);
        Z = new Zielhilfe();

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

        menuPanel         = new CustomPanel(ImageLoader.getImage(ImageLoader.GAME_BACKGROUND));
//        wahlstation       = new Wahlstation();
        gameLayout        = new GridLayout(0, 1);
        buttonDelete      = new DeleteButton("DELETE");
        buttonReady       = new MenuButton("START GAME",   ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuStart   = new MenuButton("MAIN MENU",    ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonRestart     = new MenuButton("RESTART GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonSaveGame    = new MenuButton("SAVE GAME",    ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonLoadGame    = new MenuButton("LOAD GAME",    ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuOptions = new MenuButton("OPTIONS",      ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonQuitGame    = new QuitButton();
        btn_size2         = new MenuButton("size 2: " + size2, ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL1));
        btn_size3         = new MenuButton("size 3: " + size3, ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL2));
        btn_size4         = new MenuButton("size 4: " + size4, ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL3));
        btn_size5         = new MenuButton("size 5: " + size5, ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL4));
        gamePanel1        = new JPanel();
        gamePanel2        = new JPanel();

        menuPanel.setLayout(null);

        gameLayout.setVgap(5);
        gamePanel1.setOpaque(false);
        gamePanel1.setLayout(gameLayout);
        gamePanel1.setVisible(false);

        gameLayout.setVgap(5);
        gamePanel2.setOpaque(false);
        gamePanel2.setLayout(gameLayout);

        btn_size2.addActionListener(e -> {
            TilePainter.setGroesse(2);
            if(size2 < 0) {
                btn_size2.setText("size 2: 0");
            } else {
                btn_size2.setText("size 2: " + size2);
            }
        });
        btn_size3.addActionListener(e -> {
            TilePainter.setGroesse(3);
            if(size3 < 0) {
                btn_size3.setText("size 3: 0");
            } else {
                btn_size3.setText("size 3: " + size3);
            }
        });
        btn_size4.addActionListener(e -> {
            TilePainter.setGroesse(4);
            if(size4 < 0) {
                btn_size4.setText("size 4: 0");
            } else {
                btn_size4.setText("size 4: " + size4);
            }
        });
        btn_size5.addActionListener(e -> {
            TilePainter.setGroesse(5);
            if(size5 < 0) {
                btn_size5.setText("size 5: 0");
            } else {
                btn_size5.setText("size 5: " + size5);
            }
        });

        buttonReady.addActionListener(l -> {

            Tile.fightstart = true;
//            wahlstation.setVisible(false);
            gamePanel1.setVisible(true);
            gamePanel2.setVisible(false);
            if(Feldvon.equals("GegnerOnline") && Multclient){
                try {
                    client.message_check(client.loopCheckIN());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        buttonDelete.addActionListener(e -> {

            System.out.println("Du hast delete gedrückt");
            ((DeleteButton) buttonDelete).switchDeleting() ;
            if(tile.deleting){
                buttonDelete.setText("DELETE");

            } else {
                buttonDelete.setText("PLACE");
            }
            tile.switchDeleting();
        });
        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            gamePanel1.setVisible(false);
            frame.dispose();
            Tile.fightstart = false;
            fullscreen = false;
            // Create MenuMain and display it
            try {
                new MenuStart();
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        buttonRestart.addActionListener(e -> {
            Tile.fightstart = false;
//            wahlstation.setVisible(true);
            gamePanel1.setVisible(false);
            gamePanel2.setVisible(true);
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

        gamePanel1.add(buttonMenuStart);
        gamePanel1.add(buttonRestart);
        gamePanel1.add(buttonSaveGame);
        gamePanel1.add(buttonLoadGame);
        gamePanel1.add(buttonMenuOptions);
        gamePanel1.add(buttonQuitGame);

        gamePanel2.add(btn_size2);
        gamePanel2.add(btn_size3);
        gamePanel2.add(btn_size4);
        gamePanel2.add(btn_size5);
        gamePanel2.add(buttonReady);
        gamePanel2.add(buttonDelete);

        menuPanel.   setBounds(0, 0, frame.getWidth(), frame.getHeight());
        tile.        setBounds(framewidth * 13 / 100, frameheigth * 25 / 100, TileSize.Tile_Size * fieldsize + 2 * Math.max(18, TileSize.Tile_Size / 8), TileSize.Tile_Size * fieldsize + 2 * Math.max(18, TileSize.Tile_Size / 8));
        tile2.       setBounds(framewidth * 63 / 100, frameheigth * 25 / 100, TileSize.Tile_Size * fieldsize + 2 * Math.max(18, TileSize.Tile_Size / 8), TileSize.Tile_Size * fieldsize + 2 * Math.max(18, TileSize.Tile_Size / 8));
//        wahlstation. setBounds(framewidth * 46 / 100, frameheigth * 25 / 100, 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2 + 2, 8 * TileSize.Tile_Size + 2); //Ohne das + 2 werden die netten Striche um die Wahlstation nicht gezeichnet
        gamePanel1.  setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, TileSize.Tile_Size * fieldsize + 2 * Math.max(18, TileSize.Tile_Size / 8));
        gamePanel2.  setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, TileSize.Tile_Size * fieldsize + 2 * Math.max(18, TileSize.Tile_Size / 8));

        menuPanel.add(gamePanel1);
        menuPanel.add(gamePanel2);
        menuPanel.add(tile);
        menuPanel.add(tile2);
//        menuPanel.add(wahlstation);
        menuPanel.add(Z);

        frame.add(menuPanel);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                int W = 4;
                int H = 3;
                Rectangle b = frame.getBounds();
                int Borderwidth = 2 * Math.max(18, TileSize.Tile_Size / 8);

                if (framewidth != frame.getWidth()) {
                    frame.setBounds(b.x, b.y, b.width, b.width*H/W);
                }
                else if (frameheigth != frame.getHeight()) {
                    frame.setBounds(b.x, b.y, b.height*W/H, b.height);
                }
            }
        });

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

            menuPanel.   setBounds(0, 0, frame.getWidth(), frame.getHeight());
            tile.        setBounds(framewidth * 13 / 100, frameheigth * 25 / 100, TileSize.Tile_Size * fieldsize + Borderwidth, TileSize.Tile_Size * fieldsize + Borderwidth);
            tile2.       setBounds(framewidth * 63 / 100, frameheigth * 25 / 100, TileSize.Tile_Size * fieldsize + Borderwidth, TileSize.Tile_Size * fieldsize + Borderwidth);
            Z.           setBounds(framewidth * 13 / 100 + Borderwidth / 2, frameheigth * 17 / 100, TileSize.Tile_Size * 3, frameheigth * 8 / 100);
//            wahlstation. setBounds(framewidth * 46 / 100, frameheigth / 4, 3 * TileSize.Tile_Size + TileSize.Tile_Size / 2 + 2, 8 * TileSize.Tile_Size + 2); //Ohne das + 2 werden die netten Striche um die Wahlstation nicht gezeichnet
            buttonReady. setBounds(framewidth * 46 / 100, frameheigth * 59 / 100, framewidth * 8 / 100, frameheigth * 5  / 100);
            gamePanel1.  setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, TileSize.Tile_Size * fieldsize + Borderwidth);
            gamePanel2.   setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, TileSize.Tile_Size * fieldsize + Borderwidth);

            menuPanel.repaint();
            menuPanel.revalidate();
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
