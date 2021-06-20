package src;

import src.components.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static src.config.*;
import static src.config.ROW;


public class MenuSingleplayer {

    GridBagLayout menuLayout;
    GridLayout buttonPanelLayout;
    GridBagConstraints constraints;
    JButton buttonMenuStart;
    JButton buttonEasy;
    JButton buttonNormal;
    JButton buttonHard;
    JButton buttonShipSize;
    JButton buttonQuitGame;
    JPanel menuSize;
    JPanel menuInformation;
    JPanel buttonPanel;
    JPanel menuPanel;
    JFrame menuFrame;

    public MenuSingleplayer(int x, int y) throws IOException, FontFormatException {

        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[] {C_GAP, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[] {ROW_INFO, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, ROW};
        constraints = new GridBagConstraints();

        // Custom Frame
        menuFrame = new MenuFrame("Pokemon yo", x, y);

        // Content Panel
        menuPanel = new MenuPanel(ImageLoader.getImage(ImageLoader.MENU_BG));
        menuPanel.setLayout(menuLayout);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextMulitplayer, menuFrame);
        makeConstraints(menuInformation, 0, 0, 4);

        buttonMenuStart = new MenuButton("MAIN MENU", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);

            // Create MenuMain and display it
            try {
                new MenuStart(menuFrame.getX(), menuFrame.getY());
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        makeConstraints(buttonMenuStart, 1, 2, 2);

        buttonPanel = new ButtonPanel();

        buttonEasy = new MenuButton("EASY", ImageLoader.getImage(ImageLoader.MENU_BUTTON2));
        buttonEasy.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);

            // Create MenuMain and display it
            new SpielWindow();
        });
        buttonPanel.add(buttonEasy);

        buttonNormal = new MenuButton("NORMAL", ImageLoader.getImage(ImageLoader.MENU_BUTTON2));
        buttonNormal.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);

            // Create MenuMain and display it
            new SpielWindow();
        });
        buttonPanel.add(buttonNormal);

        buttonHard = new MenuButton("HARD", ImageLoader.getImage(ImageLoader.MENU_BUTTON2));
        buttonHard.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);

            // Create MenuMain and display it
            new SpielWindow();
        });
        buttonPanel.add(buttonHard);
        makeConstraints(buttonPanel, 1, 4, 2);

        buttonShipSize = new MenuButton("POKEMON SIZE", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        makeConstraints(buttonShipSize, 1, 6, 2);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 8, 2);

        menuFrame.add(menuPanel);
    }

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
