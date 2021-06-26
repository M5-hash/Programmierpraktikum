package src.components;

import src.ImageLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;
import static src.FontLoader.Pokemon;
import static src.config.selectedTheme;

public class CustomComboBox2 extends JPanel {

    JComboBox<String> comboBox;

    public CustomComboBox2(String[] text, Font Pokemon) {

        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.black));

        DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer();
        listCellRenderer.setHorizontalAlignment(CENTER);

        comboBox = new JComboBox<>(text);
        comboBox.setOpaque(false);
        comboBox.setBackground(Color.darkGray);
        comboBox.setRenderer(listCellRenderer);
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBox.setFont(Pokemon);
        comboBox.addActionListener(e -> {
            selectedTheme = (String) comboBox.getSelectedItem();
            System.out.println(selectedTheme);
        });
        add(comboBox, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(ImageLoader.getImage(ImageLoader.MENU_BUTTON), 0, 0, getWidth(), getHeight(), this);
        super.paintComponent(g);
    }
}
