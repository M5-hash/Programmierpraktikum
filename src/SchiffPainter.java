package src;

import java.awt.*;

public class SchiffPainter {

    private int[][] SchiffPos;
    public static int[][] BugHeckMeck = new int[SpielWindow.field_height][SpielWindow.field_width];
    //public static int[][] BugHeckdummy = new int[SpielWindow.field_height][SpielWindow.field_width];      Hatte drüber nachgedacht, dass nicht jedesmal ohne Änderung das nette Schiffzeichner aufgerufen wird
    public static boolean ready = false;
    PlayingField playingField = new PlayingField(10);
    Bildloader Bild = new Bildloader();


    public SchiffPainter() {
        Schiffteil();
    }

    private boolean Enemyplacement() {

        for (int[] schiffPo : SchiffPos) {
            for (int j = 0; j < SchiffPos[0].length; j++) {
                switch (schiffPo[j]) {
                    case 0:                                             //An dieser position befindet sich kein Schiff bzw. Schiffteil
                        break;
                    case 1:                                             //Hier befindet sich ein zerstörtes Schiffteil
                        drawEntity(1);
                    case 2:                                             //Hier befindet ein zerstörtes Schiff
                        drawEntity(2);
                    case 3:                                             //Hier befindet sich ein intaktes Schiffteil
                        drawEntity(3);

                    default:                                        //Invalid Entry mein Gamer, dass sollte aber nicht vorkommen.
                        System.out.println("Invalid entry");
                        return false;
                }
            }
        }
        return true;
    }

    private void drawEntity(int Ship) {
        //Bildloader

    }

    public boolean Schiffplatzieren(int x, int y, int groesse, boolean vertikal) {

        int var = vertikal ? 1 : 0;
        int j = y;

        for (int i = x; i <= x + var * groesse; i++) {
            System.out.println(i + " , " + j);
            for (; j >= y - Math.pow(-1, var) * groesse; j--) {
                System.out.println(i + " , " + j);
            }
        }

        return true;
    }

    /*
     * Ermittelt, wo beim Schiff es sich um das Bug(Vorne) oder Heck(Hinten) handelt, um so das richtige Image zu wählen, sodass die Aesthetic passt.
     *
     * Das Array wir mit getField() besorgt
     *
     * */


