package src;

/**
 * <h1>Config</h1>
 *
 * <p>Die Config dient zur Implementierung von Werten,
 * die in den meisten Klassen verwendet werden.
 */
public class config {

    /**
     * Breite des Frames beim Erstellen des Hauptmenü
     */
    public static int       INITIAL_WIDTH           = 640;
    /**
     * Höhe des Frames beim Erstellen des Hauptmenü
     */
    public static int       INITIAL_HEIGHT          = 480;
    /**
     * Breite des Spielfeldframes
     */
    public static int       GF_WIDTH                = 1920;
    /**
     * Höhe des Spielfeldframes
     */
    public static int       GF_HEIGHT               = 1080;
    /**
     * True - Schwierigkeitsstufe Easy <br>
     * False - Schwierigkeitsstufe Normal
     */
    public static boolean   KIisEasy                = false;
    /**
     * True - Singleplayer <br>
     * False - Multiplayer
     */
    public static boolean   GameMode                = false; // false = Singleplayer; true = Multiplayer
    /**
     * 0 - Mensch verwendet des Feld <br>
     * 1 - KI verwendet das Feld <br>
     * 2 - Multiplayer
     */
    public static int       SpielFeld1              = 0; // 0 = Spieler ; 1 = GegnerKI ; 2 = GegnerOnline
    /**
     * 0 - Mensch verwendet des Feld <br>
     * 1 - KI verwendet das Feld <br>
     * 2 - Multiplayer
     */
    public static int       SpielFeld2              = 1; // 0 = Spieler ; 1 = GegnerKI ; 2 = GegnerOnline
    public static boolean   onlineCom               = false;

    /**
     *  Aktuelle Breite des Spielfeldframes
     */
    public static int       framewidth              = 0;
    /**
     *  Aktuelle Höhe des Spielfeldframes
     */
    public static int       frameheigth             = 0;
    /**
     *  Dient zum Joinen eines Spiels im Multiplayermenü
     */
    public static String    IP                      = "";
    /**
     * Default Resolution 1920x1080 <br>
     * Auswahl an Resolutions für das Spiel
     */
    public static String[]  Resolutions             = {"Resolution", "2560x1440", "1920x1080", "1600x900","1366x768","1280x720","1024x576","960x540","854x480",};
    /**
     * Default Theme Pokemon <br>
     * Auswahl der Themes für das Spiel
     */
    public static String[]  Themes                  = {"Pokemon", "Classic", ""};
    /**
     * Speichert den Wert der ausgewählten Resolution
     */
    public static String    selectedResolution      = "";
    /**
     * Speichert den Wert des ausgewählten Themes
     */
    public static String    selectedTheme           = "Pokemon";
    /**
     * True - Spielfeld wird im Fullscreen aufgerufen <br>
     * False - Spielfeld wird in ausgewählter Resolution oder Default Wert aufgerufen
     */
    public static boolean   fullscreen              = false;

    /**
     * Setzt die Feldgröße auf 10 <br>
     */
    public static int       fieldsize               = 10;
    /**
     * Anzahl an size2 Ships<br>
     */
    public static int       size2                   = 1;
    /**
     * Anzahl an size3 Ships<br>
     */
    public static int       size3                   = 1;
    /**
     * Anzahl an size4 Ships<br>
     */
    public static int       size4                   = 1;
    /**
     * Anzahl an size5 Ships<br>
     */
    public static int       size5                   = 1;
    /**
     * Anzahl aller Schiffe, die auf das Spielfeld gesetzt werden darf<br>
     */
    public static int       sumofships              = 4;

    // Load Multiplayer Game
    public static String    filepath                = "";
    /**
     * Informationen für das Singleplayerwindow
     */
    public static String  TextSingleplayer          =  """
            You  can  select  the  difficulty  of  the  game\s\040
            by  clicking  the  EASY  or NORMAL button.

            You  can  also  load  a  saved  game\s
            by  clicking  the  load  game  button.""";
    /**
     * Informationen für das Multiplayerwindow
     */
    public static String  TextMulitplayer           =  """
            You  can  either  join  a  game  or host  a  game.
            Please  enter  your  partner's  IP  before\s
            joining  a  game.\040
            """;
    /**
     * Informationen für das Optionswindow
     */
    public static String  TextOptions               = """
            You  can  select  fullscreen  mode,  the  resolution\s
            and  change  the  theme  of  the  game  here.
            """;
    /**
     * Informationen für das Sizewindow
     */
    public static String  TextSize                  =  """
            You  can  set  the  amount  of  size  2, 3, 4 and 5 Pokemon
            as  well  as  the  size  of  the  playing  field.\040

           """;
}
