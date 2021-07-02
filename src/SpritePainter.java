package src;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static src.Tile.field_size;
import static src.config.fieldsize;
import static src.config.selectedTheme;

public class SpritePainter {


    public static boolean ready = false;
    public static int counter;
    static ArrayList<BufferedImage> Finished = new ArrayList<>();              // Zwischenspeicher für bereits geladene Bilder
    static ArrayList<String> Loaded = new ArrayList<>();                       // Speichert als String die Quellen der bereits geladenen Bilder ab
    static boolean fits = true;
    static Bildloader Bild = new Bildloader();
    int[][] Pokemon = new int[field_size][field_size];
    int fieldof;
    SpielWindow frame;
    TilePainter Interface;
    PlayingField pf;
    int addnumber;
    boolean IsHit = false;
    int[][] Vorhersage = new int[fieldsize][fieldsize];
    /**
     * Haenle seine Tabelle
     * <p>
     * 0 = Wasser
     * 1 = Abgeschossenes Schiffsteil
     * 2 = Komplett zerstört
     * 3 = Normales Schiffsteil
     * 4 = Geplantes Schiffsteil, noch nicht gesetzt
     * 5 = Wasser abgeschossen
     */

    private int[][] BugHeckMeck = new int[fieldsize][fieldsize];

    /**
     * @param Feldvon gibt an für wenn die Schiffe gezeichnet werden
     *                "Spieler" = Spieler
     *                "GegnerKI" = Computer Gegner
     *                "GegnerMensch" = OnlineGegners / Menschlicher Gegner
     *                "Preview" = Feld wird verwendet um das setzen des Spieler besser darzustellen
     */
    public SpritePainter(int Feldvon, TilePainter Kontakt, SpielWindow frame, PlayingField playingField) {

        pf = playingField;
        fieldof = Feldvon;
        Interface = Kontakt;
        this.frame = frame;
        Schiffteil();
        if (selectedTheme.equals("Pokemon")) {
            PokemonAnimator();
        }
    }

