package src;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    public ComPlayerNormal(PlayingField pf, int[] ships) throws Exception {
        super(pf, ships);
        this.setRowSeq(pf.getField().length);
        this.difficulty = 1;
    }

    private void setRowSeq(int rows) {
        this.rowSeq = new ArrayList<Integer>();
        for (int i = 0; i < rows; i++){
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
            System.out.println("TEST");
            //Abgeschossenes, nicht zerstörtes Schiff existiert.
            next = this.findNextPotentialShip(hit[0], hit[1]);
        }

        //3: Schuss ohne Antwort
        this.enemyField[next[1]][next[0]] = 3;
        this.lastCoords = next;

        return next;
    }

    private int[] findNextCheckPattern() throws Exception {
        //int

        for (int y = 0; y < this.enemyField.length; y++) {
            for (int x = 0; x < this.enemyField.length; x++) {
                boolean xC = x - 1 < 0;
                boolean yC = y - 1 < 0;
                boolean xP = x + 1 >= this.enemyField.length;
                boolean yP = y + 1 >= this.enemyField.length;

                if (this.enemyField[y][x] == 0
                        && (xC || this.enemyField[y][x - 1] == 0 || this.enemyField[y][x - 1] == 4)
                        && (xP || this.enemyField[y][x + 1] == 0 || this.enemyField[y][x + 1] == 4)
                        && (yC || this.enemyField[y - 1][x] == 0 || this.enemyField[y - 1][x] == 4)
                        && (yP || this.enemyField[y + 1][x] == 0 || this.enemyField[y + 1][x] == 4)
                ) {
                    return new int[]{x, y};
                }

            }
        }

        throw new Exception("findNextCheckPattern: Fehler beim Ermitteln des nächsten Schachfeld-Schuss.");
    }

    private int[] findHit() {
        for (int y = 0; y < this.enemyField.length; y++) {
            for (int x = 0; x < this.enemyField.length; x++) {
                if (this.enemyField[y][x] == 1) {
                    return new int[]{x, y};
                }
            }
        }

        return null;
    }

    private int[] findNextPotentialShip(int x, int y) throws Exception {
        boolean horizontal;

        //Rechts oder Links abgeschossenes Schiffsteil => Horizontal
        if((x - 1 >= 0 && this.enemyField[y][x-1] == 1) || (x + 1 < this.enemyField.length && this.enemyField[y][x+1] == 1)) horizontal = true;
        //Oben oder Unten abgeschossenes Schiffsteil => Vertikal
        else if((y - 1 >= 0 && this.enemyField[y-1][x] == 1) || (y + 1 < this.enemyField.length && this.enemyField[y+1][x] == 1)) horizontal = false;
        //Wenn Kein Schiffsteil daneben, überprüfen ob ein nicht abgeschossenes Feld in der horizontalen Nähe
        else if((x - 1 >= 0 && this.enemyField[y][x - 1] == 0) || (x + 1 < this.enemyField.length && this.enemyField[y][x+1] == 0)) horizontal = true;
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
        while (x >= 0 && x < this.enemyField.length
                && y >= 0 && y < this.enemyField.length
                && this.enemyField[y][x] == 1
        ) {
            x += xOff;
            y += yOff;
        }

        //Existiert noch ein Wasserfeld?
        if (x >= 0 && x < this.enemyField.length
                && y >= 0 && y < this.enemyField.length
                && this.enemyField[y][x] == 0) {
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
        if (lastCoords == null)
            throw new Exception("didHit-Aufruf ohne vorherigen doNextShot-Aufruf");
        int x = lastCoords[0];
        int y = lastCoords[1];

        switch (hit) {
            case 0: //Wasser erwischt
                //0 zu -1, da 0 bereits unbeschossenes Feld ist
                this.enemyField[y][x] = -1;
                break;
            case 1: //Treffer
                this.enemyField[y][x] = 1;
                this.markNotImportant(x + 1, y + 1, 4);
                this.markNotImportant(x + 1, y - 1, 4);
                this.markNotImportant(x - 1, y + 1, 4);
                this.markNotImportant(x - 1, y - 1, 4);
                break;
            case 2: //Treffer versenkt
                //Zuerst mit 1 markieren, für den Algorithmus der das komplette Schiff und Umgebung mit 2 markiert
                this.enemyField[y][x] = 1;

                //Komplettes Schiff mit 2 Markieren
                boolean first = true;
                int[] xyh = PlayingField.getDirHeadOfShipStatic(this.enemyField, x, y);
                x = xyh[0];
                y = xyh[1];
                while (y < this.enemyField.length && x < this.enemyField.length && this.enemyField[y][x] == 1) {
                    this.enemyField[y][x] = 2;

                    if (xyh[2] == 1) { //Horizontal
                        //Beim ersten Schiffsteil, die Felder links davon markieren.
                        //Da direkt an einem anderen Schiff kein zweites platziert werden darf
                        //
                        // 4 .. ..
                        // 4 2  ..
                        // 4 .. ..
                        // ^
                        if (first) {
                            first = false;
                            this.markNotImportant(x - 1, y, 4);
                            this.markNotImportant(x - 1, y - 1, 4);
                            this.markNotImportant(x - 1, y + 1, 4);
                        }

                        //Felder über und unter dem Schiffsfeld markieren
                        //
                        // .. 4 ..
                        // .. 2 ..
                        // .. 4 ..
                        //    ^
                        this.markNotImportant(x, y - 1, 4);
                        this.markNotImportant(x, y + 1, 4);

                        //Beim letzten Schiffsteil, die drei Felder rechts davon markieren
                        //
                        // .. 4 4
                        // .. 2 4
                        // .. 4 4
                        //      ^
                        if (x + 1 < this.enemyField.length && this.enemyField[y][x + 1] != 1) {
                            this.markNotImportant(x + 1, y, 4);
                            this.markNotImportant(x + 1, y - 1, 4);
                            this.markNotImportant(x + 1, y + 1, 4);
                        }

                        //Weiter nach rechts
                        x++;
                    } else {//Vertikal
                        //Beim ersten Schiffsteil, die Felder darüber markieren
                        //
                        // 4  4  4 <-
                        // .. 2  ..
                        // .. .. ..
                        if (first) {
                            first = false;
                            this.markNotImportant(x, y - 1, 4);
                            this.markNotImportant(x + 1, y - 1, 4);
                            this.markNotImportant(x - 1, y - 1, 4);
                        }

                        //Felder rechts und links von dem Schiffsfeld markieren
                        //
                        // .. .. ..
                        // 4  2  4 <-
                        // .. .. ..
                        this.markNotImportant(x - 1, y, 4);
                        this.markNotImportant(x + 1, y, 4);

                        //Beim letzten Schiffsteil, die drei Felder darunter markieren
                        //
                        // .. .. ..
                        // 4  2  4
                        // 4  4  4 <-
                        if (y + 1 < this.enemyField.length && this.enemyField[y + 1][x] != 1) {
                            this.markNotImportant(x, y + 1, 4);
                            this.markNotImportant(x - 1, y + 1, 4);
                            this.markNotImportant(x + 1, y + 1, 4);
                        }

                        //Weiter nach unten
                        y++;
                    }
                }
                break;
            default:
                throw new Exception("Parameter (" + hit + ") nicht im Bereich von 0 bis 2");
        }

        System.out.println("ENEMY:");
        System.out.println(Arrays.deepToString(this.enemyField).replace("]", "]\n"));
    }

    private void markNotImportant(int x, int y, int val) {
        if (x < 0 || x >= this.enemyField.length) return;
        if (y < 0 || y >= this.enemyField.length) return;
        if(this.enemyField[y][x] == -1) return;

        this.enemyField[y][x] = val;
    }
}
