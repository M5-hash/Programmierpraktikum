package src.components;

import src.Bildloader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import static src.config.selectedTheme ;

public class CustomPanel extends JPanel {

    private BufferedImage background;

    public CustomPanel(BufferedImage image){
        this.background = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(!selectedTheme.equals("Pokemon")){
            Bildloader Bild = new Bildloader() ;
            background = Bild.BildLoader("src/Images/NavalBackground.jpg") ;
        }

        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
