package src.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import static src.FontLoader.Pokemon;

public class ToggleButton extends JToggleButton {

    public Image image;
    public Image selected;
    public Image icon;

    public ToggleButton(String button_title, Image image, Image selected, Image icon) {
        super();
        this.image = image;
        this.icon = icon;
        this.selected = selected;

        setText(button_title);
        setBorder(new LineBorder(Color.darkGray));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(Pokemon);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setVerticalTextPosition(CENTER);
        setHorizontalTextPosition(RIGHT);
        setIcon(new ImageIcon(icon));
        addActionListener(e -> {
            System.out.println("------------------");
            System.out.println(button_title);
            System.out.println("Button Height: " + getHeight());
            System.out.println("Button Width: " + getWidth());
            System.out.println("X Position: " + getX());
            System.out.println("Y Position: " + getY());
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(isSelected()){
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.drawImage(selected, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}