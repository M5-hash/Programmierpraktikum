package src.components;

import src.SpielWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Pattern;

import static src.FontLoader.Pokemon;
import static src.config.GameMode;

public class SaveGameButton extends JButton {

    public Image image;

    public SaveGameButton(String button_title, BufferedImage image, SpielWindow sw) {
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
            jfc.setDialogTitle("Choose a directory to save your file: ");
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setDialogTitle("Sava a File");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
            jfc.setFileFilter(filter);

            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    //Spieler
                    long ID = sw.getPlayingField().saveGame(jfc.getSelectedFile().toString());

                    if (GameMode) {//Multiplayer
                        sw.getServer().Send("save " + ID);
                    } else {//Singleplayer
                        //Computer
                        String path = jfc.getSelectedFile().getAbsolutePath();
                        String[] patharr = path.split(Pattern.quote(System.getProperty("file.separator")));
                        String fname = patharr[patharr.length - 1];
                        //SchiffeVersenkenHSAalenSaves-Ordner überprüfen
                        String fpath = System.getProperty("java.io.tmpdir") + File.separator + "SchiffeVersenkenHSAalenSaves";
                        File directory = new File(fpath);
                        if (!directory.exists())
                            if (!directory.mkdir()) throw new Exception("Temp-Ordner existiert nicht");
                        sw.getCom().saveGame(fpath + File.separator + sw.getPlayingField().getTimestamp() + fname);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
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
}
