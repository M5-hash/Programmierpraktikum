package src;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * Einrichtung der Server-Schnittstelle und Aufsetzen von Kommunikation und Spielfeld
 * Enthält alle notwendige Daten für die Netzwerk-Kommunikation als Server
 */
public class Server extends Com_base {

    /**
     * Erwartet Verbindungsversuch durch einen Socket und etabliert die Socket-Verbindung
     */
    private ServerSocket serverSocket;

    /**
     * Vermerkt true in RoleServer
     * Wartet auf eine Socket-Verbindung und baut diese auf
     * Vermerkt den Socket als aktiv mit true in SocketActive
     * Richtet Reader und Writer für einlesen des Input- und schreiben in den Output-Stream ein
     * Setzt das Playingfield auf
     * Falls die KI spielen soll wird comPl erstellt
     * Bei Problemen beim Aufsetzen von Socket oder Serversocket, wird 10 Sekunden lang sekündlich erneut versucht eine
     * Verbindung herzustellen, ist dies erfolglos, wird eine Fehlermeldung ausgegeben und das Programm wird nach
     * kurzer Zeit von selbst geschlossen
     *
     * @param fieldSize      Größe des Spielfelds
     * @param availableShips String der Schiffsgrößen getrennt durch Leerzeichen enthält
     */
    public Server(int fieldSize, String availableShips) {
        super();
        this.RoleServer = true;

        try {
            this.serverSocket = new ServerSocket(this.port);
            this.socket = this.serverSocket.accept();
            this.SocketActive = true;
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new OutputStreamWriter(this.socket.getOutputStream());
            this.pf = setupPlayingfield(fieldSize, availableShips);
            if (config.onlineCom && this.comPl == null) {
                this.comPl = new ComPlayerNormal(this.pf);
            }
            config.fieldsize = this.pf.getField().length;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection konnte nicht hergestellt werden");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.exit(1);
        }
    }

    /**
     * Setzt das Playingfield des neuen oder geladenen Spiels auf
     * Sendet entsprechende Befehle an den Client, damit dieser sein eigenes Playingfield aufsetzen kann
     * <p>
     * Bei Problemen beim Aufsetzen von Spielfeld oder ComPlayer, wird eine Fehlermeldung ausgegeben und das Spiel nach
     * kurzer Zeit beendet
     *
     * @param fieldSize      Größe des Spielfelds
     * @param availableShips String der Schiffsgrößen getrennt durch Leerzeichen enthält
     * @return gibt ein geladenes oder neu erstelltes Playingfield zurück
     */
    protected PlayingField setupPlayingfield(int fieldSize, String availableShips) {
        PlayingField pf_holder = null;
        try {
            if (!config.filepath.equals("")) {
                if (config.onlineCom) {
                    this.comPl = new ComPlayerNormal(config.filepath);
                    pf_holder = this.comPl.getPlayingField();
                } else {
                    pf_holder = new PlayingField();
                    pf_holder.loadGame(config.filepath);
                }
                TimeUnit.MILLISECONDS.sleep(100);
                this.loaded = true;
                setMyTurn(true);
                this.Send("load " + pf_holder.getFilenameLongID(config.filepath));
                setMyTurn(true);
                if (ReceiveCheckedInputStream().equals("done")) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    setMyTurn(true);
                    Send("ready");
                }
            } else {
                pf_holder = new PlayingField(fieldSize, ParseStringArrayToIntArray(availableShips.split(" "), 0), RoleServer);
                TimeUnit.MILLISECONDS.sleep(100);

                setMyTurn(true);
                Send("size " + fieldSize);

                if (ReceiveCheckedInputStream().equals("done")) {

                    setMyTurn(true);
                    Send("ships " + availableShips);
                }


                if (ReceiveCheckedInputStream().equals("done")) {

                    setMyTurn(true);
                    Send("ready");
                }
                if (ReceiveCheckedInputStream().equals("ready")) ;


                this.myTurn = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Es gab ein Fehler! Bitte neustarten.");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.exit(1);
        }

        return pf_holder;
    }
}