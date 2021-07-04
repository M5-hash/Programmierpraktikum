package src;

import javax.swing.*;
import java.awt.*;

/**
 * Zeigt an, ob man selbst oder der Gegner am Zug ist
 */
public class DisplayTurn extends JPanel {

    /**
     * Ermöglich das laden von Bilddateien
     */
    Bildloader Bild = new Bildloader();
    /**
     * Gibt an, ober der Spieler gerade am Zug ist
     */
    boolean Turn = true;

    /**
     * @param g Graphics Object
     *          <p>
     *          Überprüft ob der Spieler am Zug ist und zeigt das passende Bild
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img;

        setOpaque(false);

        if (Turn) {
            img = Bild.BildLoader("src/Images/yourturn.png");
        } else {
            img = Bild.BildLoader("src/Images/pleasewait.png");
        }
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }


    /**
     * Setzt Turn auf die übergebene Variable
     *
     * @param Set boolean wird direkt an die Methode übergeben
     */
    public void switchTurn(boolean Set) {
        Turn = Set;
    }


}