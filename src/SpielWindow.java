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

import static src.config.*;

public class SpielWindow extends JPanel {

    boolean Multclient ;
    String Feldvon = "Spieler"; //"GegnerKI" "GegnerOnline"

    Client          client;
    Server          server;
    TilePainter     tile2;
    TilePainter     tile;
    Zielhilfe       Z;
    GridLayout      gameLayout;
    JPanel          menuPanel;
    JPanel          gamePanel1;
    JPanel          gamePanel2;
    JButton         buttonMenuStart;
    JButton         buttonRestart;
    JButton         buttonSaveGame;
    JButton         buttonLoadGame;
    JButton         buttonMenuOptions;
    JButton         buttonQuitGame;
    JButton         buttonReady;
    JButton         buttonDelete;
    ButtonGroup     buttonGroup;
    ToggleButton    btn_size2;
    ToggleButton    btn_size3;
    ToggleButton    btn_size4;
    ToggleButton    btn_size5;

    private ComPlayer Com;
    private PlayingField playingField;


    public SpielWindow(JFrame frame) throws IOException, FontFormatException {
        playingField = new PlayingField(fieldsize, calculateships(), true);
        makeComponents(frame);
    }

    public SpielWindow(JFrame frame, Client Client){
        client = Client ;
        playingField = Client.pf ;
        makeComponents(frame);
        Multclient = true ;
    }

    public SpielWindow(JFrame frame, Server Server){
        this.server = Server ;
        playingField = Server.pf ;
        makeComponents(frame);
        Multclient = false ;
    }

    private void makeComponents(JFrame frame) {

        frameheigth = frame.getHeight();
        framewidth = frame.getWidth();

        if(SpielFeld1 == 1 || SpielFeld2 == 1){
            {
                try {
                    Com = new ComPlayerNormal(new PlayingField(fieldsize, calculateships(), false));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (fullscreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        }
        frame.setSize(GF_WIDTH, GF_HEIGHT);
        frame.setMinimumSize(new Dimension(854,480));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        frameheigth = frame.getHeight();
        framewidth = frame.getWidth();

        tile              = new TilePainter(fieldsize, SpielFeld1, this, Com, playingField);
        tile2             = new TilePainter(fieldsize, SpielFeld2, this, Com, playingField);
        Z                 = new Zielhilfe();
        menuPanel         = new CustomPanel(ImageLoader.getImage(ImageLoader.GAME_BACKGROUND));
        gameLayout        = new GridLayout(0, 1);
        buttonDelete      = new DeleteButton();
        buttonReady       = new MenuButton("START GAME",   ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuStart   = new MenuButton("MAIN MENU",    ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonRestart     = new MenuButton("RESTART GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonSaveGame    = new MenuButton("SAVE GAME",    ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonLoadGame    = new MenuButton("LOAD GAME",    ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuOptions = new MenuButton("OPTIONS",      ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonQuitGame    = new QuitButton();
        btn_size2         = new ToggleButton("size 2: " + size2, ImageLoader.getImage(ImageLoader.RED), ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL1));
        btn_size3         = new ToggleButton("size 3: " + size3, ImageLoader.getImage(ImageLoader.RED), ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL2));
        btn_size4         = new ToggleButton("size 4: " + size4, ImageLoader.getImage(ImageLoader.RED), ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL3));
        btn_size5         = new ToggleButton("size 5: " + size5, ImageLoader.getImage(ImageLoader.RED), ImageLoader.getImage(ImageLoader.MENU_BUTTON2), ImageLoader.getImage(ImageLoader.GAME_BTN_BALL4));
        gamePanel1        = new JPanel();
        gamePanel2        = new JPanel();
        buttonGroup       = new ButtonGroup();

        menuPanel. setLayout(null);

        tile2.     setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        tile.      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        gameLayout.setVgap(5);

        gamePanel1.setOpaque(false);
        gamePanel1.setLayout(gameLayout);
        gamePanel1.setVisible(false);

        gamePanel2.setOpaque(false);
        gamePanel2.setLayout(gameLayout);

        btn_size2.addActionListener(e -> {
            TilePainter.setGroesse(2);
        });
        buttonGroup.add(btn_size2);
        btn_size3.addActionListener(e -> {
            TilePainter.setGroesse(3);
        });
        buttonGroup.add(btn_size3);
        btn_size4.addActionListener(e -> {
            TilePainter.setGroesse(4);
        });
        buttonGroup.add(btn_size4);
        btn_size5.addActionListener(e -> {
            TilePainter.setGroesse(5);
        });
        buttonGroup.add(btn_size5);
        buttonReady.addActionListener(e -> {

            Tile.fightstart = true;
            gamePanel1.setVisible(true);
            gamePanel2.setVisible(false);
            if(SpielFeld2 == 2 && Multclient){
                try {
                    do{
                        client.message_check();
                        tile.repaint();
                        //tile.revalidate();

                    }while(!client.myTurn);
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        });
        buttonDelete.addActionListener(e -> {

            ((DeleteButton) buttonDelete).switchDeleting() ;
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

        TileSize.setTile_Size(((framewidth / 4) - 2 * Math.max(18, TileSize.Tile_Size / 8)) / fieldsize);
        menuPanel.   setBounds(0, 0, frame.getWidth(), frame.getHeight());
        tile.        setBounds(framewidth * 13 / 100, frameheigth * 25 / 100, framewidth * 25 / 100, framewidth * 25 / 100);
        tile2.       setBounds(framewidth * 62 / 100, frameheigth * 25 / 100, framewidth * 25 / 100, framewidth * 25 / 100);
        gamePanel1.  setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, framewidth * 25 / 100);
        gamePanel2.  setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, framewidth * 25 / 100);

        menuPanel.add(gamePanel1);
        menuPanel.add(gamePanel2);
        menuPanel.add(tile);
        menuPanel.add(tile2);
        menuPanel.add(Z);

        frame.add(menuPanel);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int W = 16;
                int H = 9;

                Rectangle b = frame.getBounds();
                int Borderwidth = 2 * Math.max(18, TileSize.Tile_Size / 8);

                //TODO rework put check in resizer as very small and very big fieldsizes mess everything up
                if (framewidth != frame.getWidth()) {
                    framewidth = frame.getWidth();
                    frame.setBounds(b.x, b.y, b.width, b.width * H/W);
                    TileSize.setTile_Size(((framewidth / 4) - Borderwidth) / fieldsize);

                } else if (frameheigth != frame.getHeight()) {
                    frameheigth = frame.getHeight();
                    frame.setBounds(b.x, b.y, b.height * W/H, b.height);
                    TileSize.setTile_Size(((framewidth / 4) - Borderwidth) / fieldsize);
                }

                menuPanel.   setBounds(0, 0, frame.getWidth(), frame.getHeight());
                tile.        setBounds(framewidth * 13 / 100, frameheigth * 25 / 100, framewidth * 25 / 100, framewidth * 25 / 100);
                tile2.       setBounds(framewidth * 62 / 100, frameheigth * 25 / 100, framewidth * 25 / 100, framewidth * 25 / 100);
                gamePanel1.  setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, framewidth * 25 / 100);
                gamePanel2.  setBounds(framewidth * 45 / 100, frameheigth * 25 / 100, framewidth * 10 / 100, framewidth * 25 / 100);

                menuPanel.revalidate();
                menuPanel.repaint();
            }
        });

        Timer timer = new Timer(110, e -> {

            menuPanel.repaint();
            menuPanel.revalidate();
        });
        timer.start();
    }


//    public static PlayingField getPlayingField() {
//        return playingField;
//    }

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
