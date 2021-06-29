package src.components;

import src.ImageLoader;
import javax.swing.*;
import java.awt.*;
import static src.config.*;


public class MenuFrame extends JFrame {

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

    private Image setIcon() {
        return ImageLoader.getImage(ImageLoader.FRAME_ICON);
    }
}
