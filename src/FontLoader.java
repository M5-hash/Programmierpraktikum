package src;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Fontloader dient zum Laden der Pokemon Font
 */
public class FontLoader {

    public static GraphicsEnvironment ge = null;
    public static Font Pokemon = null;

    public FontLoader() {
    }

    /**
     * Lädt Pokemon Font aus Assets und
     * @throws IOException Fehler beim Laden der Grafiken
     * @throws FontFormatException Fehler beim Laden der Font
     */
    public static void createFont() throws IOException, FontFormatException {
        Pokemon = Font.createFont(Font.TRUETYPE_FONT, new File("assets/pokemon.ttf")).deriveFont(12f);
        //create the font to use. Specify the size!
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        ge.registerFont(Pokemon);
    }
}
