package src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class SchiffPainter {

    public static int[][] BugHeckMeck = new int[SpielWindow.field_size][SpielWindow.field_size];
    //public static int[][] BugHeckdummy = new int[SpielWindow.field_size][SpielWindow.field_size];      Hatte drüber nachgedacht, dass nicht jedesmal ohne Änderung das nette Schiffzeichner aufgerufen wird
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




    public SchiffPainter(String Feldvon) {

        Fieldof = Feldvon;
        System.out.println( Fieldof );

        Schiffteil();
    }

    /*
     * Ermittelt, wo beim Schiff es sich um das Bug(Vorne) oder Heck(Hinten) handelt, um so das richtige Image zu wählen, sodass die Aesthetic passt.
     *
     * Das Array wir mit getField() besorgt
     *
     * */


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

    public void Schiffzeichner(Graphics g, boolean f) {

        fits = f ;
        Schiffzeichner(g);

    }


    /**
     * @param g wird benötigt, sodass eine Variable des Typs Graphics existiert
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
//                        System.out.println("Gamer, dass ist aber dick nicht Gut mein bester, das sollte nämlich nicht gehen");
//                        System.out.println("Es gibt also einen Fehler in der Schiffteil Methode");
                        Schiffdir = "src/Images/PokeTest32.jpg";

                }


                if (dosmthng) {
                    Schiff = Bild.BildLoader(Schiffdir);

                    BufferedImage dummyImg ;


                    if(Fieldof.equals("Vorhersage")){
                        dummyImg = colorpng(Schiff) ;
                    } else {
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

        counter = 0 ;

    }

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

    private static BufferedImage colorpng(BufferedImage image) {

        BufferedImage copy = deepCopy(image);

        int width = copy.getWidth();
        int height = copy.getHeight();

        System.out.println("Es wurden insgesamt " + counter++ + " Bilder bearbeitet/umgefärbt");

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (((copy.getRGB(x, y) >> 24) & 0xFF) != 0 ) {

                    Color red = new Color(0.42f,0.0f,0.0f,0.42f);
                    Color green = new Color(0.0f, 0.5f, 0.42f, 0.82f) ;
                    if(fits) copy.setRGB(x, y,green.getRGB());
                    else copy.setRGB(x,y,red.getRGB());
                }
            }
        }
        return copy;
    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied() ;
        WritableRaster wr = bi.copyData(null) ;
        return new BufferedImage(cm , wr, isAlphaPremultiplied, null) ;
    }


}
