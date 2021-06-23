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

    public Com_base(){
        this.port = 50000;
        this.setup = false;
    }

    public void Send(String input) throws IOException {
        this.out.write(String.format("%s%n", input));
        this.out.flush();
    }

    public String Receive() throws IOException{
        System.out.println(this.line);
        return this.line;
    }


    public void KillSocket() throws IOException{
        this.s.shutdownOutput();
        System.out.println("Connection closed.");
    }

    public boolean out_check() throws IOException{
        this.line = this.usr.readLine();
        if (this.line == null || this.line.equals("")){
            return false;
        }
        return true;

    }

    public boolean in_check() throws IOException {
        this.line = this.in.readLine();
        if (this.line == null || this.line.equals("")) {
            return false;
        }
        return true;
    }

    protected void message_check(String in) throws Exception{

        String message = "";
        String [] holder = in.split(" ");
        if(holder[0].equals("shot")){
            int x = Integer.parseInt(holder[1]);
            int y = Integer.parseInt(holder[2]);

            int hit = pf.isShot(x, y);
            if(hit == 0){
                //gui enabler true
            }
            else if(hit == 1 || hit == 2){
                Send("done");
                message_check(Receive());
            }
        }

        else if(holder[0].equals("save")){
            //saveGame
        }

        else if(holder[0].equals("done")){
            //gui enabler true
        }
    }

    protected int[] ship_array(String [] in_ships){
        int [] out_ships = new int[in_ships.length];
        for (int i = 0; i < in_ships.length; i++){
            out_ships[i] = Integer.parseInt(in_ships[i]);
        }
        return out_ships;
    }
}
