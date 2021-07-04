package src;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Einrichtung der Client-Schnittstelle und Aufsetzen von Kommunikation und Spielfeld
 * Enthält alle notwendige Daten für die Netzwerk-Kommunikation als Client
 */
public class Client extends Com_base {

    /**
     * IP-Adresse des Server-Sockets
     */
    private final String IP;

    /**
     * Vermerkt false in RoleServer
     * Setzt den Werr von this.IP auf inputIP
     * Versucht eine Socketverbindung zum Server-Socket zu etablieren
     * Vermerkt den Socket als aktiv mit true in SocketActive
     * Richtet Reader und Writer für einlesen des Input- und schreiben in den Output-Stream ein
     * Setzt das Playingfield auf
     * Falls die KI spielen soll wird comPl erstellt
     *
     * Bei Problemen beim Aufsetzen von Socket wird 10 Sekunden lang sekündlich erneut versucht eine
     * Verbindung herzustellen, ist dies erfolglos, wird eine Fehlermeldung ausgegeben und das Programm wird nach
     * kurzer Zeit von selbst geschlossen
     *
     * @param inputIP enthält die vom Spieler eingegebene IP
     */
    public Client(String inputIP){
        super();
        this.RoleServer = false;
        this.IP = inputIP;
        try {
            for (int i = 0; i < 10; i++) {
                try {
                    this.socket = new Socket(this.IP, this.port);
                    i = 10;
                } catch (IOException e) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    if (i == 9) {
                        JOptionPane.showMessageDialog(null, "Connection konnte nicht hergestellt werden");
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        System.exit(1);
                    }
                }
            }
            this.SocketActive = true;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new OutputStreamWriter(socket.getOutputStream());
            this.pf = setupPlayingfield();
            if (config.onlineCom && this.comPl == null) {
                this.comPl = new ComPlayerNormal(this.pf);
            }

            config.fieldsize = this.pf.getField().length;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setzt das Playingfield des neuen oder geladenen Spiels auf
     * Playingfield wird entsprechend der empfangenen Daten aufgesetzt oder geladen
     *
     * Bei Problemen beim Aufsetzen von Spielfeld oder ComPlayer, wird eine Fehlermeldung ausgegeben und das Spiel nach
     * kurzer Zeit beendet
     *
     * @return gibt ein geladenes oder neu erstelltes Playingfield zurück
     * @throws FileNotFoundException wird geworfen falls keine Datei zum Laden des Spiels existiert
     */
    protected PlayingField setupPlayingfield() throws FileNotFoundException {
        PlayingField pf_holder = null;
            String[] in_size = ReceiveCheckedInputStream().split(" ");

            if (in_size[0].equals("size")) {
                config.fieldsize = Integer.parseInt(in_size[1]);
                setMyTurn(true);
                Send("done");


                String[] in_ships_Str = ReceiveCheckedInputStream().split(" ");
                int[] ships_int_arr = ParseStringArrayToIntArray(in_ships_Str, 1);
                config.size2 = 0;
                config.size3 = 0;
                config.size4 = 0;
                config.size5 = 0;

                for (int i = 0; i < ships_int_arr.length; i++) {
                    if (ships_int_arr[i] == 2) {
                        config.size2++;
                    } else if (ships_int_arr[i] == 3) {
                        config.size3++;
                    } else if (ships_int_arr[i] == 4) {
                        config.size4++;
                    } else if (ships_int_arr[i] == 5) {
                        config.size5++;
                    }
                }
                pf_holder = new PlayingField(Integer.parseInt(in_size[1]), ships_int_arr, RoleServer);

                setMyTurn(true);
                Send("done");

                if (ReceiveCheckedInputStream().equals("ready")) {

                    setMyTurn(true);
                    Send("ready");
                }
            } else {
                this.loaded = true;
                if (config.onlineCom) {
                    this.comPl = new ComPlayerNormal(Long.parseLong(in_size[1]));
                    pf_holder = this.comPl.getPlayingField();
                } else {
                    pf_holder = new PlayingField();
                    pf_holder.loadGame(Long.parseLong(in_size[1]));
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setMyTurn(true);
                Send("done");
                if (ReceiveCheckedInputStream().equals("ready")) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Send("ready");
                }
            }

        this.myTurn = false;
        return pf_holder;
    }
}
