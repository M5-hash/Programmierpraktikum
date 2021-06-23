package src.components;

import src.ImageLoader;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import static src.config.*;

public class MenuShipSize{

    GridBagLayout menuLayout;
    GridBagConstraints constraints;
    JTextField textField;
    JButton buttonApply;
    JButton buttonCancel;
    JSlider menuSlider;
    JLabel menuSlidername;
    JPanel menuInformation;
    JPanel menuPanel;
    JFrame menuframe;

    public MenuShipSize(JFrame menuFrame, JPanel previousPanel) throws IOException, FontFormatException {
        this.menuframe = menuFrame;

        COL = menuFrame.getWidth() * 20 / 100;
        ROW = menuFrame.getHeight() * 5 / 100;
        menuLayout = new GridBagLayout();
//        menuLayout.columnWidths = new int[] {COL, COL, COL, COL, COL};
//        menuLayout.rowHeights = new int[] {ROW_INFO, ROW, ROW, ROW, ROW, ROW};
        constraints = new GridBagConstraints();

        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuPanel.setLayout(menuLayout);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextSize, menuPanel);
        makeConstraints(this.menuInformation, 0,0, 5);

        for (int i = 1; i <= 4; i++) {
            menuSlidername = new JLabel("<html><body>   Size " + (i+1) + "<br>Pokemon</body></html>");
            makeConstraints(menuSlidername, 0, i, 1);
        }

        for(int i = 1; i <= 4; i++){
            textField = new JTextField();
            textField.setBorder(null);
            makeConstraints(textField, 1, i, 1);
        }

        for(int i = 1; i <= 4; i++){
            menuSlider = new MenuSlider();
            menuSlider.setOpaque(false);
            makeConstraints(menuSlider, 2, i, 3);
        }

        buttonApply = new MenuButton("APPLY", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonApply.addActionListener(e -> {
            menuPanel.setVisible(false);
            previousPanel.setVisible(true);
            this.menuInformation.setVisible(true);
        });
        makeConstraints(buttonApply,1,5, 1);

        buttonCancel = new MenuButton("CANCEL", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonCancel.addActionListener(e -> {
            menuPanel.setVisible(false);
            previousPanel.setVisible(true);
            this.menuInformation.setVisible(true);
        });
        makeConstraints(buttonCancel,3,5, 1);
    }

    private void makeConstraints(JComponent comp, int gridx, int gridy, int gridweight) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridheight = 1;
        constraints.gridwidth = gridweight;
        menuPanel.add(comp, constraints);
    }
}
