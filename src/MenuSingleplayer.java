package src;

import src.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import static src.config.*;
import static src.config.ROW;


public class MenuSingleplayer {

    GridBagLayout menuLayout;
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

    public MenuSingleplayer(JFrame menuFrame, JPanel menuMain) throws IOException, FontFormatException {
        this.menuFrame = menuFrame;

        INITIAL_WIDTH = menuFrame.getWidth();
        INITIAL_HEIGHT = menuFrame.getHeight();

        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[]{C_GAP, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[]{ROW_INFO, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, ROW};
        constraints = new GridBagConstraints();

        // Content Panel
        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuPanel.setLayout(menuLayout);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextMulitplayer, menuFrame);
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

        buttonEasy = new MenuButton("EASY", ImageLoader.getImage(ImageLoader.MENU_BUTTON2));
        buttonEasy.addActionListener(e -> {
            // Hide this window
            menuPanel.setVisible(false);
            menuFrame.dispose();
            // Create SpielWindow and display it
            try {
                new SpielWindow(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        buttonPanel.add(buttonEasy);

        buttonNormal = new MenuButton("NORMAL", ImageLoader.getImage(ImageLoader.MENU_BUTTON2));
        buttonNormal.addActionListener(e -> {
            // Hide this window
            menuPanel.setVisible(false);
            menuFrame.dispose();

            // Create MenuMain and display it
            try {
                new SpielWindow(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        buttonPanel.add(buttonNormal);

        buttonHard = new MenuButton("HARD", ImageLoader.getImage(ImageLoader.MENU_BUTTON2));
        buttonHard.addActionListener(e -> {
            // Hide this window
            menuPanel.setVisible(false);
            menuFrame.dispose();
            // Create MenuMain and display it
            try {
                new SpielWindow(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        buttonPanel.add(buttonHard);
        makeConstraints(buttonPanel, 1, 4, 2);

        buttonShipSize = new MenuButton("POKEMON SIZE", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonShipSize.addActionListener(e -> {
            menuPanel.setVisible(false);

            try {
                new MenuShipSize(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        makeConstraints(buttonShipSize, 1, 6, 2);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 8, 2);

        menuFrame.add(menuPanel);

        menuFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                INITIAL_WIDTH = menuFrame.getWidth();
                INITIAL_HEIGHT = menuFrame.getHeight();

                COL = (INITIAL_WIDTH * 20 / 100) - 10;
                C_GAP = (INITIAL_WIDTH * 30 / 100) - 10;
                ROW_INFO = (INITIAL_HEIGHT * 33 / 100) - 10;
                ROW = (INITIAL_HEIGHT * 10 / 100) - 10;
                R_GAP = (INITIAL_HEIGHT * 2) / 100;

                menuLayout.columnWidths = new int[]{C_GAP, COL, COL, C_GAP};
                menuLayout.rowHeights = new int[]{ROW_INFO, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, ROW};

                menuPanel.revalidate();
                menuPanel.repaint();
            }
        });
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
