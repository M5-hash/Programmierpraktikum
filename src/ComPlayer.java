package src;

import java.io.FileNotFoundException;

public abstract class ComPlayer {
    protected PlayingField pf;
    protected int[] possibleShips;
    protected int difficulty;

    public ComPlayer(int rows, int[] ships){
        pf = new PlayingField(rows);
        possibleShips = ships;
    }

    public ComPlayer(long id) throws FileNotFoundException {
        pf = new PlayingField();
        this.loadGame(id);
    }

    /**
     * Laden der Computer-Spieler-Sicht
     * @param id
     * @throws FileNotFoundException
     */
    private void loadGame(long id) throws FileNotFoundException {
        //pf.loadGame(id, true);
    }

    protected abstract void setShips();
}
