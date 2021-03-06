package src.components;

import src.Bildloader;
import src.ImageLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.swing.SwingConstants.CENTER;
import static src.config.*;

/**
 * CustomComboBox zur Auswahl der Resolution
 */
public class CustomComboBox extends JPanel {
    /**
     * ComboBox mit Resolutions
     */
    JComboBox<String> comboBox;

    /**
     * Erzeugt Combobox mit Auswahl der Resolution <br>
     * Bei Änderung der Resolution wird die Breite und Höhe des Spielframes angepasst
     *
     * @param text    Stringarray mit Auswahl der Resolutions
     * @param Pokemon Font der comboBox
     */
    public CustomComboBox(String[] text, Font Pokemon) {

        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.black));

        DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer();
        listCellRenderer.setHorizontalAlignment(CENTER);
        listCellRenderer.setIcon(new ImageIcon(ImageLoader.getImage(ImageLoader.MENU_BUTTON)));

        comboBox = new JComboBox<>(text);
        comboBox.setBackground(new Color(248, 247, 201));
        comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        comboBox.setOpaque(false);
        comboBox.setRenderer(listCellRenderer);
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBox.setFont(Pokemon);
        comboBox.addActionListener(e -> {
            selectedResolution = (String) comboBox.getSelectedItem();
            System.out.println(selectedResolution);
            if (!(selectedResolution.equals("Resolution"))) {
                String[] segments = selectedResolution.split("x");
                GF_WIDTH = Integer.parseInt(segments[0]);
                GF_HEIGHT = Integer.parseInt(segments[1]);
                System.out.println("Frame width: " + GF_WIDTH);
                System.out.println("Frame height: " + GF_HEIGHT);
            }
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
        super.paintComponent(g);
        if (selectedTheme.equals("Pokemon")) {

            g.drawImage(ImageLoader.getImage(ImageLoader.MENU_BUTTON), 0, 0, getWidth(), getHeight(), this);
        } else {
            Bildloader Bild = new Bildloader();
            comboBox.setBackground(new Color(0, 35, 102));
            comboBox.setForeground(Color.white);
            BufferedImage NavalButton = Bild.BildLoader("src/Images/NavalButton.png");
            g.drawImage(NavalButton, 0, 0, getWidth(), getHeight(), null);

        }
    }
}
