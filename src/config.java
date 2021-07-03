package src;

/**
 * <h1>Config</h1>
 *
 * <p>Die Config dient zur Implementierung von Werten,
 * die in den meisten Klassen verwendet werden.
 */
public class config {
    /**
     * <p>Vorgegebene Breite und Höhe des Frames beim Erstellen des Hauptmenü</p>
     *
     * <p>INITIAL_WIDTH    Breite des Frames</p>
     * <p>INITIAL_HEIGHT   Höhe des Frames</p>
     *
     */
    public static int       INITIAL_WIDTH           = 640;
    public static int       INITIAL_HEIGHT          = 480;

    /**
     * Default Werte des SpielWindow
     * Werte können in den {@link MenuOptions} abgeändert werden.
     * @param   GF_WIDTH    Breite des SpielWindow
     * @param   GF_HEIGHT   Höhe des SpielWindow
     */
    public static int       GF_WIDTH                = 1920;
    public static int       GF_HEIGHT               = 1080;

    /**
     * <p>Default Werte zur Erstellung der Spielfelder.
     * Legt fest ob Online oder Offline gespielt wird,
     * ob Mensch oder Computer spielt, sowie die
     * Schwierigkeitsstufe der KI im Falle, dass die
     * KI spielt.</p>
     * @param   KIisEasy    True - Schwierigkeitsstufe Easy
     *                      False - Schwierigkeitsstufe Normal
     * @param   GameMode    True - Es wird Online gespielt
     *                      False - Es wird Offline gespielt
     * @param   Spielfeld1  0 - Mensch verwendet das Feld
     *                      1 - KI verwendet das Feld
     *                      2 - Multiplayer
     * @param   Spielfeld2  0 - Mensch verwendet das Feld
     *                      1 - KI verwendet das Feld
     *                      2 - Multiplayer
     */
    public static boolean   KIisEasy                = false;
    public static boolean   GameMode                = false; // false = Singleplayer; true = Multiplayer
    public static int       SpielFeld1              = 0; // 0 = Spieler ; 1 = GegnerKI ; 2 = GegnerOnline
    public static int       SpielFeld2              = 1; // 0 = Spieler ; 1 = GegnerKI ; 2 = GegnerOnline
    public static boolean   onlineCom               = false;

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
