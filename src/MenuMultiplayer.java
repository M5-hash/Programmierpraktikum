package src;

import src.components.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static src.config.*;


public class MenuMultiplayer {

    GridBagLayout menuLayout;
    GridLayout buttonPanelLayout;
    GridBagConstraints constraints;
    JButton buttonMenuStart;
    JButton buttonMenuHost;
    JButton buttonJoin;
    JButton buttonShipSize;
    JButton buttonQuitGame;
    JPanel menuFiller;
    JPanel getIP;
    JPanel menuSize;
    JPanel menuInformation;
    JPanel buttonPanel;
    JPanel menuPanel;
    JFrame menuFrame;

    public MenuMultiplayer(JFrame menuFrame, JPanel menuMain) throws IOException, FontFormatException {
        this.menuFrame = menuFrame;

        INITIAL_HEIGHT = menuFrame.getHeight();
        INITIAL_WIDTH = menuFrame.getWidth();


        COL = (INITIAL_WIDTH * 20 / 100) - 10;
        C_GAP = (INITIAL_WIDTH * 30 / 100) - 10;
        ROW_INFO = (INITIAL_HEIGHT * 33 / 100) - 10;
        ROW = (INITIAL_HEIGHT * 10 / 100) - 10;
        R_GAP = (INITIAL_HEIGHT * 2) / 100;
        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[] {C_GAP, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[] {ROW_INFO, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, ROW};
        constraints = new GridBagConstraints();

        constraints = new GridBagConstraints();

        this.menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        this.menuPanel.setLayout(menuLayout);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextMulitplayer, menuFrame);
        makeConstraints(menuInformation, 0, 0, 4, 1);

        buttonMenuStart = new MenuButton("MAIN MENU", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            menuPanel.setVisible(false);

            // Create MenuMain and display it
            menuMain.setVisible(true);
        });
        makeConstraints(buttonMenuStart, 1, 2, 2, 1);

        buttonPanel = new ButtonPanel();

        buttonJoin = new MenuButton("JOIN GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON3));
        buttonJoin.addActionListener(e -> {
            String[] options = new String[] {"Player", "Computer", "Cancel"};
            ImageIcon icon = new ImageIcon("");
            int x = JOptionPane.showOptionDialog(menuFrame, "Wollen Sie selbst spielen oder als Computer?",
                    "Selfplay or KI", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);

            if(x == 0){
                menuPanel.setVisible(false);
                menuFrame.dispose();
                // Create SpielWindow and display it
                try {
                    new SpielWindow(menuFrame, menuPanel);
                } catch (IOException | FontFormatException ioException) {
                    ioException.printStackTrace();
                }
            } else if (x == 1){
                menuPanel.setVisible(false);
                menuFrame.dispose();
                // Create SpielWindow and display it
                try {
                    new SpielWindow(menuFrame, menuPanel);
                } catch (IOException | FontFormatException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                System.out.println("no");
            }
        });
        buttonPanel.add(buttonJoin);

        buttonMenuHost = new MenuButton("HOST GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON3));
        buttonMenuHost.addActionListener(e -> {
            String[] options = new String[] {"Player", "Computer", "Cancel"};
            ImageIcon icon = new ImageIcon("");
            int x = JOptionPane.showOptionDialog(menuFrame, "Wollen Sie selbst spielen oder als Computer?",
                    "Selfplay or KI", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);

            if(x == 0){
                menuPanel.setVisible(false);
                menuFrame.dispose();
                // Create SpielWindow and display it
                try {
                    new SpielWindow(menuFrame, menuPanel);
                } catch (IOException | FontFormatException ioException) {
                    ioException.printStackTrace();
                }
            } else if (x == 1){
                menuPanel.setVisible(false);
                menuFrame.dispose();
                // Create SpielWindow and display it
                try {
                    new SpielWindow(menuFrame, menuPanel);
                } catch (IOException | FontFormatException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                System.out.println("no");
            }
        });
        buttonPanel.add(buttonMenuHost);
        makeConstraints(buttonPanel, 1, 4, 2, 1);

        buttonPanel = new ButtonPanel();

        getIP = new Textfield("Enter IP");
        buttonPanel.add(getIP);

        buttonShipSize = new MenuButton("Size", ImageLoader.getImage(ImageLoader.MENU_BUTTON3));
        buttonShipSize.addActionListener(e -> {
            menuPanel.setVisible(false);

            try {
                new MenuSize(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        buttonPanel.add(buttonShipSize);
        makeConstraints(buttonPanel, 1, 6, 2, 1);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 8, 2, 1);

        menuFrame.add(this.menuPanel);
    }

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
