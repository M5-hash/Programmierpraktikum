package src;

import java.util.Random;

/**
 * Computer-Spieler mit Schwierigkeitsgrad Einfach
 * Schüsse komplett zufällig (Getroffene Ziele werden nicht beachtet)
 */
public class ComPlayerEasy extends ComPlayer {
    public ComPlayerEasy(int rows, int[] ships) throws Exception {
        super(rows, ships);
        this.difficulty = 0;
    }

    /**
     * Markiert nächstes Feld (intern), auf das der Computer schießt und gibt X/Y Koordinate zurück
     * Schwierigkeitsgrad Einfach:
     * Komplett zufälliges Feld, solange dieses nicht abgeschossen wurde.
     *
     * @return Array
     * [0]: X-Koordinate
     * [1]: Y-Koordinate
     */
    @Override
    public int[] doNextShot() {
        Random rand = new Random();
        int rows = this.enemyField.length;
        int x = rand.nextInt(rows);
        int y = rand.nextInt(rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (enemyField[y][x] == 0) {
                    //Einfaches Markieren mit 1, da ein leichter Computer einfach
                    //nur zufällig Felder abschießt und nicht auf getroffene Schiffsteile achtet
                    enemyField[y][x] = 1;
                    return new int[]{x, y};
                }
                x = (x + 1) % rows;
            }
            y = (y + 1) % rows;
        }

        return new int[]{x, y};
    }
}
