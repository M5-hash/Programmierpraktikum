package src.components;

import src.Bildloader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static src.config.selectedTheme;

/**
 * Custom Panel für Hintergrundbilder
 */
public class CustomPanel extends JPanel {
    /**
     * Hintergrundbild für Pokemon Theme
     */
    private final BufferedImage givenbackground;

    /**
     * @param image Hintergrundbild des Pokemon Theme
     */
    public CustomPanel(BufferedImage image) {
        this.givenbackground = image;
    }

    /**
     * Update des Hintergrundbilds
     *
     * @param g Übergibt Graphics Objekt
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        /**
         * Hintergrundbild des Classic Theme
         */
        BufferedImage background;
        if (!selectedTheme.equals("Pokemon")) {
            Bildloader Bild = new Bildloader();
            background = Bild.BildLoader("src/Images/NavalBackground.jpg");
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        } else {

            g.drawImage(givenbackground, 0, 0, getWidth(), getHeight(), this);
        }

    }
}
