package src;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Server extends Com_base {


    private ServerSocket ss;


    public Server(String start_mode, int in_size, String in_ships, JFrame menuFrame, boolean KI) throws Exception{

        super();
        this.ss = new ServerSocket(this.port);

        System.out.println("Waiting for client connection ...");
        this.s = this.ss.accept();
        System.out.println("Connection established.");
        this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.out = new OutputStreamWriter(this.s.getOutputStream());
        this.usr = new BufferedReader(new InputStreamReader(System.in));
        this.pf = setupPlayingfield(start_mode, in_size, in_ships);
        new SpielWindow(menuFrame, KI);
        this.run();
    }

    protected PlayingField setupPlayingfield(String start_mode, int in_size, String in_ships) throws IOException{
        PlayingField pf_holder;
        if(start_mode.equals("setup")){
            pf_holder = new PlayingField();
            Send("size "+ in_size);
            if(Receive().equals("done")){
                Send("ships " + in_ships);
            }
            if(Receive().equals("done")){
                Send("ready");
            }
        }

        else{
            pf_holder = new PlayingField();
            pf_holder.loadGame(Long.valueOf(start_mode.split(" ")[1]));
        }

        this.myTurn = true;
        return pf_holder;
    }



    public void ServerCommunicate() throws IOException {
        while (true) {
            if(!out_check()) break;
            Send(" ");

            if(!in_check()) break;
            Receive();
        }
    }
}
