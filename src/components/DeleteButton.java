package src.components;

import src.Bildloader;

import javax.swing.*;
import java.awt.*;

public class DeleteButton extends JButton {
    static boolean deleting ;
    Bildloader Bild = new Bildloader();

    public DeleteButton(String button_title) {
        super("DELETE");
    }

    public void switchDeleting() {
        this.deleting = !deleting;
    }

    @Override
    protected void paintComponent(Graphics g) {


        Image image ;

        if(deleting){
            image = Bild.BildLoader("assets/button1green.png") ;
        } else{
            image = Bild.BildLoader("assets/button1red.png") ;
        }

        g.drawImage(image, 0,0, null) ;
        super.paintComponent(g);
    }
}
