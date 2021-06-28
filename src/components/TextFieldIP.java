package src.components;

import src.ImageLoader;
import static src.config.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

import static src.FontLoader.Pokemon;

public class TextFieldIP extends JPanel {

    private static JTextField textField;

    public TextFieldIP(String text){

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
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                System.out.println(textField.getText());
                IP = textField.getText();
            }
        });
        add(textField, BorderLayout.CENTER);
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.getImage(ImageLoader.MENU_BUTTON), 0, 0, getWidth(), getHeight(), this);
    }
}
