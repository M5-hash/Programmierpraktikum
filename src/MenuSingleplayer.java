package src;

import src.components.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static src.config.*;

/**
 * Singleplayermenü des Spiels
 */
public class MenuSingleplayer {
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
     * Öffnet das Sizemenü und stellt die Schwierigkeit der KI auf Easy
     */
    JButton buttonEasy;
    /**
     * Öffnet das Sizemenü und stellt die Schwierigkeit der KI auf Normal
     */
    JButton buttonNormal;
    /**
     * Öffnet Frame zum Laden alter Spielstände
     */
    JButton buttonLoadGame;
    /**
     * Button um das Spiel zu schließen
     */
    JButton buttonQuitGame;
    /**
     * Informationen zum Menü
     */
    JPanel menuInformation;
    /**
     * Panel für Inputcomponents
     */
    JPanel buttonPanel;
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
    public MenuSingleplayer(JFrame menuFrame, JPanel menuMain) throws IOException, FontFormatException {
        this.menuFrame = menuFrame;

        int COL = (INITIAL_WIDTH * 22 / 100) - 10;
        int C_GAP = (INITIAL_WIDTH * 28 / 100) - 10;
        int ROW_INFO = (INITIAL_HEIGHT * 33 / 100) - 10;
        int ROW = (INITIAL_HEIGHT * 10 / 100) - 10;
        int R_GAP = (INITIAL_HEIGHT * 2) / 100;
        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[]{C_GAP, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[]{ROW_INFO, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, ROW};
        constraints = new GridBagConstraints();

        // Content Panel
        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuPanel.setLayout(menuLayout);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextSingleplayer, menuFrame);
        makeConstraints(menuInformation, 0, 0, 4);

        buttonMenuStart = new MenuButton("MAIN MENU", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            menuPanel.setVisible(false);

            // Create MenuMain and display it
            menuMain.setVisible(true);
        });
        makeConstraints(buttonMenuStart, 1, 2, 2);

        buttonPanel = new ButtonPanel();

        buttonEasy = new MenuButton("EASY", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonEasy.addActionListener(e -> {
            menuPanel.setVisible(false);

            KIisEasy = true;
            GameMode = false;
            SpielFeld1 = 0;
            SpielFeld2 = 1;
            try {
                new MenuSize(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        buttonPanel.add(buttonEasy);

        buttonNormal = new MenuButton("NORMAL", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonNormal.addActionListener(e -> {
            menuPanel.setVisible(false);

            KIisEasy = false;
            GameMode = false;
            SpielFeld1 = 0;
            SpielFeld2 = 1;
            try {
                new MenuSize(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        buttonPanel.add(buttonNormal);

        makeConstraints(buttonPanel, 1, 4, 2);

        buttonLoadGame = new LoadGameButton(menuFrame, menuPanel, "LOAD GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        makeConstraints(buttonLoadGame, 1, 6, 2);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 8, 2);

        menuFrame.add(menuPanel);
    }

    /**
     * Legt Größe und Position der Components fest
     *
     * @param comp      Ein Swing Component, welcher in constraints embedded werden soll.
     * @param gridx     X-Position auf der X-Achse
     * @param gridy     Y-Position auf der Y-Achse
     * @param gridwidth Breite des Components
     */
    private void makeConstraints(JComponent comp, int gridx, int gridy, int gridwidth) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = 1;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.BOTH;
        menuPanel.add(comp, constraints);
    }
}
