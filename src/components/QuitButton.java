package src.components;

import src.ImageLoader;

/**
 * Quit Game Button
 */
public class QuitButton extends MenuButton {

    /**
     * Erbt Default Werte des MenuButton <br>
     * Schließt das Spiel bei Buttondruck
     */
    public QuitButton() {
        super("QUIT GAME", ImageLoader.getImage(ImageLoader.MENU_BUTTON));
        addActionListener(e -> System.exit(0));
    }
}
