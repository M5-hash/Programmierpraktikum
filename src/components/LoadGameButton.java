package src.components;

import src.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static src.FontLoader.Pokemon;
import static src.config.*;

/**
 * Button um gespeicherte Spielstände zu laden
 */
public class LoadGameButton extends JButton {

    /**
     * Hintergrundbild des Buttons
     */
    public Image image;

    /**
     * Das PlayingField welches dann anhand der ausgewählten Datei den Spielstand liest
     */
    private PlayingField pf;

    /**
     * Der Computer-Gegner der möglicherweise durch den Spielstand mit geladen wird
     */
    private ComPlayer com;

    /**
     * Gespeicherter Spielstand wird geladen und in einem neuen Spielwindow geöffnet <br>
     * Alter Frame wird geschlossen.
     *
     * @param menuFrame    Frame in den das Spiel geladen wird
     * @param menuPanel    menuPanel des aktuellen Windows
     * @param button_title Text des Buttons
     * @param image        Hintergrundbild
     */
    public LoadGameButton(JFrame menuFrame, JPanel menuPanel, String button_title, BufferedImage image) {
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
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                if (!selectedFile.exists()) return;

                try {
                    if (GameMode) {
                        filepath = selectedFile.getAbsolutePath();
                        this.loadGameMultiplayer(menuFrame, menuPanel);
                    } else {
                        this.loadGameSingleplayer(selectedFile.getAbsolutePath());
                        menuPanel.setVisible(false);
                        menuFrame.dispose();
                        new SpielWindow(menuFrame, this.pf, this.com);
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

    /**
     * Update des Hintergrundbilds
     *
     * @param g Übergibt Graphics Objekt
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (selectedTheme.equals("Pokemon")) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } else {
            setForeground(Color.white);
            Bildloader Bild = new Bildloader();
            BufferedImage NavalButton = Bild.BildLoader("src/Images/NavalButton.png");
            g.drawImage(NavalButton, 0, 0, getWidth(), getHeight(), null);
        }
        super.paintComponent(g);
    }

    /**
     * Lädt Multiplayerspielstand
     *
     * @param menuFrame Frame in den das Spiel geladen wird
     * @param menuPanel menuPanel des aktuellen Windows
     */
    private void loadGameMultiplayer(JFrame menuFrame, JPanel menuPanel) {
        onlineCom = false;
        menuPanel.setVisible(false);
        menuFrame.dispose();
        // Create SpielWindow and display it
        try {
            SpielFeld1 = 0;
            SpielFeld2 = 2;
            Server server = new Server(fieldsize, null);
            server.setSpielwindow(new SpielWindow(menuFrame, server));
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Lädt Singleplayer Spielstand
     *
     * @param file Speicherdatei
     * @throws Exception Probleme beim Laden der Datei
     */
    private void loadGameSingleplayer(String file) throws Exception {
        File f = new File(file);

        //com Wert ermitteln
        Scanner s = new Scanner(f);
        int com = s.nextInt();
        s.close();

        //SchiffeVersenkenHSAalenSaves-Ordner überprüfen
        String fpath = System.getProperty("java.io.tmpdir") + File.separator + "SchiffeVersenkenHSAalenSaves";
        File directory = new File(fpath);
        if (!directory.exists()) throw new FileNotFoundException("Temp-Ordner existiert nicht");
        String[] fileArr = file.split(Pattern.quote(System.getProperty("file.separator")));
        String filename = fileArr[fileArr.length - 1];

        //Spieler PF
        this.pf = new PlayingField();
        this.pf.loadGame(file);

        String fileCom = fpath + File.separator + this.pf.getTimestamp() + filename;

        if (com == 1) this.com = new ComPlayerEasy(fileCom);
        else this.com = new ComPlayerNormal(fileCom);
    }
}
