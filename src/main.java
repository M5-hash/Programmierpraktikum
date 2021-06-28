package src;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class main{

    /**
     * The main function for the battleships game.
     * @param args The arguments which are passed to the programme - ignored
     */
    public static void main(String[] args) throws IOException, FontFormatException {

        // Try to load the sprites
        try {
            ImageLoader.init();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "The game was unable to load its graphical assets. \n"
                            + "Make certain that you are running it in the same folder as the 'assets' folder.",
                    "Asset Loading Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        FontLoader.createFont();
        
        // Create the start window
        new MenuStart();

    }
}