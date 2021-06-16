package src;

import src.components.*;

import javax.swing.*;
import java.awt.*;


public class MenuSingleplayer {

    GridBagLayout menuLayout;
    GridBagConstraints constraints;
    JButton buttonMenuStart;
    JButton buttonEasy;
    JButton buttonNormal;
    JButton buttonHard;
    JButton buttonShipSize;
    JButton buttonQuitGame;
    JPanel buttonDifficulties;
    JPanel menuFiller;
    JPanel menuPanel;
    JFrame menuFrame;

    public MenuSingleplayer(int x, int y) {

        menuLayout = new GridBagLayout();
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.SOUTH;

        // Custom Frame
        menuFrame = new MenuFrame("Pokemon yo", x, y);

        // Content Panel
        menuPanel = new MenuPanel(ImageLoader.getImage(ImageLoader.MENU_BG));
        menuPanel.setLayout(menuLayout);

        menuFiller = new MenuPanelFiller();
        makeConstraints(menuFiller, 0, 0, 7, 1, 0.4, 1.0, 5);

        menuFiller = new MenuPanelFiller();
        makeConstraints(menuFiller, 0, 1, 1, 4, 0.6, 0.2, 5);

        menuFiller = new MenuPanelFiller();
        makeConstraints(menuFiller, 4, 1, 1, 4, 0.6, 0.2, 5);

        buttonMenuStart = new MenuButton("MAIN MENU");
        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);

            // Create MenuMain and display it
            new MenuStart(menuFrame.getX(), menuFrame.getY());
        });
        makeConstraints(buttonMenuStart, 1, 1, 3, 1, 0.15, 0.6, 5);

        GridLayout buttonDifficultiesLayout = new GridLayout(1,0);
        buttonDifficultiesLayout.setHgap(5);
        buttonDifficulties = new JPanel();
        buttonDifficulties.setOpaque(false);
        buttonDifficulties.setLayout(buttonDifficultiesLayout);

        buttonEasy = new MenuButton("EASY");
        buttonEasy.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);

            // Create MenuMain and display it
            new SpielWindow();
        });
        buttonDifficulties.add(buttonEasy);

        buttonNormal = new MenuButton("NORMAL");
        buttonNormal.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);

            // Create MenuMain and display it
            new SpielWindow();
        });
        buttonDifficulties.add(buttonNormal);

        buttonHard = new MenuButton("HARD");
        buttonHard.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);

            // Create MenuMain and display it
            new SpielWindow();
        });
        buttonDifficulties.add(buttonHard);
        makeConstraints(buttonDifficulties, 1, 2, 3, 1, 0.15, 0.6, 5);

        buttonShipSize = new MenuButton("POKEMON SIZE");
        makeConstraints(buttonShipSize, 1, 3, 3, 1, 0.15, 0.6, 5);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 4, 3, 1, 0.15, 0.6, 10);

        menuFrame.add(menuPanel);
    }

    private void makeConstraints(JComponent comp, int gridx, int gridy, int gridwidth, int gridheight, double weighty, double weightx, int bottom) {
        constraints.insets = new Insets(5, 5, bottom, 5);
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.fill = GridBagConstraints.BOTH;
        menuPanel.add(comp, constraints);
    }
}