    static int fetchImg(String Schiffdir) {
        if (counter > 0) {                                                        // Macht sicher, dass die zuladende Datei auch eine neue Datei ist. Falls die Datei schon einmal geladen wurde, wurde Sie gespeichert
            for (int i = 0; i < Loaded.size(); i++) {// Aus diesem Speicher wird Sie nun wieder ausgelesen
                String Stringaling = Schiffdir + fits;
                if (Loaded.get(i).contentEquals(Stringaling)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * @param image      Übergebenes BufferedImage
     * @param Schiff_dir Übergibt den Pfad des Bildes
     * @return Rückgabe des bearbeiteten BufferedImage
     * <p>
     * Verändert die Färbung, der in der Preview verwendeten BufferedImages, sodass diese direkt angeben, ob Sie gesetzt werden können
     */
    private static BufferedImage colorshiftpng(BufferedImage image, String Schiff_dir) {


        if (selectedTheme.equals("Pokemon")) {

            if (fits) {
                return Bild.BildLoader("src/Images/greenOutline.png");
            } else {
                return Bild.BildLoader("src/Images/redOutline.png");
            }

        } else {

            BufferedImage copy;

            copy = deepCopy(image);

            int width = copy.getWidth();
            int height = copy.getHeight();

//        System.out.println("Es wurden insgesamt " + counter + " Bilder bearbeitet/umgefärbt");

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (((copy.getRGB(x, y) >> 24) & 0xFF) != 0) {

                        int pixel = copy.getRGB(x, y);

                        float blue = (pixel) & 0xff;
                        float green = (pixel >> 8) & 0xff;
                        float red = (pixel >> 16) & 0xff;

                        if (fits) {
                            blue = blue / 510;     //Da nicht von 0 -> 255 erlaubt sondern von 0.0 -> 1.0 weitere Halbierung um die B Menge zu verringern/ es Grüner zu machen
                            red = red / 510;       //Verdopplung ist zufällig gewählt und wird sich wahrscheinlich noch ändern (Ich nehme Vorschläge)


                            Color Cgreen = new Color(red, 0.42f, blue, 0.82f); //G und A zufällig gewählt

                            copy.setRGB(x, y, Cgreen.getRGB());
                        } else {

                            green = green / 510;
                            blue = blue / 510;

                            Color Cred = new Color(0.5f, green, blue, 0.62f); //Grün schlechter sichtbar als Rot --> höherer Alpha Wert

                            copy.setRGB(x, y, Cred.getRGB());
                        }
                    }
                }
            }
            String Stringaling = Schiff_dir + fits;

            Loaded.add(counter, Stringaling);                                         // Fügt die Quelle dem Zwischenspeicher hinzu
            Finished.add(counter, copy);                                             // Fügt das Bild dem Zwischenspeicher hinzu
            counter++;
            return copy;
        }

    }

    /**
     * @param bi Gibt zu klonendes Bild an die Methode weiter
     * @return gibt das geklonte BufferedImage zurück
     * <p>
     * Methode garantiert, dass es sich um verschieden BufferedImage Objekte handelt
     */
    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster wr = bi.copyData(null);
        return new BufferedImage(cm, wr, isAlphaPremultiplied, null);
    }

    /**
     * Das Array, welches verwendet wird um die Pokemon zu zeichnen wird geupdated
     */
    void updatePokemon() {

        for (int i = 0; i < field_size; i++) {
            for (int j = 0; j < field_size; j++) {


                if (Pokemon[i][j] > 0 && pf.getField()[i][j] == 0) {
                    Pokemon[i][j] = pf.getField()[i][j];
                } else if (Pokemon[i][j] == 0 && pf.getField()[i][j] == 3) {
                    Pokemon[i][j] = -1;
                } else if (pf.getField()[i][j] == 1) {
                    Pokemon[i][j] = -4;
                } else if (pf.getField()[i][j] == 2) {
                    Pokemon[i][j] = -5;
                } else if (Pokemon[i][j] == -2) {
                    Pokemon[i][j] = -3;
                } else if (Pokemon[i][j] != -3 && pf.getField()[i][j] == 5) {
                    Pokemon[i][j] = -2;
                }


            }
        }

        if (frame.tile2.field != 2) {
            frame.tile2.allowchange = false;
        }


    }

    /**
     * Wird verwendet um die Schiffe welche in Playingfield abgespeichert werden mit der richtigen Ausrichtung darzustellen
     * <p>
     * wird auch in der Preview verwendet um eine richtige Ausrichtung der Schiffe zu garantieren
     */
    public void Schiffteil() {

        //System.out.println("Schiffteil wurde aufgerufen");

        int[][] Schiffe = pf.getField();

        if (fieldof == 4) {
            Schiffe = Vorhersage;
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
         * x1 entspricht Schiffe[i - 1][j] links von Punkt
         * x2 entspricht Schiffe[i + 1][j] rechts von Punkt
         * y1 entspricht Schiffe[i][j - 1] über dem Punkt
         * y2 entspricht Schiffe[i][j + 1] unter dem Punkt
         **/

        for (int i = 0; i < Schiffe.length; i++) {
            for (int j = 0; j < Schiffe[0].length; j++) {
                if (Schiffe[i][j] == 3 || Schiffe[i][j] == 1 || Schiffe[i][j] == 2) {

                    x1 = false;
                    x2 = false;
                    y1 = false;
                    y2 = false;


                    if (i != 0 && Schiffe[i - 1][j] != 0 && Schiffe[i - 1][j] != 5)
                        x1 = true;
                    if (i != (fieldsize - 1) && Schiffe[i + 1][j] != 0 && Schiffe[i + 1][j] != 5)
                        x2 = true;
                    if (j != 0 && Schiffe[i][j - 1] != 0 && Schiffe[i][j - 1] != 5)
                        y1 = true;
                    if (j != (fieldsize - 1) && Schiffe[i][j + 1] != 0 && Schiffe[i][j + 1] != 5)
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


    }

    /**
     * @param g Gibt Graphics Objekt weiter
     * @param f Gibt der Preview an, ob das Schiff gesetzt werden könnte
     *          <p>
     *          Wird nur von der Preview verwendet um gleichzeitig die Schiffzeichner Methode aufzurufen und
     *          anzugeben ob das Schiff gesetzt werden könnte
     */
    public void Schiffzeichner(Graphics g, boolean f) {

        fits = f;
        Schiffzeichner(g);

    }

    /**
     * @param g Gibt Graphics Objekt weiter
     *          <p>
     *          Zeichnet die Schiffe wie sie durch das in Schiffteil() ermittelt Array vorgegeben werden (Feld von Spieler)
     *          <p>
     *          Zeichnet was dem Spieler vom Feld seines Gegners bekannt ist (Feld von GegnerKi & GegnerMensch)
     *          <p>
     *          Zeichnet die Schiffe wie sie durch das in Schiffteil() ermittelt Array vorgegeben werden, aber mit der
     *          weiteren Information ob diese dort überhaupt gesetzt werden dürfen (Feld von Preview)
     */
    public void Schiffzeichner(Graphics g) {

        int[][] dummy;

        //Schiffe werden nur genau dargestellt, wenn es sich um das Feld des Menschen handelt ansonsten nicht
        if (fieldof == 0 || fieldof == 4) Schiffteil();

        String Schiffdir = "Ich bin der String und ich bin ein Platzhalter";
        BufferedImage Schiff; //Nur ein Platzhalter, dass die IDE nicht weint
        boolean dosmthng = false;


        //Die größe des Rahmens um das Spielfeld herum wird hier berchnet, sodass alle Schiffe an genau der richtigen Position sind.
        int SizeofBorder = Math.max(18, TileSize.Tile_Size / 12);
        int isX;
        int isY;
        int picSize;


        //Abhänig von dem Spielfeld das Angezeigt werden soll wird hier ein anderes Array eingelesen, sodass die Informationen auch der Situation entsprechen
        switch (fieldof) {
            case 1 -> dummy = pf.getFieldEnemy();

            case 2 -> {
                if (frame.Multclient) {
                    dummy = frame.client.pf.getFieldEnemy();
                } else {
                    dummy = frame.server.pf.getFieldEnemy();
                }
                //case "Vorhersage" -> Vorhersage;
            }

            default -> dummy = BugHeckMeck;
        }


        //Garantiert, dass auch das gesamte Array abgelaufen wird
        for (int y = 0; y < dummy.length; y++) {
            for (int x = 0; x < dummy[0].length; x++) {

                isX = (x * TileSize.Tile_Size + SizeofBorder);
                isY = (y * TileSize.Tile_Size + SizeofBorder);
                picSize = TileSize.Tile_Size;

                //checkt ob es sich bei dem Spielfeld auch um das des Spielers handelt
                if (fieldof == 0 || fieldof == 4) {


                    //Wenn das Schiff getroffen wurde, dann ist das Array an der Stelle entweder 1 (Teil getroffen aber Schiff gibt es noch) oder 2 (Teil und gesamtes Schiff zerstört)
                    IsHit = pf.getField()[y][x] == 1 || pf.getField()[y][x] == 2;
                    if(pf.getField()[y][x] == 5){

                        BufferedImage dummyImg = Bild.BildLoader("src/Images/WaterHitCircle.png");

                        g.drawImage(dummyImg, isX,
                                isY,
                                picSize,
                                picSize, null);
                    }

                    //Die Art des Schiffteils wird ausgelesen und dieser wird dann ein Bild zugewiesen
                    //Es wird auch eine Variable gesetzt, die angibt das etwas gezeichnet werden muss oder eben nicht
                    switch (dummy[y][x]) {

                        case 0:
                            break;

                        case 1:
                            //Gibt das zu zeichnende Bild an
                            Schiffdir = "src/Images/Vorne32true" + IsHit + ".png";
                            //Gibt an, dass etwas zu zeichnen ist
                            dosmthng = true;
                            break;

                        case 2:
                            //Aus dem String geht heraus was gezeichnet wird
                            //Vorne/Mitte/Hinten Ob es ich um Bug Heck oder Mittelstück handelt
                            //32 ist die Größe des Bildes in Pixel
                            //true/false ob horizontal (true) oder vertikal (false)
                            //isHIt ob getroffen oder nicht
                            Schiffdir = "src/Images/Vorne32false" + IsHit + ".png";
                            dosmthng = true;
                            break;

                        case 3:
                            Schiffdir = "src/Images/Mitte32true" + IsHit + ".png";
                            dosmthng = true;
                            break;

                        case 4:
                            Schiffdir = "src/Images/Mitte32false" + IsHit + ".png";
                            dosmthng = true;
                            break;

                        case 5:
                            Schiffdir = "src/Images/Hinten32true" + IsHit + ".png";
                            dosmthng = true;
                            break;

                        case 6:
                            Schiffdir = "src/Images/Hinten32false" + IsHit + ".png";
                            dosmthng = true;
                            break;

                        default:
                            System.out.println(dummy[y][x]);
                            System.out.println("Gamer, dass ist aber dick nicht Gut mein bester, das sollte nämlich nicht gehen");
                            System.out.println("Es gibt also einen Fehler in der Schiffteil Methode");
                            Schiffdir = "src/Images/PokeTest32.jpg";

                    }

                    if (frame.tile2.hasshot && x == frame.tile2.getRecentshot()[0] && y == frame.tile2.getRecentshot()[1]) {

                        BufferedImage dummyImg = Bild.BildLoader("src/Images/SniperScope.png");

                        g.drawImage(dummyImg, isX,
                                isY,
                                picSize,
                                picSize, null);
                    }
                }


                //Checkt ob es das Spielfeld des Computer Gegners ist
                if (fieldof == 2 || fieldof == 1) {

                    //Das, was gezeichnet werden muss wird ausgelesen
                    //Es wird auch eine Variable gesetzt, die angibt, dass eine etwas gezeichnet werden muss oder eben nicht
                    if (selectedTheme.equals("Pokemon")) {

                        isX = (x * TileSize.Tile_Size + SizeofBorder) + 1;
                        isY = (y * TileSize.Tile_Size + SizeofBorder) + 1;
                        picSize = TileSize.Tile_Size - 1;

                        switch (dummy[y][x]) {
                            /*
                             * 0 = Wasser
                             * 1 = Abgeschossenes Schiffsteil
                             * 2 = Komplett zerstört
                             * 3 = Normales Schiffsteil
                             * 4 = Geplantes Schiffsteil, noch nicht gesetzt
                             * 5 = Wasser abgeschossen
                             */


                            case 0:
                                break;

                            case 1:
                                Schiffdir = "src/Images/PokeTest.png";
                                dosmthng = true;
                                //Normaler Pokeball (kein Pokemon mehr)
                                break;

                            case 2:
                                Schiffdir = "src/Images/greyPokeball.png";
                                dosmthng = true;
                                //ausgegrauter Pokeball (Das ganze Schiff zerstört)
                                break;

                            case 3:
                                //Sollte es nicht geben
                                break;

                            case 4:
                                //Schiff kann dort nicht sein

                            case 5:
                                Schiffdir = "src/Images/fullCutGrass.jpg";
                                dosmthng = true;
                                break;
                            //Schiff war dort nicht
                        }
                    } else {

                        switch (dummy[y][x]) {
                            /*
                             * 0 = Wasser
                             * 1 = Abgeschossenes Schiffsteil
                             * 2 = Komplett zerstört
                             * 3 = Normales Schiffsteil
                             * 4 = Geplantes Schiffsteil, noch nicht gesetzt
                             * 5 = Wasser abgeschossen
                             */

                            case 0:
                                break;

                            case 1:
                                Schiffdir = "src/Images/BurningTile.png";
                                dosmthng = true;
                                //Normaler Pokeball (kein Pokemon mehr)
                                break;

                            case 2:
                                Schiffdir = "src/Images/Mitte32truetrue.png";
                                dosmthng = true;
                                //ausgegrauter Pokeball (Das ganze Schiff zerstört)
                                break;

                            case 3:
                                //Sollte es nicht geben
                                break;

                            case 4:
                                //Schiff kann dort nicht sein

                            case 5:
                                Schiffdir = "src/Images/WaterHitCircle.png";
                                dosmthng = true;
                                break;
                            //Schiff war dort nicht

                        }
                    }
                }

                //checkt ob es ein zu zeichnendes Image gibt
                if (dosmthng) {
                    Schiff = Bild.BildLoader(Schiffdir);
                    BufferedImage dummyImg;

                    if (fieldof == 4) {
                        int check = fetchImg(Schiffdir);
                        if (check != -1) {
                            dummyImg = Finished.get(check);
                        } else {
                            dummyImg = colorshiftpng(Schiff, Schiffdir);
                        }
                    } else {
                        dummyImg = Schiff;
                    }


                    g.drawImage(dummyImg, isX,
                            isY,
                            picSize,
                            picSize, null);
                    dosmthng = false;

                }

            }
        }

    }

    /**
     * @param y Die y Postion des Tile über dem momentan die Maus hovert
     * @param x Die x Postion des Tiles über dem momentan die Maus hovert
     *          <p>
     *          Da nur der Startpunkt des Schiffes übergeben wird, muss berechnet werden wo sich der ganze Rest des Schiffes befinden wird
     */
    public void setPrediction(int y, int x) {

        int size = TilePainter.getGroesse();
        boolean hor = TilePainter.horizontal;

        //Da es in der Vorhersage immer nur ein Schiff geben kann und alte Positionen nicht nur irrelevant sondern verfälschend sind
        //wird hier mit einem leeren Array gearbeitet
        Vorhersage = new int[fieldsize][fieldsize];

        for (int i = 0; i < size; i++) {
            if (x < fieldsize && y < fieldsize) {
                Vorhersage[x][y] = 3;
                if (!hor) {
                    x++;
                } else
                    y++;
            }
        }
        Schiffteil();

    }

    public void Pokemonpicker(Graphics g) {


        //Das Tileset Bild wird eingeladen
        BufferedImage PokemonBild = Bild.BildLoader("src/Images/PokemonTileSetremove.png");

        if (fieldof == 0) {
            //Die größe des Border wir hier berechnet, sodass die Sprites alle an richtiger Ort und Stelle sein können
            int SizeofBorder = Math.max(18, TileSize.Tile_Size / 12);

            //Es wird abgefragt ob es zu Änderungen im Array kam
            if (!Tile.fightstart || Objects.requireNonNull(frame.tile2).allowchange) updatePokemon();


            //durch die 2 for Schleifen wird das gesamte Array abgelaufen
            for (int y = 0; y < pf.getField().length; y++) {
                for (int x = 0; x < pf.getField()[0].length; x++) {

                    Random rd = new Random();

                    if (Pokemon[y][x] == -1) {
                        Pokemon[y][x] = rd.nextInt(152) * 4;
                    }

                    int index;
                    if (Pokemon[y][x] >= 0) {
                        index = Pokemon[y][x] + addnumber;
                    } else {
                        index = Pokemon[y][x];
                    }

                    int yOffset = 0;

                    //Höhe & Breite per Tile 80
                    if (index > (PokemonBild.getWidth() / 80) - 1) {
                        // Da das Tileset nicht nur horizontal ausgerichtet ist, muss jedes mal wenn die rechte Seite des TileSets erreicht wurde unsere source
                        while ((index > (PokemonBild.getWidth() / 80) - 1)) {
                            // Wieder an die linke Seite des Bildes verschoben werden
                            index = index - (PokemonBild.getWidth() / 80);
                            //Aber um eine Zeile nach unten verschoben
                            yOffset++;
                        }
                    }


                    //checkt ab, ob es hier etwas zu zeichnen gibt
                    if (Pokemon[y][x] != 0) {

                        if (Pokemon[y][x] == -3) {

                            BufferedImage dummyImg = Bild.BildLoader("src/Images/PokemonCutGrass.jpg");

                            g.drawImage(dummyImg, (x * TileSize.Tile_Size + SizeofBorder),
                                    (y * TileSize.Tile_Size + SizeofBorder),
                                    TileSize.Tile_Size,
                                    TileSize.Tile_Size, null);

                        } else if (Pokemon[y][x] == -2) {

                            BufferedImage dummyImg = Bild.BildLoader("src/Images/SniperScope.png");

                            g.drawImage(dummyImg, (x * TileSize.Tile_Size + SizeofBorder),
                                    (y * TileSize.Tile_Size + SizeofBorder),
                                    TileSize.Tile_Size,
                                    TileSize.Tile_Size, null);

                        } else if (Pokemon[y][x] == -5) {

                            BufferedImage dummyImg = Bild.BildLoader("src/Images/greyPokeball.png");

                            g.drawImage(dummyImg, (x * TileSize.Tile_Size + SizeofBorder),
                                    (y * TileSize.Tile_Size + SizeofBorder),
                                    TileSize.Tile_Size,
                                    TileSize.Tile_Size, null);

                        } else {

                            if (Pokemon[y][x] == -4) {

                                BufferedImage dummyImg = Bild.BildLoader("src/Images/redsheen.png");

                                g.drawImage(dummyImg, (x * TileSize.Tile_Size + SizeofBorder),
                                        (y * TileSize.Tile_Size + SizeofBorder),
                                        TileSize.Tile_Size,
                                        TileSize.Tile_Size, null);

                                g.drawImage(PokemonBild, (x * TileSize.Tile_Size + SizeofBorder),
                                        (y * TileSize.Tile_Size + SizeofBorder),
                                        (x + 1) * TileSize.Tile_Size + SizeofBorder,
                                        (y + 1) * TileSize.Tile_Size + SizeofBorder,
                                        //Es wird ein Viereck zwischen diesen 2 Punkten aufgeschlagen, die ersten 2 sind das linke obere ende
                                        index * 80,
                                        yOffset * 80,
                                        //die anderen 2 sind das rechte untere ende. Es handelt sich hierbei um die Quelle
                                        (index + 1) * 80,
                                        (yOffset + 1) * 80,
                                        null);

                            }

                            g.drawImage(PokemonBild, (x * TileSize.Tile_Size + SizeofBorder),
                                    (y * TileSize.Tile_Size + SizeofBorder),
                                    (x + 1) * TileSize.Tile_Size + SizeofBorder,
                                    (y + 1) * TileSize.Tile_Size + SizeofBorder,
                                    //Es wird ein Viereck zwischen diesen 2 Punkten aufgeschlagen, die ersten 2 sind das linke obere ende
                                    index * 80,
                                    yOffset * 80,
                                    //die anderen 2 sind das rechte untere ende. Es handelt sich hierbei um die Quelle
                                    (index + 1) * 80,
                                    (yOffset + 1) * 80,
                                    null);

                        }
                    }
                }
            }
        }
    }

    public void PokemonAnimator() {
        Timer t;

        ActionListener taskPerformer = evt -> addnumber = ++addnumber % 4;
        t = new javax.swing.Timer(650, taskPerformer);
        t.start();
    }


}
