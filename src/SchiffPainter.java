package src;

import java.awt.*;

public class SchiffPainter {

    public static int[][] BugHeckMeck = new int[SpielWindow.field_size][SpielWindow.field_size];
    //public static int[][] BugHeckdummy = new int[SpielWindow.field_size][SpielWindow.field_size];      Hatte drüber nachgedacht, dass nicht jedesmal ohne Änderung das nette Schiffzeichner aufgerufen wird
    public static boolean ready = false;
    Bildloader Bild = new Bildloader();
    String Fieldof ;
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
    private int[][] SchiffPos;


    public SchiffPainter(String Feldvon) {

        Fieldof = Feldvon ;

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

    /*
     * Ermittelt, wo beim Schiff es sich um das Bug(Vorne) oder Heck(Hinten) handelt, um so das richtige Image zu wählen, sodass die Aesthetic passt.
     *
     * Das Array wir mit getField() besorgt
     *
     * */


    public int[][] Schiffteil() {

        System.out.println("Schiffteil wurde aufgerufen");

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

//        int[][] BugHeckMeck = new int[SpielWindow.field_size][SpielWindow.field_size];

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
        SpielWindow.change = false;

        return BugHeckMeck;

    }


    /**
     * @param g wird benötigt, sodass eine Variable des Typs Graphics existiert
     * @return ob die Schiffe gezeichnet wurden
     */
    public boolean Schiffzeichner(Graphics g) {

        int[][] dummy;

        if (SpielWindow.change) Schiffteil();

//            System.out.println("Schiffzeichner wurde aufgerufen");

        String Schiffdir = "Ich bin der String und ich bin ein Platzhalter";
        Image Schiff; //Nur ein Platzhalter, dass die IDE nicht weint
        boolean dosmthng = false;


        //System.out.println(TileSize.getFighting());

        dummy = switch (Fieldof) {
            case "Spieler" -> BugHeckMeck;
            case "GegnerKI" -> getEnemyPlacement;
            case "GegnerMensch" -> getEnemyPlacement;
            default -> BugHeckMeck;
        };


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
                        Schiffdir = "src/Images/Nebel1.png";
                        dosmthng = true;
                        //System.out.println("Ich wurde aufgerufen");
                        break;

                    default:
                        System.out.println("Gamer, dass ist aber dick nicht Gut mein bester, das sollte nämlich nicht gehen");
                        System.out.println("Es gibt also einen Fehler in der Schiffteil Methode");

                }


                if (dosmthng) {
                    Schiff = Bild.BildLoader(Schiffdir);

                    g.drawImage(Schiff, (x * TileSize.Tile_Size + Tile.side_gapl),
                            (y * TileSize.Tile_Size + Tile.top_gap),
                            TileSize.Tile_Size,
                            TileSize.Tile_Size, null);
                    dosmthng = false;
                }
            }
        }

        return true;
    }


}
