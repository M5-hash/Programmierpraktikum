package src.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import static src.FontLoader.Pokemon;

public class DeleteButton extends JButton {

    Image background1;
    Image background2;
    static boolean deleting = false;

    public DeleteButton(Image background1, Image background2) {
        this.background1 = background1;
        this.background2 = background2;

        setText("DELETE");
        setBorder(new LineBorder(Color.darkGray));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(Pokemon);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setVerticalTextPosition(CENTER);
        setHorizontalTextPosition(CENTER);
        addActionListener(e -> {
            deleting = !deleting;
            if (deleting) {
                setText("PLACE");
            } else {
                setText("DELETE");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (deleting) {
            g.drawImage(background1, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.drawImage(background2, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
