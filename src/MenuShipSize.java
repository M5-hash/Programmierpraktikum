package src;

import src.components.MenuButton;
import src.components.MenuInformation;
import src.components.MenuSlider;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static src.config.*;


public class MenuShipSize extends JPanel {

    int COL1 = (INITIAL_WIDTH * 2 / 3) * 25 / 100;
    int COL2 = (INITIAL_WIDTH * 2 / 3) * 20 / 100;
    int COL3 = (INITIAL_WIDTH * 2 / 3) * 55 / 100;
    int ROW1 = (INITIAL_HEIGHT * 2 / 3) * 30 / 100;
    int ROWi = (INITIAL_HEIGHT * 2 / 3) * 10 / 100;

    GridBagLayout menuLayout;
    GridBagConstraints constraints;
    JTextField textField;
    JButton buttonApply;
    JButton buttonCancel;
    JSlider menuSlider;
    JLabel menuSlidername;
    JPanel menuInformation;

    public MenuShipSize(JPanel menuPanel) throws IOException, FontFormatException {
        setVisible(false);

        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[] {COL1, COL2, COL3};
        menuLayout.rowHeights = new int[] {ROW1, ROWi, ROWi, ROWi, ROWi};
        constraints = new GridBagConstraints();

        this.menuInformation = new MenuInformation(ImageLoader.getImage(ImageLoader.STARTMENU_BTN_TEXTFIELD_EICH), TextSize, this);
        makeConstraints(this.menuInformation, 0,0);

        for (int i = 1; i <= 4; i++) {
            menuSlidername = new JLabel("<html><body>   Size " + (i+1) + "<br>Pokemon</body></html>");
            makeConstraints(menuSlidername, 0, i);
        }

        for(int i = 1; i <= 4; i++){
            textField = new JTextField();
            textField.setBorder(null);
            makeConstraints(textField, 1, i);
        }

        for(int i = 1; i <= 4; i++){
            menuSlider = new MenuSlider();
            menuSlider.setOpaque(false);
            makeConstraints(menuSlider, 2, i);
        }

        buttonApply = new MenuButton("APPLY", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonApply.addActionListener(e -> {
            setVisible(false);
            menuPanel.setVisible(true);
            this.menuInformation.setVisible(true);
        });
        makeConstraints(buttonApply,1,5);

        buttonCancel = new MenuButton("CANCEL", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonCancel.addActionListener(e -> {
            setVisible(false);
            menuPanel.setVisible(true);
            this.menuInformation.setVisible(true);
        });
        makeConstraints(buttonApply,2,5);
    }

    private void makeConstraints(JComponent comp, int gridx, int gridy) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        add(comp, constraints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
