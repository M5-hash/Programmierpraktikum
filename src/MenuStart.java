package src;

import src.components.*;

import javax.swing.*;
import java.awt.*;

public class MenuStart {

    GridBagLayout menuLayout;
    GridBagConstraints constraints;
    JButton buttonMenuSingleplayer;
    JButton buttonMenuMultiplayer;
    JButton buttonMenuOptions;
    JButton buttonQuitGame;
    JPanel menuFiller;
    JPanel menuPanel;
    JFrame menuFrame;

    public MenuStart() {

        menuLayout = new GridBagLayout();
        constraints = new GridBagConstraints();

        // Custom Frame
        menuFrame = new MenuFrame("Pokemon yo");

        // Content Panel
        menuPanel = new MenuPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuPanel.setLayout(menuLayout);

        menuFiller = new MenuPanelFiller();
        makeConstraints(menuFiller, 0, 0, 4, 1, 0.4, 1.0, 0, 0, 0 ,0);

        menuFiller = new MenuPanelFiller();
        makeConstraints(menuFiller, 0, 1, 1, 4, 0.6, 0.2, 0, 0, 0 ,0);

        menuFiller = new MenuPanelFiller();
        makeConstraints(menuFiller, 3, 1, 1, 4, 0.6, 0.2, 0, 0 ,0, 0);

        //The Buttons
        buttonMenuSingleplayer = new MenuButton("SINGLEPLAYER", ImageLoader.getImage(ImageLoader.STARTMENU_BTN_BISASAM));
        buttonMenuSingleplayer.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
            menuFrame.dispose();

            // Create new singleplayer menu
            new MenuSingleplayer(menuFrame.getX(), menuFrame.getY());
        });
        makeConstraints(buttonMenuSingleplayer, 1, 1, 1, 1, 0.15, 0.6, 5, 5, 5, 5);

        buttonMenuMultiplayer = new MenuButton("MULTIPLAYER", ImageLoader.getImage(ImageLoader.STARTMENU_BTN_BISASAM));
        buttonMenuMultiplayer.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
            menuFrame.dispose();

            // Create new singleplayer menu
            new MenuMultiplayer(menuFrame.getX(), menuFrame.getY());
        });
        makeConstraints(buttonMenuMultiplayer, 1, 2, 1, 1, 0.15, 0.6, 5, 5, 5, 5);

        buttonMenuOptions = new MenuButton("OPTIONS", ImageLoader.getImage(ImageLoader.STARTMENU_BTN_BISASAM));
        buttonMenuOptions.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
            menuFrame.dispose();

            // Create new singleplayer menu
            new MenuOptions(menuFrame.getX(), menuFrame.getY());
        });
        makeConstraints(buttonMenuOptions, 1, 3, 1, 1, 0.15, 0.6, 5, 5, 5, 5);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 4, 1, 1, 0.15, 0.6, 5, 5, 10, 5);

        menuFrame.add(menuPanel);
    }

    public MenuStart(int x, int y) {
        menuLayout = new GridBagLayout();
        constraints = new GridBagConstraints();

        // Custom Frame
        menuFrame = new MenuFrame("Pokemon yo", x, y);

        // Content Panel
        menuPanel = new MenuPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuPanel.setLayout(menuLayout);

        menuFiller = new MenuPanelFiller();
        makeConstraints(menuFiller, 0, 0, 4, 1, 0.4, 1.0, 0, 0, 0 ,0);

        menuFiller = new MenuPanelFiller();
        makeConstraints(menuFiller, 0, 1, 1, 4, 0.6, 0.2, 0, 0, 0 ,0);

        menuFiller = new MenuPanelFiller();
        makeConstraints(menuFiller, 3, 1, 1, 4, 0.6, 0.2, 0, 0 ,0, 0);

        //The Buttons
        buttonMenuSingleplayer = new MenuButton("SINGLEPLAYER", ImageLoader.getImage(ImageLoader.STARTMENU_BTN_BISASAM));
        buttonMenuSingleplayer.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
            menuFrame.dispose();

            // Create new singleplayer menu
            new MenuSingleplayer(menuFrame.getX(), menuFrame.getY());
        });
        makeConstraints(buttonMenuSingleplayer, 1, 1, 1, 1, 0.15, 0.6, 5, 5, 5, 5);

        buttonMenuMultiplayer = new MenuButton("MULTIPLAYER", ImageLoader.getImage(ImageLoader.STARTMENU_BTN_BISASAM));
        buttonMenuMultiplayer.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
            menuFrame.dispose();

            // Create new singleplayer menu
            new MenuMultiplayer(menuFrame.getX(), menuFrame.getY());
        });
        makeConstraints(buttonMenuMultiplayer, 1, 2, 1, 1, 0.15, 0.6, 5, 5, 5, 5);

        buttonMenuOptions = new MenuButton("OPTIONS", ImageLoader.getImage(ImageLoader.STARTMENU_BTN_BISASAM));
        buttonMenuOptions.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
            menuFrame.dispose();

            // Create new singleplayer menu
            new MenuOptions(menuFrame.getX(), menuFrame.getY());
        });
        makeConstraints(buttonMenuOptions, 1, 3, 1, 1, 0.15, 0.6, 5, 5, 5, 5);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 4, 1, 1, 0.15, 0.6, 5, 5, 10, 5);

        menuFrame.add(menuPanel);
    }

    private void makeConstraints(JComponent comp, int gridx, int gridy, int gridwidth, int gridheight, double weighty, double weightx, int top, int left, int bottom, int right) {
        constraints.insets = new Insets(top, left, bottom, right);
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
