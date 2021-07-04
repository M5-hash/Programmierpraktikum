package src;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static src.config.selectedTheme;

/**
 * Zeichnet den Hintergrund des Spielfeldes, also je nach Theme, Wasser oder Büsche sowie deren Rahmen.
 *
 * Oder wenn das Spiel beendet ist zeigt es dem Spieler einen Win oder Loss Screen je nach Ergebnis
 */
public class Tile extends JPanel {

    public static int field_size;
    static boolean fightstart = false;
    private static int counter = 0;
    private final Bildloader Bild = new Bildloader();
    private final int[][] Feld;
    boolean scnd ;
    BufferedImage Image;
    BufferedImage Border;

    /**
     * @param x int gibt die Größe des zu zeichnenden Spielfeldes weiter
     *          <p>
     *          Zeichnet die individuellen Tiles des Spielfeldes
     */
    public Tile(int x) {
        field_size = x;
        Feld = new int[field_size][field_size];
        DummyLeser(Feld);
        Border = Bild.BildLoader("src/Images/Border.jpg");
    }

    /**
     * @return boolean fightstart
     * <p>
     * true --> Es wird gekämpft / platzieren ist vorbei
     * false --> Es wird nicht gekämpft / platzieren ist vorbei
     */
    public static boolean isFightstart() {
        return fightstart;
    }

    /**
     * @param g Übergebenes Graphics Object
     *          <p>
     *          Die Methode zeichnet dem im Konstruktor übergebenen Array entsprechend das Spielfeld
     */
    public void DrawLayer(Graphics g) {

        //Aus Graphics g wird ein Graphics2D Objekt erstellt, dies wird benötigt um später die Striche durch das Spielfeld
        // zu zeichnen welches die einzelnen Tiles/Felder verdeutlicht
        Graphics2D g2 = (Graphics2D) g;

        //Hier wird die dicke der Unterteilungsstriche gesetzt, diese skalieren mit der Größe der einzelnen Felder
        //Es wird aber garantiert, das ihre dicke nie 0 werden kann
        g2.setStroke(new BasicStroke(Math.max(1, TileSize.Tile_Size / 25)));

        //Hier wird die dicke des Rahmens definiert, welcher das Spielfeld umgibt
        //Dieser skaliert auch mit der größe der individuellen Felder
        int SizeofBorder = Math.max(18, TileSize.Tile_Size / 12);

        //Die Größe des Gesamten Panels, welches die Größe des Spielfelds und die Größe des Rahmens beinhaltet
        int Size = field_size * TileSize.Tile_Size + 2 * SizeofBorder;

        //Zeichnet den Rahmen zuerst, sodass das Spielfeld ihn übermalen kann, da er aber Größer ist als das Spielfeld
        //bleibt er immer sichtbar
        g.drawImage(Border, 0, 0, Size, Size, null);


        //Läuft das gesamte Array ab
        for (int y = 0; y < field_size; y++) {
            for (int x = 0; x < field_size; x++) {

                //Überprüft welches Theme dargestellt werden soll, da es nur 2 gibt reicht die if Abfrage
                if (!selectedTheme.equals("Pokemon")) {
                    //Hier wird ein TileSet verwendet
                    Image = Bild.BildLoader("src/Images/Tileset.jpg");
                    //Ein Tileset ist eine Bilddatei welche aus mehreren einzelnen nach einem festen Muster angeordneten
                    //Bildern besteht diese haben alle eine feste Position, sodass jedes Bild klar definiert ist
                    //Und auch klar angesprochen werden kann


                    //Garantie, dass das gewählte Bild eines der 32 frames ist (0 - 31)
                    //Der index dient als Zeiger, welchen Teil des Bildes man darstellen will
                    int index = ((Feld[y][x] + counter) % 32);
                    int yOffset = 0;

                    //Ein Bild ist 32 Pixel hoch und breit, also kann so die Anzahl an Bildern in einer Zeile berechnet werden
                    //Wenn der index also über die Länge einer Zeile hinausreicht ...
                    if (index > (Image.getWidth() / 32) - 1) {
                        while ((index > (Image.getWidth() / 32) - 1)) {

                            //... wird die nun "übersprungene" Anzahl an Bildern von Index abgezogen
                            index = index - (Image.getWidth() / 32);

                            //... und die Suche eine Zeile nach unten verschoben
                            yOffset++;
                        }
                    }

                    //Hier wird nun unser Tile gezeichnet, egal welcher frame dargestellt werden soll es wird immer
                    //die selbe Quelldatei eingelesen
                    g.drawImage(Image,
                            //Das Viereck welches definiert wo unser Tile hingezeichnet werden soll wird definiert
                            //Begonnen mit dem Punkt Oben Links
                            (x * TileSize.Tile_Size) + SizeofBorder,
                            (y * TileSize.Tile_Size) + SizeofBorder,
                            //Und dann dem Punkt Unten Rechts
                            ((x + 1) * TileSize.Tile_Size) + SizeofBorder,
                            ((y + 1) * TileSize.Tile_Size) + SizeofBorder,
                            //Hier wird der "Quellbereich" definiert, also welcher Teil der Quelldatei
                            //auch gezeichnet werden soll
                            //Begonnen mit dem Punkt Oben Links
                            index * 32,
                            yOffset * 32,
                            //Und dann dem Punkt Unten Rechts
                            (index + 1) * 32,
                            (yOffset + 1) * 32,
                            null);

                } else {

                    //Da es sich bei dem Pokemon Theme um statische Bilder handelt kann das Bild einfach geladen...
                    Image = Bild.BildLoader("src/images/PokemonGrass.jpg");


                    //... und dann dem Array entsprechend gezeichnet werden
                    g.drawImage(Image, (x * TileSize.Tile_Size) + SizeofBorder,
                            (y * TileSize.Tile_Size) + SizeofBorder,
                            TileSize.Tile_Size,
                            TileSize.Tile_Size, null);

                }

                g2.drawLine(x * TileSize.Tile_Size + SizeofBorder, SizeofBorder, x * TileSize.Tile_Size + SizeofBorder, field_size * TileSize.Tile_Size + SizeofBorder);
                // Zeichnet alle Vertikale Linien, welche die Felder des Spiels klarer macht
                g2.drawLine(SizeofBorder, y * TileSize.Tile_Size + SizeofBorder, field_size * TileSize.Tile_Size + SizeofBorder, y * TileSize.Tile_Size + SizeofBorder);
                //Zeichnet alle Horizontalen Linien, welche die Felder des Spiels klarer macht


            }
            //Um das Wasser zu animieren müssen verschieden Frames des Wassers aufgerufen werden, das wird durch die Erhöhung des counters erreicht
            //Um aber zu garantieren, das immer eines der 32 Frames gewählt wird, wird mod 32 gerechnet
            if(scnd){
                counter = counter + 1;
                scnd = false ;
            } else {
                scnd = true ;
            }




        }

        //Da man jeweils x + 1 Linien braucht um x Felder zu umranden werden hier die letzten 2 Striche gezeichnet
        g2.drawLine(field_size * TileSize.Tile_Size + SizeofBorder, SizeofBorder, field_size * TileSize.Tile_Size + SizeofBorder, field_size * TileSize.Tile_Size + SizeofBorder);
        g2.drawLine(SizeofBorder, field_size * TileSize.Tile_Size + SizeofBorder, field_size * TileSize.Tile_Size + SizeofBorder, field_size * TileSize.Tile_Size + SizeofBorder);


    }

