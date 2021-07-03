package src;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


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
    private Timer t;
    protected boolean first = true;
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

    public void Send(String input) throws Exception {
        if (this.myTurn) {
            TimeUnit.MILLISECONDS.sleep(250);
            this.out.write(String.format("%s%n", input));
            this.out.flush();
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

    public void KillSocket() throws IOException {
        this.s.shutdownOutput();
        System.out.println("Connection closed.");
    }

    public String loopCheckIN(boolean repaint) throws IOException {
        this.loopBreaker = true;
        String hold = "";
        while (this.loopBreaker) {

            if (!in_check(repaint)) break;
            hold = Receive();
        }
        return hold;
    }

    public boolean in_check(boolean repaint) throws IOException {
        this.line = this.in.readLine();
        if (repaint) {
            this.frame.repaint();
            this.frame.tile.repaint();
            this.frame.tile2.repaint();
        }
        if (this.line == null || this.line.equals("")) {
            return false;
        }
        return true;
    }

    public void setSpielwindow(SpielWindow frame) {
        this.frame = frame;
    }

    protected void message_check() throws Exception {
        setTurn(true);
        String in = loopCheckIN(true);
        String[] holder = in.split(" ");
        if (holder[0].equals("shot")) {
            int x = Integer.parseInt(holder[1]);
            int y = Integer.parseInt(holder[2]);

            int hit = pf.isShot(x, y);
            if (hit == 0) {
                Send("answer 0");
                this.myTurn = false;
            } else if (hit == 1) {
                Send("answer 1");
                this.myTurn = false;
            } else if (hit == 2) {
                Send("answer 2");
                if (pf.enemygameover()) {
                    //Win Screen
                    KillSocket();
                }
                this.myTurn = false;
            }
        } else if (holder[0].equals("answer")) {
            if (holder[1].equals("0")) {
                pf.didHit(0, this.lastX, this.lastY);
                Send("pass");
                this.myTurn = false;
            } else if (holder[1].equals("1")) {
                pf.didHit(1, this.lastX, this.lastY);
                timeMyTurn();
            } else if (holder[1].equals("2")) {
                pf.didHit(2, this.lastX, this.lastY);
                if (pf.gameover()) {
                    //hier loose Screen
                }
                timeMyTurn();
            }

        } else if (holder[0].equals("save")) {
            pf.saveGame(Long.parseLong(holder[1]));
        } else if (holder[0].equals("ready")) {
            timeMyTurn();
        } else if (holder[0].equals("pass")) {
            timeMyTurn();
        }
        frame.revalidate();
        frame.tile.revalidate();
        frame.tile2.revalidate();
        frame.repaint();
        frame.tile.repaint();
        frame.tile2.repaint();
    }

    protected void timeMyTurn() {

        ActionListener taskPerformer = evt -> {
            if (!first) {
                myTurn = true;
                first = true;
                tStop();
            }
            first = false;
        };
        t = new javax.swing.Timer(3000, taskPerformer);
        t.start();
    }

    private void tStop() {
        t.stop();
    }

    protected int[] ship_array_toInt(String[] in_ships, int begin) {
        int[] out_ships = new int[in_ships.length - begin];
        for (int i = begin; i < in_ships.length; i++) {
            out_ships[i - begin] = Integer.parseInt(in_ships[i]);
        }
        return out_ships;
    }


}
