package src;

import src.components.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import static src.config.*;

/**
 * Startmenü des Spiels
 */
public class MenuStart {

    GridBagConstraints  constraints;
    GridBagLayout       menuLayout;
    JButton             buttonMenuSingleplayer;
    JButton             buttonMenuMultiplayer;
    JButton             buttonMenuOptions;
    JButton             buttonQuitGame;
    JPanel              menuFiller;
    JPanel              menuPanel;
    JFrame              menuFrame;

    /**
     * Erstellung der Components im Startmenü
     * @throws IOException
     * @throws FontFormatException
     */
    public MenuStart() throws IOException, FontFormatException {
        // Custom Frame
        menuFrame = new MenuFrame();
        int COL         = (INITIAL_WIDTH * 22 / 100) - 10;
        int C_GAP       = (INITIAL_WIDTH * 28 / 100) - 10;
        int ROW_INFO    = (INITIAL_HEIGHT * 33 / 100) - 10;
        int ROW         = (INITIAL_HEIGHT * 10 / 100) - 10;
        int R_GAP       = (INITIAL_HEIGHT * 2) / 100;
        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[]{C_GAP, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[]{ROW_INFO, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, ROW};
        constraints = new GridBagConstraints();

        // Content Panel
        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuFiller = new MenuPanelFiller();
        buttonMenuSingleplayer = new MenuButton("SINGLEPLAYER", ImageLoader.getImage(ImageLoader.MENU_BUTTON));

        menuPanel.setLayout(menuLayout);


        makeConstraints(menuFiller, 0, 0 , 4);

        buttonMenuSingleplayer.addActionListener(e -> {
            // Hide this window
            menuPanel.setVisible(false);

            // Create new singleplayer menu
            try {
                new MenuSingleplayer(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        makeConstraints(buttonMenuSingleplayer, 1, 2, 2);

        buttonMenuMultiplayer = new MenuButton("MULTIPLAYER", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuMultiplayer.addActionListener(e -> {
            // Hide this window
            menuPanel.setVisible(false);

            // Create new singleplayer menu
            try {
                new MenuMultiplayer(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        makeConstraints(buttonMenuMultiplayer, 1, 4, 2);

        buttonMenuOptions = new MenuButton("OPTIONS", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuOptions.addActionListener(e -> {
            // Hide this window
            menuPanel.setVisible(false);

            // Create new singleplayer menu
            try {
                new MenuOptions(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        makeConstraints(buttonMenuOptions, 1, 6, 2);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 8, 2);

        menuFrame.add(menuPanel);
        menuFrame.repaint();
        menuFrame.revalidate();
    }

    /**
     * Legt Größe und Position der Components fest
     * @param comp Ein Swing Component, welcher in constraints embedded werden soll.
     * @param gridx X-Position auf der X-Achse
     * @param gridy Y-Position auf der Y-Achse
     * @param gridwidth Breite des Components
     */
    private void makeConstraints(JComponent comp, int gridx, int gridy, int gridwidth) {
        constraints.fill        = GridBagConstraints.BOTH;
        constraints.gridheight  = 1;
        constraints.gridwidth   = gridwidth;
        constraints.gridx       = gridx;
        constraints.gridy       = gridy;
        constraints.weightx     = 0.5;
        constraints.weighty     = 0.1;
        menuPanel.add(comp, constraints);
    }
}


