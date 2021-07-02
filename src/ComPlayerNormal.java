package src;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Computer-Spieler mit Schwierigkeitsgrad Normal
 * Schüsse sind so lange zufällig platziert, bis ein Schiff erwischt wird.
 * Dann wird ermittelt wie das Schiff platziert ist und dieses komplett zerstört.
 */
public class ComPlayerNormal extends ComPlayer {
    /**
     * Die zuletzt abgeschossenen Koordinaten,
     * die durch doNextShot() ermittelt werden
     */
    private int[] lastCoords = null;

    /**
     * Liste mit Reihenfolge der abzuschießenden Reihen.
     * Liste statt Array um shuffle zu nutzen.
     */
    private List<Integer> rowSeq = null;

    /**
     * Die nächste Row des Spielfeldes die nach Schiffen abgesucht wird
     */
    private int nextRow = -1;

    /**
     * Konstruktor
     *
     * @param pf PlayingField des Computers
     * @throws Exception X/Y-Koordinatenprüfung
     */
    public ComPlayerNormal(PlayingField pf) throws Exception {
        super(pf);
        this.pf.setCom(2);
        this.setRowSeq(pf.getField().length);
    }

    /**
     * Konstruktor, falls man das Spiel laden möchte
     *
     * @param id Die ID die an loadGame(..) übergeben wird
     * @throws FileNotFoundException Wenn die zugehörige Speicherdatei nicht existiert
     */
    public ComPlayerNormal(long id) throws FileNotFoundException {
        super();
        this.loadGame(id);
    }

    /**
     * Konstruktor, falls man das Spiel laden möchte
     *
     * @param file Dateipfad und Dateiname
     * @throws FileNotFoundException Wenn die zugehörige Speicherdatei nicht existiert
     */
    public ComPlayerNormal(String file) throws FileNotFoundException {
        super();
        this.loadGame(file);
    }

    /**
     * rowSeq Setzen anhand Anzahl der Rows
     *
     * @param rows Größenangabe
     */
    private void setRowSeq(int rows) {
        this.rowSeq = new ArrayList<Integer>();
        for (int i = 0; i < rows; i++) {
            this.rowSeq.add(i);
        }
        Collections.shuffle(rowSeq);
    }

    /**
     * rowSeq-Getter
     *
     * @return this.rowSeq
     */
    public List<Integer> getRowSeq() {
        return this.rowSeq;
    }

    /**
     * rowSeq-Setter
     *
     * @param val Neuer Wert für rowSeq
     */
    public void setRowSeq(List<Integer> val) {
        this.rowSeq = val;
    }

    /**
     * lastCoords-Getter
     *
     * @return this.lastCoords
     */
    public int[] getLastCoords() {
        return this.lastCoords;
    }

    /**
     * lastCoords-Setter
     *
     * @param val Neuer Wert für lastCoords
     */
    public void setLastCoords(int[] val) {
        this.lastCoords = val;
    }

    /**
     * nextRow-Getter
     *
     * @return this.nextRow
     */
    public int getNextRow() {
        return this.nextRow;
    }

    /**
     * nextRow-Setter
     *
     * @param val Neuer Wert für nextRow
     */
    public void setNextRow(int val) {
        this.nextRow = val;
    }

    /**
     * Methode die die nächsten Koordinaten ausgibt, die der Computer-Spieler abschießen will
     *
     * @return int[]{x, y}
     * @throws Exception Wenn es keine sinnvolle Möglichkeit mehr gibt
     */
    @Override
    public int[] doNextShot() throws Exception {
        int[] hit = this.findHit();
        int[] next = null;

        if (hit == null) {
            //Schach-Pattern schießen
            next = this.findNextCheckPattern();
        } else {
            //Abgeschossenes, nicht zerstörtes Schiff existiert.
            next = this.findNextPotentialShip(hit[0], hit[1]);
        }

        //3: Schuss ohne Antwort
        this.pf.setFieldEnemy(next[0], next[1], 3);
        //this.pf.getFieldEnemy()[next[1]][next[0]] = 3;
        this.lastCoords = next;

        return next;
    }

    /**
     * Ermittelt anhand nextRow und rowSeq welche Reihe als nächstes überprüft wird
     * und gibt passende X/Y-Koordinaten zurück
     *
     * @return int[]{x,y}
     * @throws Exception
     */
    private int[] findNextCheckPattern() throws Exception {
        if (this.nextRow < 0) {
            this.nextRow = this.rowSeq.indexOf(Collections.min(this.rowSeq));

            //Wenn nurnoch Elemente mit der max Priorität existieren, darf eigentlich kein ungefundenes Schiff mehr existieren
            if (this.nextRow >= this.pf.getFieldEnemy().length) {
                throw new Exception("findNextCheckPattern: Fehler beim Ermitteln des nächsten Schachfeld-Schuss.");
            }
        }

        //x alternierend mit 0 oder 1 beginnen.
        //und jeweils ein Feld überspringen (x = x+2)
        for (int x = this.nextRow % 2; x < this.pf.getFieldEnemy().length; x = x + 2) {
            if (this.pf.getFieldEnemy()[this.nextRow][x] == 0) {
                return new int[]{x, this.nextRow};
            }
        }

        //Keine Möglichkeiten mehr in dieser row
        //row Priorität auf max setzen und nächste Row überprüfen
        this.rowSeq.set(this.nextRow, this.pf.getFieldEnemy().length);
        this.nextRow = -1;
        return this.findNextCheckPattern();
    }

