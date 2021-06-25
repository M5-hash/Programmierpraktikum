package src;

import java.util.Random;

/**
 * Computer-Spieler mit Schwierigkeitsgrad Einfach
 * Schüsse komplett zufällig (Getroffene Ziele werden nicht beachtet)
 */
public class ComPlayerEasy extends ComPlayer {
    public ComPlayerEasy(PlayingField pf, int[] ships) throws Exception {
        super(pf, ships);
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
        int rows = this.pf.getFieldEnemy().length;
        int x = rand.nextInt(rows);
        int y = rand.nextInt(rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (this.pf.getFieldEnemy()[y][x] == 0) {
                    //Einfaches Markieren mit 1, da ein leichter Computer einfach
                    //nur zufällig Felder abschießt und nicht auf getroffene Schiffsteile achtet
                    this.pf.getFieldEnemy()[y][x] = 1;
                    return new int[]{x, y};
                }
                x = (x + 1) % rows;
            }
            y = (y + 1) % rows;
        }

        return new int[]{x, y};
    }

    @Override
    public void didHit(int hit) throws Exception {
        //Do nothing
        //Methode ist in ComPlayer abstract, da ComPlayerNormal diese verwendet
        //und wir durch diese leere Implementierung beim Verwenden der Klasse nicht zwischen Easy und Normal unterscheiden müssen
    }
}
