package src;

import src.components.CustomPanel;
import src.components.MenuInformation;
import src.components.PanelSize;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static src.config.*;
import static src.config.ROW;

public class MenuSize {

    GridBagConstraints constraints;
    GridBagLayout menuLayout;
    JPanel menuInformation;
    JPanel inputPanel;
    JPanel menuPanel;
    JFrame menuFrame;

    public MenuSize(JFrame menuFrame, JPanel previousPanel) throws IOException, FontFormatException{
        this.menuFrame = menuFrame;

        C_GAP = menuFrame.getWidth() / 10;
        COL = menuFrame.getWidth() * 8 / 10;
        ROW = menuFrame.getHeight() * 50 / 100;
        ROW_INFO = menuFrame.getHeight() * 35 / 100;
        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[]{C_GAP, COL, C_GAP};
        menuLayout.rowHeights = new int[]{ROW_INFO, R_GAP, ROW, R_GAP};
        constraints = new GridBagConstraints();

        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.MENU_BG));
        menuPanel.setLayout(menuLayout);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextSize, menuFrame);
        makeConstraints(menuInformation, 0, 0, 3);

        inputPanel = new PanelSize(menuFrame, ImageLoader.getImage(ImageLoader.OPTIONS_BACKGROUND), previousPanel, menuPanel);
        makeConstraints(inputPanel, 1, 2, 1);

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
