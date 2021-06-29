package src;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    public static final int STARTMENU_BG = 0;
    public static final int STARTMENU_BTN_QUIT = 1;
    public static final int GAME_BACKGROUND = 2;
    public static final int GAME_BTN_BALL1= 3;
    public static final int GAME_BTN_BALL2 = 4;
    public static final int GAME_BTN_BALL3 = 5;
    public static final int GAME_BTN_BALL4 = 6;
    public static final int STARTMENU_BTN_TEXTFIELD_EICH = 7;
    public static final int MENU_BG = 8;
    public static final int MENU_BUTTON = 9;
    public static final int MENU_BUTTON2 = 10;
    public static final int GAME_BTN_BACKGROUND = 11;
    public static final int FRAME_ICON = 12;
    public static final int OPTIONS_BACKGROUND = 13;
    public static final int GREEN = 14;
    public static final int RED = 15;

    private static BufferedImage[] image;

    public static void init() throws IOException {
        image = new BufferedImage[16];

        image[STARTMENU_BG]                 = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG.png"));
        image[MENU_BG]                      = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG2.png"));
        image[STARTMENU_BTN_QUIT]           = ImageIO.read(new File("assets/THEME_POKEMON_SM_BG.png"));
        image[GAME_BACKGROUND]              = ImageIO.read(new File("src/Images/theme_1_background.jpg"));
        image[GAME_BTN_BALL1]               = ImageIO.read(new File("assets/Ball1.png"));
        image[GAME_BTN_BALL2]               = ImageIO.read(new File("assets/Ball2.png"));
        image[GAME_BTN_BALL3]               = ImageIO.read(new File("assets/Ball3.png"));
        image[GAME_BTN_BALL4]               = ImageIO.read(new File("assets/Ball4.png"));
        image[STARTMENU_BTN_TEXTFIELD_EICH] = ImageIO.read(new File("assets/textfeld_eich.png"));
        image[MENU_BUTTON]                  = ImageIO.read(new File("assets/button2.png"));
        image[MENU_BUTTON2]                 = ImageIO.read(new File("assets/button21.png"));
        image[GAME_BTN_BACKGROUND]          = ImageIO.read(new File("assets/THEME_POKEMON_GAMEPLAY_BG.png"));
        image[FRAME_ICON]                   = ImageIO.read(new File("assets/Pokeball.png"));
        image[OPTIONS_BACKGROUND]           = ImageIO.read(new File("assets/OptionsBackground.png"));
        image[GREEN]                        = ImageIO.read(new File("assets/button1green.png"));
        image[RED]                          = ImageIO.read(new File("assets/button1red.png"));
    }

    public static BufferedImage getImage(int id) {
        if (image == null)
            throw new Error("Image not yet loaded");
        return image[id];
    }
}
