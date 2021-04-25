package Feld;

import javax.swing.*;
import java.awt.*;

public class TilePainter extends JPanel {

    private final Tile Ebene;

    public TilePainter(int y, int x) {
        Ebene = new Tile(y, x);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Ebene.DrawLayer(g);


    }


}
