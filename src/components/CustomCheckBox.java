package src.components;

import src.Bildloader;
import src.ImageLoader;
import src.config;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static src.config.selectedTheme;

/**
 * CustomCheckBox mit Hintergrundbild
 */
public class CustomCheckBox extends JPanel {
    /**
     * Setzt Fullscreen True oder False
     */
    JCheckBox checkBox;

    /**
     * Erzeugt transparentes Panel mit CheckBox <br>
     *
     * @param fullscreen Text der CheckBox
     * @param b          Setzt Checkbox auf False
     * @param Pokemon    Font der CheckBox
     */
    public CustomCheckBox(String fullscreen, boolean b, Font Pokemon) {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.black));

        checkBox = new JCheckBox();
        checkBox.setText(fullscreen);
        checkBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkBox.setSelected(b);
        checkBox.setMnemonic(KeyEvent.VK_C);
        checkBox.setHorizontalAlignment((int) JCheckBox.CENTER_ALIGNMENT);
        checkBox.setIconTextGap(5);
        checkBox.setFont(Pokemon);
        checkBox.setOpaque(false);
        checkBox.addItemListener(e -> {
            if (checkBox.isSelected()) {
                config.fullscreen = true;
                System.out.println("Fullscreen active");
            } else {
                config.fullscreen = false;
                System.out.println("Fullscreen inactive");
            }
        });
        add(checkBox, BorderLayout.CENTER);
    }

    /**
     * Update des Hintergrundbilds
     *
     * @param g Übergibt Graphics Objekt
     */
    @Override
    protected void paintComponent(Graphics g) {

        if (selectedTheme.equals("Pokemon")) {

            g.drawImage(ImageLoader.getImage(ImageLoader.MENU_BUTTON2), 0, 0, getWidth(), getHeight(), this);
        } else {
            Bildloader Bild = new Bildloader();

            checkBox.setForeground(Color.white);
            BufferedImage NavalButton = Bild.BildLoader("src/Images/NavalButton.png");
            g.drawImage(NavalButton, 0, 0, getWidth(), getHeight(), null);

        }

        super.paintComponent(g);
    }
}
