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
    GridBagConstraints constraints;
    JButton buttonMenuStart;
    JButton buttonMenuHost;
    JButton buttonJoin;
    JButton buttonQuitGame;
    JButton buttonConfirm;
    JButton buttonCancel;
    JPanel getIP;
    JPanel menuInformation;
    JPanel buttonPanel1;
    JPanel buttonPanel2;
    JPanel menuPanel;
    JFrame menuFrame;

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
        buttonPanel2 = new ButtonPanel();
        buttonJoin = new MenuButton("JOIN GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuHost = new MenuButton("HOST GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonQuitGame = new QuitButton();
        buttonConfirm = new MenuButton("CONFIRM", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonCancel = new MenuButton("CANCEL", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
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

                if(x==0 || x == 1) {
                    menuPanel.setVisible(false);
                    //menuFrame.dispose();
                }
                if (x == 0) {
                    onlineCom = false;
                    // Create SpielWindow and display it
                    try {
                        Client client = new Client(IP, menuFrame);
                        SpielFeld1 = 0;
                        client.setSpielwindow(new SpielWindow(menuFrame, client));

                    } catch (Exception ioException) {
                        ioException.printStackTrace();
                    }
                } else if (x == 1) {
                    onlineCom = true;
                    try {

                        Client client = new Client(IP, menuFrame);
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

        buttonPanel2.add(buttonConfirm);
        buttonPanel2.add(buttonCancel);

        makeConstraints(menuInformation, 0, 0, 4, 1);
        makeConstraints(buttonMenuStart, 1, 2, 2, 1);
        makeConstraints(buttonPanel1, 1, 4, 2, 1);
        makeConstraints(getIP, 1, 6, 2, 1);
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
