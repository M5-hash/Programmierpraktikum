package src.components;

import src.ImageLoader;

import javax.swing.*;
import java.awt.*;

import static src.config.*;

/**
 * Frame des Hauptmenü
 */
public class MenuFrame extends JFrame {
    /**
     * Setzt Default Werte des Frames des Hauptmenü
     */
    public MenuFrame() {
        super();

        setTitle("Pokemon");
        setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(setIcon());
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Setzt das Icon des Hauptmenü
     *
     * @return Icon des Hauptmenü
     */
    private Image setIcon() {
        return ImageLoader.getImage(ImageLoader.FRAME_ICON);
    }
}
