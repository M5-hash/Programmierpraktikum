package src.components;

import src.ImageLoader;
import src.config;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import static src.FontLoader.Pokemon;

public class CustomCheckBox extends JPanel {

    JCheckBox checkBox;

    public CustomCheckBox(String fullscreen, boolean b, Font Pokemon){
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.black));

        checkBox = new JCheckBox();
        checkBox.setText(fullscreen);
        checkBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkBox.setSelected(b);
        checkBox.setMnemonic(KeyEvent.VK_C);
        checkBox.setHorizontalAlignment((int) JCheckBox.CENTER_ALIGNMENT);
        checkBox.setIconTextGap(5);
        checkBox.setFont(Pokemon);
        checkBox.setOpaque(false);
        checkBox.addItemListener(e -> {
            if(checkBox.isSelected()){
                config.fullscreen = true;
                System.out.println("Fullscreen active");
            } else {
                config.fullscreen = false;
                System.out.println("Fullscreen inactive");
            }
        });
        add(checkBox, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(ImageLoader.getImage(ImageLoader.MENU_BUTTON2), 0, 0, getWidth(), getHeight(), this);
        super.paintComponent(g);
    }
}
