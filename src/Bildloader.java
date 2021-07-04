package src;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Lädt neue Dateien und speichert diese für schnellere Wiederverwundung zwischen
 */
public class Bildloader {

    /**
     * Key, der garantiert, dass bei Finished und Loaded die Objects übereinstimmen
     */
    static int counter = 0;
    /**
     * BufferImage ArrayList Zwischenspeicher für die bereits geladenen Bilddateien
     */
    static ArrayList<BufferedImage> Finished = new ArrayList<>();
    /**
     * String ArrayList Zwischenspeicher für die Quellen als String für die bereits geladenen Bilder
     */
    static ArrayList<String> Loaded = new ArrayList<>();


    /**
     * @param Bild_dir gibt die zu ladende Datei an
     * @return img Das gewollte image
     */
    public BufferedImage BildLoader(String Bild_dir) {

        if (counter > 0) {
            // Macht sicher, dass die zuladende Datei auch eine neue Datei ist. Falls die Datei schon einmal geladen wurde, wurde Sie gespeichert
            for (int i = 0; i < Loaded.size(); i++) {
                // Aus diesem Speicher wird Sie nun wieder ausgelesen
                if (Loaded.get(i).contentEquals(Bild_dir)) {
                    return Finished.get(i);
                }
            }
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(Bild_dir));
        } catch (IOException e) {
            System.out.println("Bild konnte nicht geladen werden" + Bild_dir);
        }
        // Fügt die Quelle dem Zwischenspeicher hinzu
        Loaded.add(counter, Bild_dir);
        // Fügt das Bild dem Zwischenspeicher hinzu
        Finished.add(counter, img);
        // Der Counter wird um Eins erhöht, das in beide Arrays mindestens ein Element hinzugefügt wurde
        counter++;
        System.out.println("Es wurden bis jetzt " + counter + " Bilder geladen");
        return img;
    }
}
