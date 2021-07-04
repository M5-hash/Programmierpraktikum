package src;

import src.components.CustomPanel;
import src.components.MenuInformation;
import src.components.PanelSize;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static src.config.*;

/**
 * Sizemenü des Spiels <br>
 * Hier können die Größen des Feldes und der Schiffe eingestellt werden
 */
public class MenuSize {
    /**
     * Constraints für das GridbagLayout
     */
    GridBagConstraints constraints;
    /**
     * GridBaglayout der Panelsize <br>
     */
    GridBagLayout menuLayout;
    /**
     * Informationen zum Menü
     */
    JPanel menuInformation;
    /**
     * Panel mit Slidern und Textfeldern um Größe des Feldes und der Schiffe anzupassen
     */
    JPanel inputPanel;
    /**
     * Panel auf dem die Components hinzugefügt werden
     */
    JPanel menuPanel;
    /**
     * Frame des Menü
     */
    JFrame menuFrame;

    /**
     * Erstellt die Components des Menü
     * @param menuFrame Frame des Menü
     * @param previousPanel Panel mit Components des vorherigen Menüs
     * @throws IOException Fehler beim Laden der Grafiken
     * @throws FontFormatException Fehler beim Laden der Font
     */
    public MenuSize(JFrame menuFrame, JPanel previousPanel) throws IOException, FontFormatException{
        this.menuFrame = menuFrame;

        int C_GAP = menuFrame.getWidth() / 10;
        int COL = menuFrame.getWidth() * 8 / 10;
        int ROW = menuFrame.getHeight() * 50 / 100;
        int ROW_INFO = menuFrame.getHeight() * 35 / 100;
        int R_GAP = (INITIAL_HEIGHT * 2) / 100;
        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[]{C_GAP, COL, C_GAP};
        menuLayout.rowHeights = new int[]{ROW_INFO, R_GAP, ROW, R_GAP};
        constraints = new GridBagConstraints();

        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.MENU_BG));
        menuPanel.setLayout(menuLayout);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), selectedTheme.equals("Pokemon") ? TextSize : TextSize.replace("Pokemon", "Ships"), menuFrame);
        makeConstraints(menuInformation, 0, 0, 3);

        inputPanel = new PanelSize(menuFrame, ImageLoader.getImage(ImageLoader.OPTIONS_BACKGROUND), previousPanel, menuPanel);
        makeConstraints(inputPanel, 1, 2, 1);

        menuFrame.add(menuPanel);
    }
    /**
     * Legt Größe und Position der Components fest
     * @param comp Ein Swing Component, welcher in constraints embedded werden soll.
     * @param gridx X-Position auf der X-Achse
     * @param gridy Y-Position auf der Y-Achse
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
        menuPanel.add(comp, constraints);
    }
}
