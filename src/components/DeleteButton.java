package src.components;

import src.Bildloader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static src.FontLoader.Pokemon;

public class DeleteButton extends JButton {

    boolean deleting;
    Bildloader Bild = new Bildloader();

    public DeleteButton() {
        super("DELETE");
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setVerticalTextPosition(JButton.CENTER);
        setFont(Pokemon);
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

    public void switchDeleting() {
        this.deleting = !deleting;
        if(!deleting){
            setText("DELETE");
        } else {
            setText("PLACE");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        BufferedImage image;

        if (deleting) {
            image = Bild.BildLoader("assets/button1green.png");
        } else {
            image = Bild.BildLoader("assets/button1red.png");
        }

        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        super.paintComponent(g);
    }
}
