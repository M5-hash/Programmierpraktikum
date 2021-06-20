package src.components;

import src.MenuShipSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MenuInformation extends JPanel {
    int j = 0;
    String[] text;

    Image bgImage;

    GridBagLayout contentLayout;
    GridBagConstraints constraints;
    JTextArea displayText;

    public MenuInformation(Image bgDisplayText, String[] text, JFrame menuFrame){
        this.bgImage = bgDisplayText;
        this.text = text;

        System.out.println(getHeight());
        int top = menuFrame.getHeight() * 4 / 100;
        int left = menuFrame.getWidth() * 13 / 100;
        int right = menuFrame.getWidth() * 7 / 100;

        contentLayout = new GridBagLayout();
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(top, left, 0, right);
        constraints.weightx = 0.8;
        constraints.weighty = 0.5;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridx = 1;
        constraints.gridy = 1;

        System.out.println(text.length);
        setLayout(contentLayout);
        setOpaque(false);

        displayText = new JTextArea();
        displayText.setForeground(Color.black);
        displayText.setWrapStyleWord(true);
        displayText.setBackground(new Color(248,248,248));
        displayText.setEditable(false);
        displayText.setFont(new Font("PKMN RBYGSC", Font.PLAIN,12));
        add(displayText, constraints);

        timer.start();
    }

    public MenuInformation(BufferedImage image, String[] textSize, MenuShipSize menuShipSize) {

    }

    Timer timer = new Timer(80, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            int i = 0;
            char[] character = text[i].toCharArray();
            int arrayNumber = character.length;

            String addedCharacter;
            String blank = "";

            addedCharacter = blank + character[j];
            displayText.append(addedCharacter);

            j++;
            if(j == arrayNumber){
                j = 0;
                i++;
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
