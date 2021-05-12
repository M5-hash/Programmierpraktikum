package src;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tile extends JPanel {

    private BufferedImage Image;
    private Bildloader Bild = new Bildloader();
    private final int[][] Feld;
    private final int field_height;
    private final int field_width;
    private static int counter = 0;
    protected static int top_gap = 30;
    protected static int side_gapl = 60;


    /*Liest die Groesse bzw, das Format des Spielfelds,welches durch das
     *  weitergegebene 2 Dimensionales int-Array dargestellt wird.
     */

   /* public Tile(int[][] FeldVorgabe) {                                       //Konstruktor der Funktioniert, nimmt das ganze Array und schreibt es in eine lokale Variable und Speichert zudem noch Momentan Nutzlos könnte ich eigentlich löschen
        this.field_height = FeldVorgabe.length;                            //Höhe und breite des Arrays
        this.field_width = FeldVorgabe[0].length;

        Feld = new int[field_height][field_width];
        TileArrangement(FeldVorgabe);                                   //Das umschreiben geschiet zugegebener Maßen in der TileArrangement aber
    }*/

    /*Wird verwendet, wenn das Feld nicht durch ein direktes Array erstellt werden soll sondern nur mit angaben für Höhe und Breite
     * Wir mit int Werten für Höhe und Breite sepperat angegeben. */
    public Tile(int y, int x) {
        this.field_height = y;
        this.field_width = x;
        Feld = new int[field_height][field_width];
        DummyLeser(Feld);
    }

    /*kopiert Array in anderen Array, ohne das funktioniert DummyLeser leider nicht, bin aber zu faul/dumm um zu verstehen, warum das so ist*/

    public void TileArrangement(int[][] Feldvorgabe) {
        System.out.println("TileArrangement aufgerufen");

        for (int y = 0; y < field_height; y++) {
            if (field_width >= 0) System.arraycopy(Feldvorgabe[y], 0, Feld[y], 0, field_width);
        }
        Image = Bild.BildLoader("src/Images/Tileset.jpg");
    }

    /*
     *++
     *Wird verwendet um die jeweiligen Tiles der Wasser Animation zu zeichnen
     * Da aus einem TileSet gelesen muss nicht nur das Ziel bzw. die Position davon geändert werden sondern auch die Source
     *
     * */

    public void DrawLayer(Graphics g) {                          //Wird nur gebraucht, falls wir alle TileFrames in einem Bild ablegen wollen (TileSet), da in diesem Fall Zeilenumsprünge benötigt werden
        for (int y = 0; y < field_height; y++) {
            for (int x = 0; x < field_width; x++) {
                int index = ((Feld[y][x] + counter) % 32);
                int yOffset = 0;

                if (index > (Image.getWidth() / 32) - 1) {           //
                    while ((index > (Image.getWidth() / 32) - 1)) {
                        index = index - (Image.getWidth() / 32);
                        yOffset++;
                    }
                }

                g.drawImage(Image, (x * TileSize.Tile_Width + side_gapl),                  //ok das ist jetzt blöd zu erklären
                        (y * TileSize.Tile_Height + top_gap),                           //Es wird ein Viereck zwischen diesen 2 Punkten aufgeschlagen, die ersten 2 sind das linke obere ende
                        ((x + 1) * TileSize.Tile_Width + side_gapl),                      //die anderen 2 sind das rechte untere ende. Es handelt sich hierbei um das Ziel
                        ((y + 1) * TileSize.Tile_Height + top_gap),
                        index * 32,                                         //Es wird ein Viereck zwischen diesen 2 Punkten aufgeschlagen, die ersten 2 sind das linke obere ende
                        yOffset * 32,                                       //die anderen 2 sind das rechte untere ende. Es handelt sich hierbei um die Quelle, da die Source und das ausgegebene
                        (index + 1) * 32,                                   //gleich groß sein sollen sind die Variablen nahezu identisch
                        (yOffset + 1) * 32,
                        null);

            }
        }
        counter = (counter + 1) % 32;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(Math.max(1, TileSize.Tile_Height / 25)));       //nach Bauchgefühl gesetz, wie viel Bild und wie viel des einzelnen Tiles Strich sein soll, das max garantiert, dass der Strich nicht dünner als ein Pixel wird
        for (int x = 0; x < (field_width + 1); x++) {
            g2.drawLine(side_gapl + x * TileSize.Tile_Width, top_gap, side_gapl + x * TileSize.Tile_Width, top_gap + field_height * TileSize.Tile_Height);
        } // Zeichnet alle Vertikale Linien, welche die Felder des Spiels klarer macht
        for (int y = 0; y < (field_width + 1); y++) {
            g2.drawLine(side_gapl, top_gap + y * TileSize.Tile_Height, side_gapl + field_width * TileSize.Tile_Width, top_gap + y * TileSize.Tile_Width);
        } //Zeichnet alle Horizontalen Linien, welche die Felder des Spiels klarer macht


        boolean vertikal = SpielWindow.horizontal;

        //BufferedImage Schiffkopf = Bild.BildLoader()

        //g.drawImage(Schiffkopf,)
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