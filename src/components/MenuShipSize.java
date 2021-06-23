package src.components;

import src.ImageLoader;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import static src.config.*;

public class MenuShipSize {

    GridBagConstraints  constraints;
    GridBagLayout       menuLayout;
    JTextField          textField;
    JButton             buttonCancel;
    JButton             buttonApply;
    JSlider             menuSlider;
    JLabel              menuSlidername;
    JPanel              menuInformation;
    JPanel              buttonPanel;
    JPanel              menuPanel;
    JFrame              menuframe;

    public MenuShipSize(JFrame menuFrame, JPanel previousPanel) throws IOException, FontFormatException {
        this.menuframe = menuFrame;

        C_GAP = menuFrame.getWidth() / 10;
        COL = menuFrame.getWidth() * 60 / 100;
        ROW = menuFrame.getHeight() / 10;
        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[] {C_GAP, C_GAP, C_GAP, COL, C_GAP};
        menuLayout.rowHeights = new int[] {ROW, ROW, ROW, ROW, ROW,
                                            ROW, ROW, ROW, ROW, ROW};
        constraints = new GridBagConstraints();

        menuPanel = new CustomPanel(ImageLoader.getImage(ImageLoader.STARTMENU_BG));
        menuPanel.setLayout(menuLayout);

        buttonPanel = new JPanel(new GridLayout(1,0,5,5));
        buttonPanel.setOpaque(false);

        menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextSize, menuFrame);
        makeConstraints(menuInformation, 0,0, 5, 3);

        for (int i = 1; i <= 5; i++) {
            menuSlidername = new JLabel("<html><body>   Size " + (i+1) + "<br>Pokemon</body></html>");
            makeConstraints(menuSlidername, 1, i+2, 1, 1);
        }

        for(int i = 1; i <= 5; i++){
            textField = new JTextField();
            textField.setBorder(null);
            makeConstraints(textField, 2, i+2, 1, 1);
        }

        for(int i = 1; i <= 5; i++){
            menuSlider = new MenuSlider();
            menuSlider.setOpaque(false);
            makeConstraints(menuSlider, 3, i+2, 1,1);
        }

        buttonApply = new MenuButton("APPLY", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonApply.addActionListener(e -> {

        });
        buttonPanel.add(buttonApply);

        buttonCancel = new MenuButton("CANCEL", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonCancel.addActionListener(e -> {

        });
        buttonPanel.add(buttonCancel);
        makeConstraints(buttonPanel, 1, 8, 3,1);

        menuFrame.add(menuPanel);
    }

    private void makeConstraints(JComponent comp, int gridx, int gridy, int gridweight, int gridheight) {
        constraints.insets = new Insets(0, 5,5,5);
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridheight = gridheight;
        constraints.gridwidth = gridweight;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        menuPanel.add(comp, constraints);
    }
}
