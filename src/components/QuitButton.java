package src.components;

import src.ImageLoader;

import java.awt.*;
import java.io.IOException;

public class QuitButton extends MenuButton {

    public QuitButton() throws IOException, FontFormatException {
        super("QUIT GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
//        setContentAreaFilled(false);
        addActionListener(e -> System.exit(0));
    }
}
