package src;

/**
 * Computer-Spieler mit Schwierigkeitsgrad Normal
 * Schüsse sind so lange zufällig platziert, bis ein Schiff erwischt wird.
 * Dann wird ermittelt wie das Schiff platziert ist und dieses komplett zerstört.
 */
public class ComPlayerNormal extends ComPlayer{
    public ComPlayerNormal(int rows, int[] ships) throws Exception {
        super(rows, ships);
        this.difficulty = 1;
    }

    @Override
    public int[] doNextShot() {
        //TODO
        return new int[0];
    }
}
