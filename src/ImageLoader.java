package src;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    public static final int STARTMENU_BG = 0;
    public static final int STARTMENU_BTN_QUIT = 1;
    public static final int STARTMENU_BTN_GLUMANDA = 2;
    public static final int STARTMENU_BTN_SHIGGY = 3;
    public static final int STARTMENU_BTN_BISASAM = 4;
    public static final int STARTMENU_BTN_DRAGONITE = 5;
    public static final int STARTMENU_BTN_TEXTFIELD = 6;
    public static final int STARTMENU_BTN_TEXTFIELD_EICH = 7;
    public static final int MENU_BG = 8;
    public static final int MENU_BUTTON = 9;
    public static final int MENU_BUTTON2 = 10;
    public static final int MENU_BUTTON3 = 11;

    private static BufferedImage[] image;

    public static void init() throws IOException {
        image = new BufferedImage[12];

        image[STARTMENU_BG]                 = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG.png"));
        image[MENU_BG]                      = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG2.png"));
        image[STARTMENU_BTN_QUIT]           = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG.png"));
        image[STARTMENU_BTN_GLUMANDA]       = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG.png"));
        image[STARTMENU_BTN_SHIGGY]         = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG.png"));
        image[STARTMENU_BTN_BISASAM]        = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG.png"));
        image[STARTMENU_BTN_DRAGONITE]      = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG.png"));
        image[STARTMENU_BTN_TEXTFIELD]      = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG.png"));
        image[STARTMENU_BTN_TEXTFIELD_EICH] = ImageIO.read(new File("assets/textfeld_eich.png"));
        image[MENU_BUTTON]                  = ImageIO.read(new File("assets/button2.png"));
        image[MENU_BUTTON2]                 = ImageIO.read(new File("assets/button3.png"));
        image[MENU_BUTTON3]                 = ImageIO.read(new File("assets/button4.png"));
    }

    public static BufferedImage getImage(int id) {
        if (image == null)
            throw new Error("Image not yet loaded");
        return image[id];
    }
}
