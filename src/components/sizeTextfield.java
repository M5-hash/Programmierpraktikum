package src.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static src.FontLoader.Pokemon;

/**
 * Custom Textfeld für das Sizemenü
 */
public class sizeTextfield extends JTextField {

    /**
     * Default Werte des Textfelds
     * @param value Text des Textfelds
     */
    public sizeTextfield(int value) {
        setText(String.valueOf(value));
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(false);
        setBorder(new LineBorder(Color.black));
        setFont(Pokemon.deriveFont(11f));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setText("");
            }
        });
        addActionListener(e -> System.out.println(getText()));
    }
}
