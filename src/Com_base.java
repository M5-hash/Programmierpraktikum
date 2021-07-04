package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;


public abstract class Com_base {
    protected ComPlayer comPl;
    protected final int port;
    protected Socket s;
    protected BufferedReader in;
    protected Writer out;
    protected String line;
    protected boolean setup;
    public PlayingField pf;
    public boolean myTurn;
    protected int lastX;
    protected int lastY;
    protected boolean role_server;
    protected boolean loopBreaker;
    private SpielWindow frame;
    protected boolean loaded;


    public Com_base() {
        this.port = 50000;
        this.setup = false;
        this.loopBreaker = false;
        this.loaded = false;
    }

    public void setTurn(boolean in) {
        this.myTurn = in;
    }

    public void setXY(int x, int y) {
        this.lastX = x;
        this.lastY = y;
    }

    public ComPlayer getComPl(){
        return this.comPl;
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
                this.frame.repaint();
                this.frame.tile.repaint();
                this.frame.tile2.repaint();
            }
            if (!in_check()) break;
            hold = Receive();
        }
        System.out.println(hold);
        return hold;
    }

    public boolean in_check(){
        try {
            this.line = this.in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (this.line == null || this.line.equals("")) {
            return false;
        }
        return true;
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
        if (holder[0].equals("shot")) {
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
        } else if (holder[0].equals("answer")) {
            if (holder[1].equals("0")) {
                pf.didHit(0, this.lastX, this.lastY);
                setTurn(true);
                Send("pass");

            } else if (holder[1].equals("1")) {
                pf.didHit(1, this.lastX, this.lastY);
                myTurn = true;
            } else if (holder[1].equals("2")) {
                pf.didHit(2, this.lastX, this.lastY);
                if (pf.gameover()) {
                    myTurn = false;
                }
                myTurn = true;
            }

        } else if (holder[0].equals("save")) {
            pf.saveGame(Long.parseLong(holder[1]));
        } else if (holder[0].equals("ready")) {
            myTurn = true;
        } else if (holder[0].equals("pass")) {
            myTurn = true;
        }

        if(myTurn){
            frame.Turn.switchTurn(true);
        }
        else{
            frame.Turn.switchTurn(false);
        }
    }



    protected int[] ship_array_toInt(String[] in_ships, int begin) {
        int[] out_ships = new int[in_ships.length - begin];
        for (int i = begin; i < in_ships.length; i++) {
            out_ships[i - begin] = Integer.parseInt(in_ships[i]);
        }
        return out_ships;
    }


}
