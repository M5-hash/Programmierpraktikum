package src;

import javax.swing.*;
import java.awt.*;

public class DisplayTurn extends JPanel {

    Bildloader Bild = new Bildloader();
    String Zahldir = "Ich bin der String und ich bin ein Platzhalter";
    Image Zahl; //Nur ein Platzhalter, dass die IDE nicht weint
    SpielWindow spWin;
    int PosX = 5;
    int PosY = 5;
    boolean draw = true;
    JFrame frame;
    boolean Turn = true;

    public DisplayTurn() {

    }


    /**
     * @param g Graphics Object
     *          <p>
     *          Wenn das Spiel bereits vorbei ist, dann wird die Zeilhilfe nicht gebraucht/gezeichnet.
     *          Überprüft ob es zu einer Änderung in der Maus Position kam und die, wenn ja wird die Zielhilfe neu gezeichnet.
     *          Die Position wird von dem momentan verwendeten Feld bezogen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img ;

        if (draw) {

            setOpaque(false);

            if(Turn){
                img = Bild.BildLoader("src/Images/fullCutGrass.jpg");
                g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
            } else {
                img = Bild.BildLoader("src/Images/redsheen.png");
                g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
            }
        }

    }

    /**
     * Verhindert, dass die Zielhilfe gezeichnet wird
     */
    public void stopdrawing() {
        draw = false;
    }

    public void switchTurn() {

    }




}