package src;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tile extends JPanel {

    public static int field_size;
    protected static int top_gap = 0;
    protected static int side_gapl = 0;
    static boolean fightstart = false;
    private static int counter = 0;
    private final Bildloader Bild = new Bildloader();
    private final int[][] Feld;
    String Fieldof;
    private BufferedImage Image;
    private BufferedImage Border ;


    /*Liest die Groesse bzw, das Format des Spielfelds,welches durch das
     *  weitergegebene 2 Dimensionales int-Array dargestellt wird.
     */

   /* public Tile(int[][] FeldVorgabe) {                                       //Konstruktor der Funktioniert, nimmt das ganze Array und schreibt es in eine lokale Variable und Speichert zudem noch Momentan Nutzlos könnte ich eigentlich löschen
        this.field_size = FeldVorgabe.length;                            //Höhe und breite des Arrays
        this.field_size = FeldVorgabe[0].length;

        Feld = new int[field_size][field_size];
        TileArrangement(FeldVorgabe);                                   //Das umschreiben geschiet zugegebener Maßen in der TileArrangement aber
    }*/

    /**
     * @param x Gibt die Größe des Feldes an, welches in Tile gezeichnet werden soll
     *          Diese Information wird per int mitgeteilt
     */

    public Tile(int x, String Feldvon) {
        field_size = x;
        Fieldof = Feldvon;
        Feld = new int[field_size][field_size];
        DummyLeser(Feld);
        Border = Bild.BildLoader("src/Images/Border.jpg");
    }

    /**
     * @param Feldvorgabe Ist das 2-Dimensionale int Array, von welchem das Spielfeld abgeleitet werden soll.
     *                    Momentan handelt es sich hierbei immer um den return aus DummyLeser
     *                    <p>
     *                    kopiert Array in anderen Array, ohne das funktioniert DummyLeser leider nicht, bin aber zu faul/dumm um zu verstehen, warum das so ist
     */
    public void TileArrangement(int[][] Feldvorgabe) {
        System.out.println("TileArrangement aufgerufen");

        for (int y = 0; y < field_size; y++) {
            if (field_size >= 0) System.arraycopy(Feldvorgabe[y], 0, Feld[y], 0, field_size);
        }
        Image = Bild.BildLoader("src/Images/Tileset.jpg");
    }

    /**
     * @param g Wird verwendet um die jeweiligen Tiles der Wasser Animation zu zeichnen
     *          Da aus einem TileSet gelesen muss nicht nur das Ziel bzw. die Position davon geändert werden sondern auch die Source
     */
    public void DrawLayer(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(Math.max(1, TileSize.Tile_Size / 25)));       //nach Bauchgefühl gesetz, wie viel Bild und wie viel des einzelnen Tiles Strich sein soll, das max garantiert, dass der Strich nicht dünner als ein Pixel wird
        int SizeofBorder = Math.max(18, TileSize.Tile_Size / 12) ;
        int Size = field_size * TileSize.Tile_Size + 2 * SizeofBorder ;



        g.drawImage(Border, 0, 0, Size, Size, null );

        for (int y = 0; y < field_size; y++) {                                // Wird nur gebraucht, falls wir alle TileFrames in einem Bild ablegen wollen (TileSet), da in diesem Fall Zeilenumsprünge benötigt werden
            for (int x = 0; x < field_size; x++) {

                if (Fieldof.equals("Spieler")) {

                    int index = ((Feld[y][x] + counter) % 32);
                    int yOffset = 0;

                    if (index > (Image.getWidth() / 32) - 1) {                      // Da das Tileset nicht nur horizontal ausgerichtet ist, muss jedes mal wenn die rechte Seite des TileSets erreicht wurde unsere source
                        while ((index > (Image.getWidth() / 32) - 1)) {             // Wieder an die linke Seite des Bildes verschoben werden
                            index = index - (Image.getWidth() / 32);
                            yOffset++;                                              //Aber um eine Zeile nach unten verschoben
                        }
                    }

                    g.drawImage(Image, (x * TileSize.Tile_Size) + SizeofBorder,                  //ok das ist jetzt blöd zu erklären
                            (y * TileSize.Tile_Size) + SizeofBorder,                           //Es wird ein Viereck zwischen diesen 2 Punkten aufgeschlagen, die ersten 2 sind das linke obere ende
                            ((x + 1) * TileSize.Tile_Size) + SizeofBorder,                      //die anderen 2 sind das rechte untere ende. Es handelt sich hierbei um das Ziel
                            ((y + 1) * TileSize.Tile_Size) + SizeofBorder,
                            index * 32,                                         //Es wird ein Viereck zwischen diesen 2 Punkten aufgeschlagen, die ersten 2 sind das linke obere ende
                            yOffset * 32,                                       //die anderen 2 sind das rechte untere ende. Es handelt sich hierbei um die Quelle, da die Source und das ausgegebene
                            (index + 1) * 32,                                   //gleich groß sein sollen sind die Variablen nahezu identisch
                            (yOffset + 1) * 32,
                            null);

                }
                if (Fieldof.equals("GegnerKI") || Fieldof.equals("GegnerMensch")) {

                    Image = Bild.BildLoader("src/Images/PokeTest.jpg*");

                    g.drawImage(Image, (x * TileSize.Tile_Size) + SizeofBorder,                  //ok das ist jetzt blöd zu erklären
                            (y * TileSize.Tile_Size) + SizeofBorder,                           //Es wird ein Viereck zwischen diesen 2 Punkten aufgeschlagen, die ersten 2 sind das linke obere ende
                            TileSize.Tile_Size,                      //die anderen 2 sind das rechte untere ende. Es handelt sich hierbei um das Ziel
                            TileSize.Tile_Size, null);

                }

                g2.drawLine(x * TileSize.Tile_Size + SizeofBorder, SizeofBorder, x * TileSize.Tile_Size + SizeofBorder, field_size * TileSize.Tile_Size + SizeofBorder);
                // Zeichnet alle Vertikale Linien, welche die Felder des Spiels klarer macht

                g2.drawLine(SizeofBorder, y * TileSize.Tile_Size + SizeofBorder, field_size * TileSize.Tile_Size + SizeofBorder, y * TileSize.Tile_Size + SizeofBorder);
                //Zeichnet alle Horizontalen Linien, welche die Felder des Spiels klarer macht


            }
            counter = (counter + 1) % 32;


        }


    }

    /*public static Tile DateiLeser(String datei_dir) {
        Tile Ebene;

        ArrayList<ArrayList<Integer>> dummyLayout = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(datei_dir))) {
            String ZeileX;
            while ((ZeileX = br.readLine()) != null) {
                if (ZeileX.isEmpty()) {
                    continue;
                }

                ArrayList<Integer> EinzelZeile = new ArrayList<>();

                String[] Werte = ZeileX.trim().split(" ");
                for (String string : Werte) {
                    if (!string.isEmpty()) {
                        int id = Integer.parseInt(string);

                        EinzelZeile.add(id);
                    }

                }

                dummyLayout.add(EinzelZeile);

            }
        } catch (IOException e) {

            System.out.println("konnte nicht mehr aus Datei ausgelesen werden");

        }

        int height = dummyLayout.get(0).size();
        int width = dummyLayout.size();

        Ebene = new Tile(height, width);

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                Ebene.Feld[y][x] = dummyLayout.get(y).get(x);
            }
        }

        Bildloader dummy = null;
        Ebene.Image = dummy.BildLoader("D:\\ProgPrak\\Tom\\src\\Tileset.jpg");

//        Ebene.Bild = Ebene.BildLoader("D:\\ProgPrak\\Tom\\src\\Tileset.jpg");

        return Ebene;
    }*/

    /**
     * @param Ebene Gibt die Größe des Spielfelds an, welche vom DummyLeser gefüllt werden soll
     *              <p>
     *              Es sieht meiner Meinung nach einfach schöner aus, wenn die Animation nicht für jedes Frame gleichzeitig den selben Frame zeigt
     *              Hier werden zufällige Werte den Feldern zugewiesen, sodass der Startframe Tile spezifisch ist.
     */
    public void DummyLeser(int[][] Ebene) {

        int height = Ebene.length;
        int width = Ebene[0].length;

        Random rd = new Random(); // creating Random object
        for (int i = 0; i < height; i++) {
            int[] arr = new int[width];
            for (int j = 0; j < arr.length; j++) {
                arr[j] = rd.nextInt(33);
            }
            Ebene[i] = arr;
            //System.out.println(Arrays.toString(Ebene[i]));
        }

        TileArrangement(Ebene);
    }

}