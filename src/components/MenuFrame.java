package src.components;

import src.ImageLoader;

import javax.swing.*;
import java.awt.*;

import static src.config.*;


public class MenuFrame extends JFrame {


    public MenuFrame() {
        super();

        setTitle("Pokemon");
        setMinimumSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(setIcon());
        setLocationRelativeTo(null);
//        setResizable(false);
        setVisible(true);
    }

    public MenuFrame(String title, int GF_WIDTH, int GF_HEIGHT) {
        super();

        if (fullscreen){
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
        }

        setMinimumSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
        setSize(GF_WIDTH, GF_HEIGHT);
        setDefaults(title);
    }

    private void setDefaults(String title) {

        setTitle(title);
        setMinimumSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setLocationRelativeTo(null);
        setIconImage(setIcon());
//        setResizable(false);
        setVisible(true);
    }

    private Image setIcon() {
        return ImageLoader.getImage(ImageLoader.FRAME_ICON);
    }
}
