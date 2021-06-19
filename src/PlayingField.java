package src;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class PlayingField {
    /**
     * 0 = Wasser
     * 1 = Abgeschossenes Schiffsteil
     * 2 = Komplett zerstört
     * 3 = Normales Schiffsteil
     * 4 = Geplantes Schiffsteil, noch nicht gesetzt
     * 5 = Wasser abgeschossen
     */
    private int[][] field;
    private int ships = 0;

    /**
     * 0 = Kein Computerspieler-Spiel
     * 1 = Computerspieler Einfach
     * 2 = Computerspieler Mittel
     * 3 = Computerspieler Schwer
     */
    private int com = 0;

    /**
     * Gibt zurück wie viel % des Spielfeldes mit Schiffen gefüllt ist
     *
     * @param rows   Größe des Spielfeldes
     * @param sparts Anzahl der Schiffsteile
     * @return Prozentuale Angabe der Schiffe im Vergleich zum Wasser
     */
    public static int shipsPercentage(int rows, int sparts) {
        return (int) ((double) sparts / (double) (rows * rows));
    }

    /**
     * Konstruktor
     *
     * @param rows - Höhe und Breite des Spielfeldes
     */
    public PlayingField(int rows) {
        this.initField(rows);
    }

    /**
     * Leerer-Konstruktor, um ein PlayingField zu erstellen, welches seine Daten über this.loadGame erhält
     */
    public PlayingField() {
    }

    /**
     * Erzeugt/Initialisiert das Spielfeld-Array
     *
     * @param rows Breite und Länge
     */
    private void initField(int rows) {
        field = new int[rows][rows];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < rows; x++) {
                field[y][x] = 0;
            }
        }
    }

    /**
     * field-Getter
     *
     * @return Gibt field zurück
     */
    public int[][] getField() {
        return this.field;
    }

    /**
     * Setzen eines Schiffes auf das Spielfeld und die davorige Überprüfung ob das Schiff überhaupt dorthin platziert werden darf.
     * Wenn set auf false, dann wird nur zurückgegeben ob das Schiff gesetzt werden darf, oder nicht
     *
     * @param length     Schifflänge
     * @param x          X-Koordinate vom Schiffkopf
     * @param y          Y-Koordinate vom Schiffkopf
     * @param horizontal In welcher Richtung vom Schiffskopf der Rest des Schiffes ist
     * @param set        True: Schiff wird paltziert, wenn erlaubt
     *                   False: Es wird nur zurückgegeben, ob das Schiff überhaupt dort paltziert werden darfs
     * @return True = Schiff gesetzt, False = Schiff durfte nicht gesetzt werden
     */
    private boolean setShipIntern(int length, int x, int y, boolean horizontal, boolean set) {
        for (int i = 0; i < length; i++) {
            //Überprüfen ob das zu besetzende Feld erlaubt ist
            //Nicht innerhalb des Spielfeldes
            if (x < 0 || x >= field.length || y < 0 || y >= field.length) {
                this.replaceNotfinal(0);
                return false;
            }

            //Überprüfen ob Schiffsteil ein anderes Schiff andockt, oder auf einem anderen Schiff ist
            //
            // x-1 y-1 | x y-1 | x+1 y-1
            // x-1 y   | x y   | x+1 y
            // x-1 y+1 | x y+1 | x+1 y+1
            //
            //xC, yC: Wenn an Spielfeldgrenze, erlauben
            boolean xC = x - 1 < 0;
            boolean yC = y - 1 < 0;
            boolean xP = x + 1 >= field.length;
            boolean yP = y + 1 >= field.length;
            //.println("xC: " + xC + " ||| x - 1: " + (x-1)  + " <= 0");
            //.println("yC: " + yC + " ||| y - 1: " + (y-1)  + " <= 0");

            if ((xC || yC || field[y - 1][x - 1] != 3)
                    && (yC || field[y - 1][x] != 3)
                    && (yC || xP || field[y - 1][x + 1] != 3)
                    && (xC || field[y][x - 1] != 3)
                    && field[y][x] != 3
                    && (xP || field[y][x + 1] != 3)
                    && (xC || yP || field[y + 1][x - 1] != 3)
                    && (yP || field[y + 1][x] != 3)
                    && (xP || yP || field[y + 1][x + 1] != 3)
            ) {
                //.println("field[y][x]: " + field[y][x] + " (y: " + y + ")(x: " + x + ")");
                field[y][x] = 4;
            } else {
                //Markierte Felder zurücksetzen, wenn Schiff nicht gesetzt werden darf
                this.replaceNotfinal(0);
                //.println(Arrays.deepToString(field).replace("]", "]\n"));
                return false;
            }

            //Abhängig von der Richtung X/Y anpassen
            if (horizontal) {
                x++; //Links nach rechts
            } else {
                y++; //Oben nach unten
            }
        }

        //Schiffmarkierung auf Schiff setzen
        if (set) {
            this.replaceNotfinal(3);
            this.ships++;
            //.println(Arrays.deepToString(field).replace("]", "]\n"));
        }else{
            this.replaceNotfinal(0);
        }
        return true;
    }

    /**
     * Wrapper von setShipWithCheck.
     * Setzt ein Schiff, wenn erlaubt.
     *
     * param siehe setShipIntern
     * return siehe setShipIntern
     */
    public boolean setShip(int length, int x, int y, boolean horizontal) {
        return setShipIntern(length, x, y, horizontal, true);
    }

    /**
     * Wrapper von setShipWithCheck.
     * Prüft ob ein Schiff an übergebene Stelle gesetzt werden darf.
     *
     * param siehe setShipIntern
     * return siehe setShipIntern
     */
    public boolean checkShip(int length, int x, int y, boolean horizontal) {
        return setShipIntern(length, x, y, horizontal, false);
    }

    /**
     * Ermittelt Schiffskopf eines Schiffsteils und gibt die Koordinaten zurück
     *
     * @param x X-Koordinate des zu überprüfenden Teiles
     * @param y Y-Koordinate des zu überprüfenden Teiles
     * @return Gibt X und Y Koordinate vom Kopf eines Schiffes zurück
     */
    private int[] getHeadOfShip(int x, int y) throws Exception {
        checkCoordinatesInField(x, y);

        int headX = x;
        int headY = y;

        while (headY - 1 > 0 && this.field[headY - 1][headX] > 0 && this.field[headY - 1][headX] < 4) {
            headY--;
        }
        while (headX - 1 > 0 && this.field[headY][headX - 1] > 0 && this.field[headY][headX - 1] < 4) {
            headX--;
        }

        return new int[]{headY, headX};
    }

    /**
     * Wrapper für getHeadOfShip mit zusätzlicher Ermittlung der Ausrichtung des Schiffes
     *
     * @param x X-Koordinate des zu überprüfenden Teiles
     * @param y Y-Koordinate des zu überprüfenden Teiles
     * @return Rückgabe von Int-Array:
     * [0] X-Koordinate vom Kopf
     * [1] Y-Koordinate vom Kopf
     * [2] 1 == Horizontal, 0 == Vertikal
     * @throws Exception, wenn x/y auserhalb des Spielfeldes
     */
    public int[] getDirHeadOfShip(int x, int y) throws Exception {
        int[] data = getHeadOfShip(x, y);
        int horizontal = 0; //int statt bool, wegen int-Array Rückgabe

        //Überprüfen ob rechts vom Schiff ein weiteres Teil. Dann ist das Schiff Horizontal ausgelegt, sonst Vertikal
        if (x + 1 < field.length && field[y][x + 1] > 0 && this.field[y][x + 1] < 4) {
            horizontal = 1;
        }

        return new int[]{data[0], data[1], horizontal};
    }

    /**
     * Schiffsteil und benachbarte Schiffsteile entfernen
     *
     * @param x X-Koordinate eines Schiffteiles
     * @param y Y-Koordinate eines Schiffteiles
     */
    public void deleteShip(int x, int y) throws Exception {
        checkCoordinatesInField(x, y);

        int[] data = getDirHeadOfShip(x, y);
        int xOffset = 0;
        int yOffset = 0;

        while (data[0] + xOffset < this.field.length && data[1] + yOffset < this.field.length
                && this.field[data[1] + yOffset][data[0] + xOffset] == 1) {

            this.field[data[1] + yOffset][data[0] + xOffset] = 0;

            if (data[2] == 1) xOffset++;
            else yOffset++;
        }

        this.ships--;
    }

    /**
     * Überprüft ob ein Schuss ein Schiff erwischt hat
     *
     * @param x X-Koordinate Schiffkopf
     * @param y Y-Koordinate Schiffkopf
     * @return 0: Kein Treffer, 1: Treffer, 2: Treffer und versenkt
     */
    public int isShot(int x, int y) throws Exception {
        checkCoordinatesInField(x, y);

        if (this.field[y][x] == 0) {
            this.field[y][x] = 5;
        } else if (this.field[y][x] == 3) {
            this.field[y][x] = 1;

            int[] data = getDirHeadOfShip(x, y);
            if (isShipDestroyed(data[0], data[1], data[2] == 1)) {
                markShipDestroyed(data[0], data[1], data[2] == 1);
                return 2;
            }

            return 1;
        }

        return 0;
    }

    /**
     * Markiert ein Schiff als komplett zerstört (Int-Wert 2)
     *
     * @param x          X-Koordinate Schiffkopf
     * @param y          Y-Koordinate Schiffkopf
     * @param horizontal Schiff ist horizontal gelegt
     */
    private void markShipDestroyed(int x, int y, boolean horizontal) throws Exception {
        checkCoordinatesInField(x, y);

        //Hier ist gefühlt ne Menge doppelter Code im Vergleich zu isShipDestroyed und das find ich hässlich :)
        this.ships--;

        int xOffset = 0;
        int yOffset = 0;

        //Nach rechts bzw nach unten
        while (x + xOffset < this.field.length && y + yOffset < this.field.length
                && this.field[y + yOffset][x + xOffset] != 0 && this.field[y + yOffset][x + xOffset] != 5) {
            this.field[y + yOffset][x + xOffset] = 2;

            if (horizontal) xOffset++;
            else yOffset++;
        }
    }

    /**
     * Überprüft ob ein komplettes Schiff zerstört ist
     *
     * @param x          X-Koordinate Schiffkopf
     * @param y          Y-Koordinate Schiffkopf
     * @param horizontal Schiff horizontal überprüfen
     * @return True: Komplettes Schiff ist zerstört
     */
    private boolean isShipDestroyed(int x, int y, boolean horizontal) throws Exception {
        checkCoordinatesInField(x, y);

        int xOffset = 0;
        int yOffset = 0;

        while (x + xOffset < this.field.length && y + yOffset < this.field.length
                && this.field[y + yOffset][x + xOffset] == 1) {
            if (horizontal) xOffset++;
            else yOffset++;
        }

        //Überprüfen ob nach zerstörten Schiffsteilen Wasser
        return this.field[y + yOffset][x + xOffset] == 0 || this.field[y + yOffset][x + xOffset] == 5;
    }

    //TODO entfernen, bei Release-Version. Nur zum testen
    public static void main(String[] args) {
        try {
            PlayingField spieler = new PlayingField(10);
            spieler.setShip(4, 1, 1, true);
            spieler.setShip(3, 5, 3, false);
            spieler.setShip(2, 2, 6, true);
            spieler.setShip(2, 7, 8, false);
            //.println(Arrays.deepToString(spieler.getField()).replace("]", "]\n"));

            ComPlayerEasy com = new ComPlayerEasy(10, new int[]{4, 3, 2, 2});
            //.println(Arrays.deepToString(com.pf.getField()).replace("]", "]\n"));
        }catch(Exception ex){
            //.println(ex.getMessage());
        }
        /*
        PlayingField pf = new PlayingField(10);
        PlayingField pf2 = new PlayingField();

        pf.setShip(3, 4, 4, true);
        pf.setShip(4, 0, 0, false);

        try {
            pf.saveGame(199191918, 0, false);
            //.println("\nLaden:" + pf2.loadGame(199191918, false));
        } catch (IOException e) {
            //.println(e.getMessage());
        }

        //.println(Arrays.deepToString(pf2.field).replace("]", "]\n"));*/
    }

    /**
     * Wrapper für saveGame (Ohne com Angabe)
     */
    public void saveGame(long id, int status) throws IOException{
        this.saveGame(id, status, false);
    }

    /**
     * Wrapper für loadGame (Ohne com Angabe)
     */
    public int loadGame(long id) throws FileNotFoundException {
        return this.loadGame(id, false);
    }

    /**
     * Speichern des Spielstandes
     *
     * @param id     ID des Spielstandes
     * @param status 0 = Schiffe setzen
     *               1 = Spieler darf schießen
     *               2 = Gegner darf schießen
     * @throws IOException Wenn Problem beim Datei beschreiben
     */
    //TODO ComputerGegner, in Spieler-Datei Schwierigkeitsgrad von Com, in Com-Datei (Auswahl mit extra Param) Spielfeld vom Com
    public void saveGame(long id, int status, boolean com) throws IOException {
        //Saves-Ordner erstellen
        File directory = new File("." + File.separator + "Saves");
        if (!directory.exists()) directory.mkdir();

        //Speicherdatei erstellen bzw überschreiben
        File file = new File("." + File.separator + "Saves" + File.separator + id + "_save.txt");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        //Spielzustand auslesen und in String schreiben
        String save = "" + status;
        save += "," + this.field.length + ",";
        for (int i = 0; i < this.field.length; i++) {
            for (int j = 0; j < this.field.length; j++) {
                save += this.field[i][j];
            }
        }

        //Daten in Datei schreiben
        bw.write(save);
        bw.close();
    }

    /**
     * Laden eines Spielstandes
     *
     * @param id ID des Spielstandes
     * @return Status
     * 0 = Schiffe setzen
     * 1 = Spieler darf schießen
     * 2 = Gegner darf schießen
     * 3 = Computer-Gegner-Spiel & Schiffe setzen
     * 4 = Computer-Gegner-Spiel & Spieler darf schißen (Computer darf schießen
     * @throws FileNotFoundException Wenn die Spielstand-Datei nicht existiert
     */
    //TODO ComputerGegner, in Spieler-Datei Schwierigkeitsgrad von Com, in Com-Datei (Auswahl mit extra Param) Spielfeld vom Com
    public int loadGame(long id, boolean com) throws FileNotFoundException {
        File f = new File("." + File.separator + "Saves" + File.separator + id + "_save.txt");
        Scanner s = new Scanner(f);

        String save = "";
        int status = -1;

        if (s.hasNextLine()) save = s.nextLine();

        //Status auslesen (Erste Zahl in save)
        status = Integer.parseInt("" + save.charAt(0));
        save = save.substring(2);

        //Spielfeldgröße auslesen
        String[] saveArr = save.split(",");
        int rows = Integer.parseInt(saveArr[0]);
        this.initField(rows);
        save = saveArr[1];

        char[] cArr = save.toCharArray();

        //Spielfeld auslesen
        int k = 0;
        for (int i = 0; i < this.field.length; i++) {
            for (int j = 0; j < this.field.length; j++) {
                this.field[i][j] = Integer.parseInt("" + cArr[k++]);
            }
        }

        for (char c : save.toCharArray()) {
            if (status == -1) status = c;

        }

        //.println(save);

        return status;
    }

    /**
     * Gibt zurück ob alle Schiffe zerstört wurden.
     * Erst sinnvoll nutzbar nach dem Platzieren der Schiffe
     *
     * @return True: Spiel vorbei
     */
    public boolean gameover() {
        return this.ships <= 0;
    }

    /**
     * Verändert alle Notfinal-Werte auf dem Spielfeld in set
     *
     * @param set Status in den die Notfinal-Werte umgeändert werden sollen
     */
    private void replaceNotfinal(int set) {
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field.length; x++) {
                if (field[y][x] == 4) {
                    field[y][x] = set;
                }
            }
        }
    }

    /**
     * Überprüft ob Koordinaten im Spielfeld sind, falls nicht wird eine Exception geworfen
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @throws Exception, wenn X/Y Koordinate nicht im Spielfeld
     */
    private void checkCoordinatesInField(int x, int y) throws Exception {
        if (x < 0 || x >= this.field.length || y < 0 || y > this.field.length) {
            throw new Exception("Die angegebenen Koordinaten befinden sich nicht im Spielfeld");
        }
    }
}