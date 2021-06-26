package src.components;

import src.ImageLoader;

public class QuitButton extends MenuButton {

    public QuitButton() {
        super("QUIT GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
//        setContentAreaFilled(false);
        addActionListener(e -> System.exit(0));
    }
}
