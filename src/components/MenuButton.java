package src.components;

import src.Bildloader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

import static src.FontLoader.Pokemon;
import static src.config.selectedTheme;

public class MenuButton extends JButton {


    public Image image;
    public Image disabledimage;
    String button_title ;

    public MenuButton(String button_title, Image image) {
        super();

        this.image = image;
        disabledimage = GrayFilter.createDisabledImage(image);
        this.button_title = button_title ;
        setText(button_title);

        makecomponent();
    }

    public MenuButton(String button_title, Image image, String ToolTipText) {
        super();

        this.image = image;
        disabledimage = GrayFilter.createDisabledImage(image);
        this.button_title = button_title ;
        setText(button_title);
        setToolTipText(ToolTipText);
        makecomponent();
    }



    private void makecomponent() {
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

    @Override
    protected void paintComponent(Graphics g) {


        if(selectedTheme.equals("Pokemon")){
            if (isEnabled()) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.drawImage(disabledimage, 0, 0, getWidth(), getHeight(), this);
            }
        } else{
            Bildloader Bild = new Bildloader() ;
            if (isEnabled()) {
                setForeground(Color.white);
                BufferedImage NavalButton = Bild.BildLoader("src/Images/NavalButton.png") ;
                g.drawImage(NavalButton, 0,0, getWidth(), getHeight(), null);
            } else {
                BufferedImage NavalButton = Bild.BildLoader("src/Images/RedNavalButton.png") ;
                g.drawImage(NavalButton, 0, 0, getWidth(), getHeight(), this);
            }

        }

        super.paintComponent(g);
    }
}
