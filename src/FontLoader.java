package src;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader {

    public static GraphicsEnvironment ge = null;
    public static Font Pokemon = null;

    public FontLoader() {
    }

    public static void createFont() throws IOException, FontFormatException {
        Pokemon = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon.ttf")).deriveFont(12f);
        //create the font to use. Specify the size!
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        ge.registerFont(Pokemon);
    }
}
