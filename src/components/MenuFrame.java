package src.components;

import javax.swing.*;
import java.awt.*;

import static src.config.*;


public class MenuFrame extends JFrame {

    public MenuFrame(String title){
        super();

        setTitle(title);
        setMinimumSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//        setResizable(false);
        setVisible(true);
    }

    public MenuFrame(String title, int x, int y) {
        super();

        setTitle(title);
        setMinimumSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(x, y);
//        setResizable(false);
        setVisible(true);
    }
}