    /**
     * Sucht ein abgeschossenes, noch nicht zerstörtes, Schiff und gibt deren Koordinaten zurück
     *
     * @return int[]{x, y}, bzw. null, wenn es keines gibt
     */
    private int[] findHit() {
        for (int y = 0; y < this.pf.getFieldEnemy().length; y++) {
            for (int x = 0; x < this.pf.getFieldEnemy().length; x++) {
                if (this.pf.getFieldEnemy()[y][x] == 1) {
                    return new int[]{x, y};
                }
            }
        }

        return null;
    }

    /**
     * Ermittelt anhand eines getroffenen Schiffteiles
     *
     * @param x X-Koordinate des getroffenen Schiffteiles
     * @param y Y-Koordinate des getroffenen Schiffteiles
     * @return int[]{x, y}, X/Y-Koordinaten des Nächsten Schusses
     * @throws Exception Wenn es keine Möglichkeiten mehr gibt
     */
    private int[] findNextPotentialShip(int x, int y) throws Exception {
        boolean horizontal;

        //Rechts oder Links abgeschossenes Schiffsteil => Horizontal
        if ((x - 1 >= 0 && this.pf.getFieldEnemy()[y][x - 1] == 1) || (x + 1 < this.pf.getFieldEnemy().length && this.pf.getFieldEnemy()[y][x + 1] == 1))
            horizontal = true;
            //Oben oder Unten abgeschossenes Schiffsteil => Vertikal
        else if ((y - 1 >= 0 && this.pf.getFieldEnemy()[y - 1][x] == 1) || (y + 1 < this.pf.getFieldEnemy().length && this.pf.getFieldEnemy()[y + 1][x] == 1))
            horizontal = false;
            //Wenn Kein Schiffsteil daneben, überprüfen ob ein nicht abgeschossenes Feld in der horizontalen Nähe
        else if ((x - 1 >= 0 && this.pf.getFieldEnemy()[y][x - 1] == 0) || (x + 1 < this.pf.getFieldEnemy().length && this.pf.getFieldEnemy()[y][x + 1] == 0))
            horizontal = true;
            //Keine andere Möglichkeit als Vertikal
        else horizontal = false;

        int[] possibleShot = null;
        if (horizontal) {
            //Links überprüfen
            possibleShot = potentialShipNeighboringField(x, y, -1, 0);
            if (possibleShot != null) return possibleShot;

            //Rechts überprüfen
            possibleShot = potentialShipNeighboringField(x, y, 1, 0);
            if (possibleShot != null) return possibleShot;
        } else {
            //Oben überprüfen
            possibleShot = potentialShipNeighboringField(x, y, 0, -1);
            if (possibleShot != null) return possibleShot;

            //Unten überprüfen
            possibleShot = potentialShipNeighboringField(x, y, 0, 1);
            if (possibleShot != null) return possibleShot;
        }

        throw new Exception("findNextPotentialShip, Fehler beim Berechnen des nächsten abzuschießenden Schiff-Feldes");
    }

    /**
     * Überprüft anhand der Offset-Parameter ob in eine Richtung noch nicht abgeschossene Stellen sind,
     * die potentiell Schiffsteile beinhalten (Unabgeschossenes Wasser)
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @param xOff  X-Offset in dessen Richtung überprüft werden soll
     * @param yOff  Y-Offset in dessen Richtung überprüft werden soll
     * @return int[]{x, y} nächster möglicher Schuss
     */
    private int[] potentialShipNeighboringField(int x, int y, int xOff, int yOff) {
        //In Richtung xOff/yOff überprüfen
        while (x >= 0 && x < this.pf.getFieldEnemy().length
                && y >= 0 && y < this.pf.getFieldEnemy().length
                && this.pf.getFieldEnemy()[y][x] == 1
        ) {
            x += xOff;
            y += yOff;
        }

        //Existiert noch ein Wasserfeld?
        if (x >= 0 && x < this.pf.getFieldEnemy().length
                && y >= 0 && y < this.pf.getFieldEnemy().length
                && this.pf.getFieldEnemy()[y][x] == 0) {
            return new int[]{x, y};
        }

        return null;
    }

    /**
     * Nach einem Aufruf auf doNextShot wird dem Computer übergeben ob er getroffen hat
     *
     * @param hit 0: Wasser
     *            1: Treffer
     *            2: Treffer versenkt
     */
    public void didHit(int hit) throws Exception {
        this.pf.didHit(hit, this.lastCoords[0], this.lastCoords[1]);
    }
}
