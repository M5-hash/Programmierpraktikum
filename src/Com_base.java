package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;

/**
 * Abstrakte Klasse, welche die gemeinsamen Funktionen und Variablen der Netzwerkschnittstellen enthält
 */
public abstract class Com_base {

    /**
     * Portnummer für die Netzwerkkommunikation
     */
    protected final int port;

    /**
     * Letzter aus dem Input-Stream gelesener String
     */
    private String line;

    /**
     * Speichert die x-Koordinate des letzen abgefeuerten Schusses
     */
    private int lastX;

    /**
     * Speichert die y-Koordinate des letzen abgefeuerten Schusses
     */
    private int lastY;

    /**
     * True, wenn das Objekt die Rolle des Servers besitzt
     * False, wenn das Objekt die Rolle des Clients besitzt
     */
    protected boolean RoleServer;

    /**
     * True, solange kein neuer String empfangen wurde
     * False nach Empfang um while-Schleife zu beenden
     */
    protected boolean loopBreaker;

    /**
     * True, wenn das aktuelle Spiel geladen wurde
     * False, wenn das Spiel neu aufgesetzt wurde
     */
    protected boolean loaded;

    /**
     * True, wenn die Socket-Verbindung eingerichtet wurde
     * False, wenn die Socket-Verbindung inaktiv ist
     */
    protected boolean SocketActive;

    /**
     * True, wenn der Spieler/Computer am Zug ist und senden darf
     * False, wenn der Spieler/Computer nicht senden darf
     */
    protected boolean myTurn;

    /**
     * Socket-Verbindung zwischen Kommunikationspartnern
     */
    protected Socket socket;

    /**
     * Reader der den Input-Stream des Sockets entgegen nimmt
     */
    protected BufferedReader in;

    /**
     * Writer der den Output des Sockets in den Output-Stream schreibt
     */
    protected Writer out;

    /**
     * Computerspieler, falls die KI online spielen soll
     */
    protected ComPlayer comPl;

    /**
     * Playingfield, auf welchem Änderungen durch Netzwerkantworten vorgenommen werden können
     */
    protected PlayingField pf;

    /**
     * SpielWindow für Zugriff auf die switchTurn-Funktion
     */
    private SpielWindow frame;


    /**
     * Konstruktor des Com_base-Objekts
     */
    public Com_base() {
        this.port = 50000;
        this.loopBreaker = false;
        this.loaded = false;
        this.SocketActive = false;
    }


    /**
     * myTurn-Setter
     *
     * @param value neuer Wert für this.myTurn
     */
    public void setMyTurn(boolean value) {
        this.myTurn = value;
    }

    /**
     * Setter für lastX und lastY
     *
     * @param x neuer Wert für this.lastX
     * @param y neuer Wert für this.lastY
     */
    public void setLastXY(int x, int y) {
        this.lastX = x;
        this.lastY = y;
    }

    /**
     * Setzt den Parameter frame
     *
     * @param frame value für this.frame
     */
    public void setSpielwindow(SpielWindow frame) {
        this.frame = frame;
    }

    /**
     * myTurn-Getter
     *
     * @return gibt Wert von this.myTurn zurück
     */
    public boolean getMyTurn() {
        return this.myTurn;
    }

    /**
     * loaded-Getter
     *
     * @return gibt Wert von this.loaded zurück
     */
    public boolean getLoaded() {
        return this.loaded;
    }

    /**
     * pf-Getter
     *
     * @return gibt this.pf zurück
     */
    public PlayingField getPf() {
        return this.pf;
    }

    /**
     * ComPl-Getter
     *
     * @return gibt this.comPl zurück
     */
    public ComPlayer getComPl() {
        return this.comPl;
    }

