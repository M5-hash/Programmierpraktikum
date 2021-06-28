package src.components;

import src.ImageLoader;
import src.Server;
import src.SpielWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

import static src.config.*;

public class HostPanel extends JPanel {

    private final BufferedImage background;

    GridBagLayout       menuLayout;
    GridBagConstraints  constraints;
    JPanel              buttonPanel;
    JScrollPane         scrollPane;
    JButton             buttonMenuMultiplayer;
    JButton             buttonStartGame;
    JList<String> listIP;

    public HostPanel(JFrame menuFrame, JPanel menuHost, JPanel previousPanel, BufferedImage image) throws IOException {

        this.background = image;

        int width   = menuFrame.getWidth() * 60 / 100;
        int height = menuFrame.getHeight() * 60 / 100;

        COL         = width * 25 / 100;
        C_GAP       = width * 5 / 100;
        ROW_INFO    = height * 70 / 100;
        ROW         = height * 10 / 100;
        menuLayout  = new GridBagLayout();
        menuLayout.columnWidths = new int[] {COL, COL, COL, COL};
        menuLayout.rowHeights = new int[] {ROW_INFO, ROW, ROW, ROW};
        constraints = new GridBagConstraints();

        setLayout(menuLayout);
        setOpaque(false);

        listIP                = new JList<>(IP_Ausgabe());
        listIP.setBorder(new LineBorder(Color.black));
        listIP.setBackground(new Color(248, 247, 201));
        scrollPane            = new JScrollPane(listIP);


        buttonPanel           = new JPanel(new GridLayout(1, 0, 5,5));
        buttonMenuMultiplayer = new MenuButton("MULTIPLAYER", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonStartGame       = new MenuButton("START GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));

        buttonPanel.setOpaque(false);

        buttonMenuMultiplayer.addActionListener(e -> {
            menuHost.setVisible(false);
            previousPanel.setVisible(true);
        });
        buttonStartGame.addActionListener(e -> {
            String[] options = new String[] {"Player", "Computer", "Cancel"};

            ImageIcon icon = new ImageIcon("");
            int x = JOptionPane.showOptionDialog(menuFrame, "Wollen Sie selbst spielen oder als Computer?",
                    "Selfplay or KI", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);

            if(x == 0){
                menuHost.setVisible(false);
                menuFrame.dispose();
                // Create SpielWindow and display it
                try {

                    Server server = new Server("setup", fieldsize, getShipString(), menuFrame, KI);
                } catch (Exception ioException) {
                    ioException.printStackTrace();
                }
            } else if (x == 1){
                KI = true;
                menuHost.setVisible(false);
                menuFrame.dispose();
                // Create SpielWindow and display it
                try {
                    new SpielWindow(menuFrame, KI);
                } catch (IOException | FontFormatException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                System.out.println("no");
            }
        });

        buttonPanel.add(buttonMenuMultiplayer);
        buttonPanel.add(buttonStartGame);

        makeConstraints(scrollPane, 0, 0, 4);
        makeConstraints(buttonPanel, 0, 2, 4);
    }

    private String getShipString() {

        String hold = "";
        for(int i = 0; i < size2; i++){
            hold += "2 ";
        }
        for(int i = 0; i < size3; i++){
            hold += "3 ";
        }
        for(int i = 0; i < size4; i++){
            hold += "4 ";
        }
        for(int i = 0; i < size5; i++){
            hold += "5 ";
        }
        return hold;
    }

    public static String[] IP_Ausgabe() throws IOException {
        ArrayList<String> IPs = new ArrayList<String>();
        Enumeration<NetworkInterface> nis =
                NetworkInterface.getNetworkInterfaces();
        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            Enumeration<InetAddress> ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = ias.nextElement();
                if (!ia.isLoopbackAddress() && ia.getHostAddress().startsWith("192.")) {
                    IPs.add(ia.getHostAddress());
                }
            }
        }
        return IPs.toArray(new String[0]);
    }

    private void makeConstraints(JComponent comp, int gridx, int gridy, int gridwidth) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = 1;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.BOTH;
        add(comp, constraints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
