package src;

/**
 * Computer-Spieler mit Schwierigkeitsgrad Normal
 * Schüsse sind so lange zufällig platziert, bis ein Schiff erwischt wird.
 * Dann wird ermittelt wie das Schiff platziert ist und dieses komplett zerstört.
 */
public class ComPlayerNormal extends ComPlayer {
    private int[] lastCoords = null;
    private int last = 0;

    public ComPlayerNormal(int rows, int[] ships) throws Exception {
        super(rows, ships);
        this.difficulty = 1;
    }

    @Override
    public int[] doNextShot() {
        int[] hit = this.findHit();

        if(hit == null){
            //Schach-Pattern schießen
            int[] next = this.findNextCheckPattern();
        }else{
            //Abgeschossenes, nicht zerstörtes Schiff existiert.

        }

        return new int[0];
    }

    private int[] findNextCheckPattern(){
        for (int y = 0; y < this.enemyField.length; y++) {
            for (int x = 0; x < this.enemyField.length; x++) {
                boolean xC = x - 1 < 0;
                boolean yC = y - 1 < 0;
                boolean xP = x + 1 >= this.enemyField.length;
                boolean yP = y + 1 >= this.enemyField.length;

                if(this.enemyField[y][x] == 0
                && (xC || this.enemyField[y][x-1] == 0)
                && (xP || this.enemyField[y][x+1] == 0)
                && (yC || this.enemyField[y-1][x] == 0)
                && (yP || this.enemyField[y+1][x] == 0)
                ){
                    return new int[]{x, y};
                }

            }
        }

        return null;
    }

    private int[] findHit(){
        for (int y = 0; y < this.enemyField.length; y++) {
            for (int x = 0; x < this.enemyField.length; x++) {
                if(this.enemyField[y][x] == 1){
                    return new int[]{x, y};
                }
            }
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

                break;
            case 2: //Treffer versenkt
                this.enemyField[y][x] = 2;

                break;
            default:
                throw new Exception("Parameter (" + hit + ") nicht im Bereich von 0 bis 2");
        }
    }
}
