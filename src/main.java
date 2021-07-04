package src;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Main des Spiels
 */
public class main {

    /**
     * Die main Funktion für das Spiel. <br>
     * Lädt Bilder und Font für das Spiel und öffnet das Hauptmenü.
     *
     * @param args Argumente, die Übergeben werden werden ignoriert
     * @throws IOException         Falls nicht alle Bilder geladen werden konnten
     * @throws FontFormatException Falls Font nicht geladen werden konnte
     */
    public static void main(String[] args) throws IOException, FontFormatException {

        try {
            ImageLoader.init();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "The game was unable to load its graphical assets. \n"
                            + "Make certain that you are running it in the same folder as the 'assets' folder.",
                    "Asset Loading Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        FontLoader.createFont();
        JFrame.setDefaultLookAndFeelDecorated(true);
        // Create the start window
        new MenuStart();
    }
}