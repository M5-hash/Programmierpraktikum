package src;

import src.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static javax.swing.SwingConstants.CENTER;
import static src.FontLoader.Pokemon;
import static src.config.*;

public class MenuOptions {
    GridBagLayout menuLayout;
    GridBagConstraints constraints;
    JButton buttonMenuStart;
    JCheckBox cbFullscreen;
    JComboBox<String> cbResolutions;
    JComboBox<String> cbThemes;
    JButton buttonShipSize;
    JButton buttonQuitGame;
    JPanel menuSize;
    JPanel menuInformation;
    JPanel buttonPanel;
    JPanel menuPanel;
    JFrame menuFrame;

    public MenuOptions(int x, int y) throws IOException, FontFormatException {
        menuFrame = new MenuFrame("Pokemon - Multiplayer", x, y);

        INITIAL_HEIGHT = menuFrame.getHeight();
        INITIAL_WIDTH = menuFrame.getWidth();

        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[] {C_GAP, COL, COL, C_GAP};
        menuLayout.rowHeights = new int[] {ROW_INFO, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, R_GAP, ROW, ROW};
        constraints = new GridBagConstraints();

        constraints = new GridBagConstraints();

        menuPanel = new MenuPanel(ImageLoader.getImage(ImageLoader.MENU_BG));
        menuPanel.setLayout(menuLayout);

        menuSize = new MenuShipSize(menuPanel);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextOptions, menuFrame);
        makeConstraints(menuInformation, 0, 0, 4, 1);

        buttonMenuStart = new MenuButton("MAIN MENU", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonMenuStart.addActionListener(e -> {
            // Hide this window
            menuFrame.setVisible(false);
//            menuFrame.dispose();

            // Create MenuMain and display it
            try {
                new MenuStart(menuFrame.getX(), menuFrame.getY());
            } catch (IOException | FontFormatException ioException) {
                ioException.printStackTrace();
            }
        });
        makeConstraints(buttonMenuStart, 1, 2, 2, 1);

        buttonPanel = new ButtonPanel();

        DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer();
        listCellRenderer.setHorizontalAlignment(CENTER);

        cbFullscreen = new JCheckBox("Fullscreen", false);
        cbFullscreen.setMnemonic(KeyEvent.VK_C);
        cbFullscreen.setHorizontalAlignment((int) JCheckBox.CENTER_ALIGNMENT);
        cbFullscreen.setIconTextGap(5);
        cbFullscreen.setFont(Pokemon);
        cbFullscreen.addItemListener(e -> {
            if(cbFullscreen.isSelected()){
                fullscreen = true;
                System.out.println("Fullscreen active");
            } else {
                System.out.println("Fullscreen inactive");
            }
        });
        buttonPanel.add(cbFullscreen);

        cbResolutions = new JComboBox<>(Resolutions);
        cbResolutions.setRenderer(listCellRenderer);
        cbResolutions.setAlignmentX(Component.CENTER_ALIGNMENT);
        cbResolutions.setFont(Pokemon);
        cbResolutions.addActionListener(e -> {
            selectedResolution = (String) cbResolutions.getSelectedItem();
            System.out.println(selectedResolution);
            String[] segments = selectedResolution.split("x");
            GF_WIDTH = Integer.parseInt(segments[0]);
            GF_HEIGHT = Integer.parseInt(segments[1]);
            System.out.println("Frame width: " + GF_WIDTH);
            System.out.println("Frame height: " + GF_HEIGHT);
        });
        buttonPanel.add(cbResolutions);
        makeConstraints(buttonPanel, 1, 4, 2, 1);

        buttonPanel = new ButtonPanel();

        cbThemes = new JComboBox<>(Themes);
        cbThemes.setRenderer(listCellRenderer);
        cbThemes.setAlignmentX(Component.CENTER_ALIGNMENT);
        cbThemes.setFont(Pokemon);
        cbThemes.addActionListener(e -> {
            selectedTheme = (String) cbThemes.getSelectedItem();
            System.out.println(selectedTheme);
        });
        buttonPanel.add(cbThemes);

        buttonShipSize = new MenuButton("Size", ImageLoader.getImage(ImageLoader.MENU_BUTTON3));
        buttonShipSize.addActionListener(e -> {

        });
        buttonPanel.add(buttonShipSize);
        makeConstraints(buttonPanel, 1, 6, 2, 1);

        buttonQuitGame = new QuitButton();
        makeConstraints(buttonQuitGame, 1, 8, 2, 1);

        menuFrame.add(menuPanel);
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
