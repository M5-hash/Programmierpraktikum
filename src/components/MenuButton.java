package src.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

import static src.FontLoader.Pokemon;

public class MenuButton extends JButton {


    public Image image;
    public Image icon;
    public Image disabledimage;

    public MenuButton(String button_title, Image image) {
        super();

        this.image = image;
        disabledimage = GrayFilter.createDisabledImage(image);

        setText(button_title);
        setBorder(new LineBorder(Color.darkGray));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(Pokemon);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setVerticalTextPosition(CENTER);
        setHorizontalTextPosition(CENTER);
        addActionListener(e -> {
            System.out.println("------------------");
            System.out.println(button_title);
            System.out.println("Button Height: " + getHeight());
            System.out.println("Button Width: " + getWidth());
            System.out.println("X Position: " + getX());
            System.out.println("Y Position: " + getY());
        });
    }

    public MenuButton(String button_title, BufferedImage image, BufferedImage icon) {
        this.image = image;
        this.icon = icon;
        disabledimage = GrayFilter.createDisabledImage(image);

        setText(button_title);
        setBorder(new LineBorder(Color.darkGray));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setIcon(new ImageIcon(icon));
        setFont(Pokemon);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setVerticalTextPosition(CENTER);
        setHorizontalTextPosition(RIGHT);
        addActionListener(e -> {
            System.out.println("------------------");
            System.out.println(button_title);
            System.out.println("Button Height: " + getHeight());
            System.out.println("Button Width: " + getWidth());
            System.out.println("X Position: " + getX());
            System.out.println("Y Position: " + getY());
            System.out.println("Y Position: " + getIconTextGap());
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        if (isEnabled()) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.drawImage(disabledimage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);

//        if (!(getIcon() == null)) {
//            icon = icon.getScaledInstance(getHeight() - getHeight() * 25 / 100, getHeight() - getHeight() * 25 / 100, Image.SCALE_SMOOTH);
//            setIcon(new ImageIcon(icon));
//            setIconTextGap(getWidth() / 10);
//        }
    }
}
