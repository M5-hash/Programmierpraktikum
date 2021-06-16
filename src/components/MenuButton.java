package src.components;

import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {

    public static Font Pokemon;
    public Image icon;

    public MenuButton(String button_title, Image icon) {
        super();

        this.icon = icon;

        setText(button_title);
        setBorder(null);
        setFont(Pokemon);
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(CENTER);
        setIcon(resizeIcon());
        //setIcon(icon);
        addActionListener(e -> System.out.println(getSize()));

//        try{
//            File fontfile = new File("pokemon-font.ttf");
//            if(fontfile.exists()){
//                Pokemon = Font.createFont(Font.TRUETYPE_FONT, fontfile).deriveFont(30f);
//                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//                ge.registerFont(Pokemon);
//                System.out.println("not null");
//            } else {
//                System.out.println("File does not exist");
//            }
//        } catch (IOException | FontFormatException e) {
//        }

    }

    public MenuButton(String button_title) {
        super();

        setText(button_title);
        setBorder(null);
        setFont(Pokemon);
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(CENTER);
        setIcon(resizeIcon());
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
