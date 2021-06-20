package src;

import src.components.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MenuStart {

    int x = 0;
    int y = 0;

    GridBagLayout menuLayout;
    GridBagConstraints constraints;
    JButton buttonMenuSingleplayer;
    JButton buttonMenuMultiplayer;
    JButton buttonMenuOptions;
    JButton buttonQuitGame;
    JPanel menuFiller;
    JPanel menuPanel;
    JFrame menuFrame;

    public MenuStart() throws IOException, FontFormatException {

        makeComponent();
    }

    public MenuStart(int x, int y) throws IOException, FontFormatException {
        this.x = x;
        this.y = y;
        makeComponent();
    }

    private void makeComponent() throws IOException, FontFormatException {
        menuLayout = new GridBagLayout();
        constraints = new GridBagConstraints();

        // Custom Frame
        menuFrame = new MenuFrame("Pokemon yo", x, y);

        // Content Panel
        menuPanel = new MenuPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuPanel.setLayout(menuLayout);

        menuFiller = new MenuPanelFiller();
        makeConstraints(menuFiller, 0 , 1.0, 0.5);

        //The Buttons
        buttonMenuSingleplayer = new MenuButton("SINGLEPLAYER", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuSingleplayer.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
            menuFrame.dispose();

            // Create new singleplayer menu
            try {
                new MenuSingleplayer(menuFrame.getX(), menuFrame.getY());
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        makeConstraints(buttonMenuSingleplayer, 1, 0.4, 0.1);

        buttonMenuMultiplayer = new MenuButton("MULTIPLAYER", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuMultiplayer.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
//            menuFrame.dispose();

            // Create new singleplayer menu
            try {
                new MenuMultiplayer(menuFrame.getX(), menuFrame.getY());
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        makeConstraints(buttonMenuMultiplayer, 2, 0.4, 0.1);

        buttonMenuOptions = new MenuButton("OPTIONS", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuOptions.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
            menuFrame.dispose();

            // Create new singleplayer menu
            try {
                new MenuOptions(menuFrame.getX(), menuFrame.getY());
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        makeConstraints(buttonMenuOptions, 3, 0.4, 0.1);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame,4, 0.4, 0.1);

        menuFrame.add(menuPanel);
    }

    private void makeConstraints(JComponent comp, int gridy, double weightx, double weighty) {
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = weightx;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;
        menuPanel.add(comp, constraints);
    }
}