    /**
     * @param Ebene Übergebenes int[][] Object
     *              <p>
     *              <p>
     *              Hier werden zufällige Werte den Feldern zugewiesen, sodass der Startframe des Tiles spezifisch ist.
     *              Da es so besser aussieht.
     */
    public void DummyLeser(int[][] Ebene) {

        //liest die Größen aus dem übergebenen Array aus
        int height = Ebene.length;
        int width = Ebene[0].length;

        //Fügt an jede Stelle in dem Array eine zufälligen Wert zwischen 0 und 32 ein
        Random rd = new Random();
        for (int i = 0; i < height; i++) {
            int[] arr = new int[width];
            for (int j = 0; j < arr.length; j++) {
                arr[j] = rd.nextInt(33);
            }
            Ebene[i] = arr;

        }
    }

    /**
     * @param g Übergebenes Graphics Object
     *          <p>
     *          Zeigt dem Spieler das er gewonnen hat
     */
    public void YouWin(Graphics g) {
        //Garantiert, dass das genze Panel gefüllt wird
        int SizeofBorder = Math.max(18, TileSize.Tile_Size / 12);
        int Size = field_size * TileSize.Tile_Size + 2 * SizeofBorder;

        //zeichnet den Hintergrund/Grenze des Panels
        g.drawImage(Border, 0, 0, Size, Size, null);

        BufferedImage WinScreen = Bild.BildLoader("src/Images/YWPD.png");

        //Das Bild, das einem sagt, dass man gewonnen hat
        g.drawImage(WinScreen, SizeofBorder, SizeofBorder, Size - 2 * SizeofBorder, Size - 2 * SizeofBorder, null);


    }

    /**
     * @param g Übergebenes Graphics Object
     *          <p>
     *          Zeigt dem Spieler das er verloren hat
     */
    public void YouLost(Graphics g) {
        //Garantiert, dass das genze Panel gefüllt wird
        int SizeofBorder = Math.max(18, TileSize.Tile_Size / 12);
        int Size = field_size * TileSize.Tile_Size + 2 * SizeofBorder;

        //zeichnet den Hintergrund/Grenze des Panels
        g.drawImage(Border, 0, 0, Size, Size, null);

        BufferedImage LossScreen = Bild.BildLoader("src/Images/YFYL.png");

        //Das Bild, das einem sagt, dass man verloren hat
        g.drawImage(LossScreen, SizeofBorder, SizeofBorder, Size - 2 * SizeofBorder, Size - 2 * SizeofBorder, null);

    }

}

