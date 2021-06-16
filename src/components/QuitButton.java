package src.components;

import src.ImageLoader;

public class QuitButton extends MenuButton {

    public QuitButton() {
        super("QUIT GAME", ImageLoader.getImage(ImageLoader.STARTMENU_BTN_BISASAM));
//        setContentAreaFilled(false);
        addActionListener(e -> System.exit(0));
    }
}
