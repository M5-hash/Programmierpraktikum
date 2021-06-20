package src;

public class TileSize {                         //Die Dimesionen der Tiles

    public static int Tile_Size = 32;



    public static int SizeofBorder = Math.max(18, TileSize.Tile_Size / 12) ;
    public static int getFighting() {
        return fighting;
    }

    public static void setFighting(int fighting) {
        TileSize.fighting = fighting;
    }
    public static int fighting = Tile.fightstart ? 1 : 0;

    public static void setTile_Size(int tile_Size) {
        if (!Tile.fightstart)
            if (tile_Size != 0) {
                Tile_Size = tile_Size;
            } else {
                if (tile_Size * 3 / 4 != 0) {
                    Tile_Size = tile_Size * 3 / 4;
                }
            }
    }

    public static int getSizeofBorder() {
        return SizeofBorder;
    }

}

/*
    int[][] getEnemyPlacement =
              {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            , {0, 0, 8, 8, 8, 8, 0, 0, 0, 0}
            , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            , {0, 0, 0, 0, 0, 7, 7, 7, 0, 0}
            , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            , {0, 0, 0, 0, 0, 8, 0, 0, 0, 0}
            , {0, 8, 8, 0, 0, 8, 0, 0, 0, 0}
            , {0, 8, 8, 0, 0, 8, 0, 0, 0, 0}
            , {0, 8, 8, 0, 0, 8, 0, 0, 0, 0}
            , {0, 8, 8, 0, 0, 0, 0, 0, 0, 0}};
*/

