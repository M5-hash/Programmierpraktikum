package src;

import static src.config.*;

public class TileSize {                         //Die Dimesionen der Tiles

    public static int Tile_Size = ((GF_WIDTH / 4) - 12 )/ fieldsize;

    public static int SizeofBorder = Math.max(18, TileSize.Tile_Size / 12) ;

    public static void setTile_Size(int tile_Size) {

            if (tile_Size != 0) {
                Tile_Size = tile_Size;
            }
    }

    public static int getSizeofBorder() {
        return SizeofBorder;
    }

}

