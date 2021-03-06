package src.components;

import src.Bildloader;
import src.ImageLoader;
import src.MenuOptions;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.swing.SwingConstants.CENTER;
import static src.config.selectedTheme;

/**
 * CustomComboBox zur Auswahl der Themes
 */
public class CustomComboBox2 extends JPanel {
    /**
     * ComboBox mit Themes
     */
    JComboBox<String> comboBox;

    /**
     * Erzeugt Combobox mit Auswahl der Resolution <br>
     * Bei Änderung des Themes wird menuFrame repainted und revalidated und Hintergrund der Components angepasst
     *
     * @param Pokemon   Font der ComboBox
     * @param menuFrame JFrame der ComboBox
     * @param text      Stringarray mit Auswahl der Themes
     */
    public CustomComboBox2(String[] text, Font Pokemon, JFrame menuFrame) {

        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.black));

        DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer();
        listCellRenderer.setHorizontalAlignment(CENTER);

        comboBox = new JComboBox<>(text);
        comboBox.setBackground(new Color(248, 247, 201));
        comboBox.setOpaque(false);
        comboBox.setRenderer(listCellRenderer);
        comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBox.setFont(Pokemon);
        comboBox.addActionListener(e -> {
            String themeCheck = selectedTheme;
            selectedTheme = (String) comboBox.getSelectedItem();
            if (!themeCheck.equals(selectedTheme)) {
                menuFrame.revalidate();
                menuFrame.repaint();
            }
            System.out.println(selectedTheme);
        });
        add(comboBox, BorderLayout.CENTER);
    }

    /**
     * Update des Hintergrundbilds
     *
     * @param g Übergibt Graphics Objekt
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (selectedTheme.equals("Pokemon")) {

            g.drawImage(ImageLoader.getImage(ImageLoader.MENU_BUTTON), 0, 0, getWidth(), getHeight(), this);
        } else {
            Bildloader Bild = new Bildloader();
            comboBox.setBackground(new Color(0, 35, 102));
            comboBox.setForeground(Color.white);
            BufferedImage NavalButton = Bild.BildLoader("src/Images/NavalButton.png");
            g.drawImage(NavalButton, 0, 0, getWidth(), getHeight(), null);

        }
        super.paintComponent(g);
    }
}
