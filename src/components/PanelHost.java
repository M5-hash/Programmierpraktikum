package src.components;

import src.ImageLoader;
import src.MenuSize;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Inputpanel des Hostmenü
 */
public class PanelHost extends JPanel {

    /**
     * Hintergrundbild
     */
    private final BufferedImage background;
    /**
     * Constraints für das GridbagLayout
     */
    GridBagConstraints constraints;
    /**
     * GridBaglayout der Panelsize <br>
     */
    GridBagLayout menuLayout;
    /**
     * Panel für Inputcomponents
     */
    JPanel buttonPanel;
    /**
     * Scrollpane um Liste der IPs darzustellen
     */
    JScrollPane scrollPane;
    /**
     * Ruft das vorherige menuPanel auf
     */
    JButton buttonCancel;
    /**
     * Button um gespeicherten Spielstand zu laden
     */
    JButton buttonLoadGame;
    /**
     * Öffnet das Sizemenü
     */
    JButton buttonConfirm;
    /**
     * Liste der IPs
     */
    JList<String> listIP;

    /**
     * Erstellung der Components des Hostmenü
     *
     * @param menuFrame     JFrame auf dem die Components erstellt werden
     * @param menuHost      Aktuelles menuPanel des Sizemenüs
     * @param previousPanel menuPanel des zuvor zusehenden Panels
     * @param image         Hintergrundbild des Panels
     * @throws IOException Falls nicht alle Bilder geladen werden konnten
     */
    public PanelHost(JFrame menuFrame, JPanel menuHost, JPanel previousPanel, BufferedImage image) throws IOException {

        this.background = image;

        int width = menuFrame.getWidth() * 60 / 100;
        int height = menuFrame.getHeight() * 60 / 100;

        int COL = width * 25 / 100;
        int C_GAP = width * 10 / 100;
        int ROW_INFO = height * 70 / 100;
        int ROW = height * 10 / 100;
        int R_GAP = height * 5 / 100;
        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[]{C_GAP, COL, COL, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[]{C_GAP, ROW_INFO, R_GAP, ROW, ROW};
        constraints = new GridBagConstraints();

        setLayout(menuLayout);
        setOpaque(false);

        listIP = new JList<>(IP_Ausgabe());
        scrollPane = new JScrollPane(listIP);
        buttonPanel = new JPanel(new GridLayout(1, 0, 5, 5));
        buttonCancel = new MenuButton("CANCEL", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonLoadGame = new LoadGameButton(menuFrame, menuHost, "LOAD GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonConfirm = new MenuButton("START GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));

        buttonPanel.setOpaque(false);

        listIP.setBorder(new LineBorder(Color.black));
        listIP.setBackground(new Color(248, 247, 201));

        buttonCancel.addActionListener(e -> {
            menuHost.setVisible(false);
            previousPanel.setVisible(true);
        });
        buttonConfirm.addActionListener(e -> {
            menuHost.setVisible(false);
            try {
                new MenuSize(menuFrame, menuHost);
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });

        buttonPanel.add(buttonConfirm);
        buttonPanel.add(buttonLoadGame);
        buttonPanel.add(buttonCancel);

        makeConstraints(scrollPane, 1, 1, 4);
        makeConstraints(buttonPanel, 1, 3, 4);
    }

    public static String[] IP_Ausgabe() throws IOException {
        ArrayList<String> IPs = new ArrayList<>();
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

    /**
     * Legt Größe und Position der Components fest
     *
     * @param comp      Ein Swing Component, welcher in constraints embedded werden soll.
     * @param gridx     X-Position auf der X-Achse
     * @param gridy     Y-Position auf der Y-Achse
     * @param gridwidth Breite des Components
     */
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

    /**
     * Update des Hintergrundbilds
     *
     * @param g Übergibt Graphics Objekt
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
