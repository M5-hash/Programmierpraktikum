package src.components;

import src.Bildloader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

import static src.FontLoader.Pokemon;
import static src.config.selectedTheme;

/**
 * Custom Button mit Hintergrundbild
 */
public class MenuButton extends JButton {

    /**
     * Hintergrundbild
     */
    public Image image;
    /**
     * Ausgegrautes Hintergrundbild
     */
    public Image disabledimage;
    /**
     * Buttontext
     */
    String button_title;

    /**
     * Default Werte des Custom Button werden gesetzt
     *
     * @param button_title Text des Buttons
     * @param image        Hintergrundbild
     */
    public MenuButton(String button_title, Image image) {
        super();

        this.image = image;
        disabledimage = GrayFilter.createDisabledImage(image);
        this.button_title = button_title;
        setText(button_title);

        makecomponent();
    }

    /**
     * Default Werte des Custom Button werden gesetzt
     *
     * @param button_title Text des Buttons
     * @param image        Hintergrundbilde
     * @param ToolTipText  TooltipText des Buttons
     */
    public MenuButton(String button_title, Image image, String ToolTipText) {
        super();

        this.image = image;
        disabledimage = GrayFilter.createDisabledImage(image);
        this.button_title = button_title;
        setText(button_title);
        setToolTipText(ToolTipText);
        makecomponent();
    }

    /**
     * Default Werte des Buttons
     */
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

    /**
     * Update des Hintergrundbilds
     *
     * @param g Übergibt Graphics Objekt
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (selectedTheme.equals("Pokemon")) {
            if (isEnabled()) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.drawImage(disabledimage, 0, 0, getWidth(), getHeight(), this);
            }
        } else {
            Bildloader Bild = new Bildloader();
            if (isEnabled()) {
                setForeground(Color.white);
                BufferedImage NavalButton = Bild.BildLoader("src/Images/NavalButton.png");
                g.drawImage(NavalButton, 0, 0, getWidth(), getHeight(), null);
            } else {
                BufferedImage NavalButton = Bild.BildLoader("src/Images/RedNavalButton.png");
                g.drawImage(NavalButton, 0, 0, getWidth(), getHeight(), this);
            }
        }
        super.paintComponent(g);
    }
}
