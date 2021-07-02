package src;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Computer-Spieler mit Schwierigkeitsgrad Einfach
 * Schüsse komplett zufällig (Getroffene Ziele werden nicht beachtet)
 */
public class ComPlayerEasy extends ComPlayer {
    /**
     * Konstruktor
     *
     * @param pf PlayingField des Computers
     * @throws Exception X/Y-Koordinatenprüfung
     */
    public ComPlayerEasy(PlayingField pf) throws Exception {
        super(pf);
        this.pf.setCom(1);
    }

    /**
     * Konstruktor, falls man das Spiel laden möchte
     *
     * @param id Die ID die an loadGame(..) übergeben wird
     * @throws FileNotFoundException Wenn die zugehörige Speicherdatei nicht existiert
     */
    public ComPlayerEasy(long id) throws FileNotFoundException {
        super();
        this.loadGame(id);
    }

    /**
     * Konstruktor, falls man das Spiel laden möchte
     *
     * @param file Dateipfad und Dateiname
     * @throws FileNotFoundException Wenn die zugehörige Speicherdatei nicht existiert
     */
    public ComPlayerEasy(String file) throws FileNotFoundException {
        super();
        this.loadGame(file);
    }

    /**
     * Markiert nächstes Feld (intern), auf das der Computer schießt und gibt X/Y Koordinate zurück
     * Schwierigkeitsgrad Einfach:
     * Komplett zufälliges Feld, solange dieses nicht abgeschossen wurde.
     *
     * @return int[]{x, y}
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

    /**
     * Für ComputerPlayerEasy leer.
     * <p>
     * Methode der übergeben wird, ob der nächste Computer-Spieler-Schuss getroffen hat
     *
     * @param hit 0: Nicht getroffen, 1: Schiff getroffen, 2: Schiff getroffen und versenkt
     * @throws Exception Wenn davor noch nicht doNextShot aufgerufen wurde
     */
    @Override
    public void didHit(int hit) throws Exception {
        //Do nothing.
        //Methode ist in ComPlayer abstract, da ComPlayerNormal diese verwendet
        //und wir durch diese leere Implementierung beim Verwenden der Klasse nicht zwischen Easy und Normal unterscheiden müssen
    }
}
