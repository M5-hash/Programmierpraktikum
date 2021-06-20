package src.components;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel{

    GridLayout buttonPanelLayout;

    public ButtonPanel(){
        buttonPanelLayout = new GridLayout(1,0);
        buttonPanelLayout.setHgap(5);

        setOpaque(false);
        setLayout(buttonPanelLayout);
    }
}
