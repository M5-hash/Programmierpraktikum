package src.components;

import src.ImageLoader;
import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import static src.FontLoader.Pokemon;
import static src.config.*;

public class PanelSize extends JPanel{

    private final BufferedImage background;

    int temp_fieldsize = fieldsize;
    int temp_fields = temp_fieldsize * temp_fieldsize;
    int temp_size2 = size2;
    int temp_size3 = size3;
    int temp_size4 = size4;
    int temp_size5 = size5;
    int temp_fieldsleft = 16;

    GridBagConstraints  constraints;
    GridBagLayout       menuLayout;
    JButton             buttonCancel;
    JButton             buttonApply;
    JSlider             menuSlider1;
    JSlider             menuSlider2;
    JSlider             menuSlider3;
    JSlider             menuSlider4;
    JSlider             menuSlider5;
    JLabel              menuCounter;
    JLabel              menuSlidername1;
    JLabel              menuSlidername2;
    JLabel              menuSlidername3;
    JLabel              menuSlidername4;
    JLabel              menuSlidername5;
    JTextField          textField1;
    JTextField          textField2;
    JTextField          textField3;
    JTextField          textField4;
    JTextField          textField5;
    JPanel              buttonPanel;

    public PanelSize(JFrame menuFrame, BufferedImage image, JPanel previousPanel, JPanel menuPanel) {
        this.background = image;
        setOpaque(false);

        int width = menuFrame.getWidth() * 8 / 10;
        int height = menuFrame.getHeight() * 65 / 100;

        int C_GAP2 = width / 20;
        C_GAP = width / 20;
        COL = width * 60 / 100;
        ROW = ((height - C_GAP2) / 7) - 10;
        menuLayout = new GridBagLayout();
        menuLayout.columnWidths = new int[] {C_GAP2, C_GAP, C_GAP, COL, C_GAP2};
        menuLayout.rowHeights = new int[] {C_GAP2, ROW, ROW, ROW, ROW, ROW, ROW, ROW, C_GAP2};
        constraints = new GridBagConstraints();

        setLayout(menuLayout);

        buttonPanel = new JPanel(new GridLayout(1,0,5,5));
        buttonPanel.setOpaque(false);

        menuCounter = new JLabel();
        menuCounter.setText("Fields left: " + temp_fieldsleft);
        menuCounter.setFont(Pokemon);
        makeConstraints(menuCounter, 1, 1,  3);

        menuSlidername1 = new JLabel("Fieldsize");
        menuSlidername1.setFont(Pokemon.deriveFont(11f));
        makeConstraints(menuSlidername1, 1, 2, 1);

        menuSlidername2 = new JLabel("<html><body>   Size  2<br>Pokemon</body></html>");
        menuSlidername2.setFont(Pokemon.deriveFont(11f));
        makeConstraints(menuSlidername2, 1, 3, 1);

        menuSlidername3 = new JLabel("<html><body>   Size 3<br>Pokemon</body></html>");
        menuSlidername3.setFont(Pokemon.deriveFont(11f));
        makeConstraints(menuSlidername3, 1, 4, 1);

        menuSlidername4 = new JLabel("<html><body>   Size 4<br>Pokemon</body></html>");
        menuSlidername4.setFont(Pokemon.deriveFont(11f));
        makeConstraints(menuSlidername4, 1, 5, 1);

        menuSlidername5 = new JLabel("<html><body>   Size 5<br>Pokemon</body></html>");
        menuSlidername5.setFont(Pokemon.deriveFont(11f));
        makeConstraints(menuSlidername5, 1, 6, 1);

        textField1 = new sizeTextfield(fieldsize);
        textField1.addActionListener(e -> {
            String typed = textField1.getText();
            menuSlider1.setValue(5);
            if(!typed.matches("\\d+") || typed.length() > 3 ) {
                return;
            }
            int value = Integer.parseInt(typed);
            menuSlider1.setValue(value);
        });

        makeConstraints(textField1, 2, 2, 1);

        textField2 = new sizeTextfield(size2);
        textField2.addActionListener(e -> {
                String typed = textField2.getText();
                menuSlider2.setValue(0);
                if(!typed.matches("\\d+") || typed.length() > 3) {
                    return;
                }
                int value = Integer.parseInt(typed);
                menuSlider2.setValue(value);
        });
        makeConstraints(textField2, 2, 3, 1);

        textField3 = new sizeTextfield(size3);
        textField3.addActionListener(e -> {
                String typed = textField3.getText();
                menuSlider3.setValue(0);
                if(!typed.matches("\\d+") || typed.length() > 3) {
                    return;
                }
                int value = Integer.parseInt(typed);
                menuSlider3.setValue(value);
        });
        makeConstraints(textField3, 2, 4, 1);

        textField4 = new sizeTextfield(size4);
        textField4.addActionListener(e -> {
                String typed = textField4.getText();
                menuSlider4.setValue(0);
                if(!typed.matches("\\d+") || typed.length() > 3) {
                    return;
                }
                int value = Integer.parseInt(typed);
                menuSlider4.setValue(value);
        });
        makeConstraints(textField4, 2, 5, 1);

        textField5 = new sizeTextfield(size5);
        textField5.addActionListener(e -> {
                String typed = textField5.getText();
                menuSlider5.setValue(0);
                if(!typed.matches("\\d+") || typed.length() > 3) {
                    return;
                }
                int value = Integer.parseInt(typed);
                menuSlider5.setValue(value);
        });
        makeConstraints(textField5, 2, 6, 1);

        menuSlider1 = new SliderSize();
        menuSlider1.setMinimum(5);
        menuSlider1.setValue(fieldsize);
        menuSlider1.addChangeListener(e -> {
            textField1.setText(String.valueOf(menuSlider1.getValue()));
            UpdateMenuCounter();
        });
        makeConstraints(menuSlider1, 3, 2, 1);

        menuSlider2 = new SliderSize();
        menuSlider2.setValue(size2);
        menuSlider2.addChangeListener(e -> {
            textField2.setText(String.valueOf(menuSlider2.getValue()));
            UpdateMenuCounter();
        });
        makeConstraints(menuSlider2, 3, 3, 1);

        menuSlider3 = new SliderSize();
        menuSlider3.setValue(size3);
        menuSlider3.addChangeListener(e -> {
            textField3.setText(String.valueOf(menuSlider3.getValue()));
            UpdateMenuCounter();
        });

        makeConstraints(menuSlider3, 3, 4, 1);

        menuSlider4 = new SliderSize();
        menuSlider4.setValue(size4);
        menuSlider4.addChangeListener(e -> {
            textField4.setText(String.valueOf(menuSlider4.getValue()));
            UpdateMenuCounter();
        });
        makeConstraints(menuSlider4, 3, 5, 1);

        menuSlider5 = new SliderSize();
        menuSlider5.setValue(size5);
        menuSlider5.addChangeListener(e -> {
            textField5.setText(String.valueOf(menuSlider5.getValue()));
            UpdateMenuCounter();
        });
        makeConstraints(menuSlider5, 3, 6, 1);

        buttonApply = new MenuButton("APPLY", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonApply.addActionListener(e -> {
            menuPanel.setVisible(false);

            fieldsize = menuSlider1.getValue();
            size2 = menuSlider2.getValue();
            size3 = menuSlider3.getValue();
            size4 = menuSlider4.getValue();
            size5 = menuSlider5.getValue();
            System.out.println("------------------");
            System.out.println("APPLIED");
            System.out.println("Fieldsize: " + fieldsize);
            System.out.println("Size 2 Pokemon: " + size2);
            System.out.println("Size 3 Pokemon: " + size3);
            System.out.println("Size 4 Pokemon: " + size4);
            System.out.println("Size 5 Pokemon: " + size5);
            previousPanel.setVisible(true);
        });
        buttonPanel.add(buttonApply);

        buttonCancel = new MenuButton("CANCEL", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        buttonCancel.addActionListener(e -> {
            menuPanel.setVisible(false);
            temp_fieldsize = 10;
            temp_size2     = 1;
            temp_size3     = 1;
            temp_size4     = 1;
            temp_size5     = 1;
            System.out.println("------------------");
            System.out.println("CANCELLED");
            System.out.println("Fieldsize: " + fieldsize);
            System.out.println("Size 2 Pokemon: " + size2);
            System.out.println("Size 3 Pokemon: " + size3);
            System.out.println("Size 4 Pokemon: " + size4);
            System.out.println("Size 5 Pokemon: " + size5);
            previousPanel.setVisible(true);
        });
        buttonPanel.add(buttonCancel);
        makeConstraints(buttonPanel, 1, 7, 3);
    }

    private void UpdateMenuCounter() {
        temp_fieldsize  = menuSlider1.getValue();
        temp_fields     = temp_fieldsize * temp_fieldsize ;
        temp_size2      = menuSlider2.getValue() * 2;
        temp_size3      = menuSlider3.getValue() * 3;
        temp_size4      = menuSlider4.getValue() * 4;
        temp_size5      = menuSlider5.getValue() * 5;

        temp_fieldsleft = (temp_fields * 30 / 100) - temp_size2 - temp_size3 - temp_size4 - temp_size5 ;
        menuCounter.setText(String.valueOf(temp_fieldsleft));

        if(temp_fieldsleft < 0) {
            temp_fieldsleft = Math.abs(temp_fieldsleft);
            menuCounter.setForeground(Color.red);
            buttonApply.setUI(new MetalButtonUI(){
                protected Color getDisabledTextColor(){
                    return Color.red;
                }
            });
            buttonApply.setBackground(Color.darkGray);
            buttonApply.setEnabled(false);
            menuCounter.setText("Reduce the amount of Pokemon by " + temp_fieldsleft);
        } else {
            buttonApply.setEnabled(true);
            buttonApply.setForeground(Color.black);
            menuCounter.setText("Fields left " + temp_fieldsleft);
            menuCounter.setForeground(Color.black);
        }

        System.out.println("------------------");
        System.out.println("fields_left: " + temp_fieldsleft);
        System.out.println("fieldsize: " + temp_fieldsize);
        System.out.println("fields: " + temp_fields);
        System.out.println("size2: " + temp_size2);
        System.out.println("size3: " + temp_size3);
        System.out.println("size4: " + temp_size4);
        System.out.println("size5: " + temp_size5);
    }

    private void makeConstraints(JComponent comp, int gridx, int gridy, int gridwidth) {
        constraints.insets = new Insets(0, 5, 5, 5);
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridheight = 1;
        constraints.gridwidth = gridwidth;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        add(comp, constraints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}