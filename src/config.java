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
    public static boolean   GameMode                = false; // false = Singleplayer; true = Multiplayer
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

    // Load Multiplayer Game
    public static String    filepath                = "";

    // Information Strings
    public static String  TextSingleplayer          =  """
            You  can  select  the  difficulty  of  the  game\s\040
            by  clicking  the  EASY  or NORMAL button.

            You  can  also  load  an  old  game\s
            by  clicking  the  load  game  button.""";

    public static String  TextMulitplayer           =  """
            You  can  either  join  a  game  or host  a  game.
            Please  enter  your  IP  before  joining  a  game.\040
            """;

    public static String  TextOptions               = """
            You  can  select  fullscreen  mode,  resolution\s
            and  change  the  theme  of  the  game  here.
            .""";

    public static String  TextSize                  =  """
            You  can  set  the  amount  of  size  2, 3, 4 and 5 Pokemon
            as  well  as  the  size  of  the  playing  field.\040

           """;
}
