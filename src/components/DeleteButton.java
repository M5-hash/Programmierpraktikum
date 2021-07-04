package src.components;

import src.Bildloader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import static src.config.selectedTheme ;

import static src.FontLoader.Pokemon;

/**
 * Button, welcher zwischen der Lösch und Platzieren Funktion hin und her wechselt
 */
public class DeleteButton extends JButton {

    /**
     * Gibt an, ob man momentan löscht
     */
    boolean deleting;

    /**
     * Bildloader, sodass auch Bilder geladen werden
     */
    Bildloader Bild = new Bildloader();

    /**
     * Initalisiert den DeleteButton mit den richtigen Eigenschaften
     */
    public DeleteButton() {
        super("DELETE");
        setBorder(new LineBorder(Color.darkGray));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(Pokemon);
        setFocusPainted(false);
        setContentAreaFilled(false);

    }

    /**
     * Wechselt beim Aufruf von Platzieren zu Löschen bzw von Löschen zu Platzieren
     */
    public void switchDeleting() {
        this.deleting = !deleting;
        if(!deleting){
            setText("DELETE");
        } else {
            setText("PLACE");
        }
    }

    /**
     * @param g Graphics Object
     *
     *          Checkt das momentane Theme und ob platziert oder gelöscht wird und zeichnet den Button dann passend
     */
    @Override
    protected void paintComponent(Graphics g) {

        BufferedImage image;

        if(selectedTheme.equals("Pokemon")){
            setForeground(Color.black);
            if (deleting) {
                image = Bild.BildLoader("assets/button1green.png");
            } else {
                image = Bild.BildLoader("assets/button1red.png");
            }

        } else {
            setForeground(Color.white);
            if (deleting) {
                image = Bild.BildLoader("src/Images/NavalButtonShort.png");
            } else {
                image = Bild.BildLoader("src/Images/RedNavalButtonShort.png");
            }
        }


        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        super.paintComponent(g);
    }
}