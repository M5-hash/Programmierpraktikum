package src;

public class config {

    // Initial frame size
    public static int       INITIAL_WIDTH           = 640;
    public static int       INITIAL_HEIGHT          = 480;

    // Spielwindow default size
    public static int       GF_WIDTH                = 1920;
    public static int       GF_HEIGHT               = 1080;

    // The difficulties of the game
    public static boolean   KIisEasy                = false;
    public static int       EASY_DIFFICULTY         = 0;
    public static int       NORMAL_DIFFICULTY       = 1;
    public static int       SpielFeld1              = 0; // 0 = Spieler ; 1 = GegnerKI ; 2 = GegnerOnline
    public static int       SpielFeld2              = 1; // 0 = Spieler ; 1 = GegnerKI ; 2 = GegnerOnline

    public static boolean   change                  = false;
    public static int       framewidth              = 0;
    public static int       frameheigth             = 0;
    public static String    IP                      = "";

    // The string array for options
    public static String[]  Resolutions             = {"Resolution", "2560x1440", "1920x1080", "1600x900","1366x768","1280x720","1024x576","960x540","854x480",};
    public static String[]  Themes                  = {"Pokemon", "Classic", ""};
    public static String    selectedResolution      = "";
    public static String    selectedTheme           = "Pokemon";
    public static boolean   fullscreen              = false;

    // Slider sizes
    public static int       fieldsize               = 10;
    public static int       size2                   = 1;
    public static int       size3                   = 1;
    public static int       size4                   = 1;
    public static int       size5                   = 1;
    public static int       sumofships              = 4;

    // Information Strings
    public static String  TextSingleplayer          =  """
            You  can  select  the  difficulty  of  the  game\s\040
            by  clicking  the  EASY  or NORMAL button.

            You  can  also  select  the  size  of the  Pokemon\s
            by  clicking  the  size  button.""";

    public static String  TextMulitplayer           =  """
            You  can  either  join  a  game  or host  a game.
            Please  enter  your  IP  before  starting  the  game.\040

            You  can  also  select  the  size  of the  Pokemon\s
            by  clicking  the  size  button.""";


    public static String  TextOptions               = """
            You  can  select  fullscreen  mode  and  resolution  here.

            You  can  also  select  the  size  of the  Pokemon\s
            by  clicking  the  size  button.""";

    public static String  TextGame                  =  """
            You  can  select  fullscreen  mode  and  resolution  here.

            You  can  also  select  the  size  of the  Pokemon\s
            by  clicking  the  size  button.""";


    public static String  TextSize                  =  """
            You  can  set  the  amount  of  size  2, 3, 4 and 5 Pokemon's
            as  well  as  the  size  of  the  playing  field.\040

            You  can  also  select  the  size  of the  Pokemon\s
            by  clicking  the  size  button.""";
}
