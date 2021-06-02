package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

public class OptionsMenu extends JFrame {

    URL bgURL = new URL("https://i.pinimg.com/originals/66/ec/4d/66ec4d8d51c359ed601cb3d00807e99a.gif");
    Icon bgImage = new ImageIcon(bgURL);

    // The size of the menus
    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 480;

    // Margins
    int MARGIN_WIDTH = DEFAULT_WIDTH / 3;
    int MARGIN_HEIGHT = DEFAULT_HEIGHT / 8;

    // The cursor
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    String[] resolutionsStrings = {"2560x1440","1920x1200","1920x1080","1680x1050","1440x900","1366x768","1280x720","1280x800","1024x768","800x600","640x480"};
    String selectedResolutions = "";
    private DefaultListCellRenderer listRenderer;

    private final MainMenu mainMenu;

    public OptionsMenu(MainMenu mainMenu) throws MalformedURLException {
        super("Battleships 0.1");

        // Make this frame dispose upon closing
        setIconImage(SpriteManager.getSprite(SpriteManager.SHIP_ICON));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setCursor(cursor);

        // Save the main menu to return to it once game is over
        this.mainMenu = mainMenu;

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,640,480);

        JLabel bgLabel = new JLabel(bgImage);
        bgLabel.setBounds(0,0,640,480);

        JPanel buttonsPanel = new JPanel();
        GridLayout buttonsLayout = new GridLayout(0,1);
        buttonsLayout.setVgap(10);
        buttonsPanel.setLayout(buttonsLayout);
        buttonsPanel.setBounds(213,40,213, 360);
        buttonsPanel.setOpaque(false);

        JButton mMButton = new JButton("Main Menu");
        mMButton.addActionListener(new Reset());
        buttonsPanel.add(mMButton);

        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

        JComboBox resolutionsDD = new JComboBox(resolutionsStrings);
        resolutionsDD.setSelectedIndex(2);
        resolutionsDD.setRenderer(listRenderer);
        resolutionsDD.setAlignmentX(CENTER_ALIGNMENT);
        selectedResolutions = (String) resolutionsDD.getSelectedItem();
        buttonsPanel.add(resolutionsDD);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> {
            // Quit the program
            System.exit(0);
        });
        buttonsPanel.add(quitButton);

        layeredPane.add(buttonsPanel);
        layeredPane.add(bgLabel);
        add(layeredPane);
    }

    private class Reset implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Hide this window
            setVisible(false);

            // Create mainMenu and display it
            mainMenu.setVisible(true);
        }
    }

    public String getResolution(String selectedResolution){
        return selectedResolution;
    }
}

