package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Computer-Spieler mit Schwierigkeitsgrad Normal
 * Schüsse sind so lange zufällig platziert, bis ein Schiff erwischt wird.
 * Dann wird ermittelt wie das Schiff platziert ist und dieses komplett zerstört.
 */
public class ComPlayerNormal extends ComPlayer {
    private int[] lastCoords = null;
    /**
     * Liste mit Reihenfolge der abzuschießenden Reihen.
     * Liste statt Array um shuffle zu nutzen.
     */
    private List<Integer> rowSeq = null;
    private int nextRow = -1;

    public ComPlayerNormal(PlayingField pf, int[] ships) throws Exception {
        super(pf, ships);
        this.setRowSeq(pf.getField().length);
        this.difficulty = 1;
    }

    private void setRowSeq(int rows) {
        this.rowSeq = new ArrayList<Integer>();
        for (int i = 0; i < rows; i++) {
            this.rowSeq.add(i);
        }
        Collections.shuffle(rowSeq);
    }

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

    private int[] findNextCheckPattern() throws Exception {
        if (this.nextRow < 0) {
            this.nextRow = this.rowSeq.indexOf(Collections.min(this.rowSeq));

            //Wenn nurnoch Elemente mit der max Priorität existieren, darf eigentlich kein ungefundenes Schiff mehr existieren
            if (this.nextRow >= this.pf.getFieldEnemy().length) {
                throw new Exception("findNextCheckPattern: Fehler beim Ermitteln des nächsten Schachfeld-Schuss.");
            }
        }

        //x alternierend mit 0 oder 1 beginnen
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
