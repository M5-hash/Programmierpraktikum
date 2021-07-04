package src;

import src.components.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static src.config.*;

/**
 * Multiplayermenü des Spiels
 */
public class MenuMultiplayer {
    /**
     * Constraints für das GridbagLayout
     */
    GridBagConstraints constraints;
    /**
     * GridBaglayout der Optionen
     */
    GridBagLayout menuLayout;
    /**
     * Button zur Wiederherstellung des Startmenü
     */
    JButton buttonMenuStart;
    /**
     * Öffnet das Hostmenü
     */
    JButton buttonMenuHost;
    /**
     * Button um einem Spiel beizutreten <br>
     * IP muss vorher über getIP übergeben werden.
     */
    JButton buttonJoin;
    /**
     * Button um das Spiel zu schließen
     */
    JButton buttonQuitGame;
    /**
     * Textfeld mit Hintergrundbild zur Übergabe der IP
     */
    JPanel getIP;
    /**
     * Informationen zum Menü
     */
    JPanel menuInformation;
    /**
     * Panel für Inputcomponents
     */
    JPanel buttonPanel1;
    /**
     * Panel auf dem die Components hinzugefügt werden
     */
    JPanel menuPanel;
    /**
     * Frame des Menü
     */
    JFrame menuFrame;

    /**
     * Components werden hier erstellt
     *
     * @param menuFrame Frame des Hauptmenü
     * @param menuMain  Panel mit Components des Startmenü
     * @throws IOException         Fehler beim Laden der Grafiken
     * @throws FontFormatException Fehler beim Laden der Font
     */
    public MenuMultiplayer(JFrame menuFrame, JPanel menuMain) throws IOException, FontFormatException {
        this.menuFrame = menuFrame;

        int COL = (INITIAL_WIDTH * 22 / 100) - 10;
        int C_GAP = (INITIAL_WIDTH * 28 / 100) - 10;
        int ROW_INFO = (INITIAL_HEIGHT * 33 / 100) - 10;
        int ROW = (INITIAL_HEIGHT * 10 / 100) - 10;
        int R_GAP = (INITIAL_HEIGHT * 2) / 100;

        menuLayout = new GridBagLayout();
        constraints = new GridBagConstraints();
        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextMulitplayer, menuFrame);
        buttonMenuStart = new MenuButton("MAIN MENU", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonPanel1 = new ButtonPanel();
        buttonJoin = new MenuButton("JOIN GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuHost = new MenuButton("HOST GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonQuitGame = new QuitButton();
        getIP = new TextFieldIP("ENTER IP");

        menuLayout.columnWidths = new int[]{C_GAP, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[]{ROW_INFO, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, ROW};
        menuPanel.setLayout(menuLayout);

        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            menuPanel.setVisible(false);

            // Create MenuMain and display it
            menuMain.setVisible(true);
        });
        buttonJoin.addActionListener(e -> {
            if (IP.equals("") || IP.equals("ENTER IP")) {
                JOptionPane.showMessageDialog(null, "PLEASE ENTER AN IP");
            } else {

                SpielFeld2 = 2;

                String[] options = new String[]{"Player", "Computer", "Cancel"};
                ImageIcon icon = new ImageIcon("");
                int x = JOptionPane.showOptionDialog(menuFrame, "Wollen Sie selbst spielen oder als Computer?",
                        "Selfplay or KI", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);

                if (x == 0 || x == 1) {
                    menuPanel.setVisible(false);
                    //menuFrame.dispose();
                }
                if (x == 0) {
                    onlineCom = false;
                    // Create SpielWindow and display it
                    try {
                        Client client = new Client(IP);
                        SpielFeld1 = 0;
                        client.setSpielwindow(new SpielWindow(menuFrame, client));

                    } catch (Exception ioException) {
                        ioException.printStackTrace();
                    }
                } else if (x == 1) {
                    onlineCom = true;
                    try {

                        Client client = new Client(IP);
                        SpielFeld1 = 0;
                        client.setSpielwindow(new SpielWindow(menuFrame, client));

                    } catch (Exception ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    System.out.println("no");
                }
            }
        });
        buttonMenuHost.addActionListener(e -> {
            menuPanel.setVisible(false);
            GameMode = true;
            try {
                new MenuHost(menuFrame, menuPanel);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        buttonPanel1.add(buttonJoin);
        buttonPanel1.add(buttonMenuHost);

        makeConstraints(menuInformation, 0, 0, 4, 1);
        makeConstraints(buttonMenuStart, 1, 2, 2, 1);
        makeConstraints(buttonPanel1, 1, 4, 2, 1);
        makeConstraints(getIP, 1, 6, 2, 1);
        makeConstraints(buttonQuitGame, 1, 8, 2, 1);

        menuFrame.add(this.menuPanel);
    }

    /**
     * Legt Größe und Position der Components fest
     *
     * @param comp       Ein Swing Component, welcher in constraints embedded werden soll.
     * @param gridx      X-Position auf der X-Achse
     * @param gridy      Y-Position auf der Y-Achse
     * @param gridwidth  Breite des Components
     * @param gridheight Höhe der Components
     */
    private void makeConstraints(JComponent comp, int gridx, int gridy, int gridwidth, int gridheight) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.BOTH;
        menuPanel.add(comp, constraints);
    }
}
