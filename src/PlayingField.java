package src;

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
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < rows; k++) {
                field[i][k] = 0;
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
     * @return
     */
    public double allShipsSetPercentage() {
        int water = 0;
        int shippart = 0;

        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field.length; y++) {
                if (field[x][y] == 0) {
                    water++;
                } else if (field[x][y] == 3 || field[x][y] == 4) {
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
                return false;
            }

            //Überprüfen ob Schiffsteil ein anderes Schiff andockt, oder auf einem anderen Schiff ist
            //
            // x-1 y-1 | x y-1 | x+1 y-1
            // x-1 y   | x y   | x+1 y
            // x-1 y+1 | x y+1 | x+1 y+1
            if (field[x - 1][y - 1] != 3
                    && field[x][y - 1] != 3
                    && field[x + 1][y - 1] != 3
                    && field[x - 1][y] != 3
                    && field[x][y] != 3
                    && field[x + 1][y] != 3
                    && field[x - 1][y + 1] != 3
                    && field[x][y + 1] != 3
                    && field[x + 1][y + 1] != 3
            ) {
                field[y][x] = 4;
            } else {
                //Markierte Felder zurücksetzen, wenn Schiff nicht gesetzt werden darf
                this.replaceNotfinal(0);

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
            return true;
        }

        return false;
    }

    /**
     * Schiffsteil und benachbarte Schiffsteile entfernen
     *
     * @param x X-Koordinate eines Schiffteiles
     * @param y Y-Koordinate eines Schiffteiles
     */
    public void deleteShip(int x, int y) throws Exception {
        checkCoordinatesInField(x, y);

        this.ships--;
        this.field[x][y] = 0;

        //Nach rechts
        int offset = 1;
        while (x + offset >= 0 && this.field[x + offset][y] != 0) {
            this.field[x + offset][y] = 0;
            offset++;
        }

        //Nach links
        offset = -1;
        while (x + offset < this.field.length && this.field[x + offset][y] != 0) {
            this.field[x + offset][y] = 0;
            offset--;
        }

        //Nach unten
        offset = 1;
        while (y + offset < this.field.length && this.field[x][y + offset] != 0) {
            this.field[x][y + offset] = 0;
            offset++;
        }

        //Nach oben
        offset = -1;
        while (y + offset >= 0 && this.field[x][y + offset] != 0) {
            this.field[x][y + offset] = 0;
            offset--;
        }

    }

    /**
     * Überprüft ob ein Schuss ein Schiff erwischt hat
     *
     * @param x X-Koordinate des Schusses
     * @param y Y-Koordinate des Schusses
     * @return 0: Kein Treffer, 1: Treffer, 2: Treffer und versenkt
     */
    public int isShot(int x, int y) throws Exception {
        checkCoordinatesInField(x, y);

        if (this.field[x][y] == 3) {
            this.field[x][y] = 1;

            //Überprüfen ob Schiff komplett zerstört
            //Falls ja, Schiff auf dem Spielfeld mit 2 markieren und ship-Variable dekrementieren
            if (isShipDestroyed(x, y, true)) { //Schiff horizontal zerstört
                markShipDestroyed(x, y, true);
                return 2;
            } else if (isShipDestroyed(x, y, false)) { //Schiff vertikal zerstört
                markShipDestroyed(x, y, true);
                return 2;
            }

            return 1;
        }

        return 0;
    }

    /**
     * Markiert ein Schiff als komplett zerstört (Int-Wert 2)
     *
     * @param x          X-Koordinate eines Schiffteiles
     * @param y          Y-Koordinate eines Schiffteiles
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
                && this.field[x + xOffset][y + yOffset] != 0) {
            this.field[x + xOffset][y + yOffset] = 2;

            if (horizontal) xOffset++;
            else yOffset++;
        }

        //Nach links bzw nach oben
        xOffset = 0;
        yOffset = 0;
        while (x + xOffset >= 0 && y + yOffset >= 0
                && this.field[x + xOffset][y + yOffset] != 0) {
            this.field[x + xOffset][y + yOffset] = 2;

            if (horizontal) xOffset--;
            else yOffset--;
        }
    }

    /**
     * Überprüft ob ein komplettes Schiff zerstört ist
     *
     * @param x          X-Koordinate eines Schiffteiles
     * @param y          Y-Koordinate eines Schiffteiles
     * @param horizontal Schiff horizontal überprüfen
     * @return True: Komplettes Schiff ist zerstört
     */
    private boolean isShipDestroyed(int x, int y, boolean horizontal) throws Exception {
        checkCoordinatesInField(x, y);

        boolean firstSideDestroyed = false;
        boolean secondSideDestroyed = false;
        int xOffset = 0;
        int yOffset = 0;

        while (x + xOffset < this.field.length && y + yOffset < this.field.length
                && this.field[x + xOffset][y + yOffset] == 1) {
            if (horizontal) xOffset++;
            else yOffset++;
        }

        //Überprüfen ob nach zerstörten Schiffsteilen Wasser
        if (this.field[x + xOffset][y + yOffset] == 0) {
            firstSideDestroyed = true;
        }

        xOffset = 0;
        yOffset = 0;
        while (x + xOffset >= 0 && y + yOffset >= 0
                && this.field[x + xOffset][y + yOffset] == 1) {
            if (horizontal) xOffset--;
            else yOffset--;
        }

        //Überprüfen ob nach zerstörten Schiffsteilen Wasser
        if (this.field[x + xOffset][y + yOffset] == 0) {
            secondSideDestroyed = true;
        }

        return firstSideDestroyed && secondSideDestroyed;
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
     * @return
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
        for (int k = 0; k < field.length; k++) {
            for (int j = 0; j < field.length; j++) {
                if (field[k][j] == 4) {
                    field[k][j] = set;
                }
            }
        }
    }

    /**
     * Überprüft ob Koordinaten im Spielfeld sind, falls nicht wird eine Exception geworfen
     *
     * @param x
     * @param y
     * @throws Exception
     */
    private void checkCoordinatesInField(int x, int y) throws Exception {
        if (x < 0 || x >= this.field.length || y < 0 || y > this.field.length) {
            throw new Exception("Die angegebenen Koordinaten befinden sich nicht im Spielfeld");
        }
    }
}