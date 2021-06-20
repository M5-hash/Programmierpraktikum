package src.components;

import javax.swing.*;
import java.awt.*;
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
        setBorder(null);
        setFont(Pokemon);
        setIcon(icon);
        setOpaque(false);
        setContentAreaFilled(false);
        setVerticalTextPosition(CENTER);
        setHorizontalTextPosition(CENTER);
        addActionListener(e -> System.out.println(getSize()));
    }

    private Icon resizeIcon() {
//        Image newimg = icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        Icon scaledIcon = new ImageIcon(icon);
//        //setIcon(resizeIcon());
//        return scaledIcon;
        return null;
    }
}
