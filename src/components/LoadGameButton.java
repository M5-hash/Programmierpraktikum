package src.components;

import src.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static src.FontLoader.Pokemon;

public class LoadGameButton extends JButton {

    public Image image;

    public LoadGameButton(JFrame menuFrame, String button_title, BufferedImage image, boolean multiplayer) {
        this.image = image;

        setText(button_title);
        setBorder(new LineBorder(Color.darkGray));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(Pokemon);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setVerticalTextPosition(CENTER);
        setHorizontalTextPosition(CENTER);
        addActionListener(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                try {
                    if (multiplayer) {
                        //TODO this.loadGameMultiplayer(selectedFile.getAbsolutePath());
                    } else {
                        this.loadGameSingleplayer(selectedFile.getAbsolutePath());
                    }
                } catch (Exception fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        addActionListener(e -> {
            System.out.println("------------------");
            System.out.println(button_title);
            System.out.println("Button Height: " + getHeight());
            System.out.println("Button Width: " + getWidth());
            System.out.println("X Position: " + getX());
            System.out.println("Y Position: " + getY());
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        super.paintComponent(g);
    }

    /**
     * Lädt Singleplayer Spielstand
     *
     * @param file Speicherdatei
     * @throws Exception Probleme beim Laden der Datei
     */
    private void loadGameSingleplayer(String file) throws Exception {
        File f = new File(file);
        if (!f.exists()) throw new FileNotFoundException();
        //com Wert ermitteln
        Scanner s = new Scanner(f);
        int com = s.nextInt();
        s.close();

        //SchiffeVersenkenHSAalenSaves-Ordner überprüfen
        String fpath = System.getProperty("java.io.tmpdir") + File.separator + "SchiffeVersenkenHSAalenSaves";
        File directory = new File(fpath);
        if (!directory.exists()) throw new FileNotFoundException("Temp-Ordner existiert nicht");
        String[] fileArr = file.split(File.separator);
        String filename = fileArr[fileArr.length - 1];

        //Spieler PF
        PlayingField pf = new PlayingField();
        pf.loadGame(file);

        String fileCom = fpath + File.separator + pf.getTimestamp() + filename;

        ComPlayer c;
        if (com == 1) c = new ComPlayerEasy(fileCom);
        else c = new ComPlayerNormal(fileCom);

        //pf, c
        //TODO Spiel starten mit Daten, this.startGame
    }
}