    public int[][] Schiffteil() {

        int[][] Schiffe = SpielWindow.playingField.getField();


        /*int x = 0;                                                 //Nur zur Ausgabe in der Konsole muss man irgendwann wieder wegmachen
        for (int i = 0; i < Schiffe.length; i++) {
            for (int j = 0; j < Schiffe[0].length; j++) {
                if (x != i)
                    System.out.print("\n");
                System.out.print(Schiffe[i][j] + " ");
                x = i;
            }
        }*/

//        int[][] BugHeckMeck = new int[SpielWindow.field_height][SpielWindow.field_width];

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
         * */

        boolean x1 = false;
        boolean x2 = false;
        boolean y1 = false;
        boolean y2 = false;

        /*Ausgehend von Schiffe[i][j]
         *
         * x1 entspricht Schiffe[i - 1][j]
         * x2 entspricht Schiffe[i + 1][j]
         * y1 entspricht Schiffe[i][j - 1]
         * y2 entspricht Schiffe[i][j + 1]
         * */

        for (int i = 0; i < Schiffe.length; i++) {
            for (int j = 0; j < Schiffe[0].length; j++) {
                if (Schiffe[i][j] == 3) {

                    x1 = false;
                    x2 = false;
                    y1 = false;
                    y2 = false;

                    if (i == 0 || i == SpielWindow.field_width - 1 || j == 0 || j == SpielWindow.field_height - 1) {
                        if (i == 0) {
                            x1 = false;
                        }
                        if (i == 10) {
                            x2 = false;
                        }
                        if (j == 0) {
                            y1 = false;
                        }
                        if (j == 10) {
                            y2 = false;
                        }
                    } else {
                        if (Schiffe[i - 1][j] != 0)
                            x1 = true;
                        if (Schiffe[i + 1][j] != 0)
                            x2 = true;
                        if(Schiffe[i][j - 1] != 0)
                            y1 = true;
                        if(Schiffe[i][j + 1] != 0)
                            y2 = true;
                    }

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
        /*int x = 0;                                                 //Nur zur Ausgabe in der Konsole muss man irgendwann wieder wegmachen
        for (int i = 0; i < BugHeckMeck.length; i++) {
            for (int j = 0; j < BugHeckMeck[0].length; j++) {
                if (x != i)
                    System.out.print("\n");
                System.out.print(BugHeckMeck[i][j] + " ");
                x = i;
            }
        }*/

        ready = true;

        return BugHeckMeck;

    }

    public boolean Schiffzeichner(Graphics g) {

        Schiffteil();

//            System.out.println("Schiffzeichner wurde aufgerufen");

            String Schiffdir = "Ich bin der String und ich bin ein Platzhalter";
            Image Schiff ; //Nur ein Platzhalter, dass die IDE nicht weint
            boolean dosmthng = false;

            for (int y = 0; y < BugHeckMeck.length; y++) {
                for (int x = 0; x < BugHeckMeck[0].length; x++) {

                    switch (BugHeckMeck[y][x]) {

                        case 0:
                            break;

                        case 1:
                            Schiffdir = "src/Vorne32true.png";
                            dosmthng = true;
                            break;

                        case 2:
                            Schiffdir = "src/Vorne32false.png";
                            dosmthng = true;
                            break;

                        case 3:
                            Schiffdir = "src/Mitte32true.png";
                            dosmthng = true;
                            break;

                        case 4:
                            Schiffdir = "src/Mitte32false.png";
                            dosmthng = true;
                            break;

                        case 5:
                            Schiffdir = "src/Hinten32true.png";
                            dosmthng = true;
                            break;

                        case 6:
                            Schiffdir = "src/Hinten32false.png";
                            dosmthng = true;
                            break;

                        default:
                            System.out.println("Gamer, dass ist aber dick nicht Gut mein bester, das sollte nämlich nicht gehen");
                            System.out.println("Es gibt also einen Fehler in der Schiffteil Methode");

                    }

                    if (dosmthng) {
                        Schiff = Bild.BildLoader(Schiffdir);

                        g.drawImage(Schiff, (x * TileSize.Tile_Width + Tile.side_gapl),
                                (y * TileSize.Tile_Height + Tile.top_gap),
                                TileSize.Tile_Width,
                                TileSize.Tile_Height, null);
                        dosmthng = false;
                    }


                }

            }

        return true;
    }

    public void Wahlstation(Graphics g){


        //int[] Usable =
        Graphics2D g2 = (Graphics2D) g ;

        int xRightEnd = Tile.side_gapl + SpielWindow.field_width * TileSize.Tile_Width;
        int halfheightField = (SpielWindow.field_height * TileSize.Tile_Height) / 2;
        int halfheightBox = 4 * TileSize.Tile_Height;
        int fieldwidth = 3 * TileSize.Tile_Width + TileSize.Tile_Width / 2;
        int FieldBox_gap = Math.max(60, 120 % TileSize.Tile_Width);

        g2.drawLine(xRightEnd + FieldBox_gap, Tile.top_gap + halfheightField + halfheightBox, xRightEnd + FieldBox_gap + fieldwidth, Tile.top_gap + halfheightField + halfheightBox);
        g2.drawLine(xRightEnd + FieldBox_gap, Tile.top_gap + halfheightField - halfheightBox, xRightEnd + FieldBox_gap + fieldwidth, Tile.top_gap + halfheightField - halfheightBox);
        g2.drawLine(xRightEnd + FieldBox_gap, Tile.top_gap + halfheightField + halfheightBox, xRightEnd + FieldBox_gap, Tile.top_gap + halfheightField - halfheightBox);
        g2.drawLine(xRightEnd + FieldBox_gap + fieldwidth, Tile.top_gap + halfheightField - halfheightBox, xRightEnd + FieldBox_gap + fieldwidth, Tile.top_gap + halfheightField + halfheightBox);

        Image Schiff = Bild.BildLoader("src/Vorne32false.png");

        //switch[] (Usable)

        g.drawImage(Schiff, xRightEnd + FieldBox_gap + TileSize.Tile_Width / 2,
                Tile.top_gap + halfheightField - halfheightBox + TileSize.Tile_Height / 2,
                TileSize.Tile_Width,
                TileSize.Tile_Height, null);

        g.drawImage(Schiff, xRightEnd + FieldBox_gap + TileSize.Tile_Width * 2 ,
                Tile.top_gap + halfheightField - halfheightBox + TileSize.Tile_Height / 2,
                TileSize.Tile_Width,
                TileSize.Tile_Height, null);

        g.drawImage(Schiff, xRightEnd + FieldBox_gap + TileSize.Tile_Width  / 2,
                Tile.top_gap + halfheightField + halfheightBox - (TileSize.Tile_Height * 5 )/ 2,
                TileSize.Tile_Width,
                TileSize.Tile_Height, null);

        g.drawImage(Schiff, xRightEnd + FieldBox_gap + TileSize.Tile_Width * 2 ,
                Tile.top_gap + halfheightField + halfheightBox - (TileSize.Tile_Height * 7 )/ 2,
                TileSize.Tile_Width,
                TileSize.Tile_Height, null);

    }


}
