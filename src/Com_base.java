package src;

import javax.swing.*;
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
    protected boolean role_server;

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
    protected Socket s;

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
     *
     */
    private SpielWindow frame;




    public Com_base() {
        this.port = 50000;
        this.loopBreaker = false;
        this.loaded = false;
        this.SocketActive = false;
    }

    public void setTurn(boolean in) {
        this.myTurn = in;
    }

    public void setXY(int x, int y) {
        this.lastX = x;
        this.lastY = y;
    }

    public void setLastX(int val){
        this.lastX = val;
    }

    public void setLastY(int val){
        this.lastY = val;
    }

    public PlayingField getPf(){
        return this.pf;
    }

    public ComPlayer getComPl(){
        return this.comPl;
    }
    public boolean isMyTurn(){
        return this.myTurn;
    }

    public void Send(String input){
        if (this.myTurn) {
            try {
                this.out.write(String.format("%s%n", input));
                try {
                    this.out.flush();
                } catch (SocketException a) {
                    System.out.println("Shutdown");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.myTurn = false;
    }

    public boolean getLoaded(){
        return this.loaded;
    }



    public String Receive() {
        System.out.println(this.line);
        this.loopBreaker = false;
        return this.line;
    }

    public void KillSocket() {
        try {
            this.SocketActive = false;
            this.s.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loopCheckIN(boolean repaint){
        this.loopBreaker = true;
        String hold = "";
        while (this.loopBreaker) {
            if (repaint) {
                //this.frame.repaint();
                //this.frame.tile.repaint();
                //this.frame.tile2.repaint();
            }
            if (!in_check()) break;
            hold = Receive();
        }
        System.out.println(hold);
        return hold;
    }

    public boolean in_check(){
        try {
            if(this.SocketActive) {
                try {
                    this.line = this.in.readLine();
                }catch(SocketException i){
                    this.SocketActive = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.line != null && !this.line.equals("");
    }

    /**
     * Setzt den Parameter frame
     * @param frame value für this.frame
     */
    public void setSpielwindow(SpielWindow frame) {
        this.frame = frame;
    }


    protected void message_check() throws Exception {
        frame.Turn.switchTurn(false);
        String in = loopCheckIN(true);
        String[] holder = in.split(" ");
        switch (holder[0]) {
            case "shot":
                int x = Integer.parseInt(holder[1]);
                int y = Integer.parseInt(holder[2]);

                int hit = pf.isShot(x, y);
                if (hit == 0) {
                    setTurn(true);
                    Send("answer 0");

                } else if (hit == 1) {
                    setTurn(true);
                    Send("answer 1");

                } else if (hit == 2) {
                    setTurn(true);
                    Send("answer 2");
                    if (pf.enemygameover()) {
                        Send("pass");
                        KillSocket();
                    }
                }
                break;
            case "answer":
                switch (holder[1]) {
                    case "0" -> {
                        pf.didHit(0, this.lastX, this.lastY);
                        setTurn(true);
                        Send("pass");
                    }
                    case "1" -> {
                        pf.didHit(1, this.lastX, this.lastY);
                        myTurn = true;
                    }
                    case "2" -> {
                        pf.didHit(2, this.lastX, this.lastY);
                        if (pf.gameover()) {
                            myTurn = false;
                        }
                        myTurn = true;
                    }
                }

                break;
            case "save":
                if (config.onlineCom) {
                    this.comPl.saveGame(Long.parseLong(holder[1]));
                } else {
                    pf.saveGame(Long.parseLong(holder[1]));
                }
                KillSocket();
                break;
            case "ready":
            case "pass":
                myTurn = true;
                break;
        }

        frame.Turn.switchTurn(myTurn);
    }



    protected int[] ship_array_toInt(String[] in_ships, int begin) {
        int[] out_ships = new int[in_ships.length - begin];
        for (int i = begin; i < in_ships.length; i++) {
            out_ships[i - begin] = Integer.parseInt(in_ships[i]);
        }
        return out_ships;
    }


}
