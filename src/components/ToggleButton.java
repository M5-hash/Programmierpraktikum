package src.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static src.FontLoader.Pokemon;

public class ToggleButton extends JToggleButton {

    public Image image;
    public Image selected;
    public Image icon;
    public Image originalIcon;

    public ToggleButton(String button_title, Image image, Image selected, Image icon) {
        super();
        this.image = image;
        this.icon = icon;
        this.originalIcon = icon;
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

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeIcon();
            }
        });
    }

    private void resizeIcon(){
        if (getIcon() != null) {
            icon = originalIcon.getScaledInstance(getHeight() - getHeight() * 25 / 100, getHeight() - getHeight() * 25 / 100, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(icon));
            setIconTextGap(getWidth() / 10);
        }
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