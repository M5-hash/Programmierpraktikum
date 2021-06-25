package src.components;

import src.ImageLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static src.FontLoader.Pokemon;

public class Textfield extends JPanel {

    JTextField textField;

    public Textfield(String text){

        setOpaque(false);
        setLayout(new BorderLayout());
        textField = new JTextField(text);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setOpaque(false);
        textField.setBorder(new LineBorder(Color.black));
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
        g.drawImage(ImageLoader.getImage(ImageLoader.MENU_BUTTON), 0, 0, getWidth(), getHeight(), this);
    }
}
