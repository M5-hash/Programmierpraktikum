package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;

public abstract class Com_base {


    protected final int port;
    protected Socket s;
    protected BufferedReader in;
    protected Writer out;
    protected BufferedReader usr;
    protected String line;
    protected boolean setup;
    public PlayingField pf;
    public boolean myTurn;
    protected int lastX;
    protected int lastY;

    public Com_base(){
        this.port = 50000;
        this.setup = false;
    }

    public void setTurn(boolean in){
        this.myTurn = in;
    }

    public void setXY(int x, int y){
        this.lastX = x;
        this.lastY = y;
    }

    public void Send(String input) throws IOException {
        this.out.write(String.format("%s%n", input));
        this.out.flush();
    }

    public String Receive(){
        System.out.println(this.line);
        return this.line;
    }


    public void KillSocket() throws IOException{
        this.s.shutdownOutput();
        System.out.println("Connection closed.");
    }

    public boolean out_check() throws IOException{
        this.line = this.usr.readLine();
        return this.line != null && !this.line.equals("");
    }



    public boolean in_check() throws IOException {
        this.line = this.in.readLine();
        if (this.line == null || this.line.equals("")) {
            return false;
        }
        return true;
    }

    protected void message_check(String in) throws Exception{

        String [] holder = in.split(" ");
        if(holder[0].equals("shot")){
            int x = Integer.parseInt(holder[1]);
            int y = Integer.parseInt(holder[2]);

            int hit = pf.isShot(x, y);
            if(hit == 0){
                Send("answer 0");
            }
            else if(hit == 1){
                Send("answer 1");
            }
            else if(hit == 2){
                Send("answer 2");
            }
        }

        else if(holder[0].equals("answer")){
            if(holder[1].equals("0")){
                pf.didHit(0, this.lastX, this.lastY);
                Send("pass");
            }
            else if(holder[1].equals("1")){
                pf.didHit(1, this.lastX, this.lastY);
                this.myTurn = true;
            } else if (holder[1].equals("2")){
                pf.didHit(2, this.lastX, this.lastY);
                this.myTurn = true;
            }
        }

        else if(holder[0].equals("save")){
            //saveGame
        }

        else if(holder[0].equals("pass")){
            this.myTurn = true;
        }
    }

    protected void run() throws Exception{
        while(true){
            if(this.in_check() == true){
                this.message_check(this.line);
            }
        }
    }

    protected int[] ship_array(String[] in_ships){
        int [] out_ships = new int[in_ships.length];
        for (int i = 0; i < in_ships.length; i++){
            out_ships[i] = Integer.parseInt(in_ships[i]);
        }
        return out_ships;
    }
}
