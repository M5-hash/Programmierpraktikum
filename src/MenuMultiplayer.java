package src;

import src.components.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import static src.config.*;


public class MenuMultiplayer {

    GridBagLayout       menuLayout;
    GridBagConstraints  constraints;
    JButton             buttonMenuStart;
    JButton             buttonMenuHost;
    JButton             buttonJoin;
    JButton             buttonShipSize;
    JButton             buttonQuitGame;
    JPanel              getIP;
    JPanel              menuInformation;
    JPanel              buttonPanel1;
    JPanel              buttonPanel2;
    JPanel              menuPanel;
    JFrame              menuFrame;

    public MenuMultiplayer(JFrame menuFrame, JPanel menuMain) throws IOException, FontFormatException {
        this.menuFrame = menuFrame;

        int COL         = (INITIAL_WIDTH * 22 / 100) - 10;
        int C_GAP       = (INITIAL_WIDTH * 28 / 100) - 10;
        int ROW_INFO    = (INITIAL_HEIGHT * 33 / 100) - 10;
        int ROW         = (INITIAL_HEIGHT * 10 / 100) - 10;
        int R_GAP       = (INITIAL_HEIGHT * 2) / 100;
        menuLayout  = new GridBagLayout();
        menuLayout.columnWidths = new int[] {C_GAP, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[] {ROW_INFO, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, ROW};
        constraints = new GridBagConstraints();

        this.menuPanel  = new CustomPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextMulitplayer, menuFrame);
        buttonPanel1    = new ButtonPanel();
        buttonPanel2    = new ButtonPanel();
        getIP           = new TextFieldIP("Enter IP");
        buttonMenuStart = new MenuButton("MAIN MENU", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonJoin      = new MenuButton("JOIN GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuHost  = new MenuButton("HOST GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonShipSize  = new MenuButton("Size",      ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonQuitGame  = new QuitButton();

        this.menuPanel.setLayout(menuLayout);

        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            menuPanel.setVisible(false);

            // Create MenuMain and display it
            menuMain.setVisible(true);
        });
        buttonJoin.addActionListener(e -> {
            if(IP.equals("")){
                JOptionPane.showMessageDialog(null, "PLEASE ENTER AN IP");
            } else {

                SpielFeld2 = 2 ;

                String[] options = new String[] {"Player", "Computer", "Cancel"};
                ImageIcon icon = new ImageIcon("");
                int x = JOptionPane.showOptionDialog(menuFrame, "Wollen Sie selbst spielen oder als Computer?",
                        "Selfplay or KI", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);

            if(x == 0){
                menuPanel.setVisible(false);
                menuFrame.dispose();
                // Create SpielWindow and display it
                try {
                    Client client = new Client(IP);
                    SpielFeld1 = 0 ;
                    //new SpielWindow(menuFrame, menuPanel, client); //warum wird hier kein SpielWindow übergeben  ?
                } catch (Exception ioException) {
                    ioException.printStackTrace();
                }
            } else if (x == 1){
                menuPanel.setVisible(false);
                menuFrame.dispose();
                // Create SpielWindow and display it
                try {

                    SpielFeld1 = 1 ;
                    new SpielWindow(menuFrame);    //Gleiche frage:Warum wird hier kein Spielfeld übergeben, die Ergebnisse von Schüssen werden in der GUI nie erfasst, die Daten sollen auch weitergeschickt werden
                } catch (IOException | FontFormatException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                System.out.println("no");
            }
        }});
        buttonMenuHost.addActionListener(e -> {
            menuPanel.setVisible(false);
            try {
                new MenuHost(menuFrame, menuPanel);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        buttonShipSize.addActionListener(e -> {
            menuPanel.setVisible(false);

            try {
                new MenuSize(menuFrame, menuPanel);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });

        buttonPanel1.add(buttonJoin);
        buttonPanel1.add(buttonMenuHost);
        buttonPanel2.add(getIP);
        buttonPanel2.add(buttonShipSize);

        makeConstraints(menuInformation, 0, 0, 4);
        makeConstraints(buttonMenuStart, 1, 2, 2);
        makeConstraints(buttonPanel1, 1, 4, 2);
        makeConstraints(buttonPanel2, 1, 6, 2);
        makeConstraints(buttonQuitGame, 1, 8, 2);

        menuFrame.add(this.menuPanel);
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
