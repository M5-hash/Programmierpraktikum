package src;

public class TileSize {                         //Die Dimesionen der Tiles

    public static int Tile_Size = 32;

    public static int xRightEnd = Tile.side_gapl + SpielWindow.field_size * Tile_Size;
    public static int halfheightField = (SpielWindow.field_size * Tile_Size) / 2;
    public static int halfheightBox = 4 * Tile_Size;
    public static int fieldwidth = 3 * Tile_Size + Tile_Size / 2;
    public static int FieldBox_gap = Math.max(60, 120 % Tile_Size);


    public static int getxRightEnd() {
        return xRightEnd;
    }

    public static int getHalfheightField() {
        return halfheightField;
    }

    public static int getHalfheightBox() {
        return halfheightBox;
    }

    public static int getFieldwidth() {
        return fieldwidth;
    }

    public static int getFieldBox_gap() {
        return FieldBox_gap;
    }


    public static int getFighting() {
        return fighting;
    }

    public static int getDisplacement() {
        return displacement;
    }

    public static void setFighting(int fighting) {
        TileSize.fighting = fighting;
    }

    public static void setDisplacement(int displacement) {
        TileSize.displacement = displacement;
    }

    public static int fighting = Tile.fightstart ? 1 : 0;
    public static int displacement = fighting * (Tile.field_size * TileSize.Tile_Size + Math.max(60, 120 % TileSize.Tile_Size));

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

