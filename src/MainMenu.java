package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;


public class MainMenu extends JFrame {

    URL bgURL = new URL("https://i.pinimg.com/originals/66/ec/4d/66ec4d8d51c359ed601cb3d00807e99a.gif");
    Icon bgImage = new ImageIcon(bgURL);

    // The size of the menus
    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 480;

    // The cursor
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);


    public MainMenu() throws MalformedURLException {
        super("Battleships 0.1");

        // Configure JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(SpriteManager.getSprite(SpriteManager.SHIP_ICON));
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setCursor(cursor);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 640, 480);

        JLabel bgLabel = new JLabel(bgImage);
        bgLabel.setBounds(0, 0, 640, 480);

        JPanel buttonsPanel = new JPanel();
        GridLayout buttonsLayout = new GridLayout(0, 1);
        buttonsLayout.setVgap(10);
        buttonsPanel.setLayout(buttonsLayout);
        buttonsPanel.setBounds(213, 40, 213, 360);
        buttonsPanel.setOpaque(false);

        JButton spButton = new JButton("Singleplayer");
        spButton.addActionListener(new singlePlayerMenu());
        buttonsPanel.add(spButton);

        JButton mpButton = new JButton("Multiplayer");
        mpButton.addActionListener(new multiPlayerMenu());
        buttonsPanel.add(mpButton);

        JButton optionsButton = new JButton("Options");
        optionsButton.addActionListener(new OptionsMenu());
        buttonsPanel.add(optionsButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Quit the program
                System.exit(0);
            }
        });
        buttonsPanel.add(quitButton);

        layeredPane.add(buttonsPanel);
        layeredPane.add(bgLabel);
        add(layeredPane);
    }

    private class singlePlayerMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Hide this window
            setVisible(false);
            dispose();
            // Create new Singleplayer Menu
            try {
                new SPMenu(MainMenu.this).setVisible(true);
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
        }
    }

    private class multiPlayerMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Hide this window
            setVisible(false);
            dispose();
            // Create new Multiplayer Menu
            try {
                new MPMenu(MainMenu.this).setVisible(true);
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
        }
    }

    private class OptionsMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Hide this window
            setVisible(false);
            dispose();
            // Create new Options Menu
            try {
                new src.OptionsMenu(MainMenu.this).setVisible(true);
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
        }
    }
}
