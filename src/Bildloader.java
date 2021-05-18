package src;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bildloader {

    static int counter = 0;
    static ArrayList<BufferedImage> Finished = new ArrayList<>() ;
    static ArrayList<String> Loaded = new ArrayList<>() ;



    public BufferedImage BildLoader(String Bild_dir) {                           // Läd die Datei ein und macht sicher, dass diese auch als Bilddatei gelesen wird
        if(counter > 0){
            for(int i = 0 ; i < Loaded.size() ; i++){
                if(Loaded.get(i).contentEquals(Bild_dir)){
                    return Finished.get(i) ;
                }
            }
        }

        BufferedImage img = null;
        try {                                                                   /*Immer wenn ein IOreader verwendet wird, braucht man ein try catch statement, welches dann eine Exception wirft*/
            img = ImageIO.read(new File(Bild_dir));
        } catch (IOException e) {
            System.out.println("Bild konnte nicht geladen werden");
        }
        Loaded.add(counter, Bild_dir) ;
        Finished.add(counter, img);
        counter++ ;
        System.out.println("Es wurden bis jetzt " + counter + " Bilder geladen");
        return img;
    }
}
