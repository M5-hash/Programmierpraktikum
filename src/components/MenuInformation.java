package src.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static src.FontLoader.Pokemon;
import static src.config.INITIAL_HEIGHT;

public class MenuInformation extends JPanel {
    int j = 0;
    String text;

    Image bgImage;

    GridBagLayout contentLayout;
    GridBagConstraints constraints;
    JTextArea displayText;

    public MenuInformation(Image bgDisplayText, String text, JFrame menuFrame){
        this.bgImage = bgDisplayText;
        this.text = text;

        System.out.println(getHeight());


        contentLayout = new GridBagLayout();
        contentLayout.columnWidths = new int[]{menuFrame.getWidth() * 15 / 100, menuFrame.getWidth() * 75 / 100, menuFrame.getWidth() / 10};
        contentLayout.rowHeights = new int[]{((INITIAL_HEIGHT * 33 / 100) - 10) / 10, ((INITIAL_HEIGHT * 33 / 100) - 10) * 8 / 10, ((INITIAL_HEIGHT * 33 / 100) - 10) / 10};
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
        displayText.setBackground(new Color(248,248,248));
        displayText.setEditable(false);
        displayText.setFont(Pokemon);
        add(displayText, constraints);

        timer.start();
    }

    Timer timer = new Timer(80, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            char[] character = text.toCharArray();
            int arrayNumber = character.length;

            String addedCharacter;
            String blank = "";

            addedCharacter = blank + character[j];
            displayText.append(addedCharacter);

            j++;
            if(j == arrayNumber){
                j = 0;
                timer.stop();
            }

        }
    });

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0,0, getWidth(), getHeight(), null);
    }
}
