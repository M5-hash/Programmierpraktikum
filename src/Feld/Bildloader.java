package Feld;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bildloader {

    public BufferedImage BildLoader(String Bild_dir) {                           // Läd die Datei ein und macht sicher, dass diese auch als Bilddatei gelesen wird

        BufferedImage img = null;
        try {                                                                   /*Immer wenn ein IOreader verwendet wird, braucht man ein try catch statement, welches dann eine Exception wirft*/
            img = ImageIO.read(new File(Bild_dir));
        } catch (IOException e) {
            System.out.println("Bild konnte nicht geladen werden");
        }

        return img;
    }
}
