package src;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;

public abstract class ComPlayer {
    protected PlayingField pf;

    /**
     * -1: Wasser
     *
     * 1: Treffer
     * 2: Treffer versenkt
     * 3: Schuss ohne Antwort
     */
    protected int[][] enemyField;
    protected int difficulty;

    //public ComPlayer(int rows, int[] ships) throws Exception {
    public ComPlayer(PlayingField pf, int[] ships) throws Exception {
        this.pf = pf;
        enemyField = new int[pf.getField().length][pf.getField().length];
        setShips(ships);
    }

    //TODO laden überarbeiten
    public ComPlayer(long id) throws FileNotFoundException {
        pf = new PlayingField();
        this.loadGame(id);
    }

    /**
     * Wrapper für die gameover Methode von PlayingField
     */
    public boolean gameover() {
        return this.pf.gameover();
    }

    /**
     * Wrapper für die isShot Methode von PlayingField
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
     * @throws Exception
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
     * Laden der Computer-Spieler-Sicht
     *
     * @param id
     * @throws FileNotFoundException
     */
    private void loadGame(long id) throws FileNotFoundException {
        //pf.loadGame(id, true);
    }

    public abstract int[] doNextShot() throws Exception;
    public abstract void didHit(int hit) throws Exception;
}
