package src;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

    /**
     * Siehe field, mit Abweichung:
     * 3: Schuss ohne Antwort
     * 4: Schiff kann dort nicht sein
     */
    private int[][] fieldEnemy;

    /**
     * Status des Spieles
     * 0 = Schiffe setzen
     * 1 = Spieler darf schießen
     * 2 = Gegner darf schießen
     */
    private int status = 0;

    /**
     * True: Wenn der Spieler mit diesem PlayingField der Server ist,
     * bzw., wenn dieser Spieler als erstes schießen darf
     * <p>
     * Sonst False.
     */
    private boolean isServer = false;

    /**
     * Anzahl der platzierten und noch ganzen Schiffe
     */
    private int ships = 0;

    /**
     * Die erlaubten Schiffe die platziert werden dürfen
     */
    private int[] allowedShips;

    /**
     * 0 = Kein Computerspieler-Spiel
     * 1 = Computerspieler Einfach
     * 2 = Computerspieler Mittel
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
     * Leerer-Konstruktor, um ein PlayingField zu erstellen, welches seine Daten über this.loadGame erhält
     */
    public PlayingField() {
    }

    /**
     * Konstruktor
     *
     * @param rows         Größe des Spielfeldes
     * @param allowedShips Die erlaubten Schiffe
     * @param isServer     True: PlayingField des Servers (Bzw. des Spielers der zuerst schießt)
     *                     False: Client
     */
    public PlayingField(int rows, int[] allowedShips, boolean isServer) {
        this.field = new int[rows][rows];
        this.fieldEnemy = new int[rows][rows];
        this.allowedShips = allowedShips;
        this.isServer = isServer;
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
     * Ermittelt Schiffskopf eines Schiffsteils und gibt die Koordinaten zurück
     *
     * @param field Das Feld auf dem der Schiffskopf gesucht werden soll
     * @param x     X-Koordinate des zu überprüfenden Teiles
     * @param y     Y-Koordinate des zu überprüfenden Teiles
     * @return Gibt X und Y Koordinate vom Kopf eines Schiffes zurück
     */
    private static int[] getHeadOfShip(int[][] field, int x, int y) throws Exception {
        PlayingField.checkCoordinatesInFieldStatic(field, x, y);

        int headX = x;
        int headY = y;

        while (headY - 1 >= 0 && field[headY - 1][headX] > 0 && field[headY - 1][headX] < 4) {
            headY--;
        }
        while (headX - 1 >= 0 && field[headY][headX - 1] > 0 && field[headY][headX - 1] < 4) {
            headX--;
        }

        return new int[]{headX, headY};
    }

    /**
     * Wrapper für getHeadOfShip mit zusätzlicher Ermittlung der Ausrichtung des Schiffes
     *
     * @param field Das Feld auf dem der Schiffskopf gesucht werden soll
     * @param x     X-Koordinate des zu überprüfenden Teiles
     * @param y     Y-Koordinate des zu überprüfenden Teiles
     * @return Rückgabe von Int-Array:
     * [0] X-Koordinate vom Kopf
     * [1] Y-Koordinate vom Kopf
     * [2] 1 == Horizontal, 0 == Vertikal
     * @throws Exception, wenn x/y auserhalb des Spielfeldes
     */
    public static int[] getDirHeadOfShipStatic(int[][] field, int x, int y) throws Exception {
        int[] data = PlayingField.getHeadOfShip(field, x, y);
        int horizontal = 0; //int statt bool, wegen int-Array Rückgabe

        //Überprüfen ob rechts vom Schiff ein weiteres Teil. Dann ist das Schiff Horizontal ausgelegt, sonst Vertikal
        if (data[0] + 1 < field.length && field[data[1]][data[0] + 1] > 0 && field[data[1]][data[0] + 1] < 4) {
            horizontal = 1;
        }

        return new int[]{data[0], data[1], horizontal};
    }

    /**
     * Überprüft ob Koordinaten im Spielfeld sind, falls nicht wird eine Exception geworfen
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @throws Exception, wenn X/Y Koordinate nicht im Spielfeld
     */
    public static void checkCoordinatesInFieldStatic(int[][] field, int x, int y) throws Exception {
        if (x < 0 || x >= field.length || y < 0 || y > field.length) {
            throw new Exception("Die angegebenen Koordinaten befinden sich nicht im Spielfeld");
        }
    }

    ///TODO entfernen, bei Release-Version. Nur zum testen
    public static void main(String[] args) {
        try {
            int[] allowedships = new int[]{3, 2};


            ComPlayer c1 = new ComPlayerNormal(new PlayingField(10, allowedships, true));
            ComPlayer c2 = new ComPlayerNormal(new PlayingField(10, allowedships, false));
            int i = 0;
            long l = 5836008514751432134L;
            long l2 = 4836008514751432134L;
            do {
                if(i > 0){
                    c1 = new ComPlayerNormal(l);
                    c2 = new ComPlayerNormal(l2);
                }

                int[] xy = c1.doNextShot();
                c1.didHit(c2.isShot(xy[0], xy[1]));
                System.out.println("C2-Feld:");
                System.out.println(Arrays.deepToString(c2.pf.getField()).replace("]", "]\n"));
                System.out.println("C1-2enemFeld");
                System.out.println(Arrays.deepToString(c1.pf.getFieldEnemy()).replace("]", "]\n"));

                xy = c2.doNextShot();
                c2.didHit(c1.isShot(xy[0], xy[1]));
                System.out.println("C1-Feld:");
                System.out.println(Arrays.deepToString(c1.pf.getField()).replace("]", "]\n"));
                System.out.println("C2-1enemFeld");
                System.out.println(Arrays.deepToString(c2.pf.getFieldEnemy()).replace("]", "]\n"));

                i++;
                if (c1.gameover()) System.out.println("Computer 2 hat gewonnen");
                if (c2.gameover()) System.out.println("Computer 1 hat gewonnen");

                c1.saveGame(l);
                c2.saveGame(l2);
            }while (!c1.gameover() && !c2.gameover());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
        }
    }

    /**
     * fieldEnemy-Getter
     *
     * @return Gibt fieldEnemy zurück
     */
    public int[][] getFieldEnemy() {
        return this.fieldEnemy;
    }

    /**
     * Wrapper von setShipWithCheck.
     * Setzt ein Schiff, wenn erlaubt.
     * <p>
     * param siehe setShipIntern
     * return siehe setShipIntern
     */
    public boolean setShip(int length, int x, int y, boolean horizontal) {
        return setShipIntern(length, x, y, horizontal, true);
    }

    /**
     * Wrapper von setShipWithCheck.
     * Prüft ob ein Schiff an übergebene Stelle gesetzt werden darf.
     * <p>
     * param siehe setShipIntern
     * return siehe setShipIntern
     */
    public boolean checkShip(int length, int x, int y, boolean horizontal) {
        return setShipIntern(length, x, y, horizontal, false);
    }

    /**
     * allowedShips-Getter
     *
     * @return this.allowedShips
     */
    public int[] getAllowedShips() {
        return this.allowedShips;
    }

    /**
     * fieldEnemy-Setter
     *
     * @param x   X-Koordinate bzw. Index 2
     * @param y   Y-Koordinate bzw. Index 1
     * @param val Value welches in fieldEnemy geschrieben werden soll
     */
    public void setFieldEnemy(int x, int y, int val) {
        this.fieldEnemy[y][x] = val;
    }

    /**
     * Non-Static Wrapper für getDirHeadOfShipStatic
     */
    public int[] getDirHeadOfShip(int x, int y) throws Exception {
        return PlayingField.getDirHeadOfShipStatic(this.field, x, y);
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
                && this.field[data[1] + yOffset][data[0] + xOffset] == 3) {

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
     * com-Setter
     * 0 = Kein Computerspieler-Spiel
     * 1 = Computerspieler Einfach
     * 2 = Computerspieler Mittel
     *
     * @param com Value für this.com
     */
    public void setCom(int com) {
        this.com = com;
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
                System.out.println("field[y][x]: " + field[y][x] + " (y: " + y + ")(x: " + x + ")");
                field[y][x] = 4;
            } else {
                //Markierte Felder zurücksetzen, wenn Schiff nicht gesetzt werden darf
                this.replaceNotfinal(0);
                //System.out.println(Arrays.deepToString(field).replace("]", "]\n"));
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

            //Nach dem Setzen der Schiffe den Status ändern
            //1: Spieler darf als erstes schießen
            //2: Gegner darf als erstes schießen
            if (this.ships == this.allowedShips.length) {
                this.status = this.isServer ? 1 : 2;
            }
            //System.out.println(Arrays.deepToString(field).replace("]", "]\n"));
        } else {
            this.replaceNotfinal(0);
        }
        return true;
    }

    /**
     * Nach einem Schuss auf den Gegner, um diesen in PlayingField zu markieren
     * und um weitere Informationen zu berechnen
     *
     * @param hit 0: Wasser
     *            1: Treffer
     *            2: Treffer versenkt
     */
    public void didHit(int hit, int x, int y) throws Exception {
        switch (hit) {
            case 0 -> //Wasser erwischt
                    //0 zu -1, da 0 bereits unbeschossenes Feld ist
                    this.fieldEnemy[y][x] = 5;
            case 1 -> { //Treffer
                this.fieldEnemy[y][x] = 1;
                this.markNotImportant(x + 1, y + 1);
                this.markNotImportant(x + 1, y - 1);
                this.markNotImportant(x - 1, y + 1);
                this.markNotImportant(x - 1, y - 1);
            }
            case 2 -> { //Treffer versenkt
                //Zuerst mit 1 markieren, für den Algorithmus der das komplette Schiff und Umgebung mit 2 markiert
                this.fieldEnemy[y][x] = 1;

                //Komplettes Schiff mit 2 Markieren
                int[] xyh = PlayingField.getDirHeadOfShipStatic(this.fieldEnemy, x, y);
                markEnemyShipDestroyed(xyh[0], xyh[1], xyh[2] == 1);
            }
            default -> throw new Exception("Parameter (" + hit + ") nicht im Bereich von 0 bis 2");
        }

        //System.out.println("didHit:");
        //System.out.println(Arrays.deepToString(this.fieldEnemy).replace("]", "]\n"));
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
        return y + yOffset >= this.field.length || x + xOffset >= this.field.length || this.field[y + yOffset][x + xOffset] == 0 || this.field[y + yOffset][x + xOffset] == 5;
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
     * Gegnerschiff als zerstört markieren und dabei alle Felder die kein Schiff mehr sein können auch markieren
     *
     * @param x          X-Koordinate
     * @param y          Y-Koordinate
     * @param horizontal True: Schiff ist Horizontal, False: Schiff ist Vertikal
     */
    private void markEnemyShipDestroyed(int x, int y, boolean horizontal) {
        //Komplettes Schiff mit 2 Markieren
        boolean first = true;

        while (y < this.fieldEnemy.length && x < this.fieldEnemy.length && this.fieldEnemy[y][x] == 1) {
            this.fieldEnemy[y][x] = 2;

            //Beim ersten Schiffsteil, die Felder links/darüber davon markieren.
            //Da direkt an einem anderen Schiff kein zweites platziert werden darf
            //          ||
            // 4 .. ..  || 4  4  4 <-
            // 4 2  ..  || .. 2  ..
            // 4 .. ..  || .. .. ..
            // ^        ||
            if (first) {
                first = false;
                this.markNotImportant(horizontal ? x - 1 : x, horizontal ? y : y - 1);
                this.markNotImportant(horizontal ? x - 1 : x + 1, y - 1);
                this.markNotImportant(x - 1, horizontal ? y + 1 : y - 1);
            }

            //Felder über und unter dem Schiffsfeld markieren
            //          ||
            // .. 4 ..  || .. .. ..
            // .. 2 ..  || 4  2  4 <-
            // .. 4 ..  || .. .. ..
            //    ^     ||
            this.markNotImportant(horizontal ? x : x - 1, horizontal ? y - 1 : y);
            this.markNotImportant(horizontal ? x : x + 1, horizontal ? y + 1 : y);

            //Beim letzten Schiffsteil, die drei Felder rechts davon markieren
            //          ||
            // .. 4 4   || .. .. ..
            // .. 2 4   || 4  2  4
            // .. 4 4   || 4  4  4 <-
            //      ^   ||
            if (horizontal ?
                    x + 1 < this.fieldEnemy.length && this.fieldEnemy[y][x + 1] != 1 :
                    y + 1 < this.fieldEnemy.length && this.fieldEnemy[y + 1][x] != 1) {
                this.markNotImportant(horizontal ? x + 1 : x, horizontal ? y : y + 1);
                this.markNotImportant(horizontal ? x + 1 : x - 1, horizontal ? y - 1 : y + 1);
                this.markNotImportant(x + 1, y + 1);
            }

            //Weiter nach rechts
            if (horizontal) x++;
            else y++;
        }
    }

    /**
     * Non-Static Wrapper für checkCoordinatesInFieldStatic
     */
    private void checkCoordinatesInField(int x, int y) throws Exception {
        PlayingField.checkCoordinatesInFieldStatic(this.field, x, y);
    }

    /**
     * fieldEnemy-Felder mit val markieren mit zusätzlichen Sicherheitsmaßnahmen
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    private void markNotImportant(int x, int y) {
        if (x < 0 || x >= this.fieldEnemy.length) return;
        if (y < 0 || y >= this.fieldEnemy.length) return;
        if (this.fieldEnemy[y][x] == 5) return;

        this.fieldEnemy[y][x] = 4;
    }

    /**
     * Weapper für saveGame mit File-Namen Angabe ohne com Angabe
     *
     * @param file Absoluter Pfad + Dateiname
     * @return Hashcode bzw. ID die als Save Nachricht über das Netzwerk geschickt wird
     * @throws IOException Wenn die Datei nicht erstellt/beschrieben werden kann
     */
    public long saveGame(String file) throws IOException {
        return this.saveGame(file, null);
    }

    /**
     * Wrapper für saveGame ohne com Angabe
     *
     * @param id
     */
    public void saveGame(long id) throws IOException {
        this.saveGame(id, null);
    }

    /**
     * Wrapper für saveGame mit Filename-Angabe.
     * Wird verwendet, wenn man als Client spielt und den Dateinamen nicht selber auswählt.
     * In dem Fall wird vom Gegenüber eine ID übergeben.
     *
     * @param id
     * @throws IOException Wenn die Datei nicht erstellt/beschrieben werden kann
     */
    public void saveGame(long id, ComPlayer com) throws IOException {
        String f = System.getProperty("java.io.tmpdir") + File.separator + "SchiffeVersenkenHSAalenSaves";

        //SchiffeVersenkenHSAalenSaves-Ordner erstellen
        File directory = new File(f);
        if (!directory.exists()) directory.mkdir();

        //Speicherdatei erstellen bzw überschreiben
        File file = new File(f + File.separator + id + "_save.txt");

        this.saveGame(file.getAbsolutePath(), com);
    }

    /**
     * Speichern des Spielstandes mit einer Dir + File Angabe
     *
     * @param file Absoluter Pfad zur Datei
     * @param com  Computer-Spieler der dieses PlayingField nutzt, oder null
     * @return Hashcode bzw. ID die als Save Nachricht über das Netzwerk geschickt wird
     * @throws IOException Wenn die Datei nicht erstellt/beschrieben werden kann
     */
    public long saveGame(String file, ComPlayer com) throws IOException {
        //Speicherdatei erstellen bzw. überschreiben
        File save = new File(file);
        FileWriter fw = new FileWriter(save.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        String s = "";

        //com
        s += this.com + "\n";
        if (this.com == 2) {
            ComPlayerNormal c = (ComPlayerNormal) com;

            //lastCoords
            s += c.getLastCoords()[0] + "," + c.getLastCoords()[1] + "\n";

            //rowSeq
            for (Integer i : c.getRowSeq()) {
                s += i + ",";
            }
            s = s.substring(0, s.length() - 1) + "\n";

            //nextRow
            s += c.getNextRow() + "\n";
        }

        //isServer
        s += this.isServer ? "1\n" : "0\n";

        //status
        s += this.status + "\n";

        //ships
        s += this.ships + "\n";

        //allowedShips
        for (Integer i : this.allowedShips) {
            s += i + ",";
        }
        s = s.substring(0, s.length() - 1) + "\n";

        //field.length
        s += this.field.length + "\n";

        //field
        s += this.getSaveString2DArray(this.field);

        //fieldEnemy
        s += this.getSaveString2DArray(this.fieldEnemy);

        //String abspeichern und die Writer schließen
        bw.write(s);
        bw.close();
        fw.close();

        //long Hashcode für file ermitteln
        String[] filenameSplit = file.split(Pattern.quote(System.getProperty("file.separator")));
        String filename = filenameSplit[filenameSplit.length - 1];
        long hash = UUID.nameUUIDFromBytes(filename.getBytes()).getMostSignificantBits();
        return hash;
    }

    /**
     * Schreibt alle Werte eines 2D-Arrays in einen String und gibt diesen mit Zeilenumbruch zurück
     *
     * @param field Das 2D-Array
     * @return String mit Zeilenumbruch
     */
    private String getSaveString2DArray(int[][] field) {
        String s = "";

        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field.length; x++) {
                s += field[y][x];
            }
        }

        return s + "\n";
    }

    /**
     * Wrapper für loadGame ohne com Angabe
     *
     * @param id ID der Speicherdatei, welche z.B. beim Netzwerkspiel vom Server beim Speichern zugeteilt wird
     * @return True: Laden war erfolgreich, False: Laden war nicht erfolgreich
     * @throws FileNotFoundException Kein Zugriff auf den Temp Ordner
     */
    public boolean loadGame(long id) throws FileNotFoundException {
        return this.loadGame(id, null);
    }

    /**
     * Wrapper für loadGame ohne com Angabe
     *
     * @param file Absoluter Pfad der Speicherdatei
     * @return True: Laden war erfolgreich, False: Laden war nicht erfolgreich
     * @throws FileNotFoundException Kein Zugriff auf den Temp Ordner
     */
    public boolean loadGame(String file) throws FileNotFoundException {
        return this.loadGame(file, null);
    }

    /**
     * loadGame-Getter mit ID, statt Dateipfad und Dateinamen
     *
     * @param id ID der Speicherdatei, welche z.B. beim Netzwerkspiel vom Server beim Speichern zugeteilt wird
     * @return True: Laden war erfolgreich, False: Laden war nicht erfolgreich
     */
    public boolean loadGame(long id, ComPlayer com) throws FileNotFoundException {
        String f = System.getProperty("java.io.tmpdir") + File.separator + "SchiffeVersenkenHSAalenSaves";

        //SchiffeVersenkenHSAalenSaves-Ordner überprüfen
        File directory = new File(f);
        if (!directory.exists()) return false;

        //File-Objekt erstellen
        File file = new File(f + File.separator + id + "_save.txt");

        //Mit Dateinamen jetzt das Spiel laden
        return this.loadGame(file.getAbsolutePath(), com);
    }

    /**
     * Laden des Spieles anhand der Save-Datei
     *
     * @param file Absoluter Pfad und Dateinamen
     */
    public boolean loadGame(String file, ComPlayer com) throws FileNotFoundException {
        //Prüfen ob die Datei existiert
        File f = new File(file);
        if (!f.exists()) return false;

        Scanner s = new Scanner(f);

        //com
        this.com = s.nextInt();
        s.skip("\n");

        if (this.com == 2) {
            ComPlayerNormal c = (ComPlayerNormal) com;

            //lastCoords
            String str = s.nextLine();
            c.setLastCoords(Stream.of(str.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray());

            //rowSeq
            str = s.nextLine();
            List<Integer> l = new ArrayList<Integer>();
            for (Integer i : Stream.of(str.split(",")).mapToInt(Integer::parseInt).toArray()) {
                l.add(i);
            }
            c.setRowSeq(l);

            //nextRow
            c.setNextRow(s.nextInt());
            s.skip("\n");
        }

        //isServer
        this.isServer = s.nextInt() == 1;
        s.skip("\n");

        //status
        this.status = s.nextInt();
        s.skip("\n");

        //ships
        this.ships = s.nextInt();
        s.skip("\n");

        //allowedShips
        String str = s.nextLine();
        this.allowedShips = Stream.of(str.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        //field.length
        int fieldLen = s.nextInt();
        s.skip("\n");

        //field
        this.field = this.saveStringTo2DArray(s.nextLine(), fieldLen);

        //fieldEnemy
        this.fieldEnemy = this.saveStringTo2DArray(s.nextLine(), fieldLen);

        s.close();

        return true;
    }

    private int[][] saveStringTo2DArray(String line, int len) {
        int[][] l = new int[len][len];

        int i = 0;
        for (int y = 0; y < len; y++) {
            for (int x = 0; x < len; x++) {
                //Char zu Int Umwandlung
                l[y][x] = line.charAt(i++) - '0';
            }
        }

        return l;
    }


}