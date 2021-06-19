package src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class SchiffPainter {

    static ArrayList<BufferedImage> Finished = new ArrayList<>();              // Zwischenspeicher für bereits geladene Bilder
    static ArrayList<String> Loaded = new ArrayList<>();                       // Speichert als String die Quellen der bereits geladenen Bilder ab
    public static int[][] BugHeckMeck = new int[SpielWindow.field_size][SpielWindow.field_size];
    public static boolean ready = false;
    Bildloader Bild = new Bildloader();
    String Fieldof;
    static boolean fits = true ;
    public static int counter ;
    int[][] getEnemyPlacement =
            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                    , {0, 0, 8, 8, 8, 8, 0, 0, 0, 0}
                    , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                    , {0, 0, 0, 0, 0, 7, 7, 7, 0, 0}
                    , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                    , {0, 0, 0, 0, 0, 8, 0, 0, 0, 0}
                    , {0, 8, 8, 0, 0, 8, 0, 0, 0, 0}
                    , {0, 8, 8, 0, 0, 8, 0, 0, 0, 0}
                    , {0, 8, 8, 0, 0, 8, 0, 0, 0, 0}
                    , {0, 8, 8, 0, 0, 0, 0, 0, 0, 0}};
    int[][] Vorhersage = new int[SpielWindow.field_size][SpielWindow.field_size];


    /**
     * @param Feldvon   gibt an für wenn die Schiffe gezeichnet werden
     *                  "Spieler" = Spieler
     *                  "GegnerKI" = Computer Gegner
     *                  "GegnerMensch" = OnlineGegners / Menschlicher Gegner
     *                  "Preview" = Feld wird verwendet um das setzen des Spieler besser darzustellen
     */
    public SchiffPainter(String Feldvon) {

        Fieldof = Feldvon;
        System.out.println( Fieldof );

        Schiffteil();
    }


    /**
     *                  Wird verwendet um die Schiffe welche in Playingfield abgespeichert werden mit der richtigen Ausrichtung darzustellen
     *
     *                  wird auch in der Preview verwendet um eine richtige Ausrichtung der Schiffe zu garantieren
     */
    public void Schiffteil() {

        System.out.println("Schiffteil wurde aufgerufen");

        int[][] Schiffe = SpielWindow.playingField.getField();

        if(Fieldof.equals("Vorhersage")){
            Schiffe = Vorhersage ;
        }

        /*
         * Werte für BugHeckMeck:
         *
         * ungerade : horizontal
         * gerade : vertikal
         *
         * 0: Nichts ist dort
         * 1: Vornetrue             (Bug und horizontal ausgerichtet)
         * 2: Vornefalse              (Bug und vertikal ausgerichtet)
         * 3: Mittetrue             (Mittelstück und horizontal ausgerichtet)
         * 4: Mittefalse              (Mittelstück und vertikal ausgerichtet)
         * 5: Hintentrue             (Heck und horizontal ausgerichtet)
         * 6: Hintenfalse            (Heck und vertikal ausgerichtet)
         *
         **/

        boolean x1;
        boolean x2;
        boolean y1;
        boolean y2;

        /* Ausgehend von Schiffe[i][j]
         *
         * x1 entspricht Schiffe[i - 1][j]
         * x2 entspricht Schiffe[i + 1][j]
         * y1 entspricht Schiffe[i][j - 1]
         * y2 entspricht Schiffe[i][j + 1]
         **/

        for (int i = 0; i < Schiffe.length; i++) {
            for (int j = 0; j < Schiffe[0].length; j++) {
                if (Schiffe[i][j] == 3) {

                    x1 = false;
                    x2 = false;
                    y1 = false;
                    y2 = false;


                    if (i != 0 && Schiffe[i - 1][j] != 0)
                        x1 = true;
                    if (i != (SpielWindow.field_size - 1) && Schiffe[i + 1][j] != 0)
                        x2 = true;
                    if (j != 0 && Schiffe[i][j - 1] != 0)
                        y1 = true;
                    if (j != (SpielWindow.field_size - 1) && Schiffe[i][j + 1] != 0)
                        y2 = true;

                    if (!x1 && !x2 && !y1 && y2)
                        BugHeckMeck[i][j] = 1;
                    if (!x1 && x2 && !y1 && !y2)
                        BugHeckMeck[i][j] = 2;
                    if (!x1 && !x2 && y1 && y2)
                        BugHeckMeck[i][j] = 3;
                    if (x1 && x2 && !y1 && !y2)
                        BugHeckMeck[i][j] = 4;
                    if (!x1 && !x2 && y1 && !y2)
                        BugHeckMeck[i][j] = 5;
                    if (x1 && !x2 && !y1 && !y2)
                        BugHeckMeck[i][j] = 6;
                } else
                    BugHeckMeck[i][j] = 0;

            }
        }


        ready = true;
        SpielWindow.change = false;


    }

    /**
     * @param g     Gibt Graphics Objekt weiter
     * @param f     Gibt der Preview an, ob das Schiff gesetzt werden könnte
     *
     *              Wird nur von der Preview verwendet um gleichzeitig die Schiffzeichner Methode aufzurufen und
     *              anzugeben ob das Schiff gesetzt werden könnte
     */
    public void Schiffzeichner(Graphics g, boolean f) {

        fits = f ;
        Schiffzeichner(g);

    }


    /**
     * @param g Gibt Graphics Objekt weiter
     *
     *          Zeichnet die Schiffe wie sie durch das in Schiffteil() ermittelt Array vorgegeben werden (Feld von Spieler)
     *
     *          Zeichnet was dem Spieler vom Feld seines Gegners bekannt ist (Feld von GegnerKi & GegnerMensch)
     *
     *          Zeichnet die Schiffe wie sie durch das in Schiffteil() ermittelt Array vorgegeben werden, aber mit der
     *          weiteren Information ob diese dort überhaupt gesetzt werden dürfen (Feld von Preview)
     */
    public void Schiffzeichner(Graphics g) {

        int[][] dummy;

        if (Fieldof.equals("Spieler") || Fieldof.equals("Vorhersage")) Schiffteil();
//            System.out.println("Schiffzeichner wurde aufgerufen");

        String Schiffdir = "Ich bin der String und ich bin ein Platzhalter";
        BufferedImage Schiff; //Nur ein Platzhalter, dass die IDE nicht weint
        boolean dosmthng = false;

        int SizeofBorder = Math.max(18, TileSize.Tile_Size / 12) ;


        //System.out.println(TileSize.getFighting());

        dummy = switch (Fieldof) {
            case "Spieler" -> BugHeckMeck;
            case "GegnerKI" -> getEnemyPlacement;
            case "GegnerMensch" -> getEnemyPlacement;
            //case "Vorhersage" -> Vorhersage;
            default -> BugHeckMeck;
        };

        System.out.println( Fieldof );


        for (int y = 0; y < dummy.length; y++) {
            for (int x = 0; x < dummy[0].length; x++) {


                switch (dummy[y][x]) {

                    case 0:
                        break;

                    case 1:
                        Schiffdir = "src/Images/Vorne32true.png";
                        dosmthng = true;
                        break;

                    case 2:
                        Schiffdir = "src/Images/Vorne32false.png";
                        dosmthng = true;
                        break;

                    case 3:
                        Schiffdir = "src/Images/Mitte32true.png";
                        dosmthng = true;
                        break;

                    case 4:
                        Schiffdir = "src/Images/Mitte32false.png";
                        dosmthng = true;
                        break;

                    case 5:
                        Schiffdir = "src/Images/Hinten32true.png";
                        dosmthng = true;
                        break;

                    case 6:
                        Schiffdir = "src/Images/Hinten32false.png";
                        dosmthng = true;
                        break;

                    case 7:
                        Schiffdir = "src/Images/EinX2.png";
                        dosmthng = true;
                        break;

                    case 8:
                        Schiffdir = "src/Images/PokeTest32.jpg";
                        dosmthng = true;
                        //System.out.println("Ich wurde aufgerufen");
                        break;

                    default:
                        System.out.println("Gamer, dass ist aber dick nicht Gut mein bester, das sollte nämlich nicht gehen");
                        System.out.println("Es gibt also einen Fehler in der Schiffteil Methode");
                        Schiffdir = "src/Images/PokeTest32.jpg";

                }


                if (dosmthng) {
                    Schiff = Bild.BildLoader(Schiffdir);
                    BufferedImage dummyImg ;

                    if(Fieldof.equals("Vorhersage")){
                        int check = fetchImg(Schiffdir) ;
                        if( check != -1)
                        {
                            dummyImg = Finished.get(check);
                        }else {
                            dummyImg = colorshiftpng(Schiff, Schiffdir) ;
                        }
                    }else {
                        dummyImg = Schiff ;
                    }


                    g.drawImage(dummyImg, (x * TileSize.Tile_Size + SizeofBorder),
                            (y * TileSize.Tile_Size + SizeofBorder),
                            TileSize.Tile_Size,
                            TileSize.Tile_Size, null);
                    dosmthng = false;
                }
            }
        }

    }

    static int fetchImg(String Schiffdir){
        if (counter > 0) {                                                        // Macht sicher, dass die zuladende Datei auch eine neue Datei ist. Falls die Datei schon einmal geladen wurde, wurde Sie gespeichert
            for (int i = 0; i < Loaded.size(); i++) {// Aus diesem Speicher wird Sie nun wieder ausgelesen
                String Stringaling = Schiffdir + fits;
                if (Loaded.get(i).contentEquals(Stringaling)) {
                    return i;
                }
            }
        }
        return  -1 ;
    }

    /**
     * @param y Die y Postion des Tile über dem momentan die Maus hovert
     * @param x Die x Postion des Tiles über dem momentan die Maus hovert
     *
     * Da nur der Startpunkt des Schiffes übergeben wird, muss berechnet werden wo sich der ganze Rest des Schiffes befinden wird
     */
    public void setPrediction(int y, int x) {

        int size = TilePainter.groesse ;
        boolean hor = TilePainter.horizontal ;

        Vorhersage = new int[SpielWindow.field_size][SpielWindow.field_size];

        for(int i = 0; i < size; i++){
            if(x < SpielWindow.field_size && y < SpielWindow.field_size){
                Vorhersage[x][y] = 3 ;
                if(!hor){
                    x++ ;
                } else
                    y++ ;
            }
        }
        Schiffteil();

    }

    /**
     * @param image         Übergebenes BufferedImage
     * @param Schiff_dir    Übergibt den Pfad des Bildes
     * @return              Rückgabe des bearbeiteten BufferedImage
     *
     *                      Verändert die Färbung, der in der Preview verwendeten BufferedImages, sodass diese direkt angeben, ob Sie gesetzt werden können
     */
    private static BufferedImage colorshiftpng(BufferedImage image, String Schiff_dir) {

        BufferedImage copy = deepCopy(image);

        int width = copy.getWidth();
        int height = copy.getHeight();

        System.out.println("Es wurden insgesamt " + counter + " Bilder bearbeitet/umgefärbt");

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (((copy.getRGB(x, y) >> 24) & 0xFF) != 0 ) {

                    int pixel = copy.getRGB(x,y) ;

                    float blue = (pixel) & 0xff ;
                    float green = (pixel >> 8) & 0xff ;
                    float red = (pixel >> 16) & 0xff ;

                    //System.out.println("Ergebnis aus getRGB : \nred: " + red + "blue: " + blue);


                    if(fits){
                        blue = blue / 510 ;     //Da nicht von 0 -> 255 erlaubt sondern von 0.0 -> 1.0 weitere Halbierung um die B Menge zu verringern/ es Grüner zu machen
                        red = red / 510 ;       //Verdopplung ist zufällig gewählt und wird sich wahrscheinlich noch ändern (Ich nehme Vorschläge)

                        //System.out.println("\nred: " + red + "blue: " + blue);

                        Color Cgreen = new Color(red, 0.42f, blue, 0.82f) ; //G und A zufällig gewählt

                        copy.setRGB(x, y,Cgreen.getRGB());
                    }
                    else {

                        green = green / 510;
                        blue = blue / 510 ;

                        Color Cred = new Color(0.5f,green ,blue ,0.62f); //Grün schlechter sichtbar als Rot --> höherer Alpha Wert

                        copy.setRGB(x,y,Cred.getRGB());
                    }
                }
            }
        }
        String Stringaling = Schiff_dir + fits ;

        Loaded.add(counter, Stringaling);                                         // Fügt die Quelle dem Zwischenspeicher hinzu
        Finished.add(counter, copy);                                             // Fügt das Bild dem Zwischenspeicher hinzu
        counter++;
        return copy;
    }

    /**
     * @param bi    Gibt zu klonendes Bild an die Methode weiter
     * @return      gibt das geklonte BufferedImage zurück
     *
     * Methode garantiert, dass es sich um verschieden BufferedImage Objekte handelt
     */
    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied() ;
        WritableRaster wr = bi.copyData(null) ;
        return new BufferedImage(cm , wr, isAlphaPremultiplied, null) ;
    }


}
