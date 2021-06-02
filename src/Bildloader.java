package src;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bildloader {

    static int counter = 0;                                                     // Wird verwendet um Sicherzugehen, dass mindestens ein Bild bereits geladen wurde und garantiert, dass Bild und Quelle an den selben key besitzen
    static ArrayList<BufferedImage> Finished = new ArrayList<>();              // Zwischenspeicher für bereits geladene Bilder
    static ArrayList<String> Loaded = new ArrayList<>();                       // Speichert als String die Quellen der bereits geladenen Bilder ab


    /**
     * @param Bild_dir gibt die zu ladende Datei an
     * @return
     */
    public BufferedImage BildLoader(String Bild_dir) {                           // Läd die Datei ein und macht sicher, dass diese auch als Bilddatei gelesen wird


        if (counter > 0) {                                                        // Macht sicher, dass die zuladende Datei auch eine neue Datei ist. Falls die Datei schon einmal geladen wurde, wurde Sie gespeichert
            for (int i = 0; i < Loaded.size(); i++) {                           // Aus diesem Speicher wird Sie nun wieder asugelesen
                if (Loaded.get(i).contentEquals(Bild_dir)) {
                    return Finished.get(i);
                }
            }
        }

        BufferedImage img = null;
        try {                                                                   /*Immer wenn ein IOreader verwendet wird, braucht man ein try catch statement, welches dann eine Exception wirft*/
            img = ImageIO.read(new File(Bild_dir));
        } catch (IOException e) {
            System.out.println("Bild konnte nicht geladen werden");
        }

        Loaded.add(counter, Bild_dir);                                         // Fügt die Quelle dem Zwischenspeicher hinzu
        Finished.add(counter, img);                                             // Fügt das Bild dem Zwischenspeicher hinzu
        counter++;                                                             // Der Counter wird um Eins erhöht, das in beide Arrays mindestens ein Element hinzugefügt wurde
        System.out.println("Es wurden bis jetzt " + counter + " Bilder geladen");
        return img;
    }
}
