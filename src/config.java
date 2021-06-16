package src;

public class config {

    // Initial frame size
    public static int       INITIAL_WIDTH           = 656;
    public static int       INITIAL_HEIGHT          = 518;

    // col row sizes
    public static int       COL                     = INITIAL_WIDTH * 60 / 100;
    public static int       C_GAP                   = INITIAL_WIDTH * 20 / 100;
    public static int       ROW_INFO                = INITIAL_WIDTH * 25 / 100;
    public static int       ROW                     = INITIAL_WIDTH * 15 / 100;

    // The difficulties of the game
    public static String    EASY_DIFFICULTY         = "Easy";
    public static String    NORMAL_DIFFICULTY       = "Normal";
    public static String    HARD_DIFFICULTY         = "Hard";

    // The string array for options
    public static String[]  Resolutions             = {"Select Resolution", "2560x1440","1920x1200","1920x1080","1680x1050","1440x900","1366x768","1280x720","1280x800","1024x768","800x600","640x480"};
    public static String[]  Themes                  = {"Select Theme", "Pokemon", "Zelda", "AoE"};
    public static String    selectedResolution      = "";
    public static String    selectedTheme           = "";
    public static boolean   fullscreen              = false;

    // Information Strings
    public static String[]  TextSingleplayer      = { "Please select the amount of ships you want for each ship.",
            "yo",
            "a"};

    public static String[]  TextMulitplayer       = { "Please select the amount of ships you want for each ship.",
            "yo",
            ""};

    public static String[]  TextOptions           = { "Please select the amount of ships you want for each ship.",
            "yo",
            ""};

    public static String[]  TextHost              = { "Please select the amount of ships you want for each ship.",
            "yo",
            ""};

    public static String[]  TextGame              = { "Please select the amount of ships you want for each ship.",
            "yoasdddddddddddddd",
            "aaaaaaaaaaaaaaaaaa"};

    public static String[]  TextSize              = { "Please select the amount of ships you want for each ship.",
            "yoyoasddddddddddddddyoasdddddddddddddd",
            "yoasddddddddddddddyoasddddddddddddddyoasdddddddddddddd"};
}
