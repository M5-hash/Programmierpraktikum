package src;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Server extends Com_base {


    private ServerSocket ss;


    public Server(String start_mode, int in_size, String in_ships) throws Exception{

        super();
        this.role_server = true;
        this.ss = new ServerSocket(this.port);


        this.s = this.ss.accept();

        this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.out = new OutputStreamWriter(this.s.getOutputStream());
        this.usr = new BufferedReader(new InputStreamReader(System.in));
        this.pf = setupPlayingfield(start_mode, in_size, in_ships);
    }

    protected PlayingField setupPlayingfield(String start_mode, int in_size, String in_ships) throws IOException{
        PlayingField pf_holder;
        if(start_mode.equals("setup")){

            pf_holder = new PlayingField(in_size, ship_array_toInt(in_ships.split(" "), 0), role_server);
            while (!this.out_check()){ }
            Send("size "+ in_size);

            while (!this.in_check()){}
            if(Receive().equals("done")){

                while (!this.out_check()){}
                Send("ships " + in_ships);
            }

            while (!this.in_check()){}
            if(Receive().equals("done")){

                while (!this.out_check()){}
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
