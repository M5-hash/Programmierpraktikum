package src;

import src.components.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.SwingConstants.CENTER;
import static src.FontLoader.Pokemon;
import static src.config.*;

public class MenuOptions {
    GridBagLayout menuLayout;
    GridBagConstraints constraints;
    JButton buttonMenuStart;
    JPanel cbResolutions;
    JPanel cbThemes;
    JButton buttonShipSize;
    JButton buttonQuitGame;
    JPanel cbFullscreen;
    JPanel menuInformation;
    JPanel buttonPanel;
    JPanel menuPanel;
    JFrame menuFrame;

    public MenuOptions(JFrame menuFrame, JPanel menuMain) throws IOException, FontFormatException {
        this.menuFrame = menuFrame;

        int COL         = (INITIAL_WIDTH * 22 / 100) - 10;
        int C_GAP       = (INITIAL_WIDTH * 28 / 100) - 10;
        int ROW_INFO    = (INITIAL_HEIGHT * 33 / 100) - 10;
        int ROW         = (INITIAL_HEIGHT * 10 / 100) - 10;
        int R_GAP       = (INITIAL_HEIGHT * 2) / 100;
        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[] {C_GAP, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[] {ROW_INFO, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, ROW};
        constraints = new GridBagConstraints();

        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuPanel.setLayout(menuLayout);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextOptions, menuFrame);
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

        DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer();
        listCellRenderer.setHorizontalAlignment(CENTER);

        cbFullscreen = new CustomCheckBox("FULLSCREEN", false, Pokemon.deriveFont(11f));
        makeConstraints(cbFullscreen, 1, 4, 2);

        buttonPanel = new ButtonPanel();

        cbResolutions = new CustomComboBox(Resolutions, Pokemon.deriveFont(11f));
        cbThemes = new CustomComboBox2(Themes, Pokemon.deriveFont(11f));

        buttonPanel.add(cbResolutions);
        buttonPanel.add(cbThemes);
        makeConstraints(buttonPanel, 1, 6, 2);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 8, 2);

        menuFrame.add(menuPanel);
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
