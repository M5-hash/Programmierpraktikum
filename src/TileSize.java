package src;

import static src.config.*;

public class TileSize {                         //Die Dimesionen der Tiles

    public static int Tile_Size = ((GF_WIDTH / 4) - 12 )/ fieldsize;

    public static int SizeofBorder = Math.max(18, TileSize.Tile_Size / 12) ;

    /**
     * @param tile_Size int --> setzt die Groesse der einzelnen Felder im Spielfeld in Pixeln
     */
    public static void setTile_Size(int tile_Size) {

            if (tile_Size != 0) {
                Tile_Size = tile_Size;
            }
    }

    /**
     * @return int Gibt die Größe des einzelnen Feldes zurück
     */
    public static int getSizeofBorder() {
        return SizeofBorder;
    }

}

