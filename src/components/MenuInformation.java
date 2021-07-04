package src.components;

import src.Bildloader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static src.FontLoader.Pokemon;
import static src.config.selectedTheme;

/**
 * Panel mit Informationen des jeweiligen Menü
 */
public class MenuInformation extends JPanel {
    /**
     * Counter für den Text der TextArea
     */
    int j = 0;
    /**
     * Text der TextArea
     */
    String text;
    /**
     * Hintergrundbild des Pokemon Theme
     */
    Image bgImage;

    /**
     * Constraints für das GridbagLayout
     */
    GridBagConstraints constraints;
    /**
     * GridBaglayout der Optionen
     */
    GridBagLayout contentLayout;
    /**
     * TextArea für Informationen
     */
    JTextArea displayText;

    /**
     * Default Werte des Panels und der Textarea
     *
     * @param bgDisplayText Hintergrund des Panels
     * @param text          Text der TextArea
     * @param menuFrame     Frame des Menü um Größe der TextArea zu berechnen
     */
    public MenuInformation(Image bgDisplayText, String text, JFrame menuFrame) {
        this.bgImage = bgDisplayText;
        this.text = text;

        contentLayout = new GridBagLayout();
        contentLayout.columnWidths = new int[]{menuFrame.getWidth() * 15 / 100, menuFrame.getWidth() * 75 / 100, menuFrame.getWidth() / 10};
        contentLayout.rowHeights = new int[]{((menuFrame.getHeight() * 33 / 100) - 10) / 10, ((menuFrame.getHeight() * 33 / 100) - 10) * 8 / 10, ((menuFrame.getHeight() * 33 / 100) - 10) / 10};
        constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 1;

        setLayout(contentLayout);
        setOpaque(false);

        displayText = new JTextArea();
        displayText.setOpaque(false);
        displayText.setForeground(Color.black);
        displayText.setWrapStyleWord(true);
        displayText.setBackground(new Color(248, 248, 248));
        displayText.setEditable(false);
        displayText.setFont(Pokemon);
        displayText.setFocusable(false);
        add(displayText, constraints);

        timer.start();
    }

    /**
     * Timer um Text der Textarea Buchstabe für Buchstabe anzuzeigen
     */
    Timer timer = new Timer(60, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            char[] character = text.toCharArray();
            int arrayNumber = character.length;

            String addedCharacter;
            String blank = "";

            addedCharacter = blank + character[j];
            displayText.append(addedCharacter);

            j++;
            if (j == arrayNumber) {
                j = 0;
                timer.stop();
            }
        }
    });

    /**
     * Update des Hintergrundbilds
     *
     * @param g Übergibt Graphics Objekt
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Bildloader Bild = new Bildloader();
        if (!selectedTheme.equals("Pokemon")) {
            bgImage = Bild.BildLoader("src/Images/captain-iglo.png");
            g.drawImage(bgImage, 0, 0, getWidth() / 5, getHeight() / 2, null);
        } else {
            bgImage = Bild.BildLoader("assets/textfeld_eich1.png");
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
        }

    }
}