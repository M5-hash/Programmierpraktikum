package src.components;

import src.Bildloader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

import static src.FontLoader.Pokemon;

public class DeleteButton extends JButton {

    boolean deleting;
    Bildloader Bild = new Bildloader();

    public DeleteButton() {
        super("DELETE");
        setBorder(new LineBorder(Color.darkGray));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
