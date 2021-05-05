package src;

import javax.swing.*;
import java.awt.*;

public class TilePainter extends JPanel {

    private final Tile Ebene;
    SchiffPainter h = new SchiffPainter();

    public TilePainter(int y, int x) {
        Ebene = new Tile(y, x);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Ebene.DrawLayer(g);
        if (SchiffPainter.ready) {
            h.Schiffzeichner(g);
            h.Wahlstation(g);

        }


    }


}
