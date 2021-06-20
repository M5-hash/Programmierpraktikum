package src.components;

import src.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static src.FontLoader.Pokemon;

public class Textfield extends JPanel {

    JTextField textField;

    public Textfield(){

        setOpaque(false);
        setLayout(new BorderLayout());
        textField = new JTextField("ENTER IP");
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setBorder(null);
        textField.setOpaque(false);
        textField.setFont(Pokemon);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setText("");
            }
        });
        textField.addActionListener(e -> System.out.println(textField.getText()));
        add(textField, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.getImage(ImageLoader.MENU_BUTTON3), 0, 0, this);
    }
}
