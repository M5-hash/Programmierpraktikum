package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

public class GameMenu extends JFrame {

    URL bgURL = new URL("https://i.pinimg.com/originals/66/ec/4d/66ec4d8d51c359ed601cb3d00807e99a.gif");
    Icon bgImage = new ImageIcon(bgURL);

    // The size of the frame
    private int DEFAULT_WIDTH = 640; // Sollte größe der ausgewählten Resolution sein
    private int DEFAULT_HEIGHT = 480; // Sollte größe der ausgewählten Resolution sein

    // The size of player battlefields
    private int TILES_LEFT = 10;

    // The cursor
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    private final SPMenu spMenu;

    public GameMenu(SPMenu spMenu, String difficulty) throws MalformedURLException {
        super("Battleship 0.1");

        // Make this frame dispose upon closing
        setIconImage(SpriteManager.getSprite(SpriteManager.SHIP_ICON));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
        setCursor(cursor);

        // Save the main menu to return to it once game is over
        this.spMenu = spMenu ;

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,640,480);

        JPanel bgPanel = new JPanel();
        bgPanel.setBackground(Color.DARK_GRAY);
        bgPanel.setBounds(0,0,640,480);

        JPanel gameplay = new JPanel();
        GridLayout gameplayLayout = new GridLayout(1,0);
        gameplayLayout.setHgap(DEFAULT_WIDTH / 10);
        gameplay.setLayout(gameplayLayout);
        gameplay.setBounds((DEFAULT_WIDTH/10), DEFAULT_HEIGHT/10 , (DEFAULT_WIDTH*8)/10, (DEFAULT_HEIGHT*6/10));
        gameplay.setOpaque(false);

        JPanel gameplay2 = new JPanel();
        gameplay2.setLayout(gameplayLayout);
        gameplay2.setBounds((DEFAULT_WIDTH/10), (DEFAULT_HEIGHT/10) - 30 , (DEFAULT_WIDTH*8)/10, 35);
        gameplay2.setOpaque(false);

        JTextField p1TextField = new JTextField("Player 1");
        p1TextField.setForeground(Color.WHITE);
        p1TextField.setOpaque(false);
        p1TextField.setBorder(null);
        gameplay2.add(p1TextField);

        JTextField p1TextField2 = new JTextField("Tiles Left: " + TILES_LEFT);
        p1TextField2.setForeground(Color.WHITE);
        p1TextField2.setOpaque(false);
        p1TextField2.setHorizontalAlignment(JTextField.RIGHT);
        p1TextField2.setBorder(null);
        gameplay2.add(p1TextField2);

        JTextField p2TextField = new JTextField("Player 2");
        p2TextField.setForeground(Color.WHITE);
        p2TextField.setOpaque(false);
        p2TextField.setHorizontalAlignment(JTextField.RIGHT);
        p2TextField.setBorder(null);
        gameplay2.add(p2TextField);

        JTextField p2TextField2 = new JTextField("Tiles Left: " + TILES_LEFT);
        p2TextField2.setForeground(Color.WHITE);
        p2TextField2.setOpaque(false);
        p2TextField2.setBorder(null);
        gameplay2.add(p2TextField2);

        JPanel p1Panel = new JPanel();
        JLabel p1Label = new JLabel(bgImage);
        p1Panel.add(p1Label);
        gameplay.add(p1Panel);

        JPanel p2Panel = new JPanel();
        JLabel p2Label = new JLabel(bgImage);
        p2Panel.add(p2Label);
        gameplay.add(p2Panel);

        JPanel buttonsPanel = new JPanel();
        GridLayout buttonLayout = new GridLayout();
        buttonLayout.setHgap(10);
        buttonsPanel.setLayout(buttonLayout);
        buttonsPanel.setBounds((DEFAULT_WIDTH/10), ((DEFAULT_HEIGHT*7)/10) + 20 , (DEFAULT_WIDTH * 3)/10, 20);
        buttonsPanel.setOpaque(false);

        JPanel pausePanel = new JPanel();
        GridLayout pauseLayout = new GridLayout(0,1);
        pauseLayout.setVgap(10);
        pausePanel.setLayout(pauseLayout);
        pausePanel.setBounds(DEFAULT_WIDTH/3, (DEFAULT_HEIGHT/10) - 20, DEFAULT_WIDTH/3, (DEFAULT_HEIGHT*8)/10);
        pausePanel.setBorder(new EmptyBorder(10,10,10,10));
        pausePanel.setBackground(Color.black);
        pausePanel.setVisible(false);

        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(e -> pausePanel.setVisible(false));
        pausePanel.add(resumeButton);

        JButton optionsButton = new JButton("Options");
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        pausePanel.add(optionsButton);

        JButton mMButton = new JButton("Main Menu");
        mMButton.addActionListener(e -> JOptionPane.showInternalConfirmDialog(null,
                "Wollen Sie wirklich das Spiel verlassen?", "",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE));
        pausePanel.add(mMButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        pausePanel.add(quitButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> pausePanel.setVisible(true));
        buttonsPanel.add(pauseButton);

        JButton logsButton = new JButton("History");
        buttonsPanel.add(logsButton);

        layeredPane.add(pausePanel, Integer.valueOf(2));
        layeredPane.add(buttonsPanel, Integer.valueOf(1));
        layeredPane.add(gameplay, Integer.valueOf(1));
        layeredPane.add(gameplay2, Integer.valueOf(1));
        layeredPane.add(bgPanel);

        add(layeredPane);
    }
}
