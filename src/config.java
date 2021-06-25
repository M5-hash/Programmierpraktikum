package src;

public class config {

    // Initial frame size
    public static int       INITIAL_WIDTH           = 640;
    public static int       INITIAL_HEIGHT          = 480;

    // Spielwindow default size
    public static int       GF_WIDTH                = 1920;
    public static int       GF_HEIGHT               = 1080;

    // col row sizes
    public static int       COL                     = (INITIAL_WIDTH * 20 / 100) - 10;
    public static int       C_GAP                   = (INITIAL_WIDTH * 30 / 100) - 10;
    public static int       ROW_INFO                = (INITIAL_HEIGHT * 33 / 100) - 10;
    public static int       ROW                     = (INITIAL_HEIGHT * 10 / 100) - 10;
    public static int       R_GAP                   = (INITIAL_HEIGHT * 2) / 100;

    // The difficulties of the game
    public static String    EASY_DIFFICULTY         = "Easy";
    public static String    NORMAL_DIFFICULTY       = "Normal";
    public static boolean   KI                      = false;

    // The string array for options
    public static String[]  Resolutions             = {"Resolution", "2560x1440","1920x1200","1920x1080","1680x1050","1440x900","1366x768","1280x720","1280x800","1024x768","800x600","640x480"};
    public static String[]  Themes                  = {"Theme", "Pokemon", "Zelda", "AoE"};
    public static String    selectedResolution      = "";
    public static String    selectedTheme           = "";
    public static boolean   fullscreen              = false;

    // Slider sizes
    public static int       fieldsize               = 10;
    public static int       size2                   = 1;
    public static int       size3                   = 1;
    public static int       size4                   = 1;
    public static int       size5                   = 1;

    // Information Strings
    public static String  TextSingleplayer          =  """
            You  can  select  fullscreen  mode  and  resolution  here.

            You  can  also  select  the  size  of the  Pokemon's\s
            by  clicking  the  size  button.""";

    public static String  TextMulitplayer           =  """
            You  can  select  fullscreen  mode  and  resolution  here.

            You  can  also  select  the  size  of the  Pokemon's\s
            by  clicking  the  size  button.""";

    public static String  TextOptions               = """
            You  can  select  fullscreen  mode  and  resolution  here.

            You  can  also  select  the  size  of the  Pokemon's\s
            by  clicking  the  size  button.""";

    public static String  TextHost                  =  """
            You  can  select  fullscreen  mode  and  resolution  here.

            You  can  also  select  the  size  of the  Pokemon's\s
            by  clicking  the  size  button.""";

    public static String  TextGame                  =  """
            You  can  select  fullscreen  mode  and  resolution  here.

            You  can  also  select  the  size  of the  Pokemon's\s
            by  clicking  the  size  button.""";


    public static String  TextSize                  =  """
            You  can  select  fullscreen  mode  and  resolution  here.

            You  can  also  select  the  size  of the  Pokemon's\s
            by  clicking  the  size  button.""";
}