    /**
     * Beendet die Socketverbindung und vermerkt dies mit false in SocketActive
     */
    public void KillSocket() {
        try {
            this.SocketActive = false;
            this.socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Schreibt den eingebenen String in den Socket und sendet diesen
     *
     * @param input String der gesendet werden soll
     */
    public void Send(String input) {
        if (getMyTurn()) {
            try {
                this.out.write(String.format("%s%n", input));
                try {
                    this.out.flush();
                } catch (SocketException a) {
                    a.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setMyTurn(false);
    }

    /**
     * Setzt loop-Breaker auf falls und gibt den zuletzt eingelesenen String
     * aus dem Input-Stream zurück
     *
     * @return gibt this.line zurück, enthält den zuletzt aus dem Input-Stream gelesenen String zurück
     */
    public String ReceiveInputStream() {
        this.loopBreaker = false;
        return this.line;
    }

    /**
     * Überprüft ob es eine neue Nachricht im Input-Stream gibt
     * Falls die Socket-Verbindung eine Exception wirft, wird dies in SocketActive mit false vermerkt
     *
     * @return true, wenn eine neue Nachricht im Input-Stream enthalten ist
     * false, wenn der Input-Stream keine neue Nachricht beinhaltet
     */
    public boolean checkInputStream() {
        try {
            if (this.SocketActive) {
                try {
                    this.line = this.in.readLine();
                } catch (SocketException i) {
                    this.SocketActive = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.line != null && !this.line.equals("");
    }

    /**
     * Wartet bis eine neue Nachricht im Input-Stream enthalten ist und gibt diese zurück
     *
     * @return gibt den zuletzt aus dem Input-Stream gelesenen String zurück
     */
    public String ReceiveCheckedInputStream() {
        this.loopBreaker = true;
        String message = "";
        while (this.loopBreaker) {
            if (!checkInputStream()) break;
            message = ReceiveInputStream();
        }
        return message;
    }

    /**
     * Wartet auf eine neue Nachricht, wertet diese aus und übernimmt das automatische Antworten falls nötig
     * Sollte sich aus der empfangenen Nachricht ergeben, dass der Spieler wieder am Zug ist, wird this.myTurn auf true
     * gesetzt
     * Es wird geprüft ob das Spiel gewonnen oder verloren wurde
     * Eigene und gegnerische Schüsse werden auf dem PlayingField markiert
     * Spiel wird, bei Aufforderung über das Netzwerk, gespeichert
     * Die Turn-Anzeige wird auf den Wert von this.myTurn gesetzt
     */
    protected void NetworkProtocol() {
        frame.Turn.switchTurn(false);
        String inputString = ReceiveCheckedInputStream();
        String[] message = inputString.split(" ");
        try {
            switch (message[0]) {
                case "shot":
                    int x = Integer.parseInt(message[1]);
                    int y = Integer.parseInt(message[2]);

                    int hit = pf.isShot(x, y);

                    if (hit == 0) {
                        setMyTurn(true);
                        Send("answer 0");

                    } else if (hit == 1) {
                        setMyTurn(true);
                        Send("answer 1");

                    } else if (hit == 2) {
                        setMyTurn(true);
                        Send("answer 2");
                        if (pf.enemygameover()) {
                            Send("pass");
                            KillSocket();
                        }
                    }
                    break;
                case "answer":
                    switch (message[1]) {
                        case "0" -> {
                            pf.didHit(0, this.lastX, this.lastY);
                            setMyTurn(true);
                            Send("pass");
                        }
                        case "1" -> {
                            pf.didHit(1, this.lastX, this.lastY);
                            setMyTurn(true);
                        }
                        case "2" -> {
                            pf.didHit(2, this.lastX, this.lastY);
                            if (pf.gameover()) {
                                setMyTurn(false);
                            }
                            setMyTurn(true);
                        }
                    }

                    break;
                case "save":
                    if (config.onlineCom) {
                        this.comPl.saveGame(Long.parseLong(message[1]));
                    } else {
                        pf.saveGame(Long.parseLong(message[1]));
                    }
                    KillSocket();
                    break;
                case "ready":
                case "pass":
                    setMyTurn(true);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.Turn.switchTurn(getMyTurn());
    }

    /**
     * Konvertiert ein String-Array in ein Integer-Array und entfernt begin-viele erste Einträge
     * Das Entfernen ist enthalten, um bei Netzwerkantworten das Signalwort zu entfernen
     *
     * @param StringArray beinhaltet die Schiffslängen als String-Objekte
     * @param begin       Ab dem wie vielten Element des String-Arrays soll die Konvertierung stattfinden
     * @return gibt ein um begin-viel erste Einträge gekürztes und in Integer konvertiertes Array zurück
     */
    protected int[] ParseStringArrayToIntArray(String[] StringArray, int begin) {
        int[] IntegerArray = new int[StringArray.length - begin];
        for (int i = begin; i < StringArray.length; i++) {
            IntegerArray[i - begin] = Integer.parseInt(StringArray[i]);
        }
        return IntegerArray;
    }
}
