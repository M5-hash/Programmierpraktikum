package src.components;

import src.Bildloader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import static src.config.selectedTheme ;

public class CustomPanel extends JPanel {

    private BufferedImage givenbackground;

    public CustomPanel(BufferedImage image){
        this.givenbackground = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage background ;
        if(!selectedTheme.equals("Pokemon")){
            Bildloader Bild = new Bildloader() ;
            background = Bild.BildLoader("src/Images/NavalBackground.jpg") ;
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        } else {

            g.drawImage(givenbackground, 0, 0, getWidth(), getHeight(), this);
        }

    }
}
