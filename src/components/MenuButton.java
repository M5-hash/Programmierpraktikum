package src.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static src.FontLoader.Pokemon;

public class MenuButton extends JButton {


    public Image image;
    public Icon icon;

    public MenuButton(String button_title, Image image) throws IOException, FontFormatException {
        super();

        this.image = image;
        icon = new ImageIcon(image);
        setText(button_title);
        setBorder(new LineBorder(Color.darkGray));
        setFont(Pokemon);
//        setIcon(icon);
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setVerticalTextPosition(CENTER);
        setHorizontalTextPosition(CENTER);
        addActionListener(e -> {
            System.out.println("-------------------");
            System.out.println(button_title);
            System.out.println("Höhe des Buttons beträgt: " + getHeight());
            System.out.println("Breite des Buttons beträgt: " + getWidth());
            System.out.println("X Position: " + getX());
            System.out.println("Y Position: " + getY());
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        super.paintComponent(g);
    }
}
