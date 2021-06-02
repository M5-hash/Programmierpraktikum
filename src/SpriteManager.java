package src;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteManager {

    // These are integer constant which define the images.
    public static final int SHIP_VERTICAL_BACK = 0;
    public static final int SHIP_VERTICAL_MIDDLE = 1;
    public static final int SHIP_VERTICAL_FRONT = 2;
    public static final int SHIP_HORIZONTAL_BACK = 3;
    public static final int SHIP_HORIZONTAL_MIDDLE = 4;
    public static final int SHIP_HORIZONTAL_FRONT = 5;
    public static final int SHIP_ICON = 6;
    public static final int SHIP_BACKGROUND = 7;

    // Storage location for the laoded sprites.
    private static BufferedImage[] sprites;

    /**
     * Loads all of the sprites into memory and saves them
     * @throws IOException
     */
    public static void init() throws IOException {
        sprites = new BufferedImage[8];

        sprites[0] = ImageIO.read(new File("assets/SHIP_VERTICAL_BACK.png"));
        sprites[1] = ImageIO.read(new File("assets/SHIP_VERTICAL_MIDDLE.png"));
        sprites[2] = ImageIO.read(new File("assets/SHIP_VERTICAL_FRONT.png"));
        sprites[3] = ImageIO.read(new File("assets/SHIP_HORIZONTAL_BACK.png"));
        sprites[4] = ImageIO.read(new File("assets/SHIP_HORIZONTAL_MIDDLE.png"));
        sprites[5] = ImageIO.read(new File("assets/SHIP_HORIZONTAL_FRONT.png"));
        sprites[6] = ImageIO.read(new File("assets/SHIP_ICON.png"));
        sprites[7] = ImageIO.read(new File("assets/SHIP_BACKGROUND.gif"));
    }

    /**
     * Gets the sprite represented by id
     * @param id the sprite to get
     * @return The BufferedImage for the Sprite
     */
    public static BufferedImage getSprite(int id){
        if (sprites == null)
            throw new Error("Sprites not yet loaded");

        return sprites[id];
    }
}
