package src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

/**
 * Abstrakte ComPlayer-Basis
 */
public abstract class ComPlayer {
    /**
     * PlayingField des Computer-Spielers
     */
    protected PlayingField pf;

    /**
     * Konstruktor
     *
     * @param pf PlayingField des Computers
     * @throws Exception X/Y-Koordinatenprüfung
     */
    public ComPlayer(PlayingField pf) throws Exception {
        this.pf = pf;
        setShips(pf.getAllowedShips());
    }

    /**
     * Parameterloser Konstruktor, falls man das Spiel laden möchte
     *
     * @throws FileNotFoundException Wenn die zugehörige Speicherdatei nicht existiert
     */
    public ComPlayer() throws FileNotFoundException {
        pf = new PlayingField();
    }

    /**
     * pf-Getter
     *
     * @return this.pf
     */
    public PlayingField getPlayingField(){
        return this.pf;
    }

    /**
     * pf-Setter
     *
     * @param pf Das zu setzende PlayingField
     */
    public void setPlayingField(PlayingField pf){
        this.pf = pf;
    }

    /**
     * Wrapper für die gameover Methode von PlayingField
     *
     * @return True: Laden erfolgreich, False: Laden nicht erfolgreich
     */
    public boolean gameover() {
        return this.pf.gameover();
    }

    /**
     * Wrapper für die isShot Methode von PlayingField
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @return 0: Kein Treffer, 1: Treffer, 2: Treffer und versenkt
     * @throws Exception X/Y-Koordinatenüberprüfung
     */
    public int isShot(int x, int y) throws Exception {
        return this.pf.isShot(x, y);
    }

    /**
     * Platzieren aller Computer-Schiffe
     *
     * @param possibleShips Array mit den Schiffsgrößen. (Bsp.: {4,3,3,2}, Ein Vierer-Schiff, zwei Dreier-Schiffe und ein Zweier-Schiff)
     * @throws Exception Wenn es einen Fehler beim Platzieren gab. Sollte solange der Algorithmus richtig ist, nicht auftreten.
     */
    private void setShips(int[] possibleShips) throws Exception {
        for (int shipLength : possibleShips) {
            int[] xyh = getRandomPossibleShip(shipLength);
            if (!this.pf.setShip(shipLength, xyh[0], xyh[1], xyh[2] == 1)) {
                throw new Exception("Computer-Schiff konnte nicht platziert werden!");
            }
        }
    }

    /**
     * Zufällige valide Platzierung eines Schiffes.
     * Die Stelle ist Zufall, aber es wird ein Algorithmus verwendet,
     * sodass man bei vielen Platzierten Schiffen nicht zu oft nach einer freien Stelle suchen muss.
     *
     * @param length Länge des zu platzierenden Schiffes
     * @return new int[]{ X-Koordinate, Y-Koordinate, Horizontal (1) bzw Vertikal (0) }
     * @throws Exception Wenn kein Platz mehr gefunden werden kann
     */
    private int[] getRandomPossibleShip(int length) throws Exception {
        Random rand = new Random();
        int rows = this.pf.getField().length;

        int x = rand.nextInt(rows);
        int y = rand.nextInt(rows);
        int h = rand.nextInt(2);//0 = Vertikal, 1 = Horizontal

        //Zwei mal für Horizontal und Vertikal
        for (int twice = 0; twice < 2; twice++) {
            //Y-Offset, falls nötig (Einmal im "Kreis" Vertikal auf dem Feld bewegen)
            for (int i = 0; i < rows; i++) {
                //X-Offset, falls nötig (Einmal im "Kreis" Horizontal auf dem Feld bewegen)
                for (int j = 0; j < rows; j++) {
                    if (this.pf.getField()[y][x] == 0 && this.pf.checkShip(length, x, y, h == 1)) {
                        return new int[]{x, y, h};
                    }

                    x = (x + 1) % rows;
                }

                if (this.pf.getField()[y][x] == 0 && this.pf.checkShip(length, x, y, h == 1)) {
                    return new int[]{x, y, h};
                }

                y = (y + 1) % rows;
            }

            h = h == 0 ? 1 : 0;
        }

        throw new Exception("Fehler beim ermitteln der Computer-Schiff-Platzierungen!");
    }

    /**
     * Laden der Computer-Spieler-Sicht, per ID
     *
     * @param id ID die man über das Netzwerk bekommt
     * @throws FileNotFoundException Wenn die dazugehörige Datei nicht existiert
     */
    protected boolean loadGame(long id) throws FileNotFoundException {
        return pf.loadGame(id, this);
    }

    /**
     * Laden der Computer-Spieler-Sicht, per File
     *
     * @param file Dateipfad und Dateiname
     * @return True: Laden erfolgreich, False: Laden nicht erfolgreich
     * @throws FileNotFoundException Wenn die dazugehörige Datei nicht existiert
     */
    protected boolean loadGame(String file) throws FileNotFoundException {
        return pf.loadGame(file, this);
    }

    /**
     * Speichern des Computer-Spieler-Spielstandes
     *
     * @param id ID die man über das Netzwerk bekommt
     * @throws IOException Wenn die Datei nicht erstellt/beschrieben werden kann
     */
    public void saveGame(long id) throws IOException {
        this.pf.saveGame(id, this);
    }

    /**
     * Speichern des Computer-Spieler-Spielstandes
     *
     * @param file Dateipfad und Dateiname
     * @throws IOException Wenn die Datei nicht erstellt/beschrieben werden kann
     */
    public void saveGame(String file) throws IOException {
        this.pf.saveGame(file, this);
    }

    /**
     * Abstrakte Methode die die nächsten Koordinaten ausgibt, die der Computer-Spieler abschießen will
     *
     * @return int[]{x, y}
     * @throws Exception Wenn es keine sinnvolle Möglichkeit mehr gibt
     */
    public abstract int[] doNextShot() throws Exception;

    /**
     * Abstrakte Methode der übergeben wird, ob der nächste Computer-Spieler-Schuss getroffen hat
     *
     * @param hit 0: Nicht getroffen, 1: Schiff getroffen, 2: Schiff getroffen und versenkt
     * @throws Exception Wenn davor noch nicht doNextShot aufgerufen wurde
     */
    public abstract void didHit(int hit) throws Exception;
}
