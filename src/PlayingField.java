package src;

import java.util.Arrays;

public class PlayingField {
    /**
     * 0 = Wasser
     * 1 = Abgeschossenes Schiffsteil
     * 2 = Komplett zerstört
     * 3 = Normales Schiffsteil
     * 4 = Geplantes Schiffsteil, noch nicht gesetzt
     */
    private final int[][] field;
    private int ships = 0;

    /**
     * Konstruktor
     *
     * @param rows - Höhe und Breite des Spielfeldes
     */
    public PlayingField(int rows) {
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
     * Gibt zurück ob 30% des Spielfeldes mit Schiffen gefüllt ist
     *
     * @return True >= 30%, False < 30%
     */
    private boolean allShipsSet() {
        return this.allShipsSetPercentage() >= 0.3;
    }

    /**
     * Gibt zurück wie viel % des Spielfeldes mit Schiffen gefüllt ist
     *
     * @return Prozentuale Angabe der Schiffe im Vergleich zum Wasser
     */
    public double allShipsSetPercentage() {
        int water = 0;
        int shippart = 0;

        for (int[] ints : field) {
            for (int x = 0; x < field.length; x++) {
                if (ints[x] == 0) {
                    water++;
                } else if (ints[x] == 3 || ints[x] == 4) {
                    shippart++;
                }
            }
        }

        return (double) shippart / (double) water;
    }

    /**
     * Setzen eines Schiffes auf das Spielfeld und die davorige Überprüfung ob das Schiff überhaupt dorthin platziert werden darf
     *
     * @param length     Schifflänge
     * @param x          X-Koordinate vom Schiffkopf
     * @param y          Y-Koordinate vom Schiffkopf
     * @param horizontal In welcher Richtung vom Schiffskopf der Rest des Schiffes ist
     * @return True = Schiff gesetzt, False = Schiff durfte nicht gesetzt werden
     */
    public boolean setShip(int length, int x, int y, boolean horizontal) {
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
            boolean xC = x - 1 <= 0;
            boolean yC = y - 1 <= 0;
            boolean xP = x + 1 >= field.length - 1;
            boolean yP = y + 1 >= field.length - 1;

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
                System.out.println(field[y][x]);
                field[y][x] = 4;
            } else {
                //Markierte Felder zurücksetzen, wenn Schiff nicht gesetzt werden darf
                this.replaceNotfinal(0);
                System.out.println(Arrays.deepToString(field).replace("]", "]\n"));
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
        if (!allShipsSet()) {
            this.replaceNotfinal(3);
            this.ships++;
            System.out.println(Arrays.deepToString(field).replace("]", "]\n"));
            return true;
        } else {
            this.replaceNotfinal(0);
        }
        System.out.println(Arrays.deepToString(field).replace("]", "]\n"));
        return false;
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
        if (x + 1 < field.length && field[x + 1][y] > 0 && this.field[x + 1][y] < 4) {
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

        if (this.field[y][x] == 3) {
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
                && this.field[y + yOffset][x + xOffset] != 0) {
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
        return this.field[y + yOffset][x + xOffset] == 0;
    }

    //TODO
    public boolean saveGame(int id) {
        return false;
    }

    //TODO
    public boolean loadGame(int id) {
        return false;
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