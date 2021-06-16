package src.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInformation extends JPanel {
    int j = 0;
    String[] text;

    Image bgImage;

    GridBagLayout contentLayout;
    GridBagConstraints constraints;
    JTextArea displayText;

    public MenuInformation(Image bgDisplayText, String[] text, int top, int left, int bottom, int right){
        this.bgImage = bgDisplayText;
        this.text = text;

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
        setBackground(Color.green);
        setLayout(contentLayout);
//        setOpaque(false);

        displayText = new JTextArea();
        displayText.setForeground(Color.black);
        displayText.setWrapStyleWord(true);
        displayText.setBackground(new Color(248,248,248));
        displayText.setEditable(false);

        timer.start();
        add(displayText, constraints);
    }

    Timer timer = new Timer(150, new ActionListener() {
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
        g.drawImage(bgImage, 0,0,null);
    }
}
