package src;

import src.components.CustomPanel;
import src.components.HostPanel;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static src.config.*;

public class MenuHost {

    GridBagLayout       menuLayout;
    GridBagConstraints  constraints;
    JFrame              menuframe;
    JPanel              menuHost;
    JPanel              menuPanel;

    public MenuHost(JFrame menuFrame, JPanel previousPanel) throws IOException {
        this.menuframe = menuFrame;

        COL     = INITIAL_WIDTH * 60 / 100;
        C_GAP   = INITIAL_WIDTH * 20 / 100;
        ROW     = INITIAL_HEIGHT * 80 / 100;
        R_GAP   = INITIAL_HEIGHT * 10 / 100;
        menuLayout = new GridBagLayout();
        constraints = new GridBagConstraints();
        menuLayout.columnWidths = new int[] {C_GAP, COL, C_GAP};
        menuLayout.rowHeights   = new int[] {R_GAP, ROW, R_GAP};

        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.MENU_BG));
        menuPanel.setLayout(menuLayout);

        menuHost = new HostPanel(menuFrame, menuPanel, previousPanel);

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
