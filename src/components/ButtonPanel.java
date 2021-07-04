package src.components;

import javax.swing.*;
import java.awt.*;

/**
 * Panel um Buttons in einem Gridlayout an
 */
public class ButtonPanel extends JPanel {
    /**
     * Layout des ButtonPanel
     */
    GridLayout buttonPanelLayout;

    /**
     * Transparentes Panel mit Gridlayout
     */
    public ButtonPanel() {
        buttonPanelLayout = new GridLayout(1, 0);
        buttonPanelLayout.setHgap(5);

        setOpaque(false);
        setLayout(buttonPanelLayout);
    }
}
