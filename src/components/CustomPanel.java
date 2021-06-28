package src.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomPanel extends JPanel {

    private final BufferedImage background;

    public CustomPanel(BufferedImage image){
        this.background = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}