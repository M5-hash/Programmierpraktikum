package src;

import src.components.*;
import javax.swing.*;
import java.awt.*;
import static src.config.*;


public class MenuMultiplayer {

    GridBagLayout menuLayout;
    GridBagConstraints constraints;
    JButton buttonMenuStart;
    JButton buttonMenuHost;
    JButton buttonJoin;
    JButton buttonShipSize;
    JButton buttonQuitGame;
    JTextField getIP;
    JPanel menuSize;
    JPanel menuInformation;
    JPanel menuFiller;
    JPanel menuPanel;
    JFrame menuFrame;

    public MenuMultiplayer(int x, int y) {

        menuFrame = new MenuFrame("Pokemon - Multiplayer", x, y);

        INITIAL_HEIGHT = menuFrame.getHeight();
        INITIAL_WIDTH = menuFrame.getWidth();

        COL = INITIAL_WIDTH * 25 / 100;
        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[] {C_GAP, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[] {ROW_INFO, ROW, ROW, ROW, ROW};

        constraints = new GridBagConstraints();

        menuPanel = new MenuPanel(ImageLoader.getImage(ImageLoader.MENU_BG));
        menuPanel.setLayout(menuLayout);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextMulitplayer, 17, 88, 0, 45 );
        makeConstraints(menuInformation, 0, 0, 5, 1, 0, 0, 0, 0);

        buttonMenuStart = new MenuButton("MAIN MENU");
        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
            menuFrame.dispose();

            // Create MenuMain and display it
            new MenuStart(menuFrame.getX(), menuFrame.getY());
        });
        makeConstraints(buttonMenuStart, 1, 1, 2, 1, 5, 5, 5, 5);

        buttonJoin = new MenuButton("JOIN GAME");
        makeConstraints(buttonJoin, 1, 2, 1, 1, 5, 5, 5, 5);

        buttonMenuHost = new MenuButton("HOST GAME");
        makeConstraints(buttonMenuHost, 2, 2, 1, 1, 5, 5, 5, 5);

        buttonShipSize = new MenuButton("POKEMON SIZE");
        makeConstraints(buttonShipSize, 1, 3, 1, 1, 5, 5, 5, 5);

        getIP = new JTextField("ENTER IP");
        getIP.setHorizontalAlignment(SwingConstants.CENTER);
        getIP.setBorder(null);
        getIP.addActionListener(e -> System.out.println(getIP.getText()));
        makeConstraints(getIP, 2, 3, 1, 1, 5, 5, 5, 5);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 4, 2, 1, 5, 5, 5, 5);

        menuFrame.add(menuPanel);
    }

    private void makeConstraints(JComponent comp, int gridx, int gridy, int gridwidth, int gridheight, int top, int left, int bottom, int right) {
        constraints.insets = new Insets(top, left, bottom, right);
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
