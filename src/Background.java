package src;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {


    Bildloader Bild = new Bildloader();
    Image img = null;

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Backgroundpainter(g);
    }


    public void Backgroundpainter(Graphics g) {

        img = Bild.BildLoader("src/Images/theme_1_background.jpg");

        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);

    }
}
