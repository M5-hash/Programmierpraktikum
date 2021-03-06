package src;

import src.components.CustomPanel;
import src.components.PanelHost;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static src.config.*;

/**
 * Hostmenü des Spiels
 */
public class MenuHost {
    /**
     * Constraints für das GridbagLayout
     */
    GridBagConstraints constraints;
    /**
     * GridBaglayout der Optionen
     */
    GridBagLayout menuLayout;
    /**
     * Panel mit InputComponents für das Hostmenü
     */
    JPanel menuHost;
    /**
     * Panel auf dem die Components hinzugefügt werden
     */
    JPanel menuPanel;
    /**
     * Frame des Menü
     */
    JFrame menuframe;

    /**
     * Components werden hier erstellt
     *
     * @param menuFrame     Frame des Hauptmenü
     * @param previousPanel Panel des vorherigen Menü
     * @throws IOException Fehler beim Laden der Grafiken
     */
    public MenuHost(JFrame menuFrame, JPanel previousPanel) throws IOException {
        this.menuframe = menuFrame;

        int COL = INITIAL_WIDTH * 80 / 100;
        int C_GAP = INITIAL_WIDTH * 10 / 100;
        int ROW = INITIAL_HEIGHT * 80 / 100;
        int R_GAP = INITIAL_HEIGHT * 10 / 100;
        menuLayout = new GridBagLayout();
        constraints = new GridBagConstraints();
        menuLayout.columnWidths = new int[]{C_GAP, COL, C_GAP};
        menuLayout.rowHeights = new int[]{R_GAP, ROW, R_GAP};

        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.MENU_BG));
        menuPanel.setLayout(menuLayout);

        menuHost = new PanelHost(menuFrame, menuPanel, previousPanel, ImageLoader.getImage(ImageLoader.OPTIONS_BACKGROUND));

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.BOTH;
        menuPanel.add(menuHost, constraints);

        menuFrame.add(menuPanel);
    }
}
