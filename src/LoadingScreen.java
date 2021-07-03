package src;

import src.components.CustomPanel;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JFrame {

    Bildloader Bild = new Bildloader();
    Image img = null;

    public LoadingScreen() {
        setBounds(400, 400, 600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        JPanel menuPanel         = new CustomPanel(ImageLoader.getImage(ImageLoader.GAME_BACKGROUND));
        menuPanel. setLayout(null);
        menuPanel.   setBounds(0, 0, getWidth(), getHeight());
        menuPanel.repaint();
    }

}
