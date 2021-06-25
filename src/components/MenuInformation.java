package src.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static src.FontLoader.Pokemon;

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

        setLayout(contentLayout);
        setOpaque(false);

        displayText = new JTextArea();
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
